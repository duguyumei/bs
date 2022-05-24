// pages/addorder/addorder.js
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    dishlist: [],
    marchantInfo: {},
    address: {},
    setTime: false,
    summoney: 0,
    pay: "立即送出",
    message: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //获取所有打开的eventChannel数据
    const eventChannel = this.getOpenerEventChannel();
    //监听index页面定义的事件
    eventChannel.on('toOrder', res => {
      this.setData({
        dishlist: res.dishes,
        marchantInfo: res.marchantInfo
      });
    });
  },
  onShow() {
    var that = this;
    wx.getStorage({
      key: "wxDefaultAddress",
      success: function (res) {
        app.globalData.address = res.data;
        that.setData({
          address: res.data
        })
      }
    })
    this.summoney();
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
  summoney() {
    var sum = 0;
    var list = this.data.dishlist;
    for (var i = 0; i < list.length; i++) {
      sum += list[i].money * list[i].num;
    }
    this.setData({
      summoney: sum
    })
  },
  changeInput(e) {
    this.data.message = e.detail.value;
  },
  payorder() {
    var that = this;
    if (this.data.address.addressname == null || this.data.address == {}) {
      wx.showModal({
        title: '提示',
        content: '请选择地址',
        showCancel: false,
        success: function () {
          that.chooseaddress();
        }
      })
    } else {
      wx.request({
        url: 'http://localhost:8090/user/isInArea',
        method: "GET",
        data: {
          address: that.data.address.latitude + "," + that.data.address.longitude,
          marchant: that.data.marchantInfo.id
        },
        success: function (res) {
          if (res.data.code == "0") {
            var stime = new Date().getTime();
            var json = JSON.parse(app.globalData.token);
            var address = that.data.address.addressname + " " + that.data.address.message;
            var people = that.data.address.name + " " + that.data.address.sex + " " + that.data.address.phone;
            // var dish = JSON.stringify(that.data.dishlist);
            var dish = "";
            for (var i = 0; i < that.data.dishlist.length; i++) {
              dish += that.data.dishlist[i].id;
              dish += " ";
              dish += that.data.dishlist[i].num;
              if (i != that.data.dishlist.length - 1) {
                dish += " ";
              }
            }
            wx.showModal({
              title: '微信支付',
              content: '是否确认支付',
              success(res) {
                if (res.confirm) {
                  //添加订单
                  var order = {
                    marchant: that.data.marchantInfo.id,
                    openid: json.openid,
                    dish: dish,
                    ride: "请选择",
                    state: "1",
                    money: that.data.summoney,
                    pay: that.data.pay,
                    address: address,
                    stime: stime,
                    people: people,
                    message: that.data.message
                  };
                  console.log(order);
                  wx.request({
                    url: 'http://localhost:8090/order/addOrder',
                    method: "POST",
                    data: order,
                    success: function (res) {
                      wx.switchTab({
                        url: '/pages/index/index',
                      });
                    }
                  })
                }
              }
            })
          } else {
            wx.showModal({
              title: '无法送达',
              content: '请重新地址',
              showCancel: false,
              success: function () {
                that.chooseaddress();
              }
            })
          }
        }
      });
    }
  }
})