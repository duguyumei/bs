// pages/user/user.js
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo:"",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      userInfo:app.globalData.userInfo
    })
  },
  myAddress(){
    wx.navigateTo({
      url: '../addresslist/addresslist',
    })
  },
  myLove(){
    wx.navigateTo({
      url: '../showLoveList/showLoveList',
    })
  },
  myEvaluate(){
    wx.navigateTo({
      url: '../showEvaluateList/showEvaluateList',
    })
  },
  quit_btn(){
    wx.clearStorage();
    wx.switchTab({
      url: '/pages/index/index',
    });
  }
})