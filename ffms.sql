/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : ffms

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 29/11/2020 18:50:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bill
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userid` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `money` float(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `typeid` int(1) NOT NULL COMMENT '类型 1 收入 2 支出',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `paywayid` int(11) NULL DEFAULT NULL COMMENT '支付方式',
  `time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '交易时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userid`(`userid`) USING BTREE,
  INDEX `type`(`typeid`) USING BTREE,
  INDEX `payway`(`paywayid`) USING BTREE,
  CONSTRAINT `bill_ibfk_2` FOREIGN KEY (`typeid`) REFERENCES `type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `bill_ibfk_3` FOREIGN KEY (`paywayid`) REFERENCES `payway` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 199 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for house
-- ----------------------------
DROP TABLE IF EXISTS `house`;
CREATE TABLE `house`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ownerid` int(11) NOT NULL COMMENT '家主(户主)编号',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家庭住址',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `holderid`(`ownerid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of house
-- ----------------------------
INSERT INTO `house` VALUES (1, 3, '昌平区');
INSERT INTO `house` VALUES (4, 21, NULL);

-- ----------------------------
-- Table structure for payway
-- ----------------------------
DROP TABLE IF EXISTS `payway`;
CREATE TABLE `payway`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `payway` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `extra` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of payway
-- ----------------------------
INSERT INTO `payway` VALUES (1, '支付宝', NULL);
INSERT INTO `payway` VALUES (2, '微信', NULL);
INSERT INTO `payway` VALUES (3, '银联', NULL);
INSERT INTO `payway` VALUES (4, '现金', NULL);
INSERT INTO `payway` VALUES (5, '其他', NULL);

-- ----------------------------
-- Table structure for privilege
-- ----------------------------
DROP TABLE IF EXISTS `privilege`;
CREATE TABLE `privilege`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `privilegeNumber` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限编号',
  `privilegeName` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `privilegeTipflag` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单级别',
  `privilegeTypeflag` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '1启用 0禁用',
  `privilegeUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限URL',
  `icon` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of privilege
-- ----------------------------
INSERT INTO `privilege` VALUES (62, '001', '支出管理', '0', '1', '', '&#xe698;');
INSERT INTO `privilege` VALUES (63, '001001', '支出详情', '1', '1', 'pay_details', '&#xe698;');
INSERT INTO `privilege` VALUES (64, '002', '收入管理', '0', '1', NULL, '&#xe702;');
INSERT INTO `privilege` VALUES (65, '002001', '收入详情', '1', '1', 'income_details', '&#xe702;');
INSERT INTO `privilege` VALUES (66, '003', '统计报表', '0', '1', NULL, '&#xe757;');
INSERT INTO `privilege` VALUES (67, '003001', '统计报表', '1', '1', 'chart_line', '&#xe757;');
INSERT INTO `privilege` VALUES (68, '004', '家庭成员管理', '0', '1', NULL, '&#xe726;');
INSERT INTO `privilege` VALUES (69, '005', '系统管理', '0', '1', '', '&#xe696;');
INSERT INTO `privilege` VALUES (70, '005001', '用户管理', '1', '1', 'sys_users', '&#xe6b8;');
INSERT INTO `privilege` VALUES (71, '005002', '角色管理', '1', '1', 'sys_roles', '&#xe70b;');
INSERT INTO `privilege` VALUES (74, '004001', '家庭成员信息', '1', '1', 'sys_users', '&#xe726;');
INSERT INTO `privilege` VALUES (76, '003002', '个人报表', '1', '1', 'chart_self', '&#xe757;');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `roleid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rolename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`roleid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '系统管理员');
INSERT INTO `role` VALUES (2, '家主');
INSERT INTO `role` VALUES (3, '用户');

-- ----------------------------
-- Table structure for roleprivilieges
-- ----------------------------
DROP TABLE IF EXISTS `roleprivilieges`;
CREATE TABLE `roleprivilieges`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `roleID` int(11) NULL DEFAULT NULL COMMENT '角色维护表主键',
  `privilegeID` int(11) NULL DEFAULT NULL COMMENT '权限维护表主键',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `roleID`(`roleID`) USING BTREE,
  INDEX `privilegeID`(`privilegeID`) USING BTREE,
  CONSTRAINT `roleprivilieges_ibfk_1` FOREIGN KEY (`roleID`) REFERENCES `role` (`roleid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `roleprivilieges_ibfk_2` FOREIGN KEY (`privilegeID`) REFERENCES `privilege` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 873 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roleprivilieges
-- ----------------------------
INSERT INTO `roleprivilieges` VALUES (829, 1, 62);
INSERT INTO `roleprivilieges` VALUES (830, 1, 63);
INSERT INTO `roleprivilieges` VALUES (831, 1, 64);
INSERT INTO `roleprivilieges` VALUES (832, 1, 65);
INSERT INTO `roleprivilieges` VALUES (833, 1, 66);
INSERT INTO `roleprivilieges` VALUES (834, 1, 67);
INSERT INTO `roleprivilieges` VALUES (836, 1, 69);
INSERT INTO `roleprivilieges` VALUES (837, 1, 70);
INSERT INTO `roleprivilieges` VALUES (838, 1, 71);
INSERT INTO `roleprivilieges` VALUES (841, 2, 62);
INSERT INTO `roleprivilieges` VALUES (842, 2, 63);
INSERT INTO `roleprivilieges` VALUES (843, 2, 64);
INSERT INTO `roleprivilieges` VALUES (844, 2, 65);
INSERT INTO `roleprivilieges` VALUES (845, 2, 66);
INSERT INTO `roleprivilieges` VALUES (846, 2, 67);
INSERT INTO `roleprivilieges` VALUES (847, 3, 62);
INSERT INTO `roleprivilieges` VALUES (848, 3, 63);
INSERT INTO `roleprivilieges` VALUES (849, 3, 64);
INSERT INTO `roleprivilieges` VALUES (850, 3, 65);
INSERT INTO `roleprivilieges` VALUES (851, 2, 68);
INSERT INTO `roleprivilieges` VALUES (852, 2, 74);
INSERT INTO `roleprivilieges` VALUES (853, 3, 66);
INSERT INTO `roleprivilieges` VALUES (854, 3, 67);
INSERT INTO `roleprivilieges` VALUES (870, 1, 76);
INSERT INTO `roleprivilieges` VALUES (871, 2, 76);
INSERT INTO `roleprivilieges` VALUES (872, 3, 76);

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '支出');
INSERT INTO `type` VALUES (2, '收入');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `realname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `roleid` int(1) NOT NULL DEFAULT 3 COMMENT '角色编号',
  `houseid` int(11) NULL DEFAULT NULL COMMENT '所属家庭编号',
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `houseid`(`houseid`) USING BTREE,
  INDEX `roleid`(`roleid`) USING BTREE,
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`houseid`) REFERENCES `house` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_ibfk_3` FOREIGN KEY (`roleid`) REFERENCES `role` (`roleid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'root', '63a9f0ea7bb98050796b649e85481845', '系管1号', 1, NULL, NULL);
INSERT INTO `user` VALUES (3, 'house1', 'e10adc3949ba59abbe56e057f20f883e', '张三', 2, 1, NULL);
INSERT INTO `user` VALUES (21, 'house2', 'e10adc3949ba59abbe56e057f20f883e', '李四', 2, 4, NULL);
INSERT INTO `user` VALUES (26, 'user1', 'e10adc3949ba59abbe56e057f20f883e', '张小小', 3, NULL, NULL);
INSERT INTO `user` VALUES (27, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin', 3, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
