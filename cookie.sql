/*
Navicat MySQL Data Transfer

Source Server         : 11703080422
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : cookie

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2021-06-01 00:06:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `mid` int(11) NOT NULL COMMENT '被收藏的菜谱id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `time` varchar(25) NOT NULL DEFAULT '1999-09-09 12:00:00' COMMENT '收藏时间',
  PRIMARY KEY (`mid`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES ('21', '3', '2021-05-19 16:16:12');
INSERT INTO `collect` VALUES ('24', '3', '2021-05-09 21:53:08');
INSERT INTO `collect` VALUES ('25', '3', '2021-05-29 09:58:38');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `cid` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `mid` int(11) NOT NULL COMMENT '菜谱id',
  `ccontent` varchar(255) NOT NULL COMMENT '评论内容',
  `ctime` varchar(255) NOT NULL COMMENT '评论时间',
  `replyId` int(4) DEFAULT '0' COMMENT '回复的评论id',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('17', '9', '13', '不好吃', '2020-11-02 20:56:48', '0');
INSERT INTO `comment` VALUES ('40', '2', '21', '马上显示图片', '2021-02-10 15:34:20', '0');
INSERT INTO `comment` VALUES ('48', '3', '22', '评论', '2021-05-09 16:15:44', '0');
INSERT INTO `comment` VALUES ('51', '3', '22', '啦啦啦啦啦', '2021-05-10 14:36:32', '0');
INSERT INTO `comment` VALUES ('52', '3', '24', 'hhh', '2021-05-29 09:57:38', '0');

-- ----------------------------
-- Table structure for focu_user
-- ----------------------------
DROP TABLE IF EXISTS `focu_user`;
CREATE TABLE `focu_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(10) NOT NULL COMMENT '用户id',
  `followId` int(10) NOT NULL COMMENT '被关注的id',
  `time` varchar(20) NOT NULL COMMENT '关注时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of focu_user
-- ----------------------------
INSERT INTO `focu_user` VALUES ('5', '3', '2', '2021-05-21 14:29:21');

-- ----------------------------
-- Table structure for good_info
-- ----------------------------
DROP TABLE IF EXISTS `good_info`;
CREATE TABLE `good_info` (
  `gid` int(4) NOT NULL AUTO_INCREMENT,
  `mid` int(4) NOT NULL COMMENT '食谱id',
  `price` double(10,2) NOT NULL COMMENT '价格',
  `reserve` int(10) NOT NULL DEFAULT '0' COMMENT '库存',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '1正常，0禁用，-1删除',
  `createTime` varchar(20) NOT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of good_info
-- ----------------------------
INSERT INTO `good_info` VALUES ('1', '24', '24.50', '10', '1', '20200102', '2021-05-28 18:23:35');
INSERT INTO `good_info` VALUES ('2', '21', '19.00', '18', '1', '2021-05-28 18:13:32', '2021-05-29 12:12:27');
INSERT INTO `good_info` VALUES ('6', '6', '12.40', '3', '0', '2021-05-28 18:18:10', '');

-- ----------------------------
-- Table structure for like
-- ----------------------------
DROP TABLE IF EXISTS `like`;
CREATE TABLE `like` (
  `uid` int(11) NOT NULL COMMENT '用户id',
  `mid` int(11) NOT NULL COMMENT '用户点赞的菜谱id',
  `time` varchar(255) NOT NULL COMMENT '点赞时间',
  PRIMARY KEY (`mid`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of like
-- ----------------------------
INSERT INTO `like` VALUES ('3', '21', '2021-05-21 14:22:19');
INSERT INTO `like` VALUES ('3', '24', '2021-05-29 09:57:24');
INSERT INTO `like` VALUES ('3', '25', '2021-05-29 09:58:36');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `mid` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜谱id',
  `uid` int(11) NOT NULL COMMENT '发表的用户id',
  `mname` varchar(255) NOT NULL COMMENT '菜谱名字',
  `reportTime` varchar(255) NOT NULL DEFAULT '1999-08-02 00:00:00' COMMENT '发表时间',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `desImg` varchar(255) DEFAULT 'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180410/ejbs8CRP2a.jpg?x-oss-process=image/crop,x_170,y_82,w_435,h_435/resize,w_456/format,webp' COMMENT '成果图',
  `tips` varchar(255) DEFAULT NULL COMMENT '小贴士',
  `prepareTime` varchar(255) DEFAULT NULL COMMENT '准备时间',
  `cookieTime` varchar(255) DEFAULT NULL COMMENT '烹饪时间',
  `flavour` varchar(255) DEFAULT NULL COMMENT '口味',
  `hard` int(255) DEFAULT '1' COMMENT '难度1-4',
  `isSelf` tinyint(255) DEFAULT '0',
  `collectCount` int(255) NOT NULL DEFAULT '0' COMMENT '收藏量',
  `likeCount` int(11) DEFAULT '0' COMMENT '点赞量',
  `views` int(11) DEFAULT '0' COMMENT '浏览量',
  `ischecked` tinyint(255) NOT NULL DEFAULT '0' COMMENT '订单是否审核',
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('6', '2', '糖醋排骨', '1999-08-02 00:00:00', '今年排骨很贵', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1604383031839&di=0eaf64e14e7da1e90b409c26274c5cdd&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180802%2F7ed3df1e03aa4271bee85dfcfc9dfe88.jpeg', 'good', '50', '4', '香甜', '1', '0', '0', '10', '300', '0');
INSERT INTO `menu` VALUES ('13', '7', '银耳莲子粥', '2020-11-02 14:29:56', '最近换季嗓子干燥，银耳莲子有滋补养颜的功能，防止感冒。', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1604383451236&di=776ccebee693ab71a18fbe6f5b6183de&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D661778252%2C3460701089%26fm%3D214%26gp%3D0.jpg', null, null, '1', '酸甜', '2', null, '0', '0', '35', '1');
INSERT INTO `menu` VALUES ('21', '2', '新年快乐', '2021-02-10 15:33:57', '好哦', '5.jpeg', null, null, '4', '酸甜', '1', null, '1', '3', '152', '1');
INSERT INTO `menu` VALUES ('22', '3', 'caipu', '2021-02-21 16:20:26', 'hhh', '5.jpeg', null, null, '2', '香辣', '3', null, '0', '0', '45', '0');
INSERT INTO `menu` VALUES ('24', '3', '修改5', '2021-05-08 15:25:09', '这是一个食谱', '9.jpeg', null, null, '3', '香辣', '2', null, '1', '2', '159', '1');
INSERT INTO `menu` VALUES ('25', '3', '青椒土豆丝', '2021-05-26 22:00:31', '青椒土豆丝好吃', '5.jpeg', null, null, '2', '五香', '2', null, '1', '1', '5', '1');

-- ----------------------------
-- Table structure for mmaterial
-- ----------------------------
DROP TABLE IF EXISTS `mmaterial`;
CREATE TABLE `mmaterial` (
  `mtId` int(11) NOT NULL AUTO_INCREMENT COMMENT '食材id',
  `materialName` varchar(255) NOT NULL DEFAULT '' COMMENT '主材料名字',
  `mamount` varchar(255) NOT NULL COMMENT '主材料数量',
  `mid` int(11) NOT NULL COMMENT '关联的菜谱id',
  PRIMARY KEY (`mtId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mmaterial
-- ----------------------------
INSERT INTO `mmaterial` VALUES ('1', '白菜', '半个', '1');
INSERT INTO `mmaterial` VALUES ('2', '辣椒', '四个', '1');
INSERT INTO `mmaterial` VALUES ('8', '银耳', '适量', '13');
INSERT INTO `mmaterial` VALUES ('9', '莲子', '适量', '13');
INSERT INTO `mmaterial` VALUES ('10', '鸡蛋', '两个', '16');
INSERT INTO `mmaterial` VALUES ('13', '土豆', '一个', '21');
INSERT INTO `mmaterial` VALUES ('14', 'tudou', 'ygi', '22');
INSERT INTO `mmaterial` VALUES ('15', 'tud', '1', '24');
INSERT INTO `mmaterial` VALUES ('16', '马铃薯', '两个', '24');
INSERT INTO `mmaterial` VALUES ('17', '青椒', '两个', '25');
INSERT INTO `mmaterial` VALUES ('18', '蒜', '一个', '25');

-- ----------------------------
-- Table structure for mstep
-- ----------------------------
DROP TABLE IF EXISTS `mstep`;
CREATE TABLE `mstep` (
  `stepId` int(11) NOT NULL AUTO_INCREMENT COMMENT '步骤id',
  `stepCount` int(6) NOT NULL COMMENT '菜谱的第几步',
  `imgUrl` varchar(255) DEFAULT 'http://app-file.beitaichufang.com/img/310636AF348FC3F2D7F7C2040807A97/20180514/d26y6WkSyC.jpg',
  `description` varchar(255) NOT NULL COMMENT '菜谱描述',
  `mid` int(11) NOT NULL COMMENT '关联的菜谱id',
  PRIMARY KEY (`stepId`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mstep
-- ----------------------------
INSERT INTO `mstep` VALUES ('1', '1', 'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180415/ikCsWNn2Jf.jpg', '111', '0');
INSERT INTO `mstep` VALUES ('2', '2', 'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180415/ikCsWNn2Jf.jpg', '222', '0');
INSERT INTO `mstep` VALUES ('3', '3', 'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180415/ikCsWNn2Jf.jpg', '333', '0');
INSERT INTO `mstep` VALUES ('4', '1', 'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180514/d26y6WkSyC.jpg', '洗白菜', '1');
INSERT INTO `mstep` VALUES ('5', '2', 'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180514/d26y6WkSyC.jpg', '洗辣椒', '1');
INSERT INTO `mstep` VALUES ('6', '3', 'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180514/d26y6WkSyC.jpg', '炒菜', '1');
INSERT INTO `mstep` VALUES ('11', '1', 'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180415/ikCsWNn2Jf.jpg', '银耳泡发', '13');
INSERT INTO `mstep` VALUES ('12', '2', 'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180415/ikCsWNn2Jf.jpg', '莲子泡发', '13');
INSERT INTO `mstep` VALUES ('13', '3', 'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180415/ikCsWNn2Jf.jpg', '炖一个小时', '13');
INSERT INTO `mstep` VALUES ('14', '1', 'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180415/ikCsWNn2Jf.jpg', '鸡蛋打散', '16');
INSERT INTO `mstep` VALUES ('15', '2', 'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180415/ikCsWNn2Jf.jpg', '加油', '16');
INSERT INTO `mstep` VALUES ('16', '3', 'http://app-file.beitaichufang.com/img/310636AF348FC3F27D7F7C2040807A97/20180415/ikCsWNn2Jf.jpg', '翻炒', '16');
INSERT INTO `mstep` VALUES ('33', '3', '4.jpeg', '第三步', '21');
INSERT INTO `mstep` VALUES ('34', '2', '5.jpeg', '第二步', '21');
INSERT INTO `mstep` VALUES ('35', '1', '4.jpeg', '第一步', '21');
INSERT INTO `mstep` VALUES ('36', '1', '4.jpeg', '111111', '22');
INSERT INTO `mstep` VALUES ('37', '2', '4.jpeg', '2222222', '22');
INSERT INTO `mstep` VALUES ('38', '3', '5.jpeg', '3333333333', '22');
INSERT INTO `mstep` VALUES ('42', '1', '9.jpeg', '第一步', '24');
INSERT INTO `mstep` VALUES ('43', '2', '4.jpeg', '第二步', '24');
INSERT INTO `mstep` VALUES ('44', '3', '12.jpeg', '第三步', '24');
INSERT INTO `mstep` VALUES ('45', '4', '5.jpeg', '44', '24');
INSERT INTO `mstep` VALUES ('46', '2', '12.jpeg', '洗蒜', '25');
INSERT INTO `mstep` VALUES ('47', '1', '4.jpeg', '洗青椒', '25');
INSERT INTO `mstep` VALUES ('48', '3', '5.jpeg', '炒', '25');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `oid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id号',
  `orderNum` varchar(255) NOT NULL COMMENT '订单号',
  `uid` int(11) NOT NULL COMMENT '订单用户id号',
  `isPay` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否支付：0未支付，1已支付',
  `allPay` double NOT NULL DEFAULT '0' COMMENT '总支付',
  `payTime` varchar(0) DEFAULT NULL COMMENT '支付时间',
  `isShip` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否发货：0未发货，1已发货，2已收货',
  `shipTime` varchar(20) DEFAULT '' COMMENT '发货时间',
  `createTime` varchar(20) NOT NULL COMMENT '订单创建时间',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '订单状态更新时间',
  `status` tinyint(11) NOT NULL DEFAULT '1' COMMENT '订单状态，1正常，0禁用，-1删除',
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('15', 'o1621757777063', '3', '1', '245', '', '2', '', '2021-05-23 16:16:17', '2021-05-26 04:02:40', '1');
INSERT INTO `order` VALUES ('16', 'o1621757989184', '3', '1', '245', '', '2', '', '2021-05-23 16:19:49', '2021-05-29 09:58:14', '1');
INSERT INTO `order` VALUES ('18', 'o1621781095006', '3', '1', '24.5', '', '0', '', '2021-05-23 22:44:55', '2021-05-26 04:12:43', '1');
INSERT INTO `order` VALUES ('19', 'o1622253480606', '3', '1', '19', '', '0', '', '2021-05-29 09:58:00', '', '1');
INSERT INTO `order` VALUES ('20', 'o1622261547627', '3', '1', '19', '', '2', '', '2021-05-29 12:12:27', '2021-05-29 12:13:40', '1');

-- ----------------------------
-- Table structure for order_goods
-- ----------------------------
DROP TABLE IF EXISTS `order_goods`;
CREATE TABLE `order_goods` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `oid` int(4) NOT NULL COMMENT '订单id',
  `mid` int(4) NOT NULL COMMENT '食谱id',
  `count` int(4) NOT NULL COMMENT '商品数量',
  `singlePrice` double(10,2) NOT NULL COMMENT '商品单价',
  `address` varchar(255) NOT NULL COMMENT '收货地址',
  `status` tinyint(2) NOT NULL COMMENT '1正常，0禁用，-1删除',
  `createTime` varchar(20) NOT NULL,
  `updateTime` varchar(20) DEFAULT NULL,
  `userName` varchar(255) NOT NULL,
  `phoneNumber` varchar(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_goods
-- ----------------------------
INSERT INTO `order_goods` VALUES ('6', '15', '24', '10', '24.50', '重庆市渝北区龙兴镇重庆理工大学两江校区1', '1', '2021-05-23 16:16:17', '2021-05-24 15:22:00', 'cc', '123456789');
INSERT INTO `order_goods` VALUES ('7', '16', '24', '10', '24.50', '重庆市渝北区龙兴镇重庆理工大学两江校区', '1', '2021-05-23 16:19:49', '2021-05-24 14:55:59', 'cc', '12345');
INSERT INTO `order_goods` VALUES ('8', '18', '24', '1', '24.50', '重庆市渝北区龙兴镇重庆理工大学两江校区', '1', '2021-05-23 22:44:55', '', 'cyl', '18888888888');
INSERT INTO `order_goods` VALUES ('9', '19', '21', '1', '19.00', '重庆市渝北区龙兴镇重庆理工大学两江校区', '1', '2021-05-29 09:58:00', '', 'cyl', '18888888888');
INSERT INTO `order_goods` VALUES ('10', '20', '21', '1', '19.00', '重庆市渝北区龙兴镇重庆理工大学两江校区', '1', '2021-05-29 12:12:27', '', 'cyl', '18888888888');

-- ----------------------------
-- Table structure for shopcart
-- ----------------------------
DROP TABLE IF EXISTS `shopcart`;
CREATE TABLE `shopcart` (
  `sid` int(11) NOT NULL AUTO_INCREMENT COMMENT '购物车id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `mid` int(11) NOT NULL COMMENT '食谱id',
  `count` int(11) NOT NULL DEFAULT '1' COMMENT '商品数量',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '商品状态，1：正常，0：禁用，-1：删除',
  `createTime` varchar(20) NOT NULL COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shopcart
-- ----------------------------
INSERT INTO `shopcart` VALUES ('4', '3', '24', '1', '1', '2021-05-29 12:09:44', '');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `phoneNumber` varchar(11) NOT NULL,
  `password` varchar(10) NOT NULL,
  `avatar` varchar(255) DEFAULT 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2256160164,2873615387&fm=26&gp=0.jpg' COMMENT '头像地址',
  `fans` int(11) DEFAULT '0' COMMENT '粉丝数量',
  `focus` int(11) DEFAULT '0' COMMENT '关注数量',
  `getLike` int(11) DEFAULT '0' COMMENT '获赞数',
  `admin` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否为管理员身份',
  `address` varchar(255) DEFAULT '' COMMENT '收货地址',
  PRIMARY KEY (`uid`,`name`,`phoneNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2', 'cc', '123', '123', 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2256160164,2873615387&fm=26&gp=0.jpg', '1', '0', '1', '0', '重庆市渝北区');
INSERT INTO `user` VALUES ('3', 'cyl', '18888888888', '123', '9.jpeg', '0', '1', '2', '1', '重庆市渝北区龙兴镇重庆理工大学两江校区');
INSERT INTO `user` VALUES ('7', '白糖酱油', '19823458123', '123', 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2256160164,2873615387&fm=26&gp=0.jpg', '0', '0', '0', '0', null);
INSERT INTO `user` VALUES ('8', '李白', '1i3231', '123', 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2256160164,2873615387&fm=26&gp=0.jpg', '0', '0', '0', '0', null);
INSERT INTO `user` VALUES ('9', '小猴子', '23333', '123', 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2256160164,2873615387&fm=26&gp=0.jpg', '0', '0', '0', '0', null);
INSERT INTO `user` VALUES ('12', 'user01', '12345', '1234', null, '0', '0', '0', '1', null);
