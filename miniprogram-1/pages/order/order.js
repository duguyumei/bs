// pages/order/order.js
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderlist:[],
    // urlSrc:"http://localhost:8090/files/7256d9c2398d40fb81ebda40ecc8071b",
    showImage:false,
    imgSrc:"",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onShow: function (options) {
    this.getorder();
  },
  getorder(){
    var that = this;
    //order表
    wx.request({
      url: 'http://localhost:8090/order/getOrder',
      method:"GET",
      data: {
        openid: JSON.parse(app.globalData.token).openid
      },
      success:function(res){
        var testJson = JSON.parse(res.data.msg);
        for(var i = 0; i < testJson.length; i++){
          if(testJson[i].state == "1"){
            testJson[i].state = "等待商家接单";
          }else if(testJson[i].state == "2"){
            testJson[i].state = "等待骑手派送";
          }else if(testJson[i].state == "3"){
            testJson[i].state = "正在派送";
          }else if(testJson[i].state == "4"){
            testJson[i].state = "已完成";
          }
        }
        //
        testJson.sort(that.sortStime);
        that.setData({
          orderlist:testJson
        })
        for(var i = 0; i < that.data.orderlist.length; i++){
          //marchant表
          var id = that.data.orderlist[i].marchant;
          that.getMarchant(id,i);
          //dish表
          var dish = that.data.orderlist[i].dish;
          that.getDish(dish,i);
        }
      }
    })
  },
  getMarchant(mid,index){
    var that = this;
    wx.request({
      url: 'http://localhost:8090/user/getDataById',
      method:"GET",
      data: {
        id:mid
      },
      success:function(res){
        // console.log("res",index,res.data.data);
        // that.data.marchantlist.push(res.data.data);
        that.data.orderlist[index].marchant = res.data.data;
        // console.log(index,that.data.orderlist[index].marchant);
        that.setData({
          orderlist:that.data.orderlist
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
  evaluate(e){
    var order = e.currentTarget.dataset.order;
    wx.navigateTo({
      url: '../evaluate/evaluate',
      success: function(res) {
        res.eventChannel.emit('toEval', { order: order })
      }
    })
  },
  showEvaluate(e){
    var order = e.currentTarget.dataset.order;
    wx.navigateTo({
      url: '../showEvaluate/showEvaluate',
      success: function(res) {
        res.eventChannel.emit('toShowEval', { order: order })
      }
    })
  },
  sortStime(a,b){
    return b.stime - a.stime;
  },
  showImage(e){
    var url = e.currentTarget.dataset.url;
    this.setData({
      showImage:true,
      imgSrc:url
    })
  },
  hiddenImage(){
    this.setData({
      showImage:false,
    })
  },
  linkMarchant(e){
    var phone = e.currentTarget.dataset.marchant.phone;
    wx.showModal({
      title: '手机号',
      content: phone,
      showCancel:false,
    })
  },
})