/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : seec_erp

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 03/03/2023 14:05:59
*/
create database seec_erp;
use seec_erp;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account_name` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行账户',
  `balance` decimal(10, 2) NULL DEFAULT NULL COMMENT '余额',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 'zym', 9000001.00);
INSERT INTO `account` VALUES (2, 'syf', 10000000.00);
INSERT INTO `account` VALUES (3, 'sxq', 11000000.00);
INSERT INTO `account` VALUES (4, 'zyf', 10000000.00);
INSERT INTO `account` VALUES (5, 'gongzi', 9998100.00);

-- ----------------------------
-- Table structure for attendance
-- ----------------------------
DROP TABLE IF EXISTS `attendance`;
CREATE TABLE `attendance`  (
  `attendance_no` int(0) NOT NULL AUTO_INCREMENT,
  `employee_id` int(0) NULL DEFAULT NULL,
  `attendance_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`attendance_no`) USING BTREE,
  UNIQUE INDEX `attendance_attendance_no_uindex`(`attendance_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of attendance
-- ----------------------------
INSERT INTO `attendance` VALUES (1, 1, '2022-06-28 10:17:41');
INSERT INTO `attendance` VALUES (2, 2, '2022-06-28 10:17:41');
INSERT INTO `attendance` VALUES (3, 3, '2022-06-28 10:17:41');
INSERT INTO `attendance` VALUES (4, 4, '2022-06-28 10:17:41');
INSERT INTO `attendance` VALUES (5, 5, '2022-06-28 10:17:41');
INSERT INTO `attendance` VALUES (6, 1, '2022-06-29 10:17:41');
INSERT INTO `attendance` VALUES (7, 2, '2022-06-29 10:17:41');
INSERT INTO `attendance` VALUES (8, 3, '2022-06-29 10:17:41');
INSERT INTO `attendance` VALUES (9, 4, '2022-06-29 10:17:41');
INSERT INTO `attendance` VALUES (10, 5, '2022-06-29 10:17:41');
INSERT INTO `attendance` VALUES (11, 4, '2022-07-08 10:44:07');
INSERT INTO `attendance` VALUES (12, 1, '2022-07-08 10:44:07');
INSERT INTO `attendance` VALUES (13, 3, '2022-07-08 10:44:07');
INSERT INTO `attendance` VALUES (14, 2, '2022-07-08 10:44:07');
INSERT INTO `attendance` VALUES (15, 5, '2022-07-08 10:44:07');
INSERT INTO `attendance` VALUES (16, 2, '2022-07-09 18:44:37');
INSERT INTO `attendance` VALUES (17, 1, '2022-07-09 18:44:37');
INSERT INTO `attendance` VALUES (18, 5, '2022-07-09 18:44:37');
INSERT INTO `attendance` VALUES (19, 3, '2022-07-09 18:44:37');
INSERT INTO `attendance` VALUES (20, 4, '2022-07-09 18:44:37');
INSERT INTO `attendance` VALUES (21, 1, '2022-07-09 20:24:04');
INSERT INTO `attendance` VALUES (22, 2, '2022-07-09 20:24:04');
INSERT INTO `attendance` VALUES (23, 5, '2022-07-09 20:24:04');
INSERT INTO `attendance` VALUES (24, 3, '2022-07-09 20:24:04');
INSERT INTO `attendance` VALUES (25, 4, '2022-07-09 20:24:04');
INSERT INTO `attendance` VALUES (26, 1, '2022-07-09 20:25:13');
INSERT INTO `attendance` VALUES (27, 3, '2022-07-09 20:25:13');
INSERT INTO `attendance` VALUES (28, 5, '2022-07-09 20:25:13');
INSERT INTO `attendance` VALUES (29, 4, '2022-07-09 20:25:13');
INSERT INTO `attendance` VALUES (30, 2, '2022-07-09 20:25:13');
INSERT INTO `attendance` VALUES (31, 5, '2022-07-10 09:00:08');
INSERT INTO `attendance` VALUES (32, 1, '2022-07-10 09:00:08');
INSERT INTO `attendance` VALUES (33, 3, '2022-07-10 09:00:08');
INSERT INTO `attendance` VALUES (34, 2, '2022-07-10 09:00:08');
INSERT INTO `attendance` VALUES (35, 4, '2022-07-10 09:00:08');

-- ----------------------------
-- Table structure for base_and_commission
-- ----------------------------
DROP TABLE IF EXISTS `base_and_commission`;
CREATE TABLE `base_and_commission`  (
  `id` int(0) NOT NULL,
  `base_salary` int(0) NULL DEFAULT NULL,
  `commission_rate` int(0) NULL DEFAULT NULL,
  `actual_sales` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_and_commission
-- ----------------------------
INSERT INTO `base_and_commission` VALUES (1, 2000, 2, 60000);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名字',
  `parent_id` int(0) NOT NULL COMMENT '父节点id',
  `is_leaf` tinyint(0) NOT NULL COMMENT '是否为叶节点',
  `item_count` int(0) NOT NULL COMMENT '商品个数',
  `item_index` int(0) NOT NULL COMMENT '插入的下一个index',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '商品', 0, 0, 0, 0);
INSERT INTO `category` VALUES (2, '电子产品', 1, 0, 0, 0);
INSERT INTO `category` VALUES (3, '生活用品', 1, 1, 0, 0);
INSERT INTO `category` VALUES (4, '电脑', 2, 1, 2, 2);
INSERT INTO `category` VALUES (5, '手机', 2, 1, 3, 3);

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类(供应商\\销售商)',
  `level` int(0) NULL DEFAULT NULL COMMENT '级别（五级，一级普通用户，五级VIP客户）',
  `name` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `zipcode` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `line_of_credit` decimal(10, 2) NULL DEFAULT NULL COMMENT '应收额度本公司给客户的信用额度，客户欠本公司的钱的总额不能超过应收额度）',
  `receivable` decimal(10, 2) NULL DEFAULT NULL COMMENT '应收（客户还应付给本公司但还未付的钱， 即本公司应收的钱）',
  `payable` decimal(10, 2) NULL DEFAULT NULL COMMENT '应付（本公司欠客户的钱）',
  `operator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '默认业务员',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (1, '供应商', 1, 'yzh', '12306', '南京大学', '123456', '654321@abc.com', 0.00, 0.00, 7500000.00, 'uncln');
INSERT INTO `customer` VALUES (2, '销售商', 1, 'lxs', '12580', '南哪儿大学', '123457', '12121@cba.com', 20000000.00, 5968750.00, 1.00, 'uncln');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` int(0) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sex` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `phone_number` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, '张三', '男', '1998-03-14', '15951012368');
INSERT INTO `employee` VALUES (2, '李四', '男', '1998-06-06', '13805577899');
INSERT INTO `employee` VALUES (3, '王五', '女', '1998-08-29', '15147603542');
INSERT INTO `employee` VALUES (4, '李一', '女', '1996-06-07', '12345678909');
INSERT INTO `employee` VALUES (5, '赵四', '男', '1980-11-25', '14325674234');

