// pages/addresslist/addresslist.js
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    list:{},
    isinfo:false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getaddress();
    //判断哪来的
    //获取所有打开的eventChannel数据
    const eventChannel = this.getOpenerEventChannel();
    //监听index页面定义的事件
    eventChannel.on('chooseaddress',res=>{
      this.setData({
        isinfo:res.isinfo
      });
    });
  },
  getaddress(){
    var that = this;
    wx.request({
      url: 'http://localhost:8090/address/getAddress',
      method:"GET",
      data: {
        openid: JSON.parse(app.globalData.token).openid
      },
      success:function(res){
        console.log(res);
        var testJson = JSON.parse(res.data.msg);
        that.setData({
          list:testJson
        })
      }
    })
  },
  addaddress(){
    wx.navigateTo({
      url: '../addaddress/addaddress',
    })
  },
  editaddress(e){
    var res = e.currentTarget.dataset.address;
    wx.navigateTo({
      url: '../addaddress/addaddress',
      success: function(e) {
        // 通过eventChannel向被打开页面传送数据
        e.eventChannel.emit('editaddress', {
          id: res.id,
          addressname: res.addressname,
          address: res.address,
          latitude: res.latitude,
          longitude: res.longitude,
          phone: res.phone,
          sex: res.sex,
          message: res.message,
          name: res.name,
          time: res.time,
          openid: res.openid
        })
      }
    })
  },
  deleteaddress(e){
    var that = this;
    var id = e.currentTarget.dataset.address.id;
    wx.showModal({
      title: '删除改地址',
      content: '确认要删除吗？',
      success: function (res) {
        if (res.confirm) {  
          var url = 'http://localhost:8090/address/deleteAddress/' + id;
          wx.request({
            url: url,
            method:"DELETE",
            success:function(res){
              console.log('deleteaddress',res);
              that.onLoad();
            }
          })
        }
      }
    })
  },
  chooseaddress(e){
    if(this.data.isinfo){
      console.log(e.currentTarget.dataset.address);
      app.globalData.address = e.currentTarget.dataset.address;
      wx.setStorage({
        key: 'wxDefaultAddress',
        data: app.globalData.address
      })
      wx.navigateBack();
    }
  }
})