/*
SQLyog Ultimate v8.71 
MySQL - 5.1.33-community : Database - data_collect
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`data_collect` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `data_collect`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id of account',
  `user_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pass_word` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qq` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mobile` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `login_time` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Save account information';

/*Table structure for table `account_action` */

DROP TABLE IF EXISTS `account_action`;

CREATE TABLE `account_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) DEFAULT NULL,
  `action` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `action_status` int(11) DEFAULT NULL,
  `action_status_reason` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `action_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_15` (`menu_id`),
  CONSTRAINT `FK_Reference_15` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Record actions of each user of this system.';

/*Table structure for table `account_role_map` */

DROP TABLE IF EXISTS `account_role_map`;

CREATE TABLE `account_role_map` (
  `account_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  KEY `FK_Reference_1` (`role_id`),
  KEY `FK_Reference_2` (`account_id`),
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Relation between AccountTbl and RoleTbl';

/*Table structure for table `db_setting` */

DROP TABLE IF EXISTS `db_setting`;

CREATE TABLE `db_setting` (
  `db_type` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `db_ip` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `db_username` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `db_password` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_test_db` int(11) DEFAULT NULL,
  `is_operate_db` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `menu_desc` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `menu_action` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `parse` */

DROP TABLE IF EXISTS `parse`;

CREATE TABLE `parse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) DEFAULT NULL,
  `parse_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `parse_desc` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `save_to_table` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '该解析保存到的表名称',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_8` (`category_id`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`category_id`) REFERENCES `parse_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `parse_category` */

DROP TABLE IF EXISTS `parse_category`;

CREATE TABLE `parse_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_name` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cat_desc` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `parse_item` */

DROP TABLE IF EXISTS `parse_item`;

CREATE TABLE `parse_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parse_id` int(11) DEFAULT NULL,
  `save_to_table` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `save_to_column` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `column_desc` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `charactor` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `not_charactor` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `default_val` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `data_type` varchar(20) DEFAULT NULL COMMENT '解析数据类型, String, Date, Number, Boolean, DateTime\n5种类型',
  `number_format` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `date_format` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_check_repeat_column` tinyint(1) DEFAULT NULL,
  `is_url` tinyint(1) DEFAULT NULL,
  `src_reg_exp` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `dest_reg_exp` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `exec_javascript` varchar(2000) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `page_url_as_value` tinyint(4) DEFAULT '0' COMMENT 'true---用该页面的url作为该字段的值，\n\nfalse---不用该页面的url作为该字段的值，\n\ndefault---false。',
  `reg_exp_item_relation` tinyint(4) DEFAULT '0' COMMENT 'true---check means and（replace multiple times according to number of src reg exp）, \n\nfalse---uncheck means or (if one of src reg exp match then return)',
  `by_ele_type` varchar(100) DEFAULT NULL COMMENT 'Search this element by what? tag, xpath, id, name, css selector etc， 通常使用id或者name，然后再按照字符串解析想要的值，这样webdriver的运行速度比较快',
  `by_ele_val` varchar(1000) DEFAULT NULL COMMENT 'id or name or xpath ... value，可以解析多个id，name，xpath等，多个值之间用分号分开',
  `by_tag_attribute` varchar(200) DEFAULT NULL COMMENT 'element attribute,多个attribute可以用分号分割，将返回多个属性的值的和的字符串',
  `parse_value_reg_exp` varchar(2000) DEFAULT NULL COMMENT '对上面解析出来的值执行正则表达式得到想要的值,该字段的格式为 regexp1;regexp2;regexp3...',
  `parse_value_string` text COMMENT '如果该值很难用正则表达式解析，就用这个字段进行字符串解析，该字段内容格式startStr1,endStr1;startStr1,endStr1;startStr1,endStr1;...',
  `is_direct_get_text` tinyint(1) DEFAULT '1' COMMENT 'true: 直接调用WebElement.getText(), false:  调用webEle.getAttribute("innerHTML") 得到文本字段后再用其他配置进行解析',
  `use_this_setting_url_charactor` varchar(1000) DEFAULT NULL COMMENT '只有解析的内容页面的url匹配这样的url特征，才用这个解析配置进行数据解析, 如果该值为空，则所有的内容页面的url都用该配置进行解析',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_9` (`parse_id`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`parse_id`) REFERENCES `parse` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8;

/*Table structure for table `parse_item_action` */

DROP TABLE IF EXISTS `parse_item_action`;

CREATE TABLE `parse_item_action` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `parse_item_id` bigint(10) DEFAULT NULL COMMENT 'this action beloned parse item table record id',
  `by_ele_type` varchar(10) DEFAULT NULL COMMENT 'Search by element type(Id, className, tagName etc )',
  `by_ele_val` varchar(1000) DEFAULT NULL COMMENT 'The value of id, className, tagName etc',
  `by_ele_action` varchar(100) DEFAULT NULL COMMENT 'Action that will take on above search web element',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `parse_page_exception` */

DROP TABLE IF EXISTS `parse_page_exception`;

