// pages/dish/dish.js

const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    marchantInfo: {},
    dish_array: [],
    money: 0,
    hideModal: true //模态框的状态  true-隐藏  false-显示
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (option) {
    //获取所有打开的eventChannel数据
    const eventChannel = this.getOpenerEventChannel();
    //监听index页面定义的todish事件
    eventChannel.on('toDish', res => {
      console.log(res.marchantName);
      this.setData({
        marchantInfo: res.marchantName
      });
      //获取菜品
      this.getDishList(res.marchantName.id);
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {},
  getDishList(id) {
    var that = this;
    wx.request({
      url: 'http://localhost:8090/dishes/getDishes',
      method: "GET",
      data: {
        id: id
      },
      success: function (res) {
        var testJson = JSON.parse(res.data.msg);
        //月销量
        for (let i = 0; i < testJson.length; i++) {
          //初始化
          testJson[i].num = 0
          let index = i;
          wx.request({
            url: 'http://localhost:8090/order/getSales',
            method: "GET",
            data: {
              method: "dish",
              id: testJson[index].id,
              date: "month",
              pid: id
            },
            success: function (res) {
              // console.log(index,res.data.data);
              testJson[index].sales = res.data.data;
              that.setData({
                dish_array: testJson
              })
            }
          })
        }
      }
    })
  },
  adddish(e) {
    //list
    var index = e.currentTarget.dataset.index;
    var list = this.data.dish_array;
    list[index].num += 1;
    this.setData({
      dish_array: list,
      money: this.data.money + list[index].money
    })
  },
  removedish(e) {
    var index = e.currentTarget.dataset.index;
    var list = this.data.dish_array;
    list[index].num -= 1;
    this.setData({
      dish_array: list,
      money: this.data.money - list[index].money
    })
  },
  clickfoot() {
    var num = 0;
    for (var i = 0; i < this.data.dish_array.length; i++) {
      if (this.data.dish_array[i].num > 0) {
        num += 1;
      }
    }
    if (num <= 0) {
      wx.showToast({
        title: '暂无数据,请添加商品',
        icon: 'none',
        duration: 1000
      })
    } else {
      if (this.data.hideModal) {
        this.showModal();
      } else {
        this.hideModal();
      }
    }
  },
  // 显示遮罩层 
  showModal() {
    var that = this;
    that.setData({
      hideModal: false
    })
    var animation = wx.createAnimation({
      duration: 400, //动画的持续时间 默认400ms 数值越大，动画越慢 数值越小，动画越快 
      timingFunction: 'ease', //动画的效果 默认值是linear 
    })
    this.animation = animation
    setTimeout(function () {
      that.fadeIn(); //调用显示动画 
    }, 200)
  },
  // 隐藏遮罩层 
  hideModal() {
    var that = this;
    var animation = wx.createAnimation({
      duration: 400, //动画的持续时间 默认400ms 数值越大，动画越慢 数值越小，动画越快 
      timingFunction: 'ease', //动画的效果 默认值是linear 
    })
    this.animation = animation
    that.fadeDown(); //调用隐藏动画 
    setTimeout(function () {
      that.setData({
        hideModal: true
      })
    }, 380) //先执行下滑动画，再隐藏模块 
  },
  //动画集 
  fadeIn() {
    this.animation.translateY(0).step()
    this.setData({
      animationData: this.animation.export() //动画实例的export方法导出动画数据传递给组件的animation属性 
    })
  },
  fadeDown() {
    this.animation.translateY(300).step()
    this.setData({
      animationData: this.animation.export(),
    })
  },
  submitorder() {
    var that = this;
    var list = this.data.dish_array;
    var dishes = [];
    for (var i = 0; i < list.length; i++) {
      if (list[i].num > 0) {
        dishes.push(list[i]);
      }
    }
    if (!dishes.length > 0) {
      wx.showToast({
        title: '暂无数据,请添加商品',
        icon: 'none',
        duration: 1000
      })
    } else {
      wx.navigateTo({
        url: '../addorder/addorder',
        success: function (res) {
          // 通过eventChannel向被打开页面传送数据
          res.eventChannel.emit('toOrder', {
            dishes: dishes,
            marchantInfo: that.data.marchantInfo
          })
        }
      })
    }
  },
  addLove() {
    var that = this;
    // console.log(this.data.marchantInfo.id,JSON.parse(app.globalData.token).openid);
    wx.request({
      url: 'http://localhost:8090/customer/addLove',
      method: "GET",
      data: {
        openid: JSON.parse(app.globalData.token).openid,
        id: this.data.marchantInfo.id,
      },
      success:function(res){
        console.log(res);
        if(res.data.code == '1'){
          wx.showToast({
            title: '收藏成功',
            icon: 'success',
            duration: 1000
          })
        }else if(res.data.code == '-1'){
          wx.showModal({
            title: '您已收藏',
            content: '是否取消收藏',
            success (res) {
              if (res.confirm) {
                that.deleteLove();
              }
            }
          })
        }
      }
    })
  },
  deleteLove(){
    wx.request({
      url: 'http://localhost:8090/customer/deleteLove',
      method: "GET",
      data: {
        openid: JSON.parse(app.globalData.token).openid,
        id: this.data.marchantInfo.id
      },
      success:function(res){
        if(res.data.data == "删除成功"){
          wx.showToast({
            title: '成功',
            icon: 'success',
            duration: 1000
          })
        }
      }
    })
  }
})