// pages/showEvaluateList/showEvaluateList.js
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    evaluateList: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.getData();
  },

  getData() {
    var that = this;
    wx.request({
      url: 'http://localhost:8090/evaluate/getAllEvaluate',
      method: "GET",
      data: {
        id:JSON.parse(app.globalData.token).openid
      },
      success: function (res) {
        var list = JSON.parse(res.data.data);
        that.setData({
          evaluateList: list
        })
        for(let i = 0; i < list.length; i++){
          let index = i;
          list[index].stime = that.toDate(list[index].stime);
          if(list[index].etime != null){
            list[index].etime = that.toDate(list[index].etime);
          }
          wx.request({
            url: 'http://localhost:8090/user/getDataById',
            method: "GET",
            data:{
              id:list[index].marchant
            },
            success:function(res) {
              list[index].marchant = res.data.data
              that.setData({
                evaluateList: list
              })
            }
          })
        }
      }
    })
  },
  toDate(str) {
    var allStr = this.getLocalTime(str);
      var year = allStr.substr(0, 4);
      var month = allStr.substr(5, 2);
      var day = allStr.substr(8, 2);
      return year + '.' + month + '.' + day;
  },
  getLocalTime: function (ns) {
    var time = new Date(parseInt(ns));
    var y = time.getFullYear();
    var m = time.getMonth() + 1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    return y + '-' + this.add0(m) + '-' + this.add0(d) + ' ' + this.add0(h) + ':' + this.add0(mm) + ':' + this.add0(s);
  },
  add0:function(m){
    return m < 10 ? '0' + m : m 
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
})