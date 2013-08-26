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

/*Data for the table `account` */

LOCK TABLES `account` WRITE;

insert  into `account`(`id`,`user_name`,`pass_word`,`qq`,`email`,`mobile`,`login_time`) values (1,'admin','admin','wewrterererew','fdfdfrerereewd',NULL,NULL);

UNLOCK TABLES;

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

/*Data for the table `account_action` */

LOCK TABLES `account_action` WRITE;

UNLOCK TABLES;

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

/*Data for the table `account_role_map` */

LOCK TABLES `account_role_map` WRITE;

insert  into `account_role_map`(`account_id`,`role_id`) values (1,2);

UNLOCK TABLES;

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

/*Data for the table `db_setting` */

LOCK TABLES `db_setting` WRITE;

UNLOCK TABLES;

/*Table structure for table `download_mq_message` */

DROP TABLE IF EXISTS `download_mq_message`;

CREATE TABLE `download_mq_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表索引id',
  `user_id` int(11) DEFAULT NULL COMMENT '发送消息的用户的id',
  `site_id` int(11) DEFAULT NULL COMMENT '站点的id',
  `download_thread_type` int(11) DEFAULT NULL COMMENT '线程的类型',
  `action` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '执行的动作',
  `thread_table_ids` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '启动或者结束的线程表的id',
  `thread_ids` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '启动或者结束的线程的id',
  `send_time` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'MQ message 发送的时间',
  `receive_time` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'MQ message 收到的时间',
  `finish_time` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Action 完成的时间',
  `fail_reason` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'MQ action 失败的原因',
  `create_thread_count` int(11) DEFAULT NULL COMMENT '创建的线程数量，范围为 1 - 5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `download_mq_message` */

LOCK TABLES `download_mq_message` WRITE;

insert  into `download_mq_message`(`id`,`user_id`,`site_id`,`download_thread_type`,`action`,`thread_table_ids`,`thread_ids`,`send_time`,`receive_time`,`finish_time`,`fail_reason`,`create_thread_count`) values (1,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','1','42','1377486037913','1377486037955','1377486037995','',1),(2,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','1','42','1377486229585','1377486229614','1377486229883','',1),(3,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','2','19','1377486273006','1377486273150','1377486273203','',1),(4,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','2','19','1377490671269','1377490672367','1377490672401','',1),(5,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','3','25','1377490738896','1377490738931','1377490738981','',1),(6,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','3','25','1377491442274','1377491442447','1377491442474','',1),(7,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','4','21','1377491473595','1377491473622','1377491473711','',1),(8,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','4','21','1377491576771','1377491583532','1377491583578','',1),(9,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','5','19','1377491601233','1377491601263','1377491601325','',1),(10,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','5','19','1377491930073','1377491930103','1377491930126','',1),(11,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','6','49','1377492000808','1377492000891','1377492000915','',1),(12,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','6','49','1377492217292','1377492217333','1377492217546','',1),(13,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','7','71','1377492272911','1377492272948','1377492272972','',1),(14,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','7','71','1377492331549','1377492362998','1377492363021','',1),(15,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','8','85','1377492384325','1377492384353','1377492384374','',1),(16,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','8','85','1377492657757','1377492657789','1377492657975','',1),(17,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','9','111','1377492721663','1377492721689','1377492721705','',1),(18,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','9','111','1377492896838','1377492896991','1377492897242','',1),(19,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','10','133','1377492908845','1377492908911','1377492908935','',1),(20,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','10','133','1377492928556','1377492998953','1377492999347','',1),(21,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','10','133','1377492998148','1377492999434','1377492999459','',1),(22,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','11','149','1377493039342','1377493039374','1377493039393','',1),(23,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','11','149','1377493140800','1377493140855','1377493141090','',1),(24,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','12','161','1377493159361','1377493159396','1377493159415','',1),(25,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','12','161','1377493675004','1377493675038','1377493675311','',1),(26,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','13','20','1377493959284','1377493959441','1377493959547','',1),(27,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','13','20','1377494012152','1377494049619','1377494049865','',1),(28,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','14','18','1377494101893','1377494102035','1377494102071','',1),(29,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','23','157','1377494294259','1377494294286','1377495014540','',9),(30,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','23,22,21,20,19,18,17,16,15,14','157,146,124,109,98,82,71,61,50,18','1377495218537','1377495218643','1377495223423','',1),(31,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','33','345','1377495562114','1377495562152','1377496373197','',10),(32,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','33,32,31,30,29,28,27,26,25,24','345,304,299,294,289,272,255,244,233,216','1377501074526','1377501075566','1377501075596','',1),(33,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','34','37','1377501400176','1377501400203','1377501400254','',1),(34,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','34','37','1377502358926','1377502359016','1377502359317','',1),(35,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','38','147','1377502627817','1377502627860','1377502897966','',10),(36,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','38,37,36,35','147,136,124,113','1377502957908','1377503310675','1377503310710','',1),(37,1,6,4,'DOWNLOAD_THREAD_ACTION_STOP','38,37,36,35','147,136,124,113','1377503319746','1377503319778','1377503319804','',1),(38,1,6,2,'DOWNLOAD_THREAD_ACTION_CREATE','48','136','1377503548265','1377503548300','1377504358635','',10);

UNLOCK TABLES;

/*Table structure for table `download_setting` */

DROP TABLE IF EXISTS `download_setting`;

CREATE TABLE `download_setting` (
  `total_common_thread_number` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `download_setting` */

LOCK TABLES `download_setting` WRITE;

UNLOCK TABLES;

/*Table structure for table `download_thread` */

DROP TABLE IF EXISTS `download_thread`;

CREATE TABLE `download_thread` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT 'User id who start this download thread.',
  `thread_id` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'The java download runner thread id. ',
  `start_time` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Thread start time.',
  `stop_time` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Thread end time, if not end then this value is -1.',
  `thread_type` tinyint(1) DEFAULT NULL COMMENT 'The work this thread implement. 0---Get web page in top url page, 1---Parse result in xml format, 2---Test working thread, 3---Running working thread.',
  `stop_reason` longtext COLLATE utf8_unicode_ci COMMENT 'The reason why this thread stop',
  `site_id` bigint(20) DEFAULT NULL COMMENT '线程访问的网站id',
  `webdriver_each_browse_start_time` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '每次 webdriver 访问网页的开始时间，webdriver每次访问一个网页都会更新这个时间，有一个监控线程轮询查看，如果 webdriver 开始访问页面时间和当前的时间之差大于10分钟，则认为 webdriver 挂起，则重新启动那个线程 ',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `download_thread` */

LOCK TABLES `download_thread` WRITE;

insert  into `download_thread`(`id`,`user_id`,`thread_id`,`start_time`,`stop_time`,`thread_type`,`stop_reason`,`site_id`,`webdriver_each_browse_start_time`) values (39,1,'31','1377503548334','',2,'',6,NULL),(40,1,'45','1377503638352','',2,'',6,NULL),(41,1,'56','1377503728385','',2,'',6,NULL),(42,1,'68','1377503818410','',2,'',6,NULL),(43,1,'77','1377503908446','',2,'',6,NULL),(44,1,'87','1377503998470','',2,'',6,NULL),(45,1,'97','1377504088514','',2,'',6,NULL),(46,1,'108','1377504178571','',2,'',6,NULL),(47,1,'113','1377504268595','',2,'',6,NULL),(48,1,'136','1377504358634','',2,'',6,NULL);

UNLOCK TABLES;

/*Table structure for table `download_thread_apply_task_status` */

DROP TABLE IF EXISTS `download_thread_apply_task_status`;

CREATE TABLE `download_thread_apply_task_status` (
  `applying_task` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0: no thread apply download task, 1: has 1 thread apply download task',
  `apply_time` varchar(20) DEFAULT NULL COMMENT 'last thread apply task time, for check how long since last apply task, use this to reset applying_task to 0 if apply thread forget reset it'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='For synchronize multiple thread apply download task. ';

/*Data for the table `download_thread_apply_task_status` */

LOCK TABLES `download_thread_apply_task_status` WRITE;

insert  into `download_thread_apply_task_status`(`applying_task`,`apply_time`) values (0,'1377507030557');

UNLOCK TABLES;

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `menu_desc` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `menu_action` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `menu` */

LOCK TABLES `menu` WRITE;

insert  into `menu`(`id`,`menu_name`,`menu_desc`,`menu_action`) values (1,'Web Site','Web Site define.','ACTION_WEB_SITE_CATEGORY_LIST'),(2,'Template','Template for parse the website page.','ACTION_PARSE_TEMPLATE_CATEGORY_LIST'),(3,'Role','Role in this system.','ACTION_ROLE_LIST'),(4,'Menu','Menu in navigation page.','ACTION_MENU_LIST'),(5,'User','User that will use this system.','ACTION_USER_LIST'),(6,'Settings','General Settings. Download Thread, etc','ACTION_SHOW_SETTING'),(7,'Report','Analyze and report.','ACTION_SHOW_ANALYTIC'),(8,'Data Table','Data table which store parsed out data.','ACTION_DATA_TABLE_LIST'),(9,'Log Out','User use this link to logout system','ACTION_LOGOUT');

UNLOCK TABLES;

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

/*Data for the table `parse` */

LOCK TABLES `parse` WRITE;

insert  into `parse`(`id`,`category_id`,`parse_name`,`parse_desc`,`save_to_table`) values (22,4,'Sina soft download','Sina soft download',NULL),(23,4,'Mac ios app parse template','Mac ios app parse template',NULL),(24,6,'淘宝店铺','解析淘宝店铺信息的模版','data_tao_bao_jie'),(25,5,'sina_weibo','sina wei bo',NULL),(26,7,'58.com招聘','解析58.com招聘信息','data_zhao_pin'),(27,6,'淘宝各种食品信息(不包括天猫)','解析淘宝里面的美食特产里面的食品信息(不包括天猫)','data_xiu_hao_chi');

UNLOCK TABLES;

/*Table structure for table `parse_category` */

DROP TABLE IF EXISTS `parse_category`;

CREATE TABLE `parse_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_name` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cat_desc` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `parse_category` */

