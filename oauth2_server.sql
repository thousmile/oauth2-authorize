/*
Navicat MySQL Data Transfer

Source Server         : 本机 MySQL 8.0
Source Server Version : 80021
Source Host           : localhost:3306
Source Database       : oauth2_server

Target Server Type    : MYSQL
Target Server Version : 80021
File Encoding         : 65001

Date: 2020-07-25 18:01:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for client_details
-- ----------------------------
DROP TABLE IF EXISTS `client_details`;
CREATE TABLE `client_details` (
  `client_id` char(50) NOT NULL COMMENT 'Client Id',
  `secret` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户端 密钥',
  `name` varchar(255) NOT NULL COMMENT '客户端名称',
  `logo_url` varchar(255) NOT NULL COMMENT '客户端 图标',
  `description` varchar(255) NOT NULL COMMENT '描述',
  `client_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户端类型，内部应用和第三方应用 [ internal , other ]',
  `grant_types` varchar(255) NOT NULL COMMENT '授权类型 数组格式 []',
  `domain_name` varchar(255) NOT NULL COMMENT '域名地址，如果是 授权码模式，\r\n必须校验回调地址是否属于此域名之下',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态： 0.禁用  1.正常  2.被删除',
  `scope` varchar(255) NOT NULL COMMENT '授权作用域',
  `create_time` datetime NOT NULL,
  `last_update_time` datetime NOT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='[ OAuth2.0 ] 客户端详情';

-- ----------------------------
-- Records of client_details
-- ----------------------------
INSERT INTO `client_details` VALUES ('483736405705764864', 'r6BUhkaBpJKktXY33H9UNxaLJZIwE2', '小鲨鱼后台管理系统', 'https://xaaef.com/static/img/logo.6eda394c.png', '小鲨鱼后台管理系统', 'internal', 'authorization_code,client_credentials', 'www.xaaef.com', '1', 'user_base', '2020-07-23 18:00:20', '2020-07-23 18:00:24');
INSERT INTO `client_details` VALUES ('484446609732808704', 'cf2e6b37eb3647c7affdba8991870eee', '测试 app 001 ', 'https://xaaef.com/static/img/logo.6eda394c.png', '测试 app 001 ', 'internal', 'client_credentials', 'localhost', '1', 'user_base', '2020-07-25 16:58:58', '2020-07-25 16:58:58');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_id` char(20) NOT NULL COMMENT '用户ID 通过雪花id 算法生成',
  `username` char(50) NOT NULL COMMENT '用户名，用于登录',
  `mobile` char(15) NOT NULL COMMENT '手机号，用于登录',
  `nickname` varchar(255) NOT NULL COMMENT '用户昵称',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `avatar` varchar(255) NOT NULL COMMENT '头像',
  `gender` tinyint(1) NOT NULL COMMENT '性别。0.女，1.男性，2.未知',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `status` tinyint(1) NOT NULL COMMENT '激活状态: 0=已禁用，1=已激活，2=已删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_update_time` datetime NOT NULL COMMENT '最后一次修改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `ind_unique_username` (`username`),
  UNIQUE KEY `ind_unique_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('484352883471691776', 'admin', '15071523322', '管理员', '$2a$10$VQ9jW0GlKufxQMDn0EQABer/081bkbU52TD45T9wm2y7SxZrsf9XO', 'https://image.xaaef.com/09083333a95f44c38e0b2ef829718a18.jpg', '1', '广东省深圳市光明区星皇大厦', '1', '2020-07-25 10:53:09', '2020-07-25 10:53:11');
INSERT INTO `user_info` VALUES ('484446383982784512', '15905180717', '15905180717', '游琛', '$2a$10$AO9but4GiPT4T25kiuQppOp/FXM8KU3X0Ww.xFFA5jmpTBn0iZm1O', 'https://image.xaaef.com/09083333a95f44c38e0b2ef829718a18.jpg', '0', '澄海二街65号-13-9', '1', '2020-07-25 16:58:04', '2020-07-25 16:58:04');
