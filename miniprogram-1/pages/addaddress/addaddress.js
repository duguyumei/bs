// pages/addaddress/addaddress.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    addressname: "点击选择收货地址",
    address: "",
    latitude: "",
    longitude: "",
    phone:"",
    sex:"先生",
    message:"",
    name:"",
    time:"",
    openid:""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //获取所有打开的eventChannel数据
    const eventChannel = this.getOpenerEventChannel();
    //监听index页面定义的todish事件
    eventChannel.on('editaddress',res=>{
      this.setData(res);
    });
  },
  chooseaddress(){
    var that = this;
    wx.authorize({
      scope: 'scope.userLocation',
      success(e){
        wx.getLocation({
          type: 'gcj02',
          success(location){
            wx.chooseLocation({
              success(res){
                console.log("address",res);
                that.setData({
                  addressname: res.name,
                  address: res.address,
                  latitude: res.latitude,
                  longitude: res.longitude
                })
              }
            })
          }
        })
      }
    })
  },
  inputname(e){
    this.data.name = e.detail.value;
  },
  inputmessage(e){
    this.data.message = e.detail.value;
  },
  inputphone(e){
    this.data.phone = e.detail.value;
  },
  choosesex(e){
    this.data.sex = e.detail.value;
  },
  formSubmit(){
    if(this.data.addressname == "" || this.data.addressname == null || this.data.phone == null || this.data.name == null || this.data.phone == "" || this.data.name == ""){
      wx.showModal({
        title: '提示',
        content: '请将信息填写完整',
        showCancel:false,
      })
    }else{
      this.data.time = new Date().getTime();
    var token = JSON.parse(app.globalData.token)
    this.data.openid = token.openid;
    //无id新增,有id修改
    if(this.data.id == undefined || this.data.id == null || this.data.id == ""){
      wx.request({
        url: 'http://localhost:8090/address/addAddress',
        method:"POST",
        data:{
          addressname: this.data.addressname,
          address: this.data.address,
          latitude: this.data.latitude,
          longitude: this.data.longitude,
          phone:this.data.phone,
          sex: this.data.sex,
          message: this.data.message,
          name: this.data.name,
          time: this.data.time,
          openid: this.data.openid
        },
        success:function(res){
          console.log('addaddress',res);
          var pages = getCurrentPages();
          var beforePage = pages[pages.length - 2];
          beforePage.onLoad();
          wx.navigateBack({
            delta: 1,
          })
        }
      })
    }else{
      wx.request({
        url: 'http://localhost:8090/address/editAddress',
        method:"PUT",
        data:{
          id: this.data.id,
          addressname: this.data.addressname,
          address: this.data.address,
          latitude: this.data.latitude,
          longitude: this.data.longitude,
          phone:this.data.phone,
          sex: this.data.sex,
          message: this.data.message,
          name: this.data.name,
          time: this.data.time,
          openid: this.data.openid
        },
        success:function(res){
          console.log('editaddress',res);
          var pages = getCurrentPages();
          var beforePage = pages[pages.length - 2];
          beforePage.onLoad();
          wx.navigateBack({
            delta: 1,
          })
        }
      })
    }
    }
  }
})