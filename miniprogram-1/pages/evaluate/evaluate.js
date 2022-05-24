// pages/evaluate/evaluate.js
const app = getApp();
Page({
  data: {
    order: {},
    staticImg: app.globalData.staticImg,
    current: 0,
    code: 1,
    code1: 2,
    userStars: [
      "../../images/sx.png",
      "../../images/sx.png",
      "../../images/sx.png",
      "../../images/kx.png",
      "../../images/kx.png",
    ],
    wjxScore: 3,
    // textarea
    min: 0, //最少字数
    max: 300, //最多字数 (根据自己需求改变)
    pics: [],
    message: "",
    urlList: []
  },
  onLoad: function (options) {
    //获取所有打开的eventChannel数据
    const eventChannel = this.getOpenerEventChannel();
    //监听index页面定义的todish事件
    eventChannel.on('toEval', res => {
      this.setData({
        order: res.order
      });
    });
  },
  // 星星点击事件
  starTap: function (e) {
    var that = this;
    var index = e.currentTarget.dataset.index; // 获取当前点击的是第几颗星星
    var tempUserStars = this.data.userStars; // 暂存星星数组
    var len = tempUserStars.length; // 获取星星数组的长度
    for (var i = 0; i < len; i++) {
      if (i <= index) { // 小于等于index的是满心
        tempUserStars[i] = "../../images/sx.png";
        that.setData({
          wjxScore: i + 1,
        })
      } else { // 其他是空心
        tempUserStars[i] = "../../images/kx.png"
      }
    }
    // 重新赋值就可以显示了
    that.setData({
      userStars: tempUserStars
    })
  },
  // 留言
  //字数限制
  inputs: function (e) {
    // 获取输入框的内容
    var value = e.detail.value;
    // 获取输入框内容的长度
    var len = parseInt(value.length);
    //最多字数限制
    if (len > this.data.max)
      return;
    // 当输入框内容的长度大于最大长度限制（max)时，终止setData()的执行
    this.setData({
      currentWordNumber: len, //当前字数
      message: value
    });
  },
  // 图片
  choose: function (e) { //这里是选取图片的方法
    var that = this;
    var pics = that.data.pics;
    wx.chooseImage({
      count: 5 - pics.length, // 最多可以选择的图片张数，默认9
      sizeType: ['original', 'compressed'], // original 原图，compressed 压缩图，默认二者都有
      sourceType: ['album', 'camera'], // album 从相册选图，camera 使用相机，默认二者都有
      success: function (res) {
        var imgsrc = res.tempFilePaths;
        pics = pics.concat(imgsrc);
        that.setData({
          pics: pics,
        });
      },
    })

  },
  uploadimg: function (path) { //这里触发图片上传的方法
    var that = this;
    return new Promise((resolve, reject) => {
      wx.uploadFile({
        url: "http://localhost:8090/files/upload/",
        filePath: path,
        name: 'file',
        success: function (res) {
          if (res.statusCode == 200) {
            var src = JSON.parse(res.data).data;
            that.data.urlList.push(src);
            // console.log(src);
          }
          resolve()
        }
      })
    })
  },
  // 删除图片
  deleteImg: function (e) {
    var pics = this.data.pics;
    var index = e.currentTarget.dataset.index;
    pics.splice(index, 1);
    this.setData({
      pics: pics
    });
  },
  // 预览图片
  previewImg: function (e) {
    //获取当前图片的下标
    var index = e.currentTarget.dataset.index;
    //所有图片
    var pics = this.data.pics;
    wx.previewImage({
      //当前显示图片
      current: pics[index],
      //所有图片
      urls: pics
    })
  },
  //提交
  handleBtn() {
    var that = this;
    let asyncFun = []
    //上传图片
    this.data.pics.forEach((item, index) => {
      asyncFun.push(that.uploadimg(item))
    })
    Promise.all(asyncFun).then(() => {
      //保存评价信息
      wx.request({
        url: 'http://localhost:8090/evaluate/addEvaluate',
        method: "POST",
        data: {
          openid: that.data.order.openid,
          orderid: that.data.order.id,
          marchant: that.data.order.marchant.id,
          source: that.data.wjxScore,
          message: that.data.message,
          url: that.data.urlList.toString(),
          stime: new Date().getTime()
        },
        success: function (res) {
          //更新订单信息
          wx.request({
            url: 'http://localhost:8090/order/evalOrder',
            method: "GET",
            data: {
              oId: that.data.order.id,
              eId: "1"
            }
          })
          if (res.data.code == "0") {
            wx.showToast({
              title: '评价成功',
              icon: 'succes',
              duration: 1500,
              mask: true,
              success: function () {
                //更新
                wx.request({
                  url: 'http://localhost:8090/user/updateScore',
                  method: "GET",
                  data: {
                    id: that.data.order.marchant.id,
                  }
                })
                setTimeout(function () {
                  wx.reLaunch({
                    url: '../order/order'
                  })
                }, 1500)
              }
            });
          } else {
            wx.showToast({
              title: '评价失败',
              duration: 1500,
              mask: true
            })
          }
        }
      })
    })
  }
})