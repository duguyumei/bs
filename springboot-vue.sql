-- MySQL dump 10.13  Distrib 5.7.24, for Win64 (x86_64)
--
-- Host: localhost    Database: springboot-vue
-- ------------------------------------------------------
-- Server version	5.7.24-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `addressname` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `latitude` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `longitude` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sex` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `message` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (4,'oxOsk6hcp97MXaAdCEWwkASoMrz1','南京市栖霞区人民政府','江苏省南京市栖霞区文枢东路1号仙林科技城','32.096359','118.90907','13851769673','先生','1013333','cc','1648297541888'),(5,'oxOsk6hcp97MXaAdCEWwkASoMrz1','东城汇','江苏省南京市栖霞区文苑路8号(南师大体育馆对面)','32.097054','118.909008','13851769673','先生','201','xx','1648259290161'),(6,'oxOsk6hcp97MXaAdCEWwkASoMrz1','南京市栖霞区人民政府','江苏省南京市栖霞区文枢东路1号仙林科技城','32.096359','118.90907','13851769673','女士','302','vv','1648279990301'),(7,'oxOsk6hcp97MXaAdCEWwkASoMrb2','栖霞区仙境路(南京理工大学紫金学院西门)','江苏省南京市栖霞区仙境路29号','32.12430507394389','118.93171510273103','15678946911','女士','西门','小红','1649164942720'),(8,'oxOsk6hcp97MXaAdCEWwkASoMrb3','栖霞区仙林南京理工大学紫金学院南门','江苏省南京市栖霞区文澜路89号','32.122787663775775','118.93497666889314','13548628741','先生','南门','小强','1649167724113'),(9,'oxOsk6hcp97MXaAdCEWwkASoMrbw','南京理工大学紫金学院-西门','江苏省南京市栖霞区文宗路与仙境路交叉口东北方向10米','32.12421','118.93186','15679684235','先生','西门','小明','1649169089323'),(10,'oxOsk6hcp97MXaAdCEWwkASoMrbw','南京大学(仙林校区)','江苏省南京市栖霞区仙林大道163号','32.118357','118.958931','13879684968','女士','南门','小刚','1650356457181'),(11,'oxOsk6hcp97MXaAdCEWwkASoMrbw','东城汇','江苏省南京市栖霞区文苑路8号(南师大体育馆对面)','32.097054','118.909008','13854797842','先生','123','123','1651387525260'),(12,'oxOsk6hcp97MXaAdCEWwkASoMrbw','鸡鸣寺','江苏省南京市玄武区地铁3号线,地铁4号线','32.057239','118.798109','13821769273','先生','112','123','1651800134616');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dishes`
--