LOCK TABLES `parse_category` WRITE;

insert  into `parse_category`(`id`,`cat_name`,`cat_desc`) values (4,'下载网站','下载网站解析模板'),(5,'社交网站','社交网站解析模板'),(6,'电子商务网站','电子商务网站解析模板'),(7,'招聘网站','抓取招聘,社区,等各种网站的招聘，简历，招聘会等信息');

UNLOCK TABLES;

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

/*Data for the table `parse_item` */

LOCK TABLES `parse_item` WRITE;

insert  into `parse_item`(`id`,`parse_id`,`save_to_table`,`save_to_column`,`column_desc`,`charactor`,`not_charactor`,`default_val`,`data_type`,`number_format`,`date_format`,`is_check_repeat_column`,`is_url`,`src_reg_exp`,`dest_reg_exp`,`exec_javascript`,`page_url_as_value`,`reg_exp_item_relation`,`by_ele_type`,`by_ele_val`,`by_tag_attribute`,`parse_value_reg_exp`,`parse_value_string`,`is_direct_get_text`,`use_this_setting_url_charactor`) values (7,23,'data_ios_app','title','app 标题','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(8,23,'data_ios_app','ituns_url','ituns url','','','','DATA_TYPE_STRING','','',1,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10,23,'data_ios_app','manufacturer','develop company','','','','DATA_TYPE_STRING','','',0,0,'^开发商：','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11,23,'data_ios_app','app_price','app 价格','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(12,23,'data_ios_app','category','类别','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(13,23,'data_ios_app','update_date','更新日期','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(14,23,'data_ios_app','app_version','版本','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(15,23,'data_ios_app','size','应用程序大小','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(16,23,'data_ios_app','app_language','语言类型','','','','DATA_TYPE_STRING','','',0,0,'','','',0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(17,23,'data_ios_app','mac_rate','本软件在app store 里面的评级','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(18,23,'data_ios_app','os_require','操作系统要求','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(19,23,'data_ios_app','current_version_rate_score','当前版本评分','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(20,23,'data_ios_app','total_version_rate_score','所有版本评分','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(21,23,'data_ios_app','new_feature','新功能','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(22,23,'data_ios_app','icon_url','图标的链接地址','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(23,23,'data_ios_app','manufacturer_web_site','开发商网址','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(24,23,'data_ios_app','manufacturer_product_support','技术支持网址','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(25,23,'data_ios_app','iphone_img_url1','iphone屏幕截图地址1','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(26,23,'data_ios_app','iphone_img_url2','iphone屏幕截图地址2','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(27,23,'data_ios_app','iphone_img_url3','iphone屏幕截图地址3','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(28,23,'data_ios_app','iphone_img_url4','iphone屏幕截图地址4','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(29,23,'data_ios_app','iphone_img_url5','iphone屏幕截图地址5','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(30,23,'data_ios_app','ipad_img_url1','ipad屏幕截图1','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(31,23,'data_ios_app','ipad_img_url2','ipad屏幕截图2','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(32,23,'data_ios_app','ipad_img_url3','ipad屏幕截图3','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(33,23,'data_ios_app','ipad_img_url4','ipad屏幕截图4','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(34,23,'data_ios_app','ipad_img_url5','ipad屏幕截图5','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(35,24,'data_tao_bao_jie','shop_name','淘宝或者天猫店铺名称','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div/div[2]/div[1]/dl/dd[1]/a;//*[@id=\"header-content\"]/div[1]/div[1]/a[1];//*[@id=\"shop-info\"]/div[1]/a;//*[@id=\"mallLogo\"]/span[2]/a','','','',1,NULL),(36,24,'data_tao_bao_jie','company_name','商品公司名称','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div/ul/li[1]/div[2]','','','',1,''),(37,24,'data_tao_bao_jie','tmall_shop_img_url','是否是天猫店铺的标志图片的url链接地址','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"mallLogo\"]/span[1]/a','','','',1,NULL),(38,24,'data_tao_bao_jie','pin_pai_zhi_xiao','是否品牌直销的标志','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"mallLogo\"]/span[2]/a/span','','','',1,NULL),(39,24,'data_tao_bao_jie','seller_name','店主的名称','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div[1]/div/a;//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div/div[2]/a','','','',1,NULL),(40,24,'data_tao_bao_jie','seller_tao_bao_wangwang_url','店主淘宝旺旺url 链接，可以直接点击该链接和店主聊天','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div/div[2]/span/a;//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div[1]/div/span/a','href','','',0,''),(41,24,'data_tao_bao_jie','main_product','店铺主营业务','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div[1]/ul/li[1];//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div/ul/li[2]','','','',1,NULL),(42,24,'data_tao_bao_jie','main_product_percentage','主营占所有业务的百分比','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[1]/div/div[3]/div[2]/div[2]/div/div[2]/div[3]','','','',1,NULL),(43,24,'data_tao_bao_jie','area','所在地区','所在地区：','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div/div[2]/div[1]/dl/dd[1];//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div[1]/ul/li[2];//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div/ul/li[3]','','','',1,''),(44,24,'data_tao_bao_jie','create_time','店铺创建时间','创店时间：','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div[1]/ul/li[3];','','','',1,NULL),(45,24,'data_tao_bao_jie','seller_credict','卖家信用','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div[2]/ul/li[1]','','','',1,NULL),(46,24,'data_tao_bao_jie','buyer_credict','买家信用','买家信用','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div[2]/ul/li[2];//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div/div[2]/div[2]/ul/li','','','',1,''),(47,24,'data_tao_bao_jie','seller_credict_pic_url','卖家信用图片链接地址','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div[2]/ul/li[1]/a/img','src','','',0,''),(48,24,'data_tao_bao_jie','buyer_credict_pic_url','买家信用图片地址','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div[2]/ul/li[2]/a/img;//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div/div[2]/div[2]/ul/li/a[2]/img','src','','',0,''),(49,24,'data_tao_bao_jie','buyer_vip_pic_url','买家 vip 图片链接地址','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div[2]/ul/li[2]/span/a/img','src','','',0,''),(50,24,'data_tao_bao_jie','buyer_vip_text','买家会员资格文字描述','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div[2]/ul/li[2]/span/a/img','title','','',0,''),(51,24,'data_tao_bao_jie','product_count','商铺拥有的商品数量','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-info\"]/div[3]/div[4]/ul/li[2];//*[@id=\"header-content\"]/div[1]/div[4]/div[2]/div[1]/dl[3]/dd/span','','','',1,NULL),(52,24,'data_tao_bao_jie','seller_xiao_fei_bao_zhang','卖家消费保障','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[2]/ul/li[1]/a','','','',1,NULL),(53,24,'data_tao_bao_jie','tmall_zheng_pin','天猫商铺保证正品的标识','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[2]/a[1]','title','','',0,''),(54,24,'data_tao_bao_jie','seller_7_tian_tui_huan','7天退换文字','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[2]/ul/li[2]/a;//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[2]/a[3]','title','','',0,''),(55,24,'data_tao_bao_jie','seller_jia_yi_pei_3','卖家承诺假一赔3的文字','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[2]/ul/li[3]/a','','','',1,NULL),(56,24,'data_tao_bao_jie','tmall_fa_piao','天猫提供发票的文字','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[2]/a[2]','title','','',0,''),(57,24,'data_tao_bao_jie','seller_bao_zheng_jin','商家保证金数量','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[2]/div[2]/div[1]/div[2]/span','','','',1,NULL),(58,24,'data_tao_bao_jie','shop_ren_zheng_zhifu_bao_ge_ren','店铺经营资质，支付宝个人认证文字','个人认证','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-info\"]/div[3]/div[3]/ul/li[2]/a/img;//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div/div[2]/div[1]/dl/dd[3]/a/img;//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[3]/div[2]/div/ul/li/a/img','title','','',0,''),(59,24,'data_tao_bao_jie','shop_ren_zheng_zhifu_bao_qi_ye','店铺经营资质，支付宝企业认证文字','企业认证','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-info\"]/div[3]/div[3]/ul/li[2]/a/img;//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[3]/div[2]/div/ul/li[1]/a/img','alt','','',0,''),(60,24,'data_tao_bao_jie','shop_ren_zheng_qi_ye_zhi_zhao_url','店铺经营资质，支付宝企业认证营业执照链接地址','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','J_ShowLicence','href','','',0,''),(61,24,'data_tao_bao_jie','product_desc_match_score','宝贝与描述相符得分','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"dsr\"]/li[1]/div[1]/em[1]','title','','',0,''),(62,24,'data_tao_bao_jie','avg_drawback_speed','商家平均退款速度','平均退款速度：','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"halfmonth\"]/div[1]/div[2]/div[1]','','','',1,''),(63,24,'data_tao_bao_jie','complaint_count_30_after_sale','因售后问题投诉数','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"halfmonth\"]/div[2]/div[3]/div[2]/div[2]/ul/li[1]/span','','','',1,''),(64,24,'data_tao_bao_jie','complaint_count_30_total','近30天投诉总数','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"halfmonth\"]/div[2]/div[3]/div[2]/div[1]/span','','','',1,NULL),(65,24,'data_tao_bao_jie','complaint_count_30_wei_gui','近30天因行为违规投诉数','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"halfmonth\"]/div[2]/div[3]/div[2]/div[2]/ul/li[2]/span','','','',1,NULL),(66,24,'data_tao_bao_jie','complaint_percentage_30','近30天投诉率','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"halfmonth\"]/div[1]/div[2]/div[3]/span[2]','','','',1,NULL),(67,24,'data_tao_bao_jie','drawback_count_30_not_recive','近30天因未收到货退款次数','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"halfmonth\"]/div[2]/div[2]/div[2]/div[2]/ul/li[2]/span','','','',1,NULL),(68,24,'data_tao_bao_jie','drawback_count_30_no_reason','近30天因买家无理由退款次数','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"halfmonth\"]/div[2]/div[2]/div[2]/div[2]/ul/li[3]/span','','','',1,NULL),(69,24,'data_tao_bao_jie','drawback_count_30_quality','近30天因商品质量问题退款次数','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"halfmonth\"]/div[2]/div[2]/div[2]/div[2]/ul/li[1]/span','','','',1,NULL),(70,24,'data_tao_bao_jie','drawback_count_30_total','近30天退款总次数','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"halfmonth\"]/div[2]/div[2]/div[2]/div[1]/span','','','',1,NULL),(71,24,'data_tao_bao_jie','product_desc_compare_average','商品描述得分与行业平均水平比较，\'高于\': 高于行业平均值，\'持平\'：等于行业平均值，\'低于\'：低于行业平均值','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"dsr\"]/li[2]/div[1]/em[2]/strong','class','','',0,''),(72,24,'data_tao_bao_jie','product_desc_match_score_percentage','商品描述得分与行业平均得分值的百分比','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"dsr\"]/li[1]/div[1]/em[2]/strong','','','',1,NULL),(73,24,'data_tao_bao_jie','product_desc_voter_number','商品描述投票人数','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"dsr\"]/li[1]/div[2]/div/div[1]/span[2]','','','',1,NULL),(74,24,'data_tao_bao_jie','punish_count_30','近30天处罚数','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"halfmonth\"]/div[1]/div[2]/div[4]','','','',1,''),(75,24,'data_tao_bao_jie','punish_count_30_patent','近30天因侵犯知识产权处罚数','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"halfmonth\"]/div[2]/div[4]/div[2]/div[2]/ul/li[2]/span','','','',1,NULL),(76,24,'data_tao_bao_jie','punish_count_30_unallow','近30天因发布违禁信息处罚数','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"halfmonth\"]/div[2]/div[4]/div[2]/div[2]/ul/li[3]/span','','','',1,NULL),(77,24,'data_tao_bao_jie','punish_count_30_untrue','近30天因虚假交易处罚数','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"halfmonth\"]/div[2]/div[4]/div[2]/div[2]/ul/li[1]/span','','','',1,NULL),(78,24,'data_tao_bao_jie','seller_good_evaluation_percentage','卖家好评率','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[1]/div/div[3]/div[1]/div[1]/h4/em','','','',1,NULL),(79,24,'data_tao_bao_jie','seller_service_compare_average','卖家服务态度得分与行业平均水平比较，\'高于\': 高于行业平均值，\'持平\'：等于行业平均值，\'低于\'：低于行业平均值','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"dsr\"]/li[2]/div[1]/em[2]/strong','class','','',0,''),(80,24,'data_tao_bao_jie','seller_service_score','卖家服务态度得分','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"dsr\"]/li[2]/div[1]/em[1]','title','','',0,''),(81,24,'data_tao_bao_jie','seller_service_score_percentage','卖家服务态度得分与行业平均得分值的百分比','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"dsr\"]/li[2]/div[1]/em[2]/strong','','','',1,NULL),(82,24,'data_tao_bao_jie','seller_service_voter_number','卖家服务态度投票人数','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"dsr\"]/li[2]/div[2]/div/div[1]/span[2]','','','',1,NULL),(83,24,'data_tao_bao_jie','shop_logo_pic_url','shop logo 图片地址 url','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-logo\"]/p/a/img','src','','',0,''),(84,24,'data_tao_bao_jie','shop_shou_cang_ren_qi','店铺收藏人气','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','J_SCollCount','','','',1,''),(85,24,'data_tao_bao_jie','shop_taobao_url','店铺淘宝链接地址','','','','DATA_TYPE_STRING','','',1,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div/div[2]/a;//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div[1]/div/a;//*[@id=\"header-content\"]/div[1]/div[1]/a[1];//*[@id=\"shop-info\"]/div[1]/a','href','','',0,''),(86,24,'data_tao_bao_jie','shop_can_use_credit_card','店铺可以使用信用卡支付的文字','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-info\"]/div[3]/div[3]/ul/li[1]/a/img','title','','',0,''),(87,24,'data_tao_bao_jie','shop_has_entity_store','是否拥有实体店铺','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-info\"]/div[3]/div[3]/ul/li[2]/a/img','alt','','',0,''),(88,24,'data_tao_bao_jie','shop_phone_number','店铺联系电话，主要天猫店铺','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rate-box\"]/div[1]/div[2]/div[1]/div[2]/div/ul/li[4]','','','',1,NULL),(89,24,'data_tao_bao_jie','seller_ship_speed_score','卖家发货速度得分','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"dsr\"]/li[3]/div[1]/em[1]','title','','',0,''),(90,24,'data_tao_bao_jie','seller_ship_speed_compare_average','卖家发货速度得分与行业平均水平比较，\'高于\': 高于行业平均值，\'持平\'：等于行业平均值，\'低于\'：低于行业平均值','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"dsr\"]/li[3]/div[1]/em[2]/strong','class','','',0,''),(91,24,'data_tao_bao_jie','seller_ship_speed_score_percentage','卖家发货速度得分与行业平均得分值的百分比','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"dsr\"]/li[3]/div[1]/em[2]/strong','','','',1,NULL),(92,24,'data_tao_bao_jie','seller_ship_speed_voter_number','卖家发货速度投票人数','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"dsr\"]/li[3]/div[2]/div/div[1]/span[2]','','','',1,NULL),(93,24,'data_tao_bao_jie','drawback_percentage_30','近30天退款率','近30天退款率：','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"halfmonth\"]/div[1]/div[2]/div[2]','','','',1,NULL),(94,24,'data_tao_bao_jie','shop_rate_url','店铺评分网址','','','','DATA_TYPE_STRING','','',0,0,'','','',1,0,'Id','qq','','','',1,''),(95,25,'data_sina_wei_bo','id','id','','','','DATA_TYPE_NUMBER','','',0,0,'','','',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(96,26,'data_zhao_pin','company','公司名称','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','main','','','dt>#@!</dt!@#>#@!<',NULL,NULL),(97,26,'data_zhao_pin','company_desc','公司描述','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','gongsi','','','',NULL,NULL),(98,26,'data_zhao_pin','company_gui_mo','公司规模','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','main','','','公司规模：#@!<',NULL,NULL),(99,26,'data_zhao_pin','company_hang_ye','公司行业','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','main','','','公司行业：#@!</dd',NULL,NULL),(100,26,'data_zhao_pin','company_web_site','公司网址','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','lianxi','','','公司网址#@!</a>!@#href=\"#@!\"',NULL,NULL),(101,26,'data_zhao_pin','company_xing_zhi','公司性质','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','main','','','公司性质：#@!<',NULL,NULL),(102,26,'data_zhao_pin','company_zhi_zhao','公司营业执照','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Class_Name','license','','','',NULL,NULL),(103,26,'data_zhao_pin','contact_address','联系地址','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','lianxi','','','联系地址：#@!</dd',NULL,NULL),(104,26,'data_zhao_pin','job_area','工作地点','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','main','','','发布区域：#@!</dd',NULL,NULL),(105,26,'data_zhao_pin','contact_person','联系人','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','lianxi','','','dd>#@!(',NULL,NULL),(106,26,'data_zhao_pin','job_browse_count','职位浏览次数','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','totalcount','','','',NULL,NULL),(107,26,'data_zhao_pin','job_desc','职位描述','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','zhiwei','','','dd>#@!</dd',NULL,NULL),(108,26,'data_zhao_pin','job_jian_li_tou_di_liang','简历投递量','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','deliveryCount','','','',NULL,NULL),(109,26,'data_zhao_pin','job_jing_yan','工作经验或者年限','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','main','','','工作年限：#@!</dd',NULL,NULL),(110,26,'data_zhao_pin','job_jing_zheng_li','职位竞争力','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Class_Name','competitiveness','','','<dd#@!<dt',NULL,NULL),(111,26,'data_zhao_pin','job_salary','转正工资','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','main','','','转正工资：#@!</dd!@#dd>#@!<',NULL,NULL),(112,26,'data_zhao_pin','job_title','职位名称','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','main','','','招聘职位：#@!</dd',NULL,NULL),(113,26,'data_zhao_pin','job_update_time','更新时间','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Class_Name','companymsg','','','lowreltime#@!更新时间!@#dd>#@!</dd',NULL,NULL),(114,26,'data_zhao_pin','job_url','工作详情网址','','','','DATA_TYPE_STRING','','',1,0,'','','',1,0,'Id','main','','','',NULL,NULL),(115,26,'data_zhao_pin','job_xue_li','学历要求','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','main','','','学历要求：#@!</dl!@#dd>#@!</dd',NULL,NULL),(116,26,'data_zhao_pin','contact_telphone','','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','showphone','','','',NULL,NULL),(117,24,'data_tao_bao_jie','shop_huo_dao_fu_kuan','是否支持货到付款','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-info\"]/div[3]/div[3]/ul/li[2]/a/img','alt','','',0,''),(119,27,'data_xiu_hao_chi','biao_ti','商品标题','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','/html/head/title','','','',1,''),(121,27,'data_xiu_hao_chi','cu_xiao_jia','促销价','','','','DATA_TYPE_STRING','','',0,0,'¥','','',0,0,'XPath','//*[@id=\"J_PromoPrice\"]/div/strong','','','',1,''),(124,27,'data_xiu_hao_chi','img_url','图片链接url','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"J_ImgBooth\"];/html/body/div[18]/img','data-src;src','','',0,''),(125,27,'data_xiu_hao_chi','item_url','商品链接url','','','','DATA_TYPE_STRING','','',1,0,'','','',1,0,'Id','qq','','','',0,''),(126,27,'data_xiu_hao_chi','jia_ge','商品价格','','','','DATA_TYPE_STRING','','',0,0,'¥','','',0,0,'Id','J_StrPrice','','','',1,''),(128,27,'data_xiu_hao_chi','ping_fen','商品评分','','','','DATA_TYPE_STRING','','',0,0,'分','','',0,0,'Id','J_RateStar','','','',1,''),(131,27,'data_xiu_hao_chi','shop_name','店铺名称','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"detail\"]/div[2]/div/div[3]/div/div[2]/p[1]/a;//*[@id=\"detail\"]/div[1]/div/div[1]/div/div[2]/p/a','','','',1,''),(132,27,'data_xiu_hao_chi','shop_url','店铺链接url','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"detail\"]/div[2]/div/div[3]/div/div[2]/p[1]/a;//*[@id=\"detail\"]/div[1]/div/div[1]/div/div[2]/p/a','href','','#@!/?',0,''),(138,27,'data_xiu_hao_chi','dan_jia','商品单价','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"J_StrPriceModBox\"]/span[2]','','','',1,''),(139,27,'data_xiu_hao_chi','meta_desc','商品 meta description 信息','','','','DATA_TYPE_STRING','','',0,0,'欢迎前来淘宝网;\"',';','',0,1,'Tag_Name','head','','','<meta name=\"description\" content=\"#@!>',0,''),(140,27,'data_xiu_hao_chi','meta_search_keyword','商品 meta keyword 关键词信息','','','','DATA_TYPE_STRING','','',0,0,'淘宝,;掏宝,;网上购物,;店铺,;\"',';;;;','',0,1,'Tag_Name','head','','','<meta name=\"keywords\" content=\"#@!>',0,''),(141,27,'data_xiu_hao_chi','shang_pin_lai_yuan','商品信息来源','','','2','DATA_TYPE_STRING','','',0,0,'','','',0,0,'Id','qq','','','',0,''),(142,27,'data_xiu_hao_chi','shop_level_img_url','店铺评级，皇冠数图片地址','','','','DATA_TYPE_STRING','','',0,0,'','','',0,0,'XPath','//*[@id=\"shop-rank\"]/img','src','','',0,'');

UNLOCK TABLES;

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

/*Data for the table `parse_item_action` */

LOCK TABLES `parse_item_action` WRITE;

insert  into `parse_item_action`(`id`,`parse_item_id`,`by_ele_type`,`by_ele_val`,`by_ele_action`) values (4,-1,'Id','telbtn','JAVA_SCRIPT_ACTION_CLICK');

UNLOCK TABLES;

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

/*Data for the table `parse_page_exception` */

LOCK TABLES `parse_page_exception` WRITE;

UNLOCK TABLES;

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `role_desc` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `role` */

LOCK TABLES `role` WRITE;

insert  into `role`(`id`,`role_name`,`role_desc`) values (1,'Admin','Administrator of this system.'),(2,'Super Admin','Super administrator of this system.');

UNLOCK TABLES;

/*Table structure for table `role_menu_map` */

DROP TABLE IF EXISTS `role_menu_map`;

CREATE TABLE `role_menu_map` (
  `role_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  KEY `FK_Reference_3` (`role_id`),
  KEY `FK_Reference_4` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Relation between RoleTbl and MenuTbl';

/*Data for the table `role_menu_map` */

LOCK TABLES `role_menu_map` WRITE;

insert  into `role_menu_map`(`role_id`,`menu_id`) values (2,1),(2,2),(2,3),(2,4),(2,5),(2,6),(2,7),(2,8),(2,9);

UNLOCK TABLES;

/*Table structure for table `selenium_setting` */

DROP TABLE IF EXISTS `selenium_setting`;

CREATE TABLE `selenium_setting` (
  `browser_type` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `selenium_setting` */

LOCK TABLES `selenium_setting` WRITE;

UNLOCK TABLES;

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

/*Data for the table `site` */

LOCK TABLES `site` WRITE;

insert  into `site`(`id`,`category_id`,`site_name`,`site_desc`,`parse_id`,`top_url`,`start_download_time`,`download_duration_seconds`,`over_write_attributes`,`download_task_reget_duration_d`,`need_login`,`login_check_return_url`,`login_check_return_data`,`login_check_return_data_xpath`,`test_passed`,`site_status`,`multi_country`,`place_holders`,`browser_type`,`access_denied_page_element_xpath`,`access_denied_page_element_value`,`alert_text_to_reset_d_task`,`need_scroll_page_url_charactor`,`not_need_scroll_page_url_charactor`,`set_high_level_task_url_charactor`,`set_middle_level_task_url_charactor`) values (1,8,'新浪软件下载','新浪软件下载',22,'http://tech.sina.com.cn/down/',8,9,'',10,0,'','','',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,8,'Apple ios app download','',23,'https://itunes.apple.com/cn/genre/ios/id36?mt=8',3,6,'https://itunes.apple.com/cn/genre/mac/id39?mt=12;https://itunes.apple.com/tw/genre/music/id34;https://itunes.apple.com/tw/genre/movies/id33;https://itunes.apple.com/tw/genre/ios/id36?mt=8;https://itunes.apple.com/tw/genre/podcasts/id26?mt=2;https://itunes.apple.com/tw/genre/books/id38?mt=11;https://itunes.apple.com/tw/genre/music-videos/id31;https://itunes.apple.com/tw/genre/audiobooks/id50000024',9,0,'','','',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,10,'淘宝店铺信息','淘宝店铺信息',24,'http://list.taobao.com/browse/cat-0.htm?',9,9,'',12,0,'','','',0,0,0,'','WEB_DRIVER_BROWSER_TYPE_FIREFOX_10','/html/head/title','访问受限了','','list.taobao.com/;list.tmall.com/','','store.taobao.com;store.tmall.com;/view_shop.htm',''),(4,11,'58同城招聘','58同城招聘',26,'http://bj.58.com/job.shtml',2,2,'',8,0,'','','',0,0,0,'','WEB_DRIVER_BROWSER_TYPE_FIREFOX_10','','',NULL,NULL,NULL,NULL,NULL),(6,10,'淘宝各种食品信息(不包括天猫)','解析淘宝里面的美食特产里面的食品信息(不包括天猫)',27,'http://list.taobao.com/itemlist/market/food2011.htm?cat=50002766;http://list.taobao.com/itemlist/market/food2011.htm?cat=50035978;http://list.taobao.com/itemlist/market/food2011.htm?cat=50008825;http://list.taobao.com/itemlist/market/food2011.htm?cat=50103359;http://list.taobao.com/itemlist/market/nongye1.htm?cat=50107919;http://list.taobao.com/itemlist/market/food2011.htm?cat=50108542',9,9,'',12,0,'','','',0,0,0,'','WEB_DRIVER_BROWSER_TYPE_FIREFOX_10','/html/head/title','访问受限了','','list.taobao.com/','','','');

UNLOCK TABLES;

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

/*Data for the table `site_auth` */

LOCK TABLES `site_auth` WRITE;

UNLOCK TABLES;

/*Table structure for table `site_category` */

DROP TABLE IF EXISTS `site_category`;

CREATE TABLE `site_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_name` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cat_desc` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `site_category` */

LOCK TABLES `site_category` WRITE;

insert  into `site_category`(`id`,`cat_name`,`cat_desc`) values (8,'下载网站','记录各种数字产品下载网站的信息'),(9,'社交网站','社交网站信息'),(10,'电子商务网站','抓取电子商务网站店铺，商品，特价，限时特价信息，然后展示给用户，通过淘宝联盟来赚取广告费佣金'),(11,'招聘网站','抓取招聘,社区,等各种网站的招聘，简历，招聘会等信息');

UNLOCK TABLES;

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

/*Data for the table `site_content_page_check` */

LOCK TABLES `site_content_page_check` WRITE;

insert  into `site_content_page_check`(`id`,`site_id`,`by_ele_type`,`by_ele_val`,`by_tag_attribute`,`charactor`,`not_charactor`,`parse_value_reg_exp`,`parse_value_string`) values (1,4,'Id','header','','招聘','','','crb_i#@!</a>');

UNLOCK TABLES;

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

/*Data for the table `site_url_parse` */

LOCK TABLES `site_url_parse` WRITE;

insert  into `site_url_parse`(`id`,`site_id`,`url_charactor`,`not_url_charactor`,`exec_javascript`,`url_page_desc`,`page_encoding`,`url_match_reg_exp`,`content_page_url_charactor`,`by_ele_type`,`by_ele_val`,`by_tag_attribute`,`run_regexp_on_url`,`run_string_find_on_url`) values (4,1,'down.tech.sina.com.cn','','','Software download page.','','','3gsoft/download.php;\ndown.tech.sina.com.cn/content/','Tag_Name','a',NULL,NULL,NULL),(5,2,'/genre/ios;/app/','','','','','','/app/','Tag_Name','a',NULL,NULL,NULL),(6,3,'list.taobao.com/;rate.taobao.com/;store.taobao.com/;list.tmall.com/','/myRate.htm;rater=1;login.;regist;https;alisec.taobao.com/','','','','','rate.taobao.com','Tag_Name','a','href;HREF','','spm=!@#&;scm!@#&;&from=!@#&;taobao_from=!@#&;ssid=!@#&;style=!@#&;auction_tag=!@#&;sort=!@#&;area_code=!@#&;rn=!@#&;n=!@#&;vmarket=!@#&;search_condition=!@#&;active=!@#&;!@##J_Filter;!@##grid-column;_u=!@#&;ppath=!@#&;sku=!@#&;_tb_token_=!@#&'),(7,4,'/yewu/;/bianji/;/zpshengchankaifa/;/cantfwy/;/cuxiao/;/jiaoshi/;/faxingshi/;/kefu/;/sheji/;/siji/;/shouying/;/yingyeyuan/;/youerjiaoshi/;/meirongshi/;/wenziluru/;/wangzhanmeigong/;/qichewx/;/canguan/;/lihuoyuan/;/jypeixunshi/;/meifaxuetu/;/qiantai/;/wangluogongchengshi/;/baojieyuan/;/chufangqiepei/;/sudiyuan/;/fanyizhaopin/;/jinrong/;/zhaopinjingli/;/jishuzhichi/;/baomuxin/;/qiantjd/;/cangku/;/yiliao/;/zpjinrongbaoxian/;/caiwu/;/chengxuyuan/;/baoanl/;/jiudianjl/;/shineisheji/;/hushi/;/lfawurenyuan/;/xiaoshouyuan/;/xiaoshouzhuli/;/xiaoshoujingli/;/xiaoshouzongjian/;/dianhuaxiaoshou/;','passport.58.com;/house.shtml;/zufang;/ershoufang;/duanzu;/sale.shtml;/diannao;/shouji;/jiadian;','','','','','','Tag_Name','a',NULL,NULL,NULL),(8,3,'list.taobao.com/;rate.taobao.com/;store.taobao.com/;list.tmall.com/','/myRate.htm;rater=1;login.;regist;https;alisec.taobao.com/','','','','','rate.taobao.com','Css_Selector','a[data-module=\"page\"];a[data-k=\"page\"]',NULL,'','spm=!@#&;scm!@#&;&from=!@#&;taobao_from=!@#&;ssid=!@#&;style=!@#&;auction_tag=!@#&;sort=!@#&;area_code=!@#&;rn=!@#&;n=!@#&;vmarket=!@#&;search_condition=!@#&;active=!@#&;!@##J_Filter;!@##grid-column;_u=!@#&;ppath=!@#&;sku=!@#&;_tb_token_=!@#&'),(9,6,'50002766;50035978;50008825;50103359;50107919;50108542','login.;regist;https;alisec.taobao.com;re.taobao.com;simba.taobao.com;search;tmall.com','','','','','http://item.taobao.com/item.htm','Tag_Name','a',NULL,'','cat;id;s'),(10,6,'50002766;50035978;50008825;50103359;50107919;50108542','login.;regist;https;alisec.taobao.com;re.taobao.com;simba.taobao.com;search;tmall.com','','','','','http://item.taobao.com/item.htm','Css_Selector','a[data-module=\"page\"];a[data-k=\"page\"]',NULL,'','cat;id;s');

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