CREATE TABLE `parse_page_exception` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `download_task_id` int(11) DEFAULT NULL,
  `parse_item_id` int(11) DEFAULT NULL,
  `error_message` varchar(2000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_13` (`download_task_id`),
  KEY `FK_Reference_14` (`parse_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `role_desc` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `role_menu_map` */

DROP TABLE IF EXISTS `role_menu_map`;

CREATE TABLE `role_menu_map` (
  `role_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  KEY `FK_Reference_3` (`role_id`),
  KEY `FK_Reference_4` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Relation between RoleTbl and MenuTbl';

/*Table structure for table `selenium_setting` */

DROP TABLE IF EXISTS `selenium_setting`;

CREATE TABLE `selenium_setting` (
  `browser_type` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `site` */

DROP TABLE IF EXISTS `site`;

CREATE TABLE `site` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) DEFAULT NULL,
  `site_name` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `site_desc` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `parse_id` int(11) DEFAULT NULL,
  `top_url` varchar(2000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `start_download_time` int(11) NOT NULL DEFAULT '0',
  `download_duration_seconds` int(11) DEFAULT NULL,
  `over_write_attributes` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `download_task_reget_duration_d` int(11) DEFAULT NULL,
  `need_login` int(11) DEFAULT NULL,
  `login_check_return_url` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `login_check_return_data` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `login_check_return_data_xpath` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `test_passed` int(11) DEFAULT NULL,
  `site_status` tinyint(1) DEFAULT NULL COMMENT '0---test status, 1---run time status',
  `multi_country` tinyint(1) DEFAULT NULL,
  `place_holders` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `browser_type` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '抓取网页的时候使用的浏览器类型',
  `access_denied_page_element_xpath` varbinary(2000) DEFAULT NULL COMMENT '记录 access denied 页面特征元素的 xpath',
  `access_denied_page_element_value` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '记录 access denied 页面特征元素的值',
  `alert_text_to_reset_d_task` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '如果访问页面的时候出现的alert窗口里面有这样的文字，则重置下载任务申请时间重新下载相应的下载任务',
  `need_scroll_page_url_charactor` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'If download page url match one of these urls, then scroll page to the end of the page to show waterfull like page, if value is all, then all page need scroll',
  `not_need_scroll_page_url_charactor` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'If download page url match one of these urls, then not scroll page to the end of the page',
  `set_high_level_task_url_charactor` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '如果url链接匹配这里的字符串，则将其task_level设置为 3 ，这样可以优先抓取解析这些页面',
  `set_middle_level_task_url_charactor` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '如果url链接匹配这里的字符串，则将其task_level设置为 2 ，这样可以次先抓取解析这些页面',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_10` (`parse_id`),
  KEY `FK_Reference_5` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `site_auth` */

DROP TABLE IF EXISTS `site_auth`;

CREATE TABLE `site_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT NULL,
  `login_page_url` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `login_user` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `login_passwd` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `login_user_xpath` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `login_passwd_xpath` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `login_submit_xpath` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_6` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='To save login account record info for browse blocked site pa';

/*Table structure for table `site_category` */

DROP TABLE IF EXISTS `site_category`;

CREATE TABLE `site_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_name` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cat_desc` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `site_content_page_check` */

DROP TABLE IF EXISTS `site_content_page_check`;

CREATE TABLE `site_content_page_check` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` bigint(20) DEFAULT NULL COMMENT 'the content page check item belonged web site id',
  `by_ele_type` varchar(100) DEFAULT NULL COMMENT 'search web element by condition',
  `by_ele_val` varchar(500) DEFAULT NULL COMMENT 'search by element value',
  `by_tag_attribute` varchar(500) DEFAULT NULL COMMENT 'if by_ele_type is Tag_Name, then by_ele_val is real tag name and by_tag_attribute is the attribute of that tag name',
  `charactor` tinytext COMMENT '是 content page 的特征字符串，multiple value can be seperated by ;',
  `not_charactor` tinytext COMMENT '非 content page 的特征字符串，multiple value can be seperated by ;',
  `parse_value_reg_exp` varchar(2000) DEFAULT NULL COMMENT '对上面解析出来的值执行正则表达式得到想要的值,该字段的格式为 regexp1;regexp2;regexp3...',
  `parse_value_string` text COMMENT '如果该值很难用正则表达式解析，就用这个字段进行字符串解析，该字段内容格式startStr1,endStr1;startStr1,endStr1;startStr1,endStr1;...',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `site_url_parse` */

DROP TABLE IF EXISTS `site_url_parse`;

CREATE TABLE `site_url_parse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT NULL COMMENT 'the url parse item belonged web site id',
  `url_charactor` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'if url link in the page match this then this url link is the url we want  ',
  `not_url_charactor` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'if url link in the page match this then this url link is not the url we want  ',
  `exec_javascript` varchar(2000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'the javascript code that will run on the element or url parsed out',
  `url_page_desc` varchar(2000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'the description of the url according page',
  `page_encoding` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'the url page''s according page encoding',
  `url_match_reg_exp` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'url that match this regular expression is the url we want',
  `content_page_url_charactor` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'record content page url charactor, if a url match 1 condition, then it is a content page',
  `by_ele_type` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'search web element by condition',
  `by_ele_val` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'search by element value',
  `by_tag_attribute` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'if by_ele_type is Tag_Name, then by_ele_val is real tag name and by_tag_attribute is the attribute of that tag name',
  `run_regexp_on_url` varchar(2000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '在解析出来的url上运行的正则表达式，使用正则表达式运行之后的url作为页面的url',
  `run_string_find_on_url` varchar(2000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '如果正则表达式不好写，则用该字段进行解析，该字段的格式是   startStr1!@#endStr1;startStr2!@#endStr2;..., 将这些字符串截取之后的字符串再拼成一个字符串作为url， 如果startStr 是字符串开始则用空字符串代表，如果endStr是字符串结尾则用空字符串代表',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_7` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
