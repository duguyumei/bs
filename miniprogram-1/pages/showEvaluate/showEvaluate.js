// pages/evaluate/evaluate.js
const app = getApp();
Page({
  data: {
    order:{},
    staticImg: app.globalData.staticImg,
    current: 0,
    code:1,
    code1:2,
    userStars: [
      "../../images/sx.png",
      "../../images/sx.png",
      "../../images/sx.png",
      "../../images/sx.png",
      "../../images/kx.png",
    ],
    wjxScore: 4,
    // textarea
    pics: [],
    message:"",
    urlList:[]
  },
  onLoad: function (options) {
    var that = this;
    //获取所有打开的eventChannel数据
    const eventChannel = this.getOpenerEventChannel();
    //监听index页面定义的todish事件
    eventChannel.on('toShowEval',res=>{
      this.setData({
        order:res.order
      });
    });
    wx.request({
      url: 'http://localhost:8090/evaluate/getDataByOrder',
      method:"GET",
      data:{
        id:that.data.order.id
      },
      success:function(res){
        var info = res.data.data;
        if(info.url != ""){
          var arr = info.url.split(",");
        }else{
          arr=[];
        }
        that.setData({
          wjxScore: info.source,
          message:info.message,
          pics: arr,
          marmessage : info.marmessage
        })
        that.starTap();
      }
    })
  },
  starTap: function () {
    var that = this;
    var index = this.data.wjxScore;
    var tempUserStars = this.data.userStars;
    var len = tempUserStars.length; // 获取星星数组的长度
    for (var i = 0; i < len; i++) {
      if (i < index) { // 小于等于index的是满心
        tempUserStars[i] = "../../images/sx.png";
      } else { // 其他是空心
        tempUserStars[i] = "../../images/kx.png"
      }
    }
    // 重新赋值就可以显示了
    that.setData({
      userStars: tempUserStars
    })
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
})