-- ----------------------------
-- Table structure for employee_post
-- ----------------------------
DROP TABLE IF EXISTS `employee_post`;
CREATE TABLE `employee_post`  (
  `id` int(0) NOT NULL,
  `bank_account` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `position` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `salary_calculation_strategy` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `salary_strategy_result` int(0) NULL DEFAULT NULL,
  `year_bonus` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee_post
-- ----------------------------
INSERT INTO `employee_post` VALUES (1, '1676453876589870909', '销售人员', '提成制', 3200, 10000);
INSERT INTO `employee_post` VALUES (2, '6546667589487645323', '库存人员', '月薪制', 5000, 12000);
INSERT INTO `employee_post` VALUES (3, '6674537898092314354', '财务人员', '月薪制', 10000, 18000);
INSERT INTO `employee_post` VALUES (4, '3847568909876574658', '总经理', '年薪制', 100000, 0);
INSERT INTO `employee_post` VALUES (5, '2345678948576909890', '人力资源人员', '月薪制', 5000, 13000);

-- ----------------------------
-- Table structure for monthly_pay
-- ----------------------------
DROP TABLE IF EXISTS `monthly_pay`;
CREATE TABLE `monthly_pay`  (
  `id` int(0) NOT NULL,
  `base_salary` int(0) NULL DEFAULT NULL,
  `post_salary` int(0) NULL DEFAULT NULL,
  `post_rank` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of monthly_pay
-- ----------------------------
INSERT INTO `monthly_pay` VALUES (2, 5000, 0, '初级');
INSERT INTO `monthly_pay` VALUES (3, 5000, 5000, '高级');
INSERT INTO `monthly_pay` VALUES (5, 5000, 0, '初级');

-- ----------------------------
-- Table structure for pay_sheet
-- ----------------------------
DROP TABLE IF EXISTS `pay_sheet`;
CREATE TABLE `pay_sheet`  (
  `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收款单id',
  `customer` int(0) NULL DEFAULT NULL COMMENT '客户',
  `operator` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '总额汇总'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_sheet
-- ----------------------------
INSERT INTO `pay_sheet` VALUES ('FKD-20220523-00000', 2, 'caiwu', '审批完成', '2022-05-23 23:13:59', 1000000.00);
INSERT INTO `pay_sheet` VALUES ('FKD-20220523-00001', 2, 'caiwu', '审批完成', '2022-05-23 23:14:34', 4400000.00);

-- ----------------------------
-- Table structure for pay_sheet_content
-- ----------------------------
DROP TABLE IF EXISTS `pay_sheet_content`;
CREATE TABLE `pay_sheet_content`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `pay_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收款单id',
  `bank_account` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行账户',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '转账金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_sheet_content
-- ----------------------------
INSERT INTO `pay_sheet_content` VALUES (1, 'FKD-20220523-00000', 'zym', 1000000.00, '');
INSERT INTO `pay_sheet_content` VALUES (2, 'FKD-20220523-00001', 'zym', 2200000.00, '');
INSERT INTO `pay_sheet_content` VALUES (3, 'FKD-20220523-00001', 'syf', 2200000.00, '');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类id(11位) + 位置(5位) = 编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名字',
  `category_id` int(0) NOT NULL COMMENT '商品分类id',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品型号',
  `quantity` int(0) NOT NULL COMMENT '商品数量',
  `purchase_price` decimal(10, 2) NOT NULL COMMENT '进价',
  `retail_price` decimal(10, 2) NOT NULL COMMENT '零售价',
  `recent_pp` decimal(10, 2) NULL DEFAULT NULL COMMENT '最近进价',
  `recent_rp` decimal(10, 2) NULL DEFAULT NULL COMMENT '最近零售价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('0000000000400000', '戴尔电脑', 4, '戴尔(DELL)Vostro笔记本电脑5410 123色 戴尔成就3500Vostro1625D', 500, 3000.00, 4056.00, 1900.00, 10000.00);
INSERT INTO `product` VALUES ('0000000000400001', '小米手机', 4, 'lalalalala', 1000, 2000.00, 3500.00, 2700.00, 3800.00);
INSERT INTO `product` VALUES ('0000000000500000', 'intel电脑', 5, 'iphone14maxpro', 0, 6000.00, 10000.00, NULL, NULL);
INSERT INTO `product` VALUES ('0000000000500001', 'iphone', 5, 'iphone14普通版', 0, 4000.00, 8000.00, NULL, NULL);
INSERT INTO `product` VALUES ('0000000000500002', '坚果', 5, 'pro3', 0, 2499.00, 3199.00, NULL, NULL);

-- ----------------------------
-- Table structure for purchase_returns_sheet
-- ----------------------------
DROP TABLE IF EXISTS `purchase_returns_sheet`;
CREATE TABLE `purchase_returns_sheet`  (
  `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '进货退货单id',
  `purchase_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联的进货单id',
  `operator` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '退货的总金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of purchase_returns_sheet
-- ----------------------------
INSERT INTO `purchase_returns_sheet` VALUES ('JHTHD-20220523-00000', 'JHD-20220523-00001', 'xiaoshoujingli', '审批完成', '2022-05-23 23:22:41', 800000.00, '退钱！');
INSERT INTO `purchase_returns_sheet` VALUES ('JHTHD-20220523-00001', 'JHD-20220523-00000', 'xiaoshoujingli', '审批完成', '2022-05-23 23:22:54', 500000.00, '退钱！！！');
INSERT INTO `purchase_returns_sheet` VALUES ('JHTHD-20220523-00002', 'JHD-20220523-00000', 'xiaoshoujingli', '审批完成', '2022-05-23 23:34:34', 100000.00, '退钱++++');
INSERT INTO `purchase_returns_sheet` VALUES ('JHTHD-20220523-00003', 'JHD-20220523-00000', 'xiaoshoujingli', '审批完成', '2022-05-23 23:39:30', 200000.00, 'mmmmm');
INSERT INTO `purchase_returns_sheet` VALUES ('JHTHD-20220523-00004', 'JHD-20220523-00000', '67', '审批完成', '2022-05-23 23:42:32', 200000.00, 'mmmmk');
INSERT INTO `purchase_returns_sheet` VALUES ('JHTHD-20220524-00000', 'JHD-20220523-00001', 'xiaoshoujingli', '待二级审批', '2022-05-24 01:00:18', 160000.00, NULL);
INSERT INTO `purchase_returns_sheet` VALUES ('JHTHD-20220524-00001', 'JHD-20220523-00002', 'xiaoshoujingli', '待一级审批', '2022-05-24 01:00:34', 140000.00, NULL);

-- ----------------------------
-- Table structure for purchase_returns_sheet_content
-- ----------------------------
DROP TABLE IF EXISTS `purchase_returns_sheet_content`;
CREATE TABLE `purchase_returns_sheet_content`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `purchase_returns_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '进货退货单id',
  `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品id',
  `quantity` int(0) NULL DEFAULT NULL COMMENT '数量',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '该商品的总金额',
  `unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '该商品的单价',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of purchase_returns_sheet_content
-- ----------------------------
INSERT INTO `purchase_returns_sheet_content` VALUES (23, 'JHTHD-20220523-00000', '0000000000400000', 500, 600000.00, 1200.00, 'b');
INSERT INTO `purchase_returns_sheet_content` VALUES (24, 'JHTHD-20220523-00000', '0000000000400001', 100, 200000.00, 2000.00, 'b');
INSERT INTO `purchase_returns_sheet_content` VALUES (25, 'JHTHD-20220523-00001', '0000000000400000', 500, 500000.00, 1000.00, 'a');
INSERT INTO `purchase_returns_sheet_content` VALUES (26, 'JHTHD-20220523-00002', '0000000000400000', 100, 100000.00, 1000.00, 'a');
INSERT INTO `purchase_returns_sheet_content` VALUES (27, 'JHTHD-20220523-00003', '0000000000400000', 200, 200000.00, 1000.00, 'a');
INSERT INTO `purchase_returns_sheet_content` VALUES (28, 'JHTHD-20220523-00004', '0000000000400000', 200, 200000.00, 1000.00, 'a');
INSERT INTO `purchase_returns_sheet_content` VALUES (29, 'JHTHD-20220524-00000', '0000000000400000', 50, 60000.00, 1200.00, 'b');
INSERT INTO `purchase_returns_sheet_content` VALUES (30, 'JHTHD-20220524-00000', '0000000000400001', 50, 100000.00, 2000.00, 'b');
INSERT INTO `purchase_returns_sheet_content` VALUES (31, 'JHTHD-20220524-00001', '0000000000400000', 0, 0.00, 1300.00, 'c');
INSERT INTO `purchase_returns_sheet_content` VALUES (32, 'JHTHD-20220524-00001', '0000000000400001', 50, 140000.00, 2800.00, 'c');

-- ----------------------------
-- Table structure for purchase_sheet
-- ----------------------------
DROP TABLE IF EXISTS `purchase_sheet`;
CREATE TABLE `purchase_sheet`  (
  `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '进货单单据编号（格式为：JHD-yyyyMMdd-xxxxx',
  `supplier` int(0) NULL DEFAULT NULL COMMENT '供应商',
  `operator` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '总额合计',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of purchase_sheet
-- ----------------------------
INSERT INTO `purchase_sheet` VALUES ('JHD-20220523-00000', 1, 'xiaoshoujingli', 'a', 1000000.00, '审批完成', '2022-05-23 23:13:59');
INSERT INTO `purchase_sheet` VALUES ('JHD-20220523-00001', 1, 'xiaoshoujingli', 'b', 2200000.00, '审批完成', '2022-05-23 23:14:34');
INSERT INTO `purchase_sheet` VALUES ('JHD-20220523-00002', 1, 'xiaoshoujingli', 'c', 3450000.00, '审批完成', '2022-05-23 23:15:57');
INSERT INTO `purchase_sheet` VALUES ('JHD-20220524-00000', 1, 'xiaoshoujingli', NULL, 2200000.00, '待二级审批', '2022-05-24 00:56:54');
INSERT INTO `purchase_sheet` VALUES ('JHD-20220524-00001', 1, 'xiaoshoujingli', NULL, 3240000.00, '待一级审批', '2022-05-24 00:57:29');
INSERT INTO `purchase_sheet` VALUES ('JHD-20220524-00002', 1, 'xiaoshoujingli', NULL, 1650000.00, '审批完成', '2022-05-24 01:02:04');

-- ----------------------------
-- Table structure for purchase_sheet_content
-- ----------------------------
DROP TABLE IF EXISTS `purchase_sheet_content`;
CREATE TABLE `purchase_sheet_content`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `purchase_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '进货单id',
  `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品id',
  `quantity` int(0) NULL DEFAULT NULL COMMENT '数量',
  `unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of purchase_sheet_content
-- ----------------------------
INSERT INTO `purchase_sheet_content` VALUES (51, 'JHD-20220523-00000', '0000000000400000', 1000, 1000.00, 1000000.00, 'a');
INSERT INTO `purchase_sheet_content` VALUES (52, 'JHD-20220523-00001', '0000000000400000', 1000, 1200.00, 1200000.00, 'b');
INSERT INTO `purchase_sheet_content` VALUES (53, 'JHD-20220523-00001', '0000000000400001', 500, 2000.00, 1000000.00, 'b');
INSERT INTO `purchase_sheet_content` VALUES (54, 'JHD-20220523-00002', '0000000000400000', 500, 1300.00, 650000.00, 'c');
INSERT INTO `purchase_sheet_content` VALUES (55, 'JHD-20220523-00002', '0000000000400001', 1000, 2800.00, 2800000.00, 'c');
INSERT INTO `purchase_sheet_content` VALUES (56, 'JHD-20220524-00000', '0000000000400000', 500, 1500.00, 750000.00, '');
INSERT INTO `purchase_sheet_content` VALUES (57, 'JHD-20220524-00000', '0000000000400001', 500, 2900.00, 1450000.00, NULL);
INSERT INTO `purchase_sheet_content` VALUES (58, 'JHD-20220524-00001', '0000000000400000', 600, 1900.00, 1140000.00, '');
INSERT INTO `purchase_sheet_content` VALUES (59, 'JHD-20220524-00001', '0000000000400001', 700, 3000.00, 2100000.00, NULL);
INSERT INTO `purchase_sheet_content` VALUES (60, 'JHD-20220524-00002', '0000000000400000', 300, 1900.00, 570000.00, '');
INSERT INTO `purchase_sheet_content` VALUES (61, 'JHD-20220524-00002', '0000000000400001', 400, 2700.00, 1080000.00, NULL);

-- ----------------------------
-- Table structure for receive_sheet
-- ----------------------------
DROP TABLE IF EXISTS `receive_sheet`;
CREATE TABLE `receive_sheet`  (
  `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收款单id',
  `customer` int(0) NULL DEFAULT NULL COMMENT '客户',
  `operator` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '总额汇总'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of receive_sheet
-- ----------------------------
INSERT INTO `receive_sheet` VALUES ('SKD-20220523-00000', 1, 'caiwu', '审批完成', '2022-05-23 23:13:59', 1000000.00);
INSERT INTO `receive_sheet` VALUES ('SKD-20220523-00001', 1, 'caiwu', '审批完成', '2022-05-23 23:14:34', 2400000.00);
INSERT INTO `receive_sheet` VALUES ('SKD-20220708-00000', 2, 'zym3', '审批完成', '2022-07-08 10:45:05', 1.00);

-- ----------------------------
-- Table structure for receive_sheet_content
-- ----------------------------
DROP TABLE IF EXISTS `receive_sheet_content`;
CREATE TABLE `receive_sheet_content`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `receive_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收款单id',
  `bank_account` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行账户',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '转账金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of receive_sheet_content
-- ----------------------------
INSERT INTO `receive_sheet_content` VALUES (1, 'SKD-20220523-00000', 'sxq', 1000000.00, '');
INSERT INTO `receive_sheet_content` VALUES (2, 'SKD-20220523-00001', 'zyf', 2200000.00, '');
INSERT INTO `receive_sheet_content` VALUES (3, 'SKD-20220523-00001', 'sxq', 200000.00, '');
INSERT INTO `receive_sheet_content` VALUES (4, 'SKD-20220708-00000', 'zym', 1.00, '');

-- ----------------------------
-- Table structure for sale_returns_sheet
-- ----------------------------
DROP TABLE IF EXISTS `sale_returns_sheet`;
CREATE TABLE `sale_returns_sheet`  (
  `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售退货单id',
  `sale_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联的销售单id',
  `operator` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '退货的总金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sale_returns_sheet
-- ----------------------------
INSERT INTO `sale_returns_sheet` VALUES ('XSTHD-20220523-00000', 'XSD-20220523-00001', 'xiaoshoujingli', '审批完成', '2022-05-23 23:22:41', 800000.00, '退钱！');
INSERT INTO `sale_returns_sheet` VALUES ('XSTHD-20220523-00001', 'XSD-20220523-00000', 'xiaoshoujingli', '审批完成', '2022-05-23 23:22:54', 500000.00, '退钱！！！');
INSERT INTO `sale_returns_sheet` VALUES ('XSTHD-20220523-00002', 'XSD-20220523-00000', 'xiaoshoujingli', '审批完成', '2022-05-23 23:34:34', 100000.00, '退钱++++');
INSERT INTO `sale_returns_sheet` VALUES ('XSTHD-20220523-00003', 'XSD-20220523-00000', 'xiaoshoujingli', '审批完成', '2022-05-23 23:39:30', 200000.00, 'mmmmm');
INSERT INTO `sale_returns_sheet` VALUES ('XSTHD-20220523-00004', 'XSD-20220523-00000', '67', '审批完成', '2022-05-23 23:42:32', 200000.00, 'mmmmk');
INSERT INTO `sale_returns_sheet` VALUES ('XSTHD-20220524-00000', 'XSD-20220523-00001', 'xiaoshoujingli', '审批完成', '2022-05-24 01:00:18', 160000.00, NULL);
INSERT INTO `sale_returns_sheet` VALUES ('XSTHD-20220524-00001', 'XSD-20220523-00002', 'xiaoshoujingli', '审批完成', '2022-05-24 01:00:34', 140000.00, NULL);
INSERT INTO `sale_returns_sheet` VALUES ('XSTHD-20220708-00000', 'XSD-20220524-00000', 'zym6', '审批完成', '2022-07-08 10:46:14', -114400.00, '1');

-- ----------------------------
-- Table structure for sale_returns_sheet_content
-- ----------------------------
DROP TABLE IF EXISTS `sale_returns_sheet_content`;
CREATE TABLE `sale_returns_sheet_content`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `sale_returns_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '进货退货单id',
  `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品id',
  `quantity` int(0) NULL DEFAULT NULL COMMENT '数量',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '该商品的总金额',
  `unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '该商品的单价',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sale_returns_sheet_content
-- ----------------------------
INSERT INTO `sale_returns_sheet_content` VALUES (23, 'XSTHD-20220523-00000', '0000000000400000', 500, 600000.00, 1200.00, 'b');
INSERT INTO `sale_returns_sheet_content` VALUES (24, 'XSTHD-20220523-00000', '0000000000400001', 100, 200000.00, 2000.00, 'b');
INSERT INTO `sale_returns_sheet_content` VALUES (25, 'XSTHD-20220523-00001', '0000000000400000', 500, 500000.00, 1000.00, 'a');
INSERT INTO `sale_returns_sheet_content` VALUES (26, 'XSTHD-20220523-00002', '0000000000400000', 100, 100000.00, 1000.00, 'a');
INSERT INTO `sale_returns_sheet_content` VALUES (27, 'XSTHD-20220523-00003', '0000000000400000', 200, 200000.00, 1000.00, 'a');
INSERT INTO `sale_returns_sheet_content` VALUES (28, 'XSTHD-20220523-00004', '0000000000400000', 200, 200000.00, 1000.00, 'a');
INSERT INTO `sale_returns_sheet_content` VALUES (29, 'XSTHD-20220524-00000', '0000000000400000', 50, 60000.00, 1200.00, 'b');
INSERT INTO `sale_returns_sheet_content` VALUES (30, 'XSTHD-20220524-00000', '0000000000400001', 50, 100000.00, 2000.00, 'b');
INSERT INTO `sale_returns_sheet_content` VALUES (31, 'XSTHD-20220524-00001', '0000000000400000', 0, 0.00, 1300.00, 'c');
INSERT INTO `sale_returns_sheet_content` VALUES (32, 'XSTHD-20220524-00001', '0000000000400001', 50, 140000.00, 2800.00, 'c');
INSERT INTO `sale_returns_sheet_content` VALUES (33, 'XSTHD-20220708-00000', '0000000000400000', 1, 3500.00, 3500.00, '');
INSERT INTO `sale_returns_sheet_content` VALUES (34, 'XSTHD-20220708-00000', '0000000000400001', 1, 3500.00, 3500.00, NULL);

-- ----------------------------
-- Table structure for sale_sheet
-- ----------------------------
DROP TABLE IF EXISTS `sale_sheet`;
CREATE TABLE `sale_sheet`  (
  `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '进货单单据编号（格式为：JHD-yyyyMMdd-xxxxx',
  `supplier` int(0) NULL DEFAULT NULL COMMENT '供应商',
  `operator` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `salesman` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务员',
  `raw_total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '折让前总金额',
  `discount` decimal(10, 2) NULL DEFAULT NULL COMMENT '折扣',
  `final_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '折让后金额',
  `voucher_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '代金券金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sale_sheet
-- ----------------------------
INSERT INTO `sale_sheet` VALUES ('XSD-20220523-00000', 2, 'xiaoshoujingli', '卖卖卖', '审批失败', '2022-05-23 23:46:12', 'xiaoshoujingli', 1300000.00, 0.80, 1039800.00, 200.00);
INSERT INTO `sale_sheet` VALUES ('XSD-20220524-00000', 2, 'xiaoshoujingli', NULL, '审批完成', '2022-05-24 00:04:37', 'xiaoshoujingli', 4200000.00, 0.80, 3359800.00, 200.00);
INSERT INTO `sale_sheet` VALUES ('XSD-20220524-00001', 2, 'xiaoshoujingli', NULL, '审批完成', '2022-05-24 00:32:41', 'xiaoshoujingli', 620000.00, 0.80, 495800.00, 200.00);
INSERT INTO `sale_sheet` VALUES ('XSD-20220524-00002', 2, 'xiaoshoujingli', NULL, '审批完成', '2022-05-24 00:45:25', 'xiaoshoujingli', 720000.00, 0.80, 575800.00, 200.00);
INSERT INTO `sale_sheet` VALUES ('XSD-20220524-00003', 2, 'xiaoshoujingli', NULL, '审批完成', '2022-05-24 01:05:15', 'xiaoshoujingli', 660000.00, 0.80, 527700.00, 300.00);
INSERT INTO `sale_sheet` VALUES ('XSD-20220524-00004', 2, 'xiaoshoujingli', NULL, '待一级审批', '2022-05-24 01:07:23', 'xiaoshoujingli', 2900000.00, 0.90, 2609800.00, 200.00);
INSERT INTO `sale_sheet` VALUES ('XSD-20220708-00000', 2, 'zym6', '1', '审批完成', '2022-07-08 10:53:25', 'zym6', 10000.00, 0.98, 9650.00, 150.00);
INSERT INTO `sale_sheet` VALUES ('XSD-20220708-00001', 2, 'zym6', '1', '待一级审批', '2022-07-08 11:02:00', 'zym6', 20000.00, 0.98, 19350.00, 250.00);

-- ----------------------------
-- Table structure for sale_sheet_content
-- ----------------------------
DROP TABLE IF EXISTS `sale_sheet_content`;
CREATE TABLE `sale_sheet_content`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `sale_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '进货单id',
  `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品id',
  `quantity` int(0) NULL DEFAULT NULL COMMENT '数量',
  `unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sale_sheet_content
-- ----------------------------
INSERT INTO `sale_sheet_content` VALUES (26, 'XSD-20220523-00000', '0000000000400000', 100, 5000.00, 500000.00, '卖卖卖1');
INSERT INTO `sale_sheet_content` VALUES (27, 'XSD-20220523-00000', '0000000000400001', 400, 2000.00, 800000.00, '卖卖卖2');
INSERT INTO `sale_sheet_content` VALUES (28, 'XSD-20220524-00000', '0000000000400000', 600, 3500.00, 2100000.00, '');
INSERT INTO `sale_sheet_content` VALUES (29, 'XSD-20220524-00000', '0000000000400001', 600, 3500.00, 2100000.00, NULL);
INSERT INTO `sale_sheet_content` VALUES (30, 'XSD-20220524-00001', '0000000000400000', 100, 2200.00, 220000.00, '');
INSERT INTO `sale_sheet_content` VALUES (31, 'XSD-20220524-00001', '0000000000400001', 100, 4000.00, 400000.00, NULL);
INSERT INTO `sale_sheet_content` VALUES (32, 'XSD-20220524-00002', '0000000000400000', 100, 3000.00, 300000.00, '');
INSERT INTO `sale_sheet_content` VALUES (33, 'XSD-20220524-00002', '0000000000400001', 100, 4200.00, 420000.00, NULL);
INSERT INTO `sale_sheet_content` VALUES (34, 'XSD-20220524-00003', '0000000000400000', 100, 2800.00, 280000.00, '');
INSERT INTO `sale_sheet_content` VALUES (35, 'XSD-20220524-00003', '0000000000400001', 100, 3800.00, 380000.00, NULL);
INSERT INTO `sale_sheet_content` VALUES (36, 'XSD-20220524-00004', '0000000000400000', 300, 3000.00, 900000.00, '');
INSERT INTO `sale_sheet_content` VALUES (37, 'XSD-20220524-00004', '0000000000400001', 500, 4000.00, 2000000.00, NULL);
INSERT INTO `sale_sheet_content` VALUES (38, 'XSD-20220708-00000', '0000000000400000', 1, 10000.00, 10000.00, '1');
INSERT INTO `sale_sheet_content` VALUES (42, 'XSD-20220708-00001', '0000000000400000', 2, 10000.00, 20000.00, '1');

-- ----------------------------
-- Table structure for total_amount_promotion
-- ----------------------------
DROP TABLE IF EXISTS `total_amount_promotion`;
CREATE TABLE `total_amount_promotion`  (
  `total_amount_min` int(0) NULL DEFAULT NULL,
  `gift_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `voucher` int(0) NULL DEFAULT NULL,
  `begin_time` datetime(0) NULL DEFAULT NULL COMMENT '起始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of total_amount_promotion
-- ----------------------------
INSERT INTO `total_amount_promotion` VALUES (10000, '三星', 100, '2022-07-03 00:00:00', '2023-07-03 00:00:00');
INSERT INTO `total_amount_promotion` VALUES (20000, '坚果', 200, '2022-07-03 00:00:00', '2023-07-03 00:00:00');
INSERT INTO `total_amount_promotion` VALUES (30000, '小米手机', 300, '2022-07-03 00:00:00', '2023-07-03 00:00:00');
INSERT INTO `total_amount_promotion` VALUES (50000, 'iphone', 500, '2022-07-03 00:00:00', '2023-07-03 00:00:00');
INSERT INTO `total_amount_promotion` VALUES (100000, 'intel电脑', 1000, '2022-07-03 00:00:00', '2023-07-03 00:00:00');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户身份',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'seecoder', '123456', 'INVENTORY_MANAGER');
INSERT INTO `user` VALUES (2, 'uncln', '123456', 'INVENTORY_MANAGER');
INSERT INTO `user` VALUES (3, 'kucun', '123456', 'INVENTORY_MANAGER');
INSERT INTO `user` VALUES (4, 'sky', '123456', 'ADMIN');
INSERT INTO `user` VALUES (5, 'zxr', '123456', 'SALE_MANAGER');
INSERT INTO `user` VALUES (6, '67', '123456', 'GM');
INSERT INTO `user` VALUES (7, 'xiaoshou', '123456', 'SALE_STAFF');
INSERT INTO `user` VALUES (8, 'Leone', '123456', 'GM');
INSERT INTO `user` VALUES (9, 'xiaoshoujingli', '123456', 'SALE_MANAGER');
INSERT INTO `user` VALUES (12, 'zym1', '123456', 'INVENTORY_MANAGER');
INSERT INTO `user` VALUES (13, 'zym2', '123456', 'SALE_STAFF');
INSERT INTO `user` VALUES (14, 'zym3', '123456', 'FINANCIAL_STAFF');
INSERT INTO `user` VALUES (15, 'zym4', '123456', 'SALE_MANAGER');
INSERT INTO `user` VALUES (16, 'zym5', '123456', 'HR');
INSERT INTO `user` VALUES (17, 'zym6', '123456', 'GM');

-- ----------------------------
-- Table structure for user_level_promotion
-- ----------------------------
DROP TABLE IF EXISTS `user_level_promotion`;
CREATE TABLE `user_level_promotion`  (
  `user_level` int(0) NULL DEFAULT NULL,
  `gift_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `discount` double NULL DEFAULT NULL,
  `voucher` int(0) NULL DEFAULT NULL,
  `begin_time` datetime(0) NULL DEFAULT NULL COMMENT '起始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_level_promotion
-- ----------------------------
INSERT INTO `user_level_promotion` VALUES (1, '三星', 0.98, 50, '2022-07-03 00:00:00', '2023-07-03 00:00:00');
INSERT INTO `user_level_promotion` VALUES (2, '三星', 0.95, 100, '2022-07-03 00:00:00', '2023-07-03 00:00:00');
INSERT INTO `user_level_promotion` VALUES (3, '坚果', 0.9, 200, '2022-07-03 00:00:00', '2023-07-03 00:00:00');
INSERT INTO `user_level_promotion` VALUES (4, '小米手机', 0.8, 300, '2022-07-03 00:00:00', '2023-07-03 00:00:00');
INSERT INTO `user_level_promotion` VALUES (5, 'iphone', 0.6, 500, '2022-07-03 00:00:00', '2023-07-03 00:00:00');

-- ----------------------------
-- Table structure for wage_sheet
-- ----------------------------
DROP TABLE IF EXISTS `wage_sheet`;
CREATE TABLE `wage_sheet`  (
  `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工资单id',
  `operator` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `employee_id` int(0) NULL DEFAULT NULL COMMENT '员工编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工姓名',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  `bank_account` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '应发工资',
  `tax` decimal(10, 2) NULL DEFAULT NULL COMMENT '应扣税款',
  `final_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '实发金额'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wage_sheet
-- ----------------------------
INSERT INTO `wage_sheet` VALUES ('GZD-20220523-00000', 'caiwu', 1, '张三', '审批完成', '1676453876589870909', '2022-05-23 23:13:59', 2000.00, 100.00, 1900.00);
INSERT INTO `wage_sheet` VALUES ('GZD-20220523-00001', 'caiwu', 2, '李四', '审批完成', '6546667589487645323', '2022-05-23 23:14:34', 2500.00, 120.00, 2380.00);

-- ----------------------------
-- Table structure for warehouse
-- ----------------------------
DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '库存id',
  `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品编号',
  `quantity` int(0) NOT NULL COMMENT '数量',
  `purchase_price` decimal(10, 2) NOT NULL COMMENT '进价',
  `batch_id` int(0) NOT NULL COMMENT '批次',
  `production_date` datetime(0) NULL DEFAULT NULL COMMENT '出厂日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehouse
-- ----------------------------
INSERT INTO `warehouse` VALUES (16, '0000000000400000', 0, 1000.00, 0, NULL);
INSERT INTO `warehouse` VALUES (17, '0000000000400000', 200, 1200.00, 1, NULL);
INSERT INTO `warehouse` VALUES (18, '0000000000400001', 400, 2000.00, 1, NULL);
INSERT INTO `warehouse` VALUES (19, '0000000000400000', 0, 1300.00, 2, NULL);
INSERT INTO `warehouse` VALUES (20, '0000000000400001', 200, 2800.00, 2, NULL);
INSERT INTO `warehouse` VALUES (21, '0000000000400000', 300, 1900.00, 3, NULL);
INSERT INTO `warehouse` VALUES (22, '0000000000400001', 400, 2700.00, 3, NULL);

-- ----------------------------
-- Table structure for warehouse_bsd_sheet
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_bsd_sheet`;
CREATE TABLE `warehouse_bsd_sheet`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'BSD + 日期 + index = 报损单编号',
  `batch_id` int(0) NOT NULL COMMENT '批次',
  `operator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品id',
  `bs_quantity` int(0) NOT NULL COMMENT '商品报损数量',
  `purchase_price` decimal(10, 2) NOT NULL COMMENT '单价',
  `production_date` datetime(0) NULL DEFAULT NULL COMMENT '出厂日期',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehouse_bsd_sheet
-- ----------------------------
INSERT INTO `warehouse_bsd_sheet` VALUES ('BSD-20220708-00000', 0, 'zym', '2022-07-08 11:02:00', '审批完成', '0000000000400000', 2, 3000.00, '2022-07-08 11:02:00', NULL);

-- ----------------------------
-- Table structure for warehouse_byd_sheet
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_byd_sheet`;
CREATE TABLE `warehouse_byd_sheet`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'BYD + 日期 + index = 报溢单编号',
  `batch_id` int(0) NOT NULL COMMENT '批次',
  `operator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品id',
  `by_quantity` int(0) NOT NULL COMMENT '商品报溢数量',
  `purchase_price` decimal(10, 2) NOT NULL COMMENT '单价',
  `production_date` datetime(0) NULL DEFAULT NULL COMMENT '出厂日期',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehouse_byd_sheet
-- ----------------------------
INSERT INTO `warehouse_byd_sheet` VALUES ('BYD-20220708-00000', 0, 'zym', '2022-07-08 11:02:00', '审批完成', '0000000000500002', 5, 2499.00, '2022-07-08 11:02:00', NULL);

-- ----------------------------
-- Table structure for warehouse_input_sheet
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_input_sheet`;
CREATE TABLE `warehouse_input_sheet`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'RKD + 日期 + index = 入库单编号',
  `batch_id` int(0) NOT NULL COMMENT '批次',
  `operator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  `purchase_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联的进货单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehouse_input_sheet
-- ----------------------------
INSERT INTO `warehouse_input_sheet` VALUES ('RKD-20220523-00000', 0, 'kucun', '2022-05-23 23:17:41', '审批完成', 'JHD-20220523-00000');
INSERT INTO `warehouse_input_sheet` VALUES ('RKD-20220523-00001', 1, 'kucun', '2022-05-23 23:17:42', '审批完成', 'JHD-20220523-00001');
INSERT INTO `warehouse_input_sheet` VALUES ('RKD-20220523-00002', 2, 'kucun', '2022-05-23 23:17:44', '审批完成', 'JHD-20220523-00002');
INSERT INTO `warehouse_input_sheet` VALUES ('RKD-20220524-00000', 3, 'kucun', '2022-05-24 01:02:31', '审批完成', 'JHD-20220524-00002');

-- ----------------------------
-- Table structure for warehouse_input_sheet_content
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_input_sheet_content`;
CREATE TABLE `warehouse_input_sheet_content`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `wi_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '入库单编号',
  `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品id',
  `quantity` int(0) NOT NULL COMMENT '商品数量',
  `purchase_price` decimal(10, 2) NOT NULL COMMENT '单价',
  `production_date` datetime(0) NULL DEFAULT NULL COMMENT '出厂日期',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehouse_input_sheet_content
-- ----------------------------
INSERT INTO `warehouse_input_sheet_content` VALUES (47, 'RKD-20220523-00000', '0000000000400000', 1000, 1000.00, NULL, 'a');
INSERT INTO `warehouse_input_sheet_content` VALUES (48, 'RKD-20220523-00001', '0000000000400000', 1000, 1200.00, NULL, 'b');
INSERT INTO `warehouse_input_sheet_content` VALUES (49, 'RKD-20220523-00001', '0000000000400001', 500, 2000.00, NULL, 'b');
INSERT INTO `warehouse_input_sheet_content` VALUES (50, 'RKD-20220523-00002', '0000000000400000', 500, 1300.00, NULL, 'c');
INSERT INTO `warehouse_input_sheet_content` VALUES (51, 'RKD-20220523-00002', '0000000000400001', 1000, 2800.00, NULL, 'c');
INSERT INTO `warehouse_input_sheet_content` VALUES (52, 'RKD-20220524-00000', '0000000000400000', 300, 1900.00, NULL, '');
INSERT INTO `warehouse_input_sheet_content` VALUES (53, 'RKD-20220524-00000', '0000000000400001', 400, 2700.00, NULL, NULL);

-- ----------------------------
-- Table structure for warehouse_output_sheet
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_output_sheet`;
CREATE TABLE `warehouse_output_sheet`  (
  `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'CKD + date + index = 出库单id',
  `operator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员名字',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `sale_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售单id',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehouse_output_sheet
-- ----------------------------
INSERT INTO `warehouse_output_sheet` VALUES ('CKD-20220524-00000', 'kucun', '2022-05-24 00:05:32', 'XSD-20220524-00000', '审批完成');
INSERT INTO `warehouse_output_sheet` VALUES ('CKD-20220524-00001', 'kucun', '2022-05-24 00:33:12', 'XSD-20220524-00001', '审批完成');
INSERT INTO `warehouse_output_sheet` VALUES ('CKD-20220524-00002', 'kucun', '2022-05-24 00:45:38', 'XSD-20220524-00002', '审批完成');
INSERT INTO `warehouse_output_sheet` VALUES ('CKD-20220708-00000', NULL, '2022-07-08 10:53:31', 'XSD-20220524-00003', '草稿');
INSERT INTO `warehouse_output_sheet` VALUES ('CKD-20220708-00001', NULL, '2022-07-08 10:55:06', 'XSD-20220708-00000', '草稿');

-- ----------------------------
-- Table structure for warehouse_output_sheet_content
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_output_sheet_content`;
CREATE TABLE `warehouse_output_sheet_content`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '出库商品列表id',
  `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品id',
  `wo_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '出库单单据编号',
  `batch_id` int(0) NULL DEFAULT NULL COMMENT '批次',
  `quantity` int(0) NOT NULL COMMENT '数量',
  `sale_price` decimal(10, 2) NOT NULL COMMENT '对应批次的单价',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehouse_output_sheet_content
-- ----------------------------
INSERT INTO `warehouse_output_sheet_content` VALUES (28, '0000000000400000', 'CKD-20220524-00000', 2, 600, 3500.00, '');
INSERT INTO `warehouse_output_sheet_content` VALUES (29, '0000000000400000', 'CKD-20220524-00000', 1, 600, 3500.00, '');
INSERT INTO `warehouse_output_sheet_content` VALUES (30, '0000000000400001', 'CKD-20220524-00000', 2, 600, 3500.00, NULL);
INSERT INTO `warehouse_output_sheet_content` VALUES (35, '0000000000400000', 'CKD-20220524-00001', 1, 100, 2200.00, '');
INSERT INTO `warehouse_output_sheet_content` VALUES (36, '0000000000400001', 'CKD-20220524-00001', 2, 100, 4000.00, NULL);
INSERT INTO `warehouse_output_sheet_content` VALUES (37, '0000000000400000', 'CKD-20220524-00002', 1, 100, 3000.00, '');
INSERT INTO `warehouse_output_sheet_content` VALUES (38, '0000000000400001', 'CKD-20220524-00002', 2, 100, 4200.00, NULL);
INSERT INTO `warehouse_output_sheet_content` VALUES (39, '0000000000400000', 'CKD-20220708-00000', NULL, 100, 2800.00, '');
INSERT INTO `warehouse_output_sheet_content` VALUES (40, '0000000000400001', 'CKD-20220708-00000', NULL, 100, 3800.00, NULL);
INSERT INTO `warehouse_output_sheet_content` VALUES (41, '0000000000400000', 'CKD-20220708-00001', NULL, 1, 10000.00, '1');

-- ----------------------------
-- Table structure for warehouse_zsd_sheet
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_zsd_sheet`;
CREATE TABLE `warehouse_zsd_sheet`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ZSD + 日期 + index = 赠送单编号',
  `batch_id` int(0) NOT NULL COMMENT '批次',
  `operator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品id',
  `zs_quantity` int(0) NOT NULL COMMENT '商品赠送数量',
  `purchase_price` decimal(10, 2) NOT NULL COMMENT '单价',
  `production_date` datetime(0) NULL DEFAULT NULL COMMENT '出厂日期',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehouse_zsd_sheet
-- ----------------------------
INSERT INTO `warehouse_zsd_sheet` VALUES ('ZSD-20220708-00000', 0, 'zym6', '2022-07-08 11:02:00', '审批完成', '0000000000400000', 1, 3000.00, NULL, NULL);
INSERT INTO `warehouse_zsd_sheet` VALUES ('ZSD-20220708-00001', 0, 'zym6', '2022-07-08 11:02:00', '审批完成', '0000000000500002', 1, 2499.00, NULL, NULL);

-- ----------------------------
-- Table structure for yearly_pay
-- ----------------------------
DROP TABLE IF EXISTS `yearly_pay`;
CREATE TABLE `yearly_pay`  (
  `id` int(0) NOT NULL,
  `yearly_salary` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of yearly_pay
-- ----------------------------
INSERT INTO `yearly_pay` VALUES (4, 1200000);

SET FOREIGN_KEY_CHECKS = 1;
