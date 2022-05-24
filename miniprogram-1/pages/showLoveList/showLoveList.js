// pages/showLoveList/showLoveList.js
const app = getApp()

Page({
  data: {
    marchant_arry: [],
  },
  onLoad() {
    //数据渲染
    this.getMarchant();
  },
  //获取餐厅数据
  getMarchant() {
    var that = this;
    wx.request({
      url: 'http://localhost:8090/customer/getLove',
      method: "GET",
      data: {
        openid: JSON.parse(app.globalData.token).openid,
      },
      success: function (res) {
        console.log(res.data.data);
        if (res.data.data != "") {
          var arr = res.data.data.split(",");
          for (let i = 0; i < arr.length; i++) {
            var id = arr[i];
            wx.request({
              url: 'http://localhost:8090/user/getDataById',
              method: "GET",
              data: {
                id: id
              },
              success: function (res) {
                var marchant = res.data.data;
                // marchantList.push(res.data.data);
                wx.request({
                  url: 'http://localhost:8090/order/getSales',
                  method: "GET",
                  data: {
                    method: "marchant",
                    id: id,
                    date: "month"
                  },
                  success: function (res) {
                    marchant.sales = res.data.data;
                    that.data.marchant_arry.push(marchant);
                    that.setData({
                      marchant_arry: that.data.marchant_arry
                    })
                    // wx.request({
                    //   url: 'http://localhost:8090/evaluate/getScore',
                    //   method: "GET",
                    //   data: {
                    //     id: id,
                    //   },
                    //   success: function (res) {
                    //     marchant.score = res.data.data;
                    //     that.data.marchant_arry.push(marchant);
                    //     that.setData({
                    //       marchant_arry: that.data.marchant_arry
                    //     })
                    //   }
                    // })
                  }
                })
              }
            })
          }
        }
      }
    })
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