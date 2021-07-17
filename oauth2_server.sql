/*
Navicat MySQL Data Transfer

Source Server         : localhost-mysql-8.0.25
Source Server Version : 80025
Source Host           : localhost:3306
Source Database       : oauth2_server

Target Server Type    : MYSQL
Target Server Version : 80025
File Encoding         : 65001

Date: 2021-07-17 20:00:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for client_details
-- ----------------------------
DROP TABLE IF EXISTS `client_details`;
CREATE TABLE `client_details` (
  `client_id` varchar(50) NOT NULL COMMENT 'Client Id',
  `secret` varchar(100) NOT NULL COMMENT '客户端 密钥',
  `name` varchar(255) NOT NULL COMMENT '客户端名称',
  `logo` varchar(255) NOT NULL COMMENT '客户端 图标',
  `description` varchar(255) NOT NULL COMMENT '描述',
  `client_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '客户端类型，0.第三方应用  1.内部应用',
  `grant_types` varchar(255) NOT NULL COMMENT '授权类型 json数组格式 ["sms","password"]',
  `domain_name` varchar(255) NOT NULL COMMENT '域名地址，如果是 授权码模式，\r\n必须校验回调地址是否属于此域名之下',
  `scope` varchar(255) NOT NULL COMMENT '授权作用域',
  `del_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '逻辑删除 【0.被删除 1.正常】',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  `last_update_time` datetime NOT NULL COMMENT '最后一次，修改时间',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='[ OAuth2.0 ] 客户端详情';

-- ----------------------------
-- Records of client_details
-- ----------------------------
INSERT INTO `client_details` VALUES ('7KutwpFgFXv0hcvkBO', '$2a$10$lLD53J3fk64uzV9Ti3h/f.J8u5UnZ4Xm7TeM/QLUKhM88lEdUMg6i', '商户管理后台', 'dwa', '多商户管理后台', '1', '[\"we_chat\",\"password\",\"tencent_qq\",\"sms\"]', 'www.xaaef.com', 'read,write', '1', '2021-07-12 17:59:08', '2021-07-12 17:59:16');
INSERT INTO `client_details` VALUES ('nssbTtp5FO6NjZpUwP', '$2a$10$5pXbuKL0l6H.bdc4ud8BCeuLkrzfys0SunrdouwPTKmKuO9DbJk5m', '总部管理后台', 'dwa', '总部管理后台', '1', '[\"*\"]', 'www.xaaef.com', 'read,write', '1', '2021-07-12 17:59:08', '2021-07-12 17:59:16');
INSERT INTO `client_details` VALUES ('VIUvXZmVXmOFh1gYWK', '$2a$10$yUa8IAmb4nC48jnmv0OuAu6zg90bKsHfqhxFNN7kYc83b7sJNL.pC', '华为硬件组,API', 'dwa', '华为硬件组', '0', '[\"client_credentials\"]', 'www.huawei.com', 'read', '1', '2021-07-12 17:59:08', '2021-07-12 17:59:16');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `avatar` varchar(255) NOT NULL COMMENT '头像',
  `username` varchar(50) NOT NULL COMMENT '账号',
  `mobile` varchar(50) NOT NULL COMMENT '手机号码',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `nickname` varchar(100) NOT NULL COMMENT '用户名称',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `gender` tinyint NOT NULL COMMENT '性别[ 0.女  1.男  2.未知]',
  `birthday` date NOT NULL COMMENT '生日',
  `status` tinyint(1) NOT NULL COMMENT '状态 【0.禁用 1.正常 2.锁定 】',
  `user_type` tinyint(1) NOT NULL COMMENT '用户类型 0.租户用户 1. 系统用户 ',
  `admin_flag` tinyint(1) NOT NULL COMMENT '0. 普通用户  1. 管理员',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_update_time` datetime NOT NULL COMMENT '最后一次修改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_ulo5s2i7qoksp54tgwl_mobile` (`mobile`) USING BTREE,
  UNIQUE KEY `UK_6i5ixxulo5s2i7qoksp54tgwl_username` (`username`) USING BTREE,
  UNIQUE KEY `UK_6i5ixxulo5s2i7984erhgwl_email` (`email`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='[ 系统 ] 用户表';

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('609429132107067392', 'https://images.xaaef.com/b9a7abacafd747bbb74cf7cb3de36c1e.png', 'admin', '15071525233', '3548794@qq.com', '管理员', '$2a$10$nslLpuye4W43EcWHIhzBEekSWKlSctKgpmVWFFysz157YCVhlOv52', '1', '1995-08-17', '1', '1', '1', '2021-05-14 11:17:33', '2021-05-14 11:17:36');
INSERT INTO `user_info` VALUES ('609429132107067393', 'https://images.xaaef.com/63e240e0f1de45c1ad7b818405e659c3.jpg', 'test', '15076549849', '57487484@qq.com', '测试', '$2a$10$wlwtSxrlPEstPBKzQX90peRAQqEIL5ycE9Z4U.17EbVovE1Vo3NL6', '0', '1990-06-28', '1', '0', '0', '2021-05-14 11:18:26', '2021-05-14 11:18:29');
INSERT INTO `user_info` VALUES ('609429132107067394', 'https://images.xaaef.com/09083333a95f44c38e0b2ef829718a18.jpg', 'doudou', '15076549869', '15076549869@qq.com', '王逗逗', '$2a$10$9.FdxRVSCT0eLDBkg01hOe99A0mMqMfGTNV8uB9T/NNsmHM1waYmW', '0', '1990-06-28', '1', '0', '0', '2021-05-14 11:18:26', '2021-05-14 11:18:29');

-- ----------------------------
-- Table structure for user_social
-- ----------------------------
DROP TABLE IF EXISTS `user_social`;
CREATE TABLE `user_social` (
  `social_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户社交ID',
  `user_id` bigint NOT NULL COMMENT '用户唯一ID',
  `open_id` varchar(100) NOT NULL DEFAULT '' COMMENT '社交账号唯一ID',
  `social_type` varchar(20) NOT NULL COMMENT 'we_chat. 微信  tencent_qq. 腾讯QQ',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`social_id`),
  UNIQUE KEY `UK_sfewfe49823_open_id` (`open_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='[ 系统 ] 用户社交平台登录';

-- ----------------------------
-- Records of user_social
-- ----------------------------
