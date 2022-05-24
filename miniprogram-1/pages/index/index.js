// index.js
// 获取应用实例
const app = getApp()

Page({
  data: {
    marchant_arry: [],
    addressname: "",
    recommendMarchant: {},
    address:""
  },
  onLoad() {
    // wx.showLoading({
    //   // title: '加载中',
    // })
    var that = this;
    // 查询是否有缓存
    wx.getStorage({
      key: 'wxUserInfo',
      success: function (res) {
        app.globalData.userInfo = res.data;
        that.getData();
      },
      fail: function (res) {
        //没有,申请获取
        wx.showModal({
          title: '申请获取用户信息',
          content: '是否授权用户信息',
          success: function (res) {
            if (res.confirm) {
              //wx接口获取头像昵称
              wx.getUserProfile({
                desc: '展示用户信息', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
                success: (res) => {
                  app.globalData.userInfo = res.userInfo;
                  //缓存
                  wx.setStorage({
                    key: 'wxUserInfo',
                    data: res.userInfo,
                    fail: function () {
                      console.log('保存wxUserInfo 失败')
                    }
                  });
                  that.getToken();
                }
              })
            }
          }
        })
      }
    })

    // wx.getStorage({
    //   key: "wxDefaultAddress",
    //   success: function (res) {
    //     app.globalData.address = res.data;
    //     that.setData({
    //       addressname: res.data.addressname + " " + res.data.message,
    //       address:res.data.latitude + "," + res.data.longitude
    //     })
    //     that.getMarchant();
    //     that.getToken();
    //   },
    //   fail:function (res) {
    //     wx.showModal({
    //       title: '无地址信息，无法获取周围商家',
    //       content: '是否选择地址信息',
    //       success:function(res){
    //         if (res.confirm) {
    //           that.chooseaddress();
    //         }
    //       }
    //     })
    //   }
    // });
    //数据渲染
    // this.getMarchant();

    wx.getStorage({
      key: 'wxUserToken',
      success: function (res) {
        app.globalData.token = res.data;
      }
    })
  },
  onShow() {
    var that = this;
    wx.getStorage({
      key: "wxDefaultAddress",
      success: function (res) {
        app.globalData.address = res.data;
        if(res.data.latitude + "," + res.data.longitude != that.data.address){
          that.setData({
            addressname: res.data.addressname + " " + res.data.message,
            address:res.data.latitude + "," + res.data.longitude
          })
          that.onLoad();
        }
      },

    });
    // this.getRecommend();
  },
  getData(){
    var that = this;
    wx.getStorage({
      key: "wxDefaultAddress",
      success: function (res) {
        app.globalData.address = res.data;
        that.setData({
          addressname: res.data.addressname + " " + res.data.message,
          address:res.data.latitude + "," + res.data.longitude
        })
        that.getMarchant();
        that.getRecommend();
      },
      fail:function (res) {
        wx.showModal({
          title: '无地址信息，无法获取周围商家',
          content: '是否选择地址信息',
          success:function(res){
            if (res.confirm) {
              that.chooseaddress();
            }
          }
        })
      }
    });
  },
  getToken() {
    var that = this;
    wx.getStorage({
      key: 'wxUserToken',
      success: function (res) {
        app.globalData.token = res.data;
        that.getData();
      },
      fail: function (res) {
        //没有,申请获取
        //wx接口获取openid
        wx.login({
          success(res) {
            if (res.code) {
              //发起网络请求
              wx.request({
                url: 'http://localhost:8090/customer/getOpenid',
                method: "POST",
                data: {
                  code: res.code
                },
                success: function (res) {
                  app.globalData.token = res.data.data;
                  console.log(app);
                  //缓存
                  wx.setStorage({
                    key: 'wxUserToken',
                    data: res.data.data,
                    fail: function () {
                      console.log('保存wxUserToken失败')
                    }
                  });
                  var info = app.globalData.userInfo;
                  var json = JSON.parse(app.globalData.token);
                  wx.request({
                    url: 'http://localhost:8090/customer/addUser',
                    method: "POST",
                    data: {
                      nikename: info.nickName,
                      sex: info.gender,
                      openid: json.openid
                    },
                    success: function (res) {
                      that.getData();
                    }
                  })
                }
              })
            } else {
              console.log('获取失败' + res.errMsg)
            }
          }
        });
      }
    })
  },
  //获取餐厅数据
  getMarchant() {
    var that = this;
    wx.request({
      url: 'http://localhost:8090/user/getMarchant',
      method: "GET",
      data:{
          address:that.data.address
      },
      success: function (res) {
        var testJson = JSON.parse(res.data.msg);
        that.setData({
          marchant_arry: testJson
        })
        //获取月销量\频分
        for (let i = 0; i < testJson.length; i++) {
          let index = i;
          //获取月销量
          wx.request({
            url: 'http://localhost:8090/order/getSales',
            method: "GET",
            data: {
              method: "marchant",































































































































































































































































































































































































































































































































































































































              
              id: testJson[index].id,
              date: "month"
            },
            success: function (res) {
              // console.log(index,res.data.data);
              testJson[index].sales = res.data.data;
              testJson[index].distance = testJson[index].distance.toFixed(2);
              that.setData({
                marchant_arry: testJson
              })
              wx.hideLoading();
            }
          })
        }
      }
    })
  },
  getMarchantBysearch(data) {
    var search = data.detail.value.search;
    var that = this;
    wx.request({
      url: 'http://localhost:8090/user/getMarchant',
      method: "GET",
      data: {
        search: search
      },
      success: function (res) {
        var testJson = JSON.parse(res.data.msg);
        that.setData({
          marchant_arry: testJson
        })
        // // that.data.marchant_arry = testJson;无法刷新数据
      }
    })
  },
  toDish(e) {
    var marchantName = e.currentTarget.dataset.name;
    wx.navigateTo({
      url: '../dish/dish',
      success: function (res) {
        // 通过eventChannel向被打开页面传送数据
        res.eventChannel.emit('toDish', {
          marchantName: marchantName
        })
      }
    })
  },
  chooseaddress() {
    var that = this;
    wx.navigateTo({
      url: '../addresslist/addresslist',
      success: function (res) {
        that.data.getaddress = true;
        // 通过eventChannel向被打开页面传送数据
        res.eventChannel.emit(
          'chooseaddress', {
            isinfo: true
          }
        )
      }
    })
  },
  getRecommend() {
    var that = this;
    wx.getStorage({
      key: 'wxUserToken',
      success: function (res) {
        wx.request({
          url: 'http://localhost:8090/recommend',
          method: "GET",
          data: {
            openid: JSON.parse(res.data).openid
          },
          success: function (res) {
            if (res.data.data != "") {
              console.log(res);
              var marchant = res.data.data.replace('(', '').replace(')', '').split(',')[0];
              wx.request({
                url: 'http://localhost:8090/user/getDataById',
                method: "GET",
                data: {
                  id: marchant
                },
                success: function (res) {
                  var recommendMarchant = res.data.data;
                  // console.log(res);
                  //获取月销量
                  wx.request({
                    url: 'http://localhost:8090/order/getSales',
                    method: "GET",
                    data: {
                      method: "marchant",
                      id: recommendMarchant.id,
                      date: "month"
                    },
                    success: function (res) {
                      recommendMarchant.sales = res.data.data;
                      that.setData({
                        recommendMarchant: recommendMarchant
                      });
                      wx.hideLoading();
                    }
                  })
                }
              })
            } else {
              var recommendMarchant = that.data.marchant_arry[that.data.marchant_arry.length - 1];
              that.setData({
                recommendMarchant: recommendMarchant
              })
            }
          }
        })
      }
    })
  }
})