-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.33 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  12.5.0.6677
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 vary_talk 的数据库结构
CREATE DATABASE IF NOT EXISTS `vary_talk` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `vary_talk`;

-- 导出  表 vary_talk.forum_category 结构
CREATE TABLE IF NOT EXISTS `forum_category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '板块id',
  `category_name` varchar(50) NOT NULL COMMENT '分区名称',
  `display_order` smallint DEFAULT '0' COMMENT '展示顺序',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COMMENT='论坛分区表';

-- 正在导出表  vary_talk.forum_category 的数据：4 rows
/*!40000 ALTER TABLE `forum_category` DISABLE KEYS */;
INSERT INTO `forum_category` (`id`, `category_name`, `display_order`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
	(1, '编程', 2, 'root', '2023-11-05 15:19:31', NULL, NULL, NULL),
	(2, '杂谈', 1, 'root', '2023-11-05 15:27:31', NULL, NULL, NULL),
	(3, '游戏', 3, 'root', '2023-11-05 15:34:18', NULL, NULL, NULL),
	(4, '硬件', 4, 'root', '2023-11-05 15:34:49', NULL, NULL, '你懂硬件吗?');
/*!40000 ALTER TABLE `forum_category` ENABLE KEYS */;

-- 导出  表 vary_talk.forum_group 结构
CREATE TABLE IF NOT EXISTS `forum_group` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '群组id',
  `owner_id` bigint NOT NULL COMMENT '群组所有者id',
  `group_score` bigint DEFAULT NULL COMMENT '群组积分',
  `group_tag` varchar(50) NOT NULL COMMENT '群组标签',
  `group_name` varchar(50) NOT NULL COMMENT '群组名称',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `fk_ownerid_userid` (`owner_id`),
  CONSTRAINT `fk_ownerid_userid` FOREIGN KEY (`owner_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- 正在导出表  vary_talk.forum_group 的数据：~1 rows (大约)
