# 毕设写的外卖软件

## 文件结构

- py：python，用于为用户推荐商家
- springboot： 系统后端
- vue：商家web端
- miniprogram-1：客户端的微信小程序
- miniprogram-2：骑手端的微信小程序
- springboot-vue.sql：mysql数据库文件
- 校园外卖订餐管理及推荐系统的研究与实现.doc：论文主要内容

## 个性化

### springboot\src\main\resources中的application.yml

- url

  改为：jdbc:mysql://localhost:3306/`数据库名`？useUnicode=true&&characterEncoding=utf-8&&useSSL=false

  例如：`url: jdbc:mysql://localhost:3306/demo?useUnicode=true&&characterEncoding=utf-8&&useSSL=false`

- username

  改为自己的mysql账号名

  例如：`username: root`

- password

  改为自己的mysql账号密码

  例如：`password: 123456`

### springboot\src\main\java\com\example\demo\controller中的UserController.java

- 将getOpenid方法中的appid与appSecret换成自己的顾客端小程序的信息

  获取方式

  - [微信公众平台 - 传送门](https://mp.weixin.qq.com/)

  - 进入网页后, 右上角扫码登录

  - 在左侧栏找到 </>开发

  - 依次点击  -> 开发管理  -> (在中间横向导航框) 点开发设置 -> 

  - 看到了开发者ID 下方有 
        AppID(小程序ID)    **********************

  - APPID下面有  AppSecret(小程序密钥)	
    但是内容为空,需要在最右方点击 重置
    (出于安全考虑，AppSecret不再被明文保存，忘记密钥请点击重置)
    重置之后就可以看到自己的 secret, 请将他保存到自己的私密地方.

  - 补充说明：
    	微信公众平台可能会因为后期版本更迭，随之排版变化，导致以上内容过时。可以按照关键词在页面使用查找功能（Ctrl+F）快速查找关键。

  例如

  ```
  String appid = "wx9e61554d02557cc2";
  String appSecret = "f877585505fb680220f735fdb2863759";

### springboot\src\main\java\com\example\demo\controller中的PythonController.java

- getRecommend方法中的args1中的地址换为python文件地址

  ```
  String[] args1 = new String[]{"python", recommend.py文件地址 , openid};
  ```

  例如：`String[] args1 = new String[]{"python", "E:\\notejs\\manage\\py\\recommend.py", openid};`

### vue\src\views中的register.vue

- addrHand中的data.key换成自己申请到的腾讯地图key

  申请方式：[(186条消息) 如何申请腾讯地图用户Key_megomap的博客-CSDN博客_腾讯地图key申请](https://blog.csdn.net/chenwen112/article/details/102910337)

  例如：`key: "UUNBZ-LV6WP-QCQDR-LE267-R3R76-D6BM6"`

  

## 开发环境

该系统的springboot与vue是根据b站的一个视频搭建的，可以先自己看视频搭一个再将我的源码复制进去。

[从0开始带你手撸一套SpringBoot+Vue后台管理系统（2022年最新版）_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1U44y1W77D?spm_id_from=333.999.0.0)