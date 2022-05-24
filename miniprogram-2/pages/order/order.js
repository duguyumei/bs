// index.js
// 获取应用实例
const app = getApp()

Page({
  data: {
    addressname: "",
    userInfo: {},
    orderlist:[],
    login:false,
    addressInfo:{},
    marchant:{}
  },
  onShow() {
    this.inspectInfo();
  },
  getorder(){
    var that = this;
    //order表
    wx.request({
      url: 'http://localhost:8090/order/getMyOrder',
      method:"GET",
      data:{
        ride:that.data.userInfo.id,
        method:"all"
      },
      success:function(res){
        var testJson = JSON.parse(res.data.msg);
        that.setData({
          orderlist:testJson
        })
        console.log("pre",that.data.orderlist);
        for(var i = 0; i < that.data.orderlist.length; i++){
          //dish表
          var dish = that.data.orderlist[i].dish;
          that.getDish(dish,i);
        }
        for(var i = 0; i < that.data.orderlist.length; i++){
          // console.log(that.data.orderlist);
          // var distance = that.getDistance(that.data.addressInfo.latitude,that.data.addressInfo.latitude,)
        }
        console.log("update",that.data.orderlist);
      }
    })
  },
  getMarchant(){
    var that = this;
    wx.request({
      url: 'http://localhost:8090/user/getDataById',
      method:"GET",
      data: {
        id:that.data.userInfo.marchant
      },
      success:function(res){
        that.setData({
          marchant:res.data.data
        })
      }
    })
  },
  getDish(dish,index){
    var that = this;
    var arr = dish.split(" ");
    var dish = [];
    var i = 0;
    for(var j = 0; j < arr.length; j += 2){
      var mid = arr[j];
      var num = arr[j+1];
      wx.request({
        url: 'http://localhost:8090/dishes/getDataById',
        method:"GET",
        data: {
          id:mid
        },
        success:function(res){
          i++;
          res.data.data.num = num;
          // console.log(2,res.data.data);
          dish.push(res.data.data);
          // console.log("index",index,"j",j);
          if(i == arr.length / 2){
            // console.log("index",index,"j",j,"dish",dish);
            // console.log("j",j);
            // console.log(3,j,dish);
            that.data.orderlist[index].dish = dish;
            that.setData({
              orderlist:that.data.orderlist
            })
          }
        }
      })
    }
  },
  toDish(e){
    var marchantName = e.currentTarget.dataset.name;
    wx.navigateTo({
      url: '../dish/dish',
      success: function(res) {
        res.eventChannel.emit('toDish', { marchantName: marchantName })
      }
    })
  },
  inspectInfo(){
    var that = this;
    // 查询是否有缓存
    wx.getStorage({
      key: 'wxUserInfo',
      success :function(res){
        app.globalData.userInfo = res.data;
        that.setData({
          userInfo:res.data
        });
        that.getorder();
        that.getMarchant();
      },
      fail:function(res){
        that.setData({
          login:true
        })
      }
    })
  },
  login(e){
    var that = this;
    var username = e.detail.value.username;
    var password = e.detail.value.password;
    wx.request({
      url: 'http://localhost:8090/ride/login',
      method:"POST",
      data: {
        username:username,
        password:password
      },
      success:function (res){
        if(res.data.data!=null){
          app.globalData.userInfo = res.data.data;
          that.setData({
            userInfo:res.data.data
          });
          that.getorder();
          that.getMarchant();
          // 缓存
          wx.setStorage({
            key: 'wxUserInfo',
            data: res.data.data,
          });
          wx.showToast({
            title: '登录成功',
            icon: 'success',
            duration: 1000
          });
          that.setData({
            login:false
          })
        }else{
          wx.showToast({
            title: '账号或密码错误',
            icon: 'error',
            duration: 1000
          })          
        }
      }
    })
  },
  showAddress(e){
    var address = this.data.orderlist[e.currentTarget.dataset.index].address;
    wx.showModal({
      title: '地址',
      content: address,
      showCancel:false,
    })
  },
})