DROP TABLE IF EXISTS `dishes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dishes` (
  `id` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜名',
  `message` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '详情',
  `money` double(255,2) DEFAULT NULL COMMENT '售价',
  `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片',
  `state` int(11) DEFAULT '0' COMMENT '状态',
  `marchant` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '商家',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dishes`
--

LOCK TABLES `dishes` WRITE;
/*!40000 ALTER TABLE `dishes` DISABLE KEYS */;
INSERT INTO `dishes` VALUES (00000000004,'可乐1','3',3.00,'http://localhost:8090/files/f7bc52c350ce44a8a60eed5e0b6fe9a5',0,'2'),(00000000005,'可乐鸡','2',1.00,'http://localhost:8090/files/b23549691dd147bca811a29595ca9fa2',0,'2'),(00000000006,'啤酒鸭','3',33.00,'http://localhost:8090/files/7d517338e9434d5badaa8bc2dfd0a0ca',0,'2'),(00000000007,'123','11',11.00,'http://localhost:8090/files/2894c4b4eb0e4f9ab73c0cb79551bd5c',0,'2'),(00000000008,'海鲜意面','海鲜意面',29.90,'http://localhost:8090/files/ca527aa0babf43f2b57d8e8e2c77f4d6',1,'4'),(00000000009,'烤肠意面','烤肠意面',24.90,'http://localhost:8090/files/eea3a24fe4f84b689d7b4e11ff712453',1,'4'),(00000000010,'回锅肉','回锅肉套餐',21.80,'http://localhost:8090/files/5c6117f98ca94236b57719f0bcf12884',1,'5'),(00000000011,'口水鸡','口水鸡',20.80,'http://localhost:8090/files/0c2389a8293548c2887bddd529148306',1,'5'),(00000000012,'经典奶茶','经典奶茶',10.80,'http://localhost:8090/files/5b6c7c6422d14f43963b6c533bf20b0a',1,'6'),(00000000013,'红豆奶茶','红豆奶茶',10.80,'http://localhost:8090/files/b4be60bdfde04f76988da7e51248fe39',1,'6'),(00000000014,'百香果','百香果',16.00,'http://localhost:8090/files/7cf0df78a1194c9f84f1a19b4a9a7c89',1,'7'),(00000000015,'葡萄多多','葡萄多多',20.00,'http://localhost:8090/files/ea8f48004c434f5daf8efbfb31b4be1c',1,'7'),(00000000016,'单人超值炸鸡套餐','单人超值炸鸡套餐',26.80,'http://localhost:8090/files/d906b7a7ae254323b315e781a08c31bd',1,'8'),(00000000017,'爆辣炸鸡','爆辣炸鸡',26.80,'http://localhost:8090/files/d47b1a65fa3f4637b85556e0a651158d',1,'8'),(00000000018,'雪顶雨后青提','雪顶雨后青提',28.00,'http://localhost:8090/files/e5005aaf5e694e1d91641c021ba4bf0c',1,'7'),(00000000019,'青梅绿茶','青梅绿茶',12.00,'http://localhost:8090/files/3b649cb762bb489a8ab74613892453c5',1,'7'),(00000000020,'樱花粉荔','樱花粉荔',22.00,'http://localhost:8090/files/04ca43af570f4fb49d847cba190f349b',1,'7'),(00000000021,'半只烤鸭+米饭+可乐','经典烤鸭',25.00,'http://localhost:8090/files/092d73685a504373b97edfba305ef9b4',1,'10'),(00000000022,'整只烤鸭','整只烤鸭',40.00,'http://localhost:8090/files/c4a3f152182847998e48196f407e530a',1,'10'),(00000000023,'特惠烤鸭','特惠烤鸭',1.00,'http://localhost:8090/files/2e3ce2997de64ef8a9e6a63101ed8516',0,'10'),(00000000024,'经典套餐','经典套餐',20.00,'http://localhost:8090/files/2d3dcf10761845cba4adb31b659b67c9',1,'10'),(00000000025,'可乐','可乐',3.00,'http://localhost:8090/files/32134b73c9254757981b1fffbc8003d0',1,'10'),(00000000026,'测试','测试',12.00,'http://localhost:8090/files/d2048fb0382e43a69af32b525dd7f93e',1,'7');
/*!40000 ALTER TABLE `dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluate`
--

DROP TABLE IF EXISTS `evaluate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `marchant` int(11) DEFAULT NULL,
  `source` int(11) DEFAULT NULL,
  `message` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `stime` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `url` varchar(2550) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `orderid` int(11) DEFAULT NULL,
  `etime` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `marmessage` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluate`
--

LOCK TABLES `evaluate` WRITE;
/*!40000 ALTER TABLE `evaluate` DISABLE KEYS */;
INSERT INTO `evaluate` VALUES (4,'oxOsk6hcp97MXaAdCEWwkASoMrz1',2,5,'132','1649055762714','http://localhost:8090/files/7b3aae9bcbfb4f4f853fb060da8ef4ee',2,NULL,NULL),(5,'oxOsk6hcp97MXaAdCEWwkASoMrz1',2,1,'不行','1649145614772','',7,NULL,NULL),(9,'oxOsk6hcp97MXaAdCEWwkASoMrb2',6,2,'不是很好喝','1649166732056','http://localhost:8090/files/72e9017bf4154fac82c7e4be3f63f3ef',8,NULL,NULL),(10,'oxOsk6hcp97MXaAdCEWwkASoMrb2',7,5,'好吃','1649167205701','',9,NULL,NULL),(11,'oxOsk6hcp97MXaAdCEWwkASoMrb3',8,4,'还行','1649168238701','',12,NULL,NULL),(12,'oxOsk6hcp97MXaAdCEWwkASoMrb3',4,1,'分量太少','1649168251797','',10,NULL,NULL),(13,'oxOsk6hcp97MXaAdCEWwkASoMrb3',5,4,'有点小贵','1649168266246','http://localhost:8090/files/72e9017bf4154fac82c7e4be3f63f3ef',11,NULL,NULL),(14,'oxOsk6hcp97MXaAdCEWwkASoMrbw',5,5,'好吃','1649169261371','',13,NULL,NULL),(16,'oxOsk6hcp97MXaAdCEWwkASoMrbw',5,1,'','1649258808852','',14,'1649596545523','???'),(17,'oxOsk6hcp97MXaAdCEWwkASoMrb2',7,5,'666','1649771257791','http://localhost:8090/files/0ee8f36aa0264854895b33104b80027d',15,'1649771278054','66666'),(18,'oxOsk6hcp97MXaAdCEWwkASoMrb2',7,1,'','1649771295702','',16,NULL,NULL),(19,'oxOsk6hcp97MXaAdCEWwkASoMrbw',7,5,'','1649771301426','',17,NULL,NULL),(20,'oxOsk6hcp97MXaAdCEWwkASoMrbw',7,5,'','1649771416369','',19,NULL,NULL),(21,'oxOsk6hcp97MXaAdCEWwkASoMrbw',7,5,'','1649771422454','',18,NULL,NULL),(22,'oxOsk6hcp97MXaAdCEWwkASoMrbw',7,5,'','1649855877560','',20,'1649855902192','666'),(23,'oxOsk6hcp97MXaAdCEWwkASoMrbw',10,5,'666666','1651387448842','',21,'1651387470612','6666666'),(24,'oxOsk6hcp97MXaAdCEWwkASoMrbw',5,5,'','1651387744543','',25,NULL,NULL),(25,'oxOsk6hcp97MXaAdCEWwkASoMrbw',5,5,'','1651387750616','',24,NULL,NULL),(26,'oxOsk6hcp97MXaAdCEWwkASoMrbw',5,5,'','1651387756486','',23,NULL,NULL),(27,'oxOsk6hcp97MXaAdCEWwkASoMrbw',5,5,'','1651387764396','',22,NULL,NULL),(28,'oxOsk6hcp97MXaAdCEWwkASoMrbw',7,5,'1234','1651800383755','http://localhost:8090/files/097283f9e5f9454886d4132d9b0871c4',28,NULL,NULL);
/*!40000 ALTER TABLE `evaluate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marchant`
--

DROP TABLE IF EXISTS `marchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marchant` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商家名',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地址',
  `message` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '简介信息',
  `documents` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '证书',
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系方式',
  `score` double(11,1) DEFAULT '0.0' COMMENT '评分',
  `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '照片',
  `status` int(11) DEFAULT '-1' COMMENT '状态',
  `area` int(11) DEFAULT '5' COMMENT '配送范围',
  `latitude` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `longitude` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商家信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marchant`
--

LOCK TABLES `marchant` WRITE;
/*!40000 ALTER TABLE `marchant` DISABLE KEYS */;
INSERT INTO `marchant` VALUES (2,'1112','xx','xc','xx','222222222222222222222222222222222222222','http://localhost:8090/files/bd388f993b064a1b8db1b3d65805e841','11111111111',3.0,'http://localhost:8090/files/481798d745fc4f5ca56e1f1d2b193fce',1,0,'32.120899','118.925819'),(4,'肉酱面也有灵魂','123456','roujiangmian','南京市栖霞区仙林街道仙林大学城学海路28号鸿运嘉园南邮广场2期15号商铺','果脯不讲究,用心做好料','http://localhost:8090/files/3694d57a6a0448a68d2ddfc7039ec917','13705188434',1.0,'http://localhost:8090/files/45b4b6a9950f493c91a5798e8d07f843',1,4,'32.120899','118.925819'),(5,'米饭主义','123456','mifanzhuyi','南京市栖霞区仙林街道文成路8-43号','暂停校园合作','http://localhost:8090/files/4836ffadd7e94ab7a1ce90a3291c69c6','14444444444',4.3,'http://localhost:8090/files/e18bee1a46b64e95a4f605aee702fef7',1,5,'32.12387','118.92429'),(6,'贡茶(仙林大学城店)','123456','gongcha','南京市栖霞区仙林街道晴天美食广场4楼','欢迎光临贡茶','http://localhost:8090/files/83fe910515294ee5b3797d6a2c0354b4','15555555555',2.0,'http://localhost:8090/files/02b16a65115e4b03bc6f8a6e858b6342',1,5,'32.097076','118.913536'),(7,'CoCo都可(南邮广场店)','123456','coco','南京市栖霞区仙林街道学海路 28-5号106室','亲爱的CoCo粉','http://localhost:8090/files/4ec53dcfa4a1432b80a214a1193e18ec','13854985796',4.5,'http://localhost:8090/files/8a89c6809a204d5e884ca6dbb2d67064',1,5,'32.120239','118.92444'),(8,'柠檬队长炸鸡(红枫街)','123456','ningmengduizhang','南京市栖霞区栖霞街道新世纪建材物贸市场','现炸现售','http://localhost:8090/files/411d8227ea5442ec970f7f13fdbe6031','18888888888',4.0,'http://localhost:8090/files/8cb0c466204f4206b725607cde847051',1,5,'32.13105','118.934006'),(9,'有滋味油炸小吃，凉皮，鸡柳','123456','youziwei','盐城市亭湖区元亨公寓3幢017号','油炸小吃','http://localhost:8090/files/3f57388f473f48d2b51eb388c5bd80de','15896337846',0.0,'',1,5,'33.37093','120.129852'),(10,'果木香烤鸭（南邮广场店）','123456','guomuxiang','南京市栖霞区仙林街道学海路28-6号108室','欢迎光临','http://localhost:8090/files/9235f54ff67046699518d606d7a5a4f4','13846879546',5.0,'http://localhost:8090/files/0e511a8648604b71824c80d1cf6964a0',1,5,'32.119916','118.92366');
/*!40000 ALTER TABLE `marchant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `marchant` int(11) DEFAULT NULL COMMENT '商家id',
  `openid` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户id',
  `dish` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜品id',
  `ride` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '骑手id',
  `money` double(11,2) DEFAULT NULL COMMENT '金额',
  `pay` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '送餐点',
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地址',
  `stime` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '开始时间',
  `etime` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '完成时间',
  `evaluate` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评分',
  `state` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态',
  `people` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收货人',
  `message` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '骑手拍照地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (2,2,'oxOsk6hcp97MXaAdCEWwkASoMrz1','4 1','1',3.00,'立即送出','东城汇 201','1648397679869','1648983114011','1','4','xx 先生 13851769673','','http://localhost:8090/files/481798d745fc4f5ca56e1f1d2b193fce'),(3,2,'oxOsk6hcp97MXaAdCEWwkASoMrz1','6 1 7 1','1',37.00,'立即送出','东城汇 201','1648397864167','1649145261969','0','4','xx 先生 13851769673','','http://localhost:8090/files/ec3cbc4e894843b5b1e94ac4d19ee953'),(4,2,'oxOsk6hcp97MXaAdCEWwkASoMrz1','6 1 7 1','1',44.00,'立即送出','东城汇 201','1648397893767',NULL,'0','3','xx 先生 13851769673','',NULL),(5,2,'oxOsk6hcp97MXaAdCEWwkASoMrz1','6 1','3',33.00,'立即送出','东城汇 201','1648568776446',NULL,'0','3','xx 先生 13851769673','',NULL),(7,2,'oxOsk6hcp97MXaAdCEWwkASoMrz1','5 2','1',2.00,'立即送出','南京市栖霞区人民政府 1013333','1649142996720','1649145144846','1','4','cc 先生 13851769673','duoduo','http://localhost:8090/files/e4457ad6f638424485221286225e56eb'),(8,6,'oxOsk6hcp97MXaAdCEWwkASoMrb2','12 2','6',21.60,'立即送出','栖霞区仙境路(南京理工大学紫金学院西门) 西门','1649164954468','1649166660509','1','4','小红 女士 15678946911','','http://localhost:8090/files/2f8182527bf9407f94be6ac3a20ed564'),(9,7,'oxOsk6hcp97MXaAdCEWwkASoMrb2','15 4','7',80.00,'立即送出','栖霞区仙境路(南京理工大学紫金学院西门) 西门','1649164988294','1649167148426','1','4','小红 女士 15678946911','gkd','http://localhost:8090/files/0e75b27488404c56ab3063a887289d38'),(10,4,'oxOsk6hcp97MXaAdCEWwkASoMrb3','8 1','4',29.90,'立即送出','栖霞区仙林南京理工大学紫金学院南门 南门','1651387670968','1649168191727','1','4','小强 先生 13548628741','','http://localhost:8090/files/8dd3ac9943f542308c5c457920037022'),(11,5,'oxOsk6hcp97MXaAdCEWwkASoMrb3','10 1','5',21.80,'立即送出','栖霞区仙林南京理工大学紫金学院南门 南门','1649167777510','1649168090857','1','4','小强 先生 13548628741','','http://localhost:8090/files/2c0be961b1f24d549e10805f30bcac51'),(12,8,'oxOsk6hcp97MXaAdCEWwkASoMrb3','17 1','8',26.80,'立即送出','栖霞区仙林南京理工大学紫金学院南门 南门','1649167800746','1649167951015','1','4','小强 先生 13548628741','','http://localhost:8090/files/eb9499a167074ce38ee3cb0d73dc5e93'),(13,5,'oxOsk6hcp97MXaAdCEWwkASoMrbw','10 1 11 1','5',42.60,'立即送出','南京理工大学紫金学院-西门 西门','1649169146983','1649169190988','1','4','小明 先生 15679684235','','http://localhost:8090/files/7a1611001e4b4c85b9ef7677e5e0ec8f'),(14,5,'oxOsk6hcp97MXaAdCEWwkASoMrbw','10 1','5',21.80,'立即送出','南京理工大学紫金学院-西门 西门','1649258147280','1649258572486','2','4','小明 先生 15679684235','','http://localhost:8090/files/9602e23b44f94c6eba6006d748d372d2'),(15,7,'oxOsk6hcp97MXaAdCEWwkASoMrbw','14 1 15 1','7',36.00,'立即送出','南京理工大学紫金学院-西门 西门','1649770325743','1649770415310','2','4','小明 先生 15679684235','123','http://localhost:8090/files/32bd275af9ab4976ba60067152d935ec'),(16,7,'oxOsk6hcp97MXaAdCEWwkASoMrbw','14 4','7',64.00,'立即送出','南京理工大学紫金学院-西门 西门','1649770749925','1649770880834','1','4','小明 先生 15679684235','快点','http://localhost:8090/files/ce402ea5e22e40b6badd7e26414e27f1'),(17,7,'oxOsk6hcp97MXaAdCEWwkASoMrbw','15 3','7',60.00,'立即送出','南京理工大学紫金学院-西门 西门','1649771059468','1649771114188','1','4','小明 先生 15679684235','','http://localhost:8090/files/7bf37adf32a1408dbb8b896245b7fbe9'),(18,7,'oxOsk6hcp97MXaAdCEWwkASoMrbw','14 1 15 1','7',36.00,'立即送出','南京理工大学紫金学院-西门 西门','1649771325025','1649771397632','1','4','小明 先生 15679684235','','http://localhost:8090/files/bab1e68631954e3891e66b568edf956a'),(19,7,'oxOsk6hcp97MXaAdCEWwkASoMrbw','14 1 15 1','7',36.00,'立即送出','南京理工大学紫金学院-西门 西门','1649771336914','1649771401821','1','4','小明 先生 15679684235','','http://localhost:8090/files/0ceec36d54394e5b82e548dbd7f45d41'),(20,7,'oxOsk6hcp97MXaAdCEWwkASoMrbw','14 1','7',16.00,'立即送出','南京理工大学紫金学院-西门 西门','1649855697155','1649855805673','2','4','小明 先生 15679684235','','http://localhost:8090/files/ce1ffc845cc34b14a322a905c1cdc546'),(21,10,'oxOsk6hcp97MXaAdCEWwkASoMrbw','21 1 25 1','9',28.00,'立即送出','南京大学(仙林校区) 南门','1651387177901','1651387406454','2','4','小刚 女士 13879684968','132','http://localhost:8090/files/e6775c4e67c94a978628cdf9fbccfbd1'),(22,5,'oxOsk6hcp97MXaAdCEWwkASoMrbw','10 1','5',21.80,'立即送出','南京大学(仙林校区) 南门','1651387663114','1651387716538','1','4','小刚 女士 13879684968','','http://localhost:8090/files/8be70319f060493790a522debd4dc86b'),(23,5,'oxOsk6hcp97MXaAdCEWwkASoMrbw','10 1','5',21.80,'立即送出','南京大学(仙林校区) 南门','1651387670968','1651387723542','1','4','小刚 女士 13879684968','','http://localhost:8090/files/1cf3c79c0d364bb58f6588f6c9189657'),(24,5,'oxOsk6hcp97MXaAdCEWwkASoMrbw','11 1','5',20.80,'立即送出','南京大学(仙林校区) 南门','1651387675992','1651387728479','1','4','小刚 女士 13879684968','','http://localhost:8090/files/9a959c8830fe4640a2b98bbc36d9c8b9'),(25,5,'oxOsk6hcp97MXaAdCEWwkASoMrbw','10 1','5',21.80,'立即送出','南京大学(仙林校区) 南门','1651387682336','1651387733082','1','4','小刚 女士 13879684968','','http://localhost:8090/files/5a7e788c0eb147e0ac08aeb57d50f4b1'),(26,7,'oxOsk6hcp97MXaAdCEWwkASoMrbw','14 1','请选择',16.00,'立即送出','南京大学(仙林校区) 南门','1651508721930',NULL,'0','2','小刚 女士 13879684968','gkd',NULL),(27,7,'oxOsk6hcp97MXaAdCEWwkASoMrbw','18 1 20 1','请选择',50.00,'立即送出','东城汇 123','1651508751550',NULL,'0','1','123 先生 13854797842','',NULL),(28,7,'oxOsk6hcp97MXaAdCEWwkASoMrbw','20 1','7',22.00,'立即送出','东城汇 123','1651510987344','1651800339475','1','4','123 先生 13854797842','','http://localhost:8090/files/60798e8445854695a342994ab9d9ced5'),(29,7,'oxOsk6hcp97MXaAdCEWwkASoMrbw','14 1 15 1','7',36.00,'立即送出','南京大学(仙林校区) 南门','1651800177122',NULL,'0','3','小刚 女士 13879684968','',NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ride`
--

DROP TABLE IF EXISTS `ride`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ride` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `marchant` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ride`
--

LOCK TABLES `ride` WRITE;
/*!40000 ALTER TABLE `ride` DISABLE KEYS */;
INSERT INTO `ride` VALUES (1,'陈章','cc','cc','13851769673',2),(3,'阿萨德','ss','ss','1343516841432',2),(4,'肉酱面','roujiangmian','123456','13333333333',4),(5,'米饭主义','mifanzhuyi','123456','133333333333',5),(6,'贡茶','gongcha','123456','16666666666',6),(7,'都可','coco','123456','17777777777',7),(8,'柠檬队长','ningmengduizhang','123456','18888888888',8),(9,'果木香','guomuxiang','123456','13846479874',10);
/*!40000 ALTER TABLE `ride` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户账号',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户密码',
  `nikename` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `sex` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `phone` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '手机',
  `love` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收藏',
  `openid` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'wxid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,NULL,NULL,'~1','男','',NULL,'oxOsk6hcp97MXaAdCEWwkASoMrz1'),(3,NULL,NULL,'~2','男','',NULL,'oxOsk6hcp97MXaAdCEWwkASoMrb2'),(4,NULL,NULL,'~3','男','',NULL,'oxOsk6hcp97MXaAdCEWwkASoMrb3'),(5,NULL,NULL,'~','男','','10','oxOsk6hcp97MXaAdCEWwkASoMrbw');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'springboot-vue'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-24 19:44:12