INSERT INTO `forum_group` (`id`, `owner_id`, `group_score`, `group_tag`, `group_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
	(1, 1, 0, 'su_group', '管理员组', 'root', '2023-11-17 23:50:50', NULL, NULL, NULL);

-- 导出  表 vary_talk.forum_group_roles 结构
CREATE TABLE IF NOT EXISTS `forum_group_roles` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '群组角色id',
  `role_name` varchar(10) NOT NULL COMMENT '角色名',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='定义群组中的角色信息, 与系统内的sys_role区分开.';

-- 正在导出表  vary_talk.forum_group_roles 的数据：~0 rows (大约)

-- 导出  表 vary_talk.forum_point 结构
CREATE TABLE IF NOT EXISTS `forum_point` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '积分id',
  `point_tag` varchar(50) NOT NULL COMMENT '积分标签(唯一)',
  `point_name` varchar(50) NOT NULL COMMENT '积分名称(可以重复, 但避免歧义尽量唯一)',
  `is_system` tinyint NOT NULL DEFAULT '0' COMMENT '是否为系统内置积分(代表不可删除)',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COMMENT='积分表：定义各种积分';

-- 正在导出表  vary_talk.forum_point 的数据：~4 rows (大约)
INSERT INTO `forum_point` (`id`, `point_tag`, `point_name`, `is_system`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
	(1, 'coin', '金币', 1, 'root', '2023-10-24 21:57:08', NULL, NULL, NULL),
	(2, 'popularity', '人气', 1, 'root', '2023-10-24 21:59:25', NULL, NULL, NULL),
	(3, 'kindness', '爱心', 1, 'root', '2023-10-24 22:03:54', NULL, NULL, NULL),
	(4, 'diamond', '钻石', 1, 'root', '2023-11-06 11:04:49', NULL, NULL, NULL);

-- 导出  表 vary_talk.forum_section 结构
CREATE TABLE IF NOT EXISTS `forum_section` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '板块id',
  `category_id` bigint NOT NULL COMMENT '所属分区id',
  `section_name` varchar(50) NOT NULL COMMENT '板块名称',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- 正在导出表  vary_talk.forum_section 的数据：~4 rows (大约)
INSERT INTO `forum_section` (`id`, `category_id`, `section_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
	(1, 1, 'Java讨论', 'root', '2023-11-12 10:43:16', NULL, NULL, NULL),
	(2, 1, 'Python讨论', 'root', '2023-11-12 10:43:30', NULL, NULL, NULL),
	(3, 1, 'Vue讨论', 'root', '2023-11-12 11:08:55', NULL, NULL, NULL),
	(4, 1, 'C/C++', 'root', '2023-11-12 11:17:12', NULL, NULL, NULL);

-- 导出  表 vary_talk.forum_tag 结构
CREATE TABLE IF NOT EXISTS `forum_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `tag_name` varchar(50) NOT NULL COMMENT '标签名称',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- 正在导出表  vary_talk.forum_tag 的数据：~2 rows (大约)
INSERT INTO `forum_tag` (`id`, `tag_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
	(1, '节日', 'root', '2023-11-17 22:22:34', NULL, NULL, '一个标签而已'),
	(2, '游戏', 'root', '2023-11-17 22:45:12', NULL, NULL, NULL);

-- 导出  表 vary_talk.forum_user_points 结构
CREATE TABLE IF NOT EXISTS `forum_user_points` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `point_id` bigint NOT NULL COMMENT '积分类型id',
  `points` bigint NOT NULL COMMENT '积分数量',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `FK_forum_user_points_sys_user` (`user_id`),
  KEY `FK_forum_user_points_forum_point` (`point_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COMMENT='用户积分数量表';

-- 正在导出表  vary_talk.forum_user_points 的数据：5 rows
/*!40000 ALTER TABLE `forum_user_points` DISABLE KEYS */;
INSERT INTO `forum_user_points` (`id`, `user_id`, `point_id`, `points`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
	(1, 1, 1, 19020, NULL, '2023-11-05 16:40:57', NULL, '2023-11-12 09:51:09', '该用户 金币 的数量'),
	(2, 1, 2, 355001, NULL, '2023-11-05 16:45:33', NULL, '2023-11-12 10:10:09', '该用户 人气 的数量'),
	(3, 1, 3, 0, NULL, '2023-11-05 16:48:43', NULL, '2023-11-05 16:48:59', '该用户 爱心 的数量'),
	(4, 2, 1, 26660, NULL, '2023-11-05 16:57:36', NULL, '2023-11-05 16:58:06', '该用户 金币 的数量'),
	(5, 2, 2, 2131, NULL, '2023-11-05 17:27:47', NULL, '2023-11-13 19:40:38', '该用户 人气 的数量');
/*!40000 ALTER TABLE `forum_user_points` ENABLE KEYS */;

-- 导出  表 vary_talk.sys_dict_sex 结构
CREATE TABLE IF NOT EXISTS `sys_dict_sex` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '性别id',
  `sex_tag` varchar(10) NOT NULL COMMENT '性别字符',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- 正在导出表  vary_talk.sys_dict_sex 的数据：~5 rows (大约)
INSERT INTO `sys_dict_sex` (`id`, `sex_tag`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
	(1, '男', 'system', '2023-09-15 22:18:37', '', NULL, '性别男'),
	(2, '女', 'system', '2023-09-15 22:18:41', '', NULL, '性别女'),
	(3, '未知', 'system', '2023-09-15 22:18:43', '', NULL, '未知性别'),
	(4, '保密', 'system', '2023-09-15 22:18:46', '', NULL, '保密用户性别'),
	(5, '其它', 'system', '2023-09-15 22:18:48', '', NULL, '其它类型');

-- 导出  表 vary_talk.sys_file 结构
CREATE TABLE IF NOT EXISTS `sys_file` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `user_id` bigint NOT NULL COMMENT '上传者id',
  `original_name` varchar(100) NOT NULL COMMENT '文件原名称',
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '文件名称(新)',
  `path` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '文件相对路径',
  `file_size` bigint NOT NULL COMMENT '文件尺寸(字节)',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `FK_sys_file_sys_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;

-- 正在导出表  vary_talk.sys_file 的数据：~2 rows (大约)
INSERT INTO `sys_file` (`id`, `user_id`, `original_name`, `name`, `path`, `file_size`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
	(11, 1, '68679473.jpg', '8db0e507-0308-4131-afba-68c39b954806.jpg', 'D:\\vary_talk\\upload_files\\2023\\11\\18\\8db0e507-0308-4131-afba-68c39b954806.jpg', 3453, NULL, '2023-11-18 14:13:19', NULL, NULL, NULL),
	(12, 1, '68679473.jpg', '0d867cef-40db-473e-834f-34adcc96cdcd.jpg', 'D:\\vary_talk\\upload_files\\2023\\11\\18\\0d867cef-40db-473e-834f-34adcc96cdcd.jpg', 3453, NULL, '2023-11-18 14:14:58', NULL, NULL, NULL);

-- 导出  表 vary_talk.sys_log 结构
CREATE TABLE IF NOT EXISTS `sys_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `user_id` bigint NOT NULL COMMENT '执行该操作的用户id',
  `ip_address` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '操作时的ip地址',
  `api_path` varchar(60) DEFAULT NULL COMMENT '该操作的接口路径',
  `op_name` varchar(60) DEFAULT NULL COMMENT '操作名称&类型',
  `op_context` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '操作的具体信息',
  `op_time` datetime DEFAULT NULL COMMENT '执行的操作时间',
  PRIMARY KEY (`id`),
  KEY `fk_user_id` (`user_id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb3;

-- 正在导出表  vary_talk.sys_log 的数据：~78 rows (大约)
INSERT INTO `sys_log` (`id`, `user_id`, `ip_address`, `api_path`, `op_name`, `op_context`, `op_time`) VALUES
	(1, 1, '127.0.0.1', '/api/role/list', '角色接口', '获取角色列表', '2023-09-18 09:02:25'),
	(2, 1, '127.0.0.1', '/api/role/list', '角色接口', '获取角色列表', '2023-09-18 09:09:08'),
	(3, 1, '127.0.0.1', '/api/role/list', '角色接口', '获取角色列表', '2023-09-18 09:10:03'),
	(4, 1, '127.0.0.1', '/api/role/add', '角色接口', '添加一个新的角色 状态信息: 该角色已存在, 请换个角色名试试!', '2023-09-18 09:52:46'),
	(5, 1, '127.0.0.1', '/api/user/add', '用户接口', '添加一个用户 状态信息: 该邮箱已被其它用户绑定, 换一个试试.', '2023-09-18 10:05:14'),
	(6, 1, '127.0.0.1', '/api/user/add', '用户接口', '添加一个用户 状态信息: 该邮箱已被其它用户绑定, 换一个试试.', '2023-09-18 10:05:20'),
	(7, 1, '127.0.0.1', '/api/user/add', '用户接口', '添加一个用户 状态信息: 该手机号已被其它用户绑定, 换一个试试.', '2023-09-18 10:05:41'),
	(8, 1, '127.0.0.1', '/api/user/add', '用户接口', '添加一个用户 状态信息: 操作成功!', '2023-09-18 10:05:48'),
	(9, 1, '127.0.0.1', '/api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-09-18 10:11:53'),
	(10, 1, '127.0.0.1', '/api/user/bind-role', '用户接口', '修改用户的角色 状态信息: 操作成功!', '2023-09-18 10:16:06'),
	(11, 1, '127.0.0.1', '/api/permission/give-role', '权限接口', '给角色添加权限 状态信息: 操作成功!', '2023-09-18 14:53:57'),
	(12, 1, '127.0.0.1', '/api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-09-18 16:31:41'),
	(13, 1, '127.0.0.1', '/api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 该权限节点值已存在, 请重新输入!', '2023-09-18 16:31:52'),
	(14, 1, '127.0.0.1', '/api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-09-18 16:32:50'),
	(15, 1, '127.0.0.1', '/api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-09-18 16:33:16'),
	(16, 1, '127.0.0.1', '/api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-09-18 16:33:53'),
	(17, 1, '127.0.0.1', '/api/file/upload', '文件管理', '用户上传文件 状态信息: 操作成功!', '2023-09-21 10:03:04'),
	(18, 1, '127.0.0.1', '/api/user/bind-role', '用户接口', '修改用户的角色 状态信息: 操作成功!', '2023-09-21 16:31:53'),
	(19, 1, '127.0.0.1', '/api/user/bind-role', '用户接口', '修改用户的角色 状态信息: 操作成功!', '2023-09-21 16:33:38'),
	(20, 1, '127.0.0.1', '/api/user/bind-role', '用户接口', '修改用户的角色 状态信息: 操作成功!', '2023-09-21 16:38:01'),
	(21, 1, '127.0.0.1', '/api/file/upload', '文件管理', '用户上传文件 状态信息: 操作成功!', '2023-09-22 20:50:54'),
	(22, 1, '127.0.0.1', '/api/user/add', '用户接口', '添加一个用户 状态信息: 该用户名已被使用, 请更换一个用户名试试.', '2023-09-25 20:20:49'),
	(23, 1, '127.0.0.1', '/api/user/add', '用户接口', '添加一个用户 状态信息: 该用户名已被使用, 请更换一个用户名试试.', '2023-09-25 20:20:51'),
	(24, 1, '127.0.0.1', '/api/user/add', '用户接口', '添加一个用户 状态信息: 该用户名已被使用, 请更换一个用户名试试.', '2023-09-25 20:20:51'),
	(25, 1, '127.0.0.1', '/api/user/add', '用户接口', '添加一个用户 状态信息: 该用户名已被使用, 请更换一个用户名试试.', '2023-09-25 20:20:52'),
	(26, 1, '127.0.0.1', '/api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-09-26 12:10:42'),
	(27, 1, '127.0.0.1', '/api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-09-26 12:11:27'),
	(28, 1, '127.0.0.1', '/api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-09-26 12:15:33'),
	(29, 1, '127.0.0.1', '/dev-api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 该权限节点值已存在, 请重新输入!', '2023-10-21 21:00:37'),
	(30, 1, '127.0.0.1', '/dev-api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 该权限节点值已存在, 请重新输入!', '2023-10-21 21:00:39'),
	(31, 1, '127.0.0.1', '/dev-api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 该权限节点值已存在, 请重新输入!', '2023-10-21 21:00:40'),
	(32, 1, '127.0.0.1', '/dev-api/permission/give-role', '权限接口', '给角色添加权限 状态信息: 该角色已拥有该权限, 无法重复给予!', '2023-10-22 09:03:18'),
	(33, 1, '127.0.0.1', '/dev-api/permission/give-role', '权限接口', '给角色添加权限 状态信息: 操作成功!', '2023-10-22 09:13:39'),
	(34, 1, '192.168.2.84', '/dev-api/role/add', '角色接口', '添加一个新的角色 状态信息: 操作成功!', '2023-10-24 08:58:55'),
	(35, 1, '127.0.0.1', '/dev-api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-10-24 21:24:58'),
	(36, 1, '127.0.0.1', '/dev-api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-10-24 21:25:55'),
	(37, 1, '127.0.0.1', '/dev-api/permission/give-role', '权限接口', '给角色添加权限 状态信息: 操作成功!', '2023-10-24 21:26:28'),
	(38, 1, '127.0.0.1', '/dev-api/permission/give-role', '权限接口', '给角色添加权限 状态信息: 该角色已拥有该权限, 无法重复给予!', '2023-10-24 21:32:36'),
	(39, 1, '127.0.0.1', '/dev-api/permission/give-role', '权限接口', '给角色添加权限 状态信息: 该角色已拥有该权限, 无法重复给予!', '2023-10-24 21:32:38'),
	(40, 1, '127.0.0.1', '/dev-api/permission/give-role', '权限接口', '给角色添加权限 状态信息: 该角色已拥有该权限, 无法重复给予!', '2023-10-24 21:32:38'),
	(41, 1, '127.0.0.1', '/dev-api/permission/give-role', '权限接口', '给角色添加权限 状态信息: 该角色已拥有该权限, 无法重复给予!', '2023-10-24 21:32:39'),
	(42, 1, '127.0.0.1', '/dev-api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-10-24 21:52:23'),
	(43, 1, '220.200.27.126', '/dev-api/user/add', '用户接口', '添加一个用户 状态信息: 该用户名已被使用, 请更换一个用户名试试.', '2023-10-30 23:06:50'),
	(44, 1, '127.0.0.1', '/dev-api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-11-05 14:14:48'),
	(45, 1, '127.0.0.1', '/dev-api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 该权限节点值已存在, 请重新输入!', '2023-11-05 14:15:31'),
	(46, 1, '127.0.0.1', '/dev-api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-11-05 15:13:15'),
	(47, 1, '127.0.0.1', '/dev-api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-11-06 10:43:27'),
	(48, 1, '127.0.0.1', '/dev-api/permission/give-user', '权限接口', '给用户添加权限 状态信息: 该用户已拥有该权限, 无法重复给予!', '2023-11-06 10:49:46'),
	(49, 1, '127.0.0.1', '/dev-api/permission/give-user', '权限接口', '给用户添加权限 状态信息: 操作成功!', '2023-11-06 10:50:51'),
	(50, 1, '127.0.0.1', '/dev-api/permission/give-role', '权限接口', '给角色添加权限 状态信息: 该角色已拥有该权限, 无法重复给予!', '2023-11-11 20:05:08'),
	(51, 1, '127.0.0.1', '/dev-api/permission/give-role', '权限接口', '给角色添加权限 状态信息: 操作成功!', '2023-11-11 20:05:12'),
	(52, 1, '127.0.0.1', '/dev-api/permission/give-role', '权限接口', '给角色添加权限 状态信息: 该角色已拥有该权限, 无法重复给予!', '2023-11-11 20:07:06'),
	(53, 1, '127.0.0.1', '/dev-api/permission/give-role', '权限接口', '给角色添加权限 状态信息: 该角色已拥有该权限, 无法重复给予!', '2023-11-11 20:07:10'),
	(54, 1, '127.0.0.1', '/dev-api/permission/give-role', '权限接口', '给角色添加权限 状态信息: 该角色已拥有该权限, 无法重复给予!', '2023-11-11 20:07:11'),
	(55, 1, '127.0.0.1', '/dev-api/permission/give-role', '权限接口', '给角色添加权限 状态信息: 操作成功!', '2023-11-11 20:08:04'),
	(56, 1, '127.0.0.1', '/dev-api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-11-12 09:49:43'),
	(57, 1, '127.0.0.1', '/dev-api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-11-12 10:43:11'),
	(58, 1, '127.0.0.1', '/dev-api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-11-12 11:00:17'),
	(59, 1, '127.0.0.1', '/dev-api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 该权限节点值已存在, 请重新输入!', '2023-11-12 11:00:40'),
	(60, 1, '127.0.0.1', '/dev-api/forum-section/add', '论坛板块接口', '添加一个新的板块 状态信息: 操作成功!', '2023-11-12 11:17:13'),
	(61, 1, '127.0.0.1', '/dev-api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-11-17 22:22:03'),
	(62, 1, '127.0.0.1', '/dev-api/forum-tag/add', '论坛标签接口', '添加一个新的标签 状态信息: 操作成功!', '2023-11-17 22:22:34'),
	(63, 1, '127.0.0.1', '/dev-api/forum-tag/add', '论坛标签接口', '添加一个新的标签 状态信息: 该标签已存在, 请重试!', '2023-11-17 22:22:51'),
	(64, 1, '127.0.0.1', '/dev-api/forum-tag/add', '论坛标签接口', '添加一个新的标签 状态信息: 该标签已存在, 请重试!', '2023-11-17 22:22:52'),
	(65, 1, '127.0.0.1', '/dev-api/forum-tag/add', '论坛标签接口', '添加一个新的标签 状态信息: 该标签已存在, 请重试!', '2023-11-17 22:22:53'),
	(66, 1, '127.0.0.1', '/dev-api/forum-tag/add', '论坛标签接口', '添加一个新的标签 状态信息: 该标签已存在, 请重试!', '2023-11-17 22:22:53'),
	(67, 1, '127.0.0.1', '/dev-api/forum-tag/add', '论坛标签接口', '添加一个新的标签 状态信息: 操作成功!', '2023-11-17 22:45:12'),
	(68, 1, '127.0.0.1', '/dev-api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-11-17 23:45:29'),
	(69, 1, '127.0.0.1', '/dev-api/permission/add', '权限接口', '添加一个新的权限节点 状态信息: 操作成功!', '2023-11-17 23:46:10'),
	(70, 1, '127.0.0.1', '/dev-api/forum-group/add', '论坛群组接口', '添加一个新的群组 状态信息: 操作成功!', '2023-11-17 23:50:51'),
	(71, 1, '127.0.0.1', '/dev-api/forum-group/add', '论坛群组接口', '添加一个新的群组 状态信息: 该群组标签已存在, 请换一个试试!', '2023-11-17 23:59:10'),
	(72, 1, '127.0.0.1', '/dev-api/forum-group/add', '论坛群组接口', '添加一个新的群组 状态信息: 该群组标签已存在, 请换一个试试!', '2023-11-17 23:59:15'),
	(73, 1, '127.0.0.1', '/dev-api/forum-tag/add', '论坛标签接口', '添加一个新的标签 状态信息: 该标签已存在, 请重试!', '2023-11-18 11:09:26'),
	(74, 1, '127.0.0.1', '/dev-api/forum-tag/add', '论坛标签接口', '添加一个新的标签 状态信息: 该标签已存在, 请重试!', '2023-11-18 11:09:27'),
	(75, 1, '127.0.0.1', '/dev-api/file/upload', '文件管理', '用户上传文件 状态信息: 操作成功!', '2023-11-18 14:02:39'),
	(76, 1, '127.0.0.1', '/dev-api/file/upload', '文件管理', '用户上传文件 状态信息: 操作成功!', '2023-11-18 14:11:39'),
	(77, 1, '127.0.0.1', '/dev-api/file/upload', '文件管理', '用户上传文件 状态信息: 操作成功!', '2023-11-18 14:13:19'),
	(78, 1, '127.0.0.1', '/dev-api/file/upload', '文件管理', '用户上传文件 状态信息: 操作成功!', '2023-11-18 14:14:58');

-- 导出  表 vary_talk.sys_permission 结构
CREATE TABLE IF NOT EXISTS `sys_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `value` varchar(60) NOT NULL COMMENT '权限节点值',
  `node_name` varchar(60) NOT NULL COMMENT '权限名称',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb3;

-- 正在导出表  vary_talk.sys_permission 的数据：~29 rows (大约)
INSERT INTO `sys_permission` (`id`, `value`, `node_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
	(1, 'system.user.list', '查询用户列表', 'root', '2023-09-16 07:22:06', NULL, NULL, '拥有该权限的用户可以查询系统内的所有用户'),
	(2, 'system.user.add', '添加用户', 'root', '2023-09-16 07:35:06', NULL, NULL, '拥有该权限的用户可以直接添加用户至系统中.'),
	(3, 'system.user.delete', '删除用户', 'root', '2023-09-16 07:50:07', NULL, NULL, '拥有该权限的用户可以直接删除特定用户.'),
	(4, 'system.role.add', '添加角色', 'root', '2023-09-16 11:11:35', NULL, NULL, '拥有该权限的用户可以添加角色.'),
	(5, 'system.role.delete', '删除角色', 'root', '2023-09-16 11:12:05', NULL, NULL, '拥有该权限的用户可以直接删除角色.'),
	(6, 'system.role.list', '查询角色列表', 'root', '2023-09-16 11:48:38', NULL, NULL, '拥有该权限的用户可以查询系统内的所有角色'),
	(7, 'system.role.*', '角色相关所有权限', 'root', '2023-09-16 18:34:12', NULL, NULL, '拥有该权限的用户可以执行角色相关的所有操作'),
	(8, 'system.*', '系统全部权限', 'root', '2023-09-16 18:40:05', NULL, NULL, '拥有该权限的用户可以执行所有操作(危险权限!请勿随意给予)'),
	(9, 'system.permission.give.user', '给予用户权限', 'root', '2023-09-16 18:55:33', NULL, NULL, '拥有该权限的用户可以给予其它用户权限(仅操作者拥有的权限<包括角色所拥有的>).'),
	(10, 'system.permission.add', '新增一个权限节点', 'root', '2023-09-16 19:02:42', NULL, NULL, '拥有该权限的用户可以在系统中新增一个权限节点'),
	(11, 'system.permission.get.users', '查询用户自身全部权限', 'root', '2023-09-16 19:04:54', NULL, NULL, '拥有该权限的用户可以查询指定用户自身所拥有的全部权限.'),
	(12, 'system.permission.list', '查询权限列表', 'root', '2023-09-16 19:08:47', NULL, NULL, '拥有该权限的用户可以查询权限列表.'),
	(13, 'system.user.get', '查询指定用户', 'root', '2023-09-16 19:11:47', NULL, NULL, '拥有该权限的用户可以查询指定用户.'),
	(14, 'system.user.online.detail', '查询在线用户信息', 'root', '2023-09-16 19:12:39', NULL, NULL, '拥有该权限的用户可以全部在线用户的详细信息.'),
	(15, 'system.user.online.count', '查询在线用户数量', 'root', '2023-09-16 19:13:45', NULL, NULL, '拥有该权限的用户可以全部在线用户(仅数量).'),
	(16, 'system.permission.give.role', '给予角色权限', 'root', '2023-09-16 19:43:06', NULL, NULL, '拥有该权限的用户可以给予角色特定权限.'),
	(17, 'system.permission.get.roles', '查询角色全部权限', 'root', '2023-09-16 19:53:59', NULL, NULL, '拥有该权限的用户可以查询指定角色的所有权限.'),
	(18, 'system.user.bind.role', '绑定角色', 'root', '2023-09-17 20:50:56', NULL, NULL, '拥有该权限的用户可以绑定(修改)用户的角色.'),
	(19, 'system.log.list', '查询日志', 'root', '2023-09-18 10:11:53', NULL, NULL, '拥有该权限的用户可以查询所有用户的日志信息.'),
	(20, 'system.file.upload', '上传文件', 'root', '2023-09-18 16:31:41', NULL, NULL, '拥有该权限的用户可以上传文件至系统存储中.'),
	(21, 'system.file.download', '下载文件', 'root', '2023-09-18 16:32:49', NULL, NULL, '拥有该权限的用户可以从文件系统中下载文件(有具体权限要求的文件除外).'),
	(22, 'system.file.*', '所有文件权限', 'root', '2023-09-18 16:33:16', NULL, NULL, '拥有该权限的用户可以执行所有文件相关的操作.'),
	(23, 'system.file.delete', '删除文件', 'root', '2023-09-18 16:33:53', NULL, NULL, '拥有该权限的用户可以删除指定文件(特殊权限要求的文件除外).'),
	(24, 'system.user.update.self', '更新自己的用户信息', 'root', '2023-09-26 12:10:41', NULL, NULL, '拥有该权限的用户可以更新自己的用户信息(不包括密码).'),
	(25, 'system.user.update.other', '更新其它用户的信息', 'root', '2023-09-26 12:11:27', NULL, NULL, '拥有该权限的用户可以更新其它人的用户信息(不包括密码).'),
	(26, 'system.user.update.*', '更新用户信息(包括其他用户)', 'root', '2023-09-26 12:15:32', NULL, NULL, '拥有该权限的用户可以更新自己和其它人的用户信息(不包括密码).'),
	(27, 'forum.point.type.list', '查询积分类型列表', 'root', '2023-10-24 21:24:57', NULL, NULL, '拥有该权限的用户可以查询所有已有的积分类型.'),
	(28, 'forum.*', '所有论坛权限', 'root', '2023-10-24 21:25:55', NULL, NULL, '拥有该权限的用户可以执行论坛的所有操作'),
	(29, 'forum.point.type.add', '添加新的积分类型', 'root', '2023-10-24 21:52:22', NULL, NULL, '拥有该权限的用户可以添加新的积分类型'),
	(30, 'forum.category.list.detail', '查询分区列表(详细)', 'root', '2023-11-05 14:14:48', NULL, NULL, '拥有该权限的用户可以查询分区列表详细信息'),
	(31, 'forum.category.add', '添加新论坛分区', 'root', '2023-11-05 15:13:15', NULL, NULL, '拥有该权限的用户可以添加一个新的论坛分区'),
	(32, 'forum.category.delete', '删除论坛分区', 'root', '2023-11-06 10:43:26', NULL, NULL, '拥有该权限的用户可以删除一个论坛分区'),
	(33, 'forum.point.user.modify', '修改用户积分', 'root', '2023-11-12 09:49:43', NULL, NULL, '拥有该权限的用户修改其它用户的特定积分'),
	(34, 'forum.section.add', '添加板块', 'root', '2023-11-12 10:43:10', NULL, NULL, '拥有该权限的用户可以添加板块'),
	(35, 'forum.section.list', '板块列表', 'root', '2023-11-12 11:00:16', NULL, NULL, '拥有该权限的用户可以查看板块列表'),
	(36, 'forum.tag.add', '添加一个新的标签', 'root', '2023-11-17 22:22:02', NULL, NULL, '拥有该权限的用户可以在论坛内新添加一个标签(可用于帖子或者普通话题)'),
	(37, 'forum.group.add', '直接添加一个新的群组', 'root', '2023-11-17 23:45:29', NULL, NULL, '拥有该权限的用户可以在论坛内直接新增一个群组(区别于创建群组)'),
	(38, 'forum.group.list', '查询群组列表(详细)', 'root', '2023-11-17 23:46:09', NULL, NULL, '拥有该权限的用户可以查询论坛中的群组列表(详细信息、用于管理端, 所以需要权限)');

-- 导出  表 vary_talk.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `father_id` bigint DEFAULT NULL COMMENT '父角色id',
  `role_name` varchar(40) NOT NULL COMMENT '角色名称',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- 正在导出表  vary_talk.sys_role 的数据：~4 rows (大约)
INSERT INTO `sys_role` (`id`, `father_id`, `role_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
	(1, NULL, '管理员', 'root', '2023-09-16 08:44:25', NULL, NULL, '管理员'),
	(2, NULL, '普通用户', 'root', '2023-09-16 08:45:09', NULL, NULL, '用户注册时默认角色'),
	(3, NULL, '版主', 'root', '2023-09-16 09:54:44', NULL, NULL, '论坛各个板块版主'),
	(4, NULL, '体验用户', 'root', '2023-10-24 08:58:55', NULL, NULL, '体验用户、权限最低');

-- 导出  表 vary_talk.sys_role_permission 结构
CREATE TABLE IF NOT EXISTS `sys_role_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `permission_id` bigint NOT NULL COMMENT '权限id',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

-- 正在导出表  vary_talk.sys_role_permission 的数据：~7 rows (大约)
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
	(1, 1, 1, 'root', '2023-09-16 19:49:06', NULL, NULL, NULL),
	(2, 1, 2, 'root', '2023-09-16 19:51:12', NULL, NULL, NULL),
	(3, 2, 13, 'root', '2023-09-18 14:53:57', NULL, NULL, NULL),
	(4, 1, 8, 'root', '2023-10-22 09:13:38', NULL, NULL, NULL),
	(5, 1, 28, 'root', '2023-10-24 21:26:28', NULL, NULL, NULL),
	(6, 1, 9, 'root', '2023-11-11 20:05:11', NULL, NULL, NULL),
	(7, 1, 10, 'root', '2023-11-11 20:08:03', NULL, NULL, NULL);

-- 导出  表 vary_talk.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户id, 主键, 自增',
  `username` varchar(40) NOT NULL COMMENT '用户账号(唯一)',
  `password` varchar(255) NOT NULL COMMENT '用户密码(MD5加密)',
  `phone_number` varchar(20) DEFAULT NULL COMMENT '手机号码(唯一)',
  `email` varchar(60) DEFAULT NULL COMMENT '邮箱(唯一)',
  `nick_name` varchar(40) DEFAULT NULL COMMENT '用户昵称',
  `role_id` bigint DEFAULT NULL COMMENT '角色id',
  `sex_id` bigint DEFAULT NULL COMMENT '性别id',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `fk_role_id` (`role_id`),
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;

-- 正在导出表  vary_talk.sys_user 的数据：~10 rows (大约)
INSERT INTO `sys_user` (`id`, `username`, `password`, `phone_number`, `email`, `nick_name`, `role_id`, `sex_id`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
	(1, 'root', 'f038c592dd1f449f83ab8387dbe2f176', '15105953171', '17689543@qq.com', '张三', 1, 4, 'system', '2023-09-15 22:47:27', 'root', '2023-09-26 11:28:51', NULL),
	(2, 'serliunx', '107c91e86f679aaaf81bf2e4e6e93137', '17689674149', 'serliunx@yeah.net', 'SerLiunx', 2, 1, 'root', '2023-09-15 22:48:00', NULL, NULL, NULL),
	(4, 'jack', 'd583667ba83397298563a0cff25af133', NULL, NULL, NULL, 2, 1, 'root', '2023-09-15 23:16:50', NULL, NULL, NULL),
	(5, 'notch', 'f038c592dd1f449f83ab8387dbe2f176', '1665546213', '1656564@qq.com', NULL, 2, 1, 'root', '2023-09-16 11:32:44', NULL, NULL, NULL),
	(6, 'jeb_', '2b058bf60006141aab61586f33833bf2', '1265546213', '165656421@qq.com', NULL, 2, 1, 'root', '2023-09-16 11:34:01', NULL, NULL, NULL),
	(7, '1927230327', 'f038c592dd1f449f83ab8387dbe2f176', NULL, NULL, NULL, 2, 1, NULL, '2023-09-16 20:04:31', '1927230327', '2023-09-21 11:30:25', NULL),
	(8, 'joy_', '4d57336d5bce9d89c3f2bbd81738d264', '12655462131', NULL, NULL, 2, 1, 'root', '2023-09-18 10:05:47', NULL, NULL, NULL),
	(9, 'hzds', '107c91e86f679aaaf81bf2e4e6e93137', NULL, NULL, NULL, 2, 1, NULL, '2023-10-21 20:51:14', NULL, NULL, NULL),
	(10, '12345', '827ccb0eea8a706c4c34a16891f84e7b', NULL, NULL, NULL, 2, 1, NULL, '2023-10-25 11:15:52', NULL, NULL, NULL),
	(11, 'hzdasd', '107c91e86f679aaaf81bf2e4e6e93137', NULL, NULL, NULL, 2, 1, NULL, '2023-10-25 15:47:58', NULL, NULL, NULL),
	(12, 'superhero', '107c91e86f679aaaf81bf2e4e6e93137', NULL, NULL, NULL, 2, 1, NULL, '2023-11-08 15:20:16', NULL, NULL, NULL);

-- 导出  表 vary_talk.sys_user_permission 结构
CREATE TABLE IF NOT EXISTS `sys_user_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `permission_id` bigint NOT NULL COMMENT '权限id',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

-- 正在导出表  vary_talk.sys_user_permission 的数据：~5 rows (大约)
INSERT INTO `sys_user_permission` (`id`, `user_id`, `permission_id`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
	(1, 2, 1, 'root', '2023-09-16 13:51:03', NULL, NULL, NULL),
	(2, 2, 2, 'root', '2023-09-16 17:35:30', NULL, NULL, NULL),
	(3, 2, 3, 'root', '2023-09-16 17:35:41', NULL, NULL, NULL),
	(4, 2, 7, 'root', '2023-09-16 18:34:34', NULL, NULL, NULL),
	(5, 1, 8, 'root', '2023-09-16 18:41:52', NULL, NULL, NULL),
	(7, 1, 32, 'root', '2023-11-06 10:50:50', NULL, NULL, NULL);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
