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

/*Table structure for table `data_ios_app` */

DROP TABLE IF EXISTS `data_ios_app`;

CREATE TABLE `data_ios_app` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '标题',
  `ituns_url` varchar(2000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'ituns中的打开地址',
  `app_desc` text COLLATE utf8_unicode_ci COMMENT '产品描述',
  `manufacturer` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '开发商',
  `app_price` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'app 价格',
  `category` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '分类',
  `update_date` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新日期',
  `app_version` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '当前版本',
  `size` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文件大小',
  `app_language` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支持语言',
  `mac_rate` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'mac 评级',
  `os_require` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '系统要求',
  `current_version_rate_score` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '当前版本用户评分',
  `current_version_rate_count` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '当前版本用户投票数量',
  `total_version_rate_score` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所有版本用户评分',
  `total_version_rate_count` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所有版本用户投票数量',
  `new_feature` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '新功能',
  `icon_url` varchar(2000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '图标链接地址',
  `manufacturer_web_site` varchar(2000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '开发商网站地址',
  `manufacturer_product_support` varchar(2000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '开发商产品支持地址',
  `iphone_img_url1` text COLLATE utf8_unicode_ci COMMENT 'iphone屏幕截图地址1',
  `iphone_img_url2` text COLLATE utf8_unicode_ci COMMENT 'iphone屏幕截图地址2',
  `iphone_img_url3` text COLLATE utf8_unicode_ci COMMENT 'iphone屏幕截图地址3',
  `iphone_img_url4` text COLLATE utf8_unicode_ci COMMENT 'iphone屏幕截图地址4',
  `iphone_img_url5` text COLLATE utf8_unicode_ci COMMENT 'iphone屏幕截图地址5',
  `ipad_img_url1` text COLLATE utf8_unicode_ci COMMENT 'ipad屏幕截图地址1',
  `ipad_img_url2` text COLLATE utf8_unicode_ci COMMENT 'ipad屏幕截图地址2',
  `ipad_img_url3` text COLLATE utf8_unicode_ci COMMENT 'ipad屏幕截图地址3',
  `ipad_img_url4` text COLLATE utf8_unicode_ci COMMENT 'ipad屏幕截图地址4',
  `ipad_img_url5` text COLLATE utf8_unicode_ci COMMENT 'ipad屏幕截图地址5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='ios app crawler data table';

/*Table structure for table `data_search_log` */

DROP TABLE IF EXISTS `data_search_log`;

CREATE TABLE `data_search_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `search_keyword` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '搜索的关键词',
  `last_search_date` bigint(20) NOT NULL COMMENT '最后一次搜索的时间,为 java 里面的 System.currentTimeMillis()',
  `total_search_count` int(11) NOT NULL DEFAULT '0' COMMENT '本关键词总共被搜索的次数，按照这个排序',
  `search_result_count` bigint(10) DEFAULT '0' COMMENT '本关键词搜索出的结果条数',
  `search_in` tinyint(4) NOT NULL DEFAULT '0' COMMENT '搜索的范围 0---全部，1---data_xiu_hao_chi, 2---data_xiu_hao_pu',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `data_search_log_detail` */

DROP TABLE IF EXISTS `data_search_log_detail`;

CREATE TABLE `data_search_log_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `search_keyword` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '搜索的关键词',
  `search_date` bigint(20) NOT NULL COMMENT '搜索的时间, 为 java 里面的 System.currentTimeMillis()',
  `searcher_ip` varchar(15) COLLATE utf8_bin NOT NULL COMMENT '搜索者的ip',
  `searcher_area` varchar(15) COLLATE utf8_bin NOT NULL COMMENT '搜索者所在地名称',
  `searcher_host` varchar(1000) COLLATE utf8_bin NOT NULL COMMENT '搜索者的主机名称',
  `search_in` int(2) NOT NULL DEFAULT '-1' COMMENT '搜索的范围 1---data_xiu_hao_chi, 2---data_xiu_hao_pu',
  `search_web_site` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '搜索的网站的代号 0---全部，1---淘宝，2---天猫，3---京东，4---苏宁，5---国美，6---亚马逊，7---当当，8---阿里巴巴，9---1号店，10---新蛋',
  `order_by` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '本次搜索 orderby 的字段',
  `search_result_count` bigint(10) NOT NULL DEFAULT '0' COMMENT '本关键词搜索出的结果条数',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='记录用户所有搜索的详细信息，包括关键词，ip，时间，地区，在哪个范围搜索等信息';

/*Table structure for table `data_tao_bao_jie` */

DROP TABLE IF EXISTS `data_tao_bao_jie`;

CREATE TABLE `data_tao_bao_jie` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_name` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺名称',
  `shop_taobao_url` text CHARACTER SET utf8 COMMENT '店铺淘宝链接地址',
  `shop_rate_url` text CHARACTER SET utf8 NOT NULL COMMENT '店铺评级链接地址，方便用户直接点击进入查看用户评价信息',
  `company_name` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺所属公司名称',
  `tmall_shop_img_url` text CHARACTER SET utf8 COMMENT '天猫商铺标志图片链接地址',
  `pin_pai_zhi_xiao` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '是否品牌直销的文字',
  `seller_name` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '卖家姓名',
  `seller_tao_bao_wangwang_url` text CHARACTER SET utf8 COMMENT '卖家淘宝旺旺链接地址',
  `main_product` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '当前主营',
  `main_product_percentage` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '主营业务所占比例',
  `area` varchar(100) CHARACTER SET utf8 DEFAULT '' COMMENT '所在地区',
  `create_time` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '创店时间',
  `seller_credict` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '卖家信用',
  `buyer_credict` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '买家信用',
  `seller_credict_pic_url` text CHARACTER SET utf8 COMMENT '卖家信用图片链接地址',
  `buyer_credict_pic_url` text CHARACTER SET utf8 COMMENT '买家信用图片链接地址',
  `buyer_vip_pic_url` text CHARACTER SET utf8 COMMENT '买家会员资格图片链接地址',
  `buyer_vip_text` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '买家会员资格文字',
  `product_count` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '宝贝数量',
  `seller_xiao_fei_bao_zhang` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '卖家承诺消费者保障的文字',
  `tmall_zheng_pin` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '天猫正品保证的文字',
  `seller_7_tian_tui_huan` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '卖家承诺7天退换的文字',
  `seller_jia_yi_pei_3` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '卖家承诺假一赔3的文字',
  `tmall_fa_piao` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '天猫提供发票的文字',
  `seller_bao_zheng_jin` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '卖家当前保证金余额',
  `shop_has_entity_store` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺拥有实体门店的文字',
  `shop_can_use_credit_card` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺可以使用信用卡支付的文字',
  `shop_ren_zheng_zhifu_bao_ge_ren` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺经营资质个人信息认证的文字',
  `shop_ren_zheng_zhifu_bao_qi_ye` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺经营资质支付宝企业信息认证的文字',
  `shop_ren_zheng_qi_ye_zhi_zhao_url` text CHARACTER SET utf8 COMMENT '店铺经营资质企业执照链接地址',
  `product_desc_match_score` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '商品与描述相符分数',
  `product_desc_voter_number` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '商品描述投票人数',
  `product_desc_compare_average` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '商品描述得分与行业平均水平比较，''高于'': 高于行业平均值，''持平''：等于行业平均值，''低于''：低于行业平均值',
  `product_desc_match_score_percentage` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '商品描述得分与行业平均得分值的百分比',
  `seller_service_score` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '卖家服务态度得分',
  `seller_service_voter_number` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '卖家服务态度投票人数',
  `seller_service_compare_average` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '卖家服务态度得分与行业平均水平比较，''高于'': 高于行业平均值，''持平''：等于行业平均值，''低于''：低于行业平均值',
  `seller_service_score_percentage` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '卖家服务态度得分与行业平均得分值的百分比',
  `seller_ship_speed_score` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '卖家发货速度得分',
  `seller_ship_speed_voter_number` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '卖家发货速度投票人数',
  `seller_ship_speed_compare_average` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '卖家发货速度得分与行业平均水平比较，''高于'': 高于行业平均值，''持平''：等于行业平均值，''低于''：低于行业平均值',
  `seller_ship_speed_score_percentage` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '卖家发货速度得分与行业平均得分值的百分比',
  `avg_drawback_speed` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '平均退款速度',
  `drawback_percentage_30` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '近30天退款率',
  `drawback_count_30_total` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '近30天退款总次数',
  `drawback_count_30_quality` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '近30天因商品质量问题退款次数',
  `drawback_count_30_not_recive` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '近30天因未收到货退款次数',
  `drawback_count_30_no_reason` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '近30天因买家无理由退款次数',
  `complaint_percentage_30` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '近30天投诉率',
  `complaint_count_30_total` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '近30天投诉总数',
  `complaint_count_30_after_sale` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '近30天因售后问题投诉数',
  `complaint_count_30_wei_gui` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '近30天因行为违规投诉数',
  `punish_count_30` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '近30天处罚数',
  `punish_count_30_untrue` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '近30天因虚假交易处罚数',
  `punish_count_30_patent` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '近30天因侵犯知识产权处罚数',
  `punish_count_30_unallow` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '近30天因发布违禁信息处罚数',
  `seller_good_evaluation_percentage` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '卖家好评率',
  `shop_shou_cang_ren_qi` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺收藏人气',
  `shop_logo_pic_url` text CHARACTER SET utf8 COMMENT 'shop logo 图片地址 url',
  `shop_phone_number` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '店铺联系电话，主要天猫店铺',
  `download_task_page_url` text COLLATE utf8_bin COMMENT '本条记录解析的页面的 url 链接, 每个数据表都有这个字段，便于分析解析不成功的情况',
  `download_task_id` bigint(20) DEFAULT NULL COMMENT '本条记录对应的下载任务 id， 每个数据表都有这个字段， 便于分析解析不成功的情况',
  `download_task_level` int(11) DEFAULT NULL COMMENT '本条记录对应的下载任务 task_level， 每个数据表都有这个字段， 便于分析解析不成功的情况',
  `download_task_data_parse_time` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '本条记录的最新解析时间，方便查看记录什么时候解析的， 每个 data table 都必须有，直接解析为时间格式字符串，可以避免客户端再解析',
  `download_task_data_parse_time_number` bigint(100) DEFAULT NULL COMMENT '本条记录的最新解析时间，方便查看记录什么时候解析的，为数字格式， 每个 data table 都必须有，直接解析为，可以避免客户端再解析',
  `download_task_useless_content_page` tinyint(1) DEFAULT '0' COMMENT '本条记录该数据是否是有用的数据，比如数据解析比较完整，如果是则相对应的下载任务也设置为有用，方便以后下载，如果不是则相对应的下载任务也设置为无用，以后就不下载，每个数据表都有该字段',
  `shop_huo_dao_fu_kuan` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '是否支持货到付款的图片的文字',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=151837 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='taobao 店铺 crawler data table';

/*Table structure for table `data_xiu_hao_chi` */

DROP TABLE IF EXISTS `data_xiu_hao_chi`;

CREATE TABLE `data_xiu_hao_chi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_name` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '店铺名称',
  `shop_url` text CHARACTER SET utf8 NOT NULL COMMENT '店铺链接地址',
  `shop_level_img_url` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '店铺评级，皇冠数，图片地址',
  `biao_ti` varchar(1000) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '商品标题',
  `fen_lei` varchar(1000) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '商品分类路径',
  `jia_ge` varchar(200) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '商品价格',
  `jia_ge_num` double(10,3) NOT NULL DEFAULT '1000000.000' COMMENT '商品价格数值',
  `dan_jia` varchar(200) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '商品单价',
  `dan_jia_num` double(10,3) NOT NULL DEFAULT '1000000.000' COMMENT '商品单价数值',
  `cu_xiao_jia` varchar(200) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '促销价',
  `cu_xiao_jia_num` double(10,3) NOT NULL DEFAULT '1000000.000' COMMENT '促销价数值',
  `ping_fen` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '商品评分',
  `ping_fen_num` double(10,3) NOT NULL DEFAULT '-1.000' COMMENT '商品评分数值',
  `jiao_yi` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '''0''' COMMENT '交易的数量',
  `jiao_yi_num` bigint(10) NOT NULL DEFAULT '0' COMMENT '交易的数量数值',
  `jiao_yi_success` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '''0''' COMMENT '交易成功的数量',
  `jiao_yi_success_num` bigint(10) NOT NULL DEFAULT '0' COMMENT '交易成功的数量数值',
  `img_url` varchar(2000) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '图片链接url',
  `meta_search_keyword` varchar(2000) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '商品 meta keywords 关键词信息, example: <meta name="keywords"',
  `meta_desc` varchar(2000) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '商品 meta description 描述信息, example: <meta name="description"',
  `item_url` text COLLATE utf8_bin NOT NULL COMMENT '商品url链接地址',
  `shang_pin_lai_yuan` tinyint(1) NOT NULL DEFAULT '1' COMMENT '商品来源，1---天猫， 2---淘宝， 3---京东，4---dangdang',
  `hao_ping_lv` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '店铺好评率',
  `hao_ping_lv_num` double(10,3) NOT NULL DEFAULT '0.000' COMMENT '店铺好评率数值',
  `click_count` int(10) NOT NULL DEFAULT '0' COMMENT '商品被点击的次数，缺省按照这个次数降序排列',
  `wang_wang_url` varchar(2000) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '掌柜旺旺链接',
  `shang_cheng_pei_song` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否由商城进行配送发货',
  `pin_pai_zhi_xiao` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是品牌直销',
  `download_task_parent_page_url` text COLLATE utf8_bin NOT NULL COMMENT 'Must have for each data table, 本下载任务的父下载任务url链接地址，方便搜索并删除相应的data内容',
  `download_task_page_url` text COLLATE utf8_bin NOT NULL COMMENT 'Must have for each data table, 本下载任务的url链接地址',
  `download_task_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT 'Must have for each data table, download_task 表 id',
  `download_task_level` int(11) NOT NULL DEFAULT '1' COMMENT 'Must have for each data table, download_task 级别',
  `download_task_data_parse_time` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Must have for each data table, 下载的内容页面的解析时间字符串格式',
  `download_task_data_parse_time_number` bigint(100) NOT NULL DEFAULT '1' COMMENT 'Must have for each data table, 下载的内容页面的解析时间数字格式',
  `download_task_useless_content_page` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Must have for each data table, 下载的页面是否是无效的内容页面，1 --- 是无效内容页面，0 --- 有效的内容页面',
  PRIMARY KEY (`id`),
  FULLTEXT KEY `NewIndex1` (`biao_ti`,`meta_search_keyword`)
) ENGINE=MyISAM AUTO_INCREMENT=1480 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='保存互联网抓取的食品信息';

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
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `download_setting` */

DROP TABLE IF EXISTS `download_setting`;

CREATE TABLE `download_setting` (
  `total_common_thread_number` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `download_task` */

DROP TABLE IF EXISTS `download_task`;

CREATE TABLE `download_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_page_url` varchar(2000) COLLATE utf8_bin NOT NULL DEFAULT '""' COMMENT '当前页url的父页面url，方便得到当前页面的解析来源，可以用作分析解析配置，过滤掉不需要的产品信息。',
  `page_url` varchar(2000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '""' COMMENT 'The page''s url that will be download.',
  `site_id` int(11) NOT NULL DEFAULT '-1' COMMENT 'The download task beloned web site id.',
  `if_content_page` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'If this url related page is the content page or not. true---content page, false---category page.',
  `in_db_time` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '""' COMMENT 'Record the date that this url saved to db table.',
  `apply_time` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '""' COMMENT 'Record the date time that this url set to be task, if this is null then this is a task waiting for run.',
  `if_site_top_url` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'true---this is the top url of this site, false---this is not the top url of this site.',
  `if_test` tinyint(1) NOT NULL DEFAULT '1',
  `duration_info` text COLLATE utf8_bin NOT NULL COMMENT 'Record duration time in each step in download and parse a task for analyse later',
  `run_time` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '""' COMMENT 'Task run time',
  `thread_table_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT 'The id of last thread that run this task',
  `task_run_delta_time` bigint(20) NOT NULL DEFAULT '-1' COMMENT 'Task run delta time',
  `error_message` text COLLATE utf8_bin COMMENT 'Task run error message',
  `task_level` int(11) NOT NULL DEFAULT '0' COMMENT 'task level, default 0, level range(0,1,2,3,4,5), applying tasks will be ordered by level, big level will be run first ',
  `useless_content_page` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'whether this url page is useful or useless, if true then will not download this page and parse even its applytime is ""',
  `parsed_out_url_count` bigint(20) NOT NULL DEFAULT '-1' COMMENT 'The url link count that parsed out from this download task url',
  `access_denied_date` varbinary(20) DEFAULT '""' COMMENT 'Record when this task is access denied by the website',
  `access_denied_thread_sleep_time` int(10) DEFAULT '-1' COMMENT 'Record the thread sleep time if this task has been denied by website',
  `reset_apply_time_reason` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT 'Record why reset this task apply time, for analysis',
  `reset_apply_time_time` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'When reset apply task time occured',
  PRIMARY KEY (`id`),
  KEY `site_id` (`site_id`,`useless_content_page`,`apply_time`,`if_content_page`),
  KEY `task_level_id` (`task_level`)
) ENGINE=MyISAM AUTO_INCREMENT=23016 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

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
) ENGINE=MyISAM AUTO_INCREMENT=120 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `download_thread_apply_task_status` */

DROP TABLE IF EXISTS `download_thread_apply_task_status`;

CREATE TABLE `download_thread_apply_task_status` (
  `applying_task` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0: no thread apply download task, 1: has 1 thread apply download task',
  `apply_time` varchar(20) DEFAULT NULL COMMENT 'last thread apply task time, for check how long since last apply task, use this to reset applying_task to 0 if apply thread forget reset it'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='For synchronize multiple thread apply download task. ';

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
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `parse_category` */

DROP TABLE IF EXISTS `parse_category`;

CREATE TABLE `parse_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_name` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cat_desc` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `parse_item` */

DROP TABLE IF EXISTS `parse_item`;

CREATE TABLE `parse_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'parse_item id',
  `parse_id` int(11) DEFAULT NULL COMMENT '所属于的 parse table id',
  `save_to_table` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '解析值保存到的表名称',
  `save_to_column` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '解析值保存到的字段名称',
  `column_desc` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '该字段含义的描述',
  `charactor` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '解析本字段时候用到的特征字符串',
  `not_charactor` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '解析本字段时候用到的非特征字符串',
  `default_val` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '本解析字段如果解析出来的值为空时候使用的缺省值',
  `data_type` varchar(20) DEFAULT NULL COMMENT '解析数据类型, String, Date, Number, Boolean, DateTime\n5种类型',
  `number_multiply_by` varchar(20) DEFAULT NULL COMMENT '如果解析出来的值是数字，如果该字段的值不为空，则用该字段的值乘以解析出来的值（例子：方便记录产品单价，值可以是小数表示除法）',
  `date_format` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '如果本字段是时间值，则代表时间格式',
  `is_check_repeat_column` tinyint(1) DEFAULT NULL COMMENT '本字段的值是否用来查重',
  `is_url` tinyint(1) DEFAULT NULL COMMENT '本字段的值是否是url链接',
  `src_reg_exp` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '对解析出来的字段值运行的源正则表达式',
  `dest_reg_exp` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '对解析出来的字段值运行的目标正则表达式，来替换源正则表达式里面的值',
  `exec_javascript` varchar(2000) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '对解析出来的值执行 javascript',
  `page_url_as_value` tinyint(4) DEFAULT '0' COMMENT 'true---用该页面的url作为该字段的值，\n\nfalse---不用该页面的url作为该字段的值，\n\ndefault---false。',
  `reg_exp_item_relation` tinyint(4) DEFAULT '0' COMMENT 'true---check means and（replace multiple times according to number of src reg exp）, \n\nfalse---uncheck means or (if one of src reg exp match then return)',
  `by_ele_type` varchar(100) DEFAULT NULL COMMENT 'Search this element by what? tag, xpath, id, name, css selector etc， 通常使用id或者name，然后再按照字符串解析想要的值，这样webdriver的运行速度比较快',
  `by_ele_val` varchar(1000) DEFAULT NULL COMMENT 'id or name or xpath ... value，可以解析多个id，name，xpath等，多个值之间用分号分开',
  `by_tag_attribute` varchar(200) DEFAULT NULL COMMENT 'element attribute,多个attribute可以用分号分割',
  `by_tag_attribute_value_plus` tinyint(1) DEFAULT '0' COMMENT '是否将多个属性的值相加后返回，为checkbox类型，选中则将所有的属性的值拼成一个字符串返回，不选中则返回第一个非空字符串的属性值',
  `parse_value_reg_exp` varchar(2000) DEFAULT NULL COMMENT '对上面解析出来的值执行正则表达式得到想要的值,该字段的格式为 regexp1;regexp2;regexp3...',
  `parse_value_string` text COMMENT '如果该值很难用正则表达式解析，就用这个字段进行字符串解析，该字段内容格式startStr1,endStr1;startStr1,endStr1;startStr1,endStr1;...',
  `is_direct_get_text` tinyint(1) DEFAULT '1' COMMENT 'true: 直接调用WebElement.getText(), false:  调用webEle.getAttribute("innerHTML") 得到文本字段后再用其他配置进行解析',
  `use_this_setting_url_charactor` varchar(1000) DEFAULT NULL COMMENT '只有解析的内容页面的url匹配这样的url特征，才用这个解析配置进行数据解析, 如果该值为空，则所有的内容页面的url都用该配置进行解析',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_9` (`parse_id`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`parse_id`) REFERENCES `parse` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=426 DEFAULT CHARSET=utf8;

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
  `show_img_in_browser` tinyint(1) NOT NULL DEFAULT '0' COMMENT '在启动浏览器的时候是否显示图片，有的网站如京东，如果不显示图片则解析不出图片的地址，可是淘宝等网站不需要显示图片就可以解析，不显示图片可以提高速度',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_10` (`parse_id`),
  KEY `FK_Reference_5` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

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
  `click_ele_xpath_before_parse_url` text COLLATE utf8_unicode_ci COMMENT 'Click These Web Elements By XPath Before Parse URL (foe parse more element in taobao, tmall, jd etc). All values here should be xpath of elements which need to be clicked before parse url link in this page. Format is urlCharactor1!@#xpath1,xpath2;urlChara',
  `by_ele_type` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'search web element by condition',
  `by_ele_val` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'search by element value',
  `by_tag_attribute` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'if by_ele_type is Tag_Name, then by_ele_val is real tag name and by_tag_attribute is the attribute of that tag name',
  `run_regexp_on_url` varchar(2000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '在解析出来的url上运行的正则表达式，使用正则表达式运行之后的url作为页面的url',
  `run_string_find_on_url` varchar(2000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '如果正则表达式不好写，则用该字段进行解析，该字段的格式是   startStr1!@#endStr1;startStr2!@#endStr2;..., 将这些字符串截取之后的字符串再拼成一个字符串作为url， 如果startStr 是字符串开始则用空字符串代表，如果endStr是字符串结尾则用空字符串代表',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_7` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
