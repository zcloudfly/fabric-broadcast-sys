/*
Navicat MySQL Data Transfer

Source Server         : bcts
Source Server Version : 80023
Source Host           : localhost:3306
Source Database       : fbsys

Target Server Type    : MYSQL
Target Server Version : 80023
File Encoding         : 65001

Date: 2021-01-30 20:03:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for appform
-- ----------------------------
DROP TABLE IF EXISTS `appform`;
CREATE TABLE `appform` (
  `id` varchar(32) NOT NULL,
  `infoType` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `checkUser` varchar(20) DEFAULT NULL,
  `checkUserId` varchar(32) DEFAULT NULL,
  `distOrg` varchar(100) DEFAULT NULL,
  `distOrgId` varchar(32) DEFAULT NULL,
  `descr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sendUser` varchar(20) DEFAULT NULL,
  `sendUserId` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of appform
-- ----------------------------
INSERT INTO `appform` VALUES ('415b5671adf84b378322aeb5773f3a0c', null, '打断点', '2021-01-29 16:14:26', '2021-01-30 00:00:00', '谁对谁错的', null, '***昌平分行(000101)', null, '生存手册', null, null);
INSERT INTO `appform` VALUES ('05283d4a6cd04033b3cd02485ba3b781', null, '吃顿饭', '2021-01-19 00:00:00', '2021-01-30 00:00:00', ' 从是CV', null, '***河北分行(000200)', null, 'VBvVC', null, null);
INSERT INTO `appform` VALUES ('fa951a9856b449979be2ec0ea913f9ac', 'pic', '吃顿饭', '2021-01-19 00:00:00', '2021-01-30 00:00:00', ' 从是CV', null, '***河北分行(000200)', null, 'VBvVC', null, null);
INSERT INTO `appform` VALUES ('3309dde3520341eab086ffdc6a398f1f', 'pic', '吃顿饭', '2021-01-19 00:00:00', '2021-01-30 00:00:00', ' 从是CV', null, '***河北分行(000200)', null, 'VBvVC', null, null);
INSERT INTO `appform` VALUES ('b113c43d9f3e4171bf154a2969ac295b', 'pic', '从是CVC', '2021-01-13 00:00:00', '2021-01-23 00:00:00', 'VB方法', null, '***石家庄分行(000201)', '000201', '发遍v发', 'zhangsan', null);
INSERT INTO `appform` VALUES ('bd6e04f9b4164711836f60930477bdec', 'mp4', '广告投放', '2021-01-06 00:00:00', '2021-01-20 00:00:00', '把把把把v', null, '***朝阳分行(000102)', '000102', '方法', 'zhangsan', '1');

-- ----------------------------
-- Table structure for org
-- ----------------------------
DROP TABLE IF EXISTS `org`;
CREATE TABLE `org` (
  `orgid` varchar(32) NOT NULL,
  `orgname` varchar(32) DEFAULT NULL,
  `orgparid` varchar(32) DEFAULT NULL,
  `procode` varchar(10) NOT NULL,
  `area` varchar(10) DEFAULT NULL,
  `level` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of org
-- ----------------------------
INSERT INTO `org` VALUES ('000000', '***总行', null, '0', '0', '0');
INSERT INTO `org` VALUES ('000100', '***北京分行', '000000', '1', '1', '1');
INSERT INTO `org` VALUES ('000101', '***昌平分行', '000100', '1', '1', '2');
INSERT INTO `org` VALUES ('000102', '***朝阳分行', '000100', '1', '1', '2');
INSERT INTO `org` VALUES ('000200', '***河北分行', '000000', '2', '1', '1');
INSERT INTO `org` VALUES ('000201', '***石家庄分行', '000200', '2', '1', '2');
INSERT INTO `org` VALUES ('000202', '***秦皇岛分行', '000200', '2', '1', '2');
INSERT INTO `org` VALUES ('000211', '***秦皇岛海港分行', '000202', '2', '1', '3');
INSERT INTO `org` VALUES ('000800', '***海南分行', '000000', '8', '5', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(32) NOT NULL,
  `acct` varchar(32) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `ctime` datetime DEFAULT NULL,
  `sts` char(1) DEFAULT NULL,
  `pwd` varchar(32) NOT NULL,
  `orgid` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '1', 'zhangsan', null, null, '123456', '000000');
