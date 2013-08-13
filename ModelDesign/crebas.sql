/*==============================================================*/
/* DBMS name:      MySQL 4.0                                    */
/* Created on:     7/1/2012 8:19:55 PM                          */
/*==============================================================*/

/* delete menu_link column in table menu_tbl */

drop table if exists account_action_tbl;

drop table if exists account_role_tbl;

drop table if exists account_tbl;

drop table if exists db_setting;

drop table if exists download_setting;

drop table if exists download_task;

drop table if exists menu_tbl;

drop table if exists page_parse_exception;

drop table if exists parse_category_tbl;

drop table if exists parse_item_tbl;

drop table if exists parse_tbl;

drop table if exists role_menu_tbl;

drop table if exists role_tbl;

drop table if exists selenium_setting;

drop table if exists site_auth_tbl;

drop table if exists site_category_tbl;

drop table if exists site_tbl;

drop table if exists site_url_parse_tbl;

drop table if exists task_download_exception;

/*==============================================================*/
/* Table: account_action_tbl                                    */
/*==============================================================*/
create table account_action_tbl
(
   id                             int                            not null,
   menu_id                        int,
   action                         varchar(1000),
   action_status                  int,
   action_status_reason           varchar(1000),
   action_date                    date,
   primary key (id)
)
comment = "Record actions of each user of this system."
ENGINE = InnoDB;

/*==============================================================*/
/* Table: account_role_tbl                                      */
/*==============================================================*/
create table account_role_tbl
(
   account_id                     int,
   role_id                        int
)
comment = "Relation between AccountTbl and RoleTbl"
ENGINE = InnoDB;


/*==============================================================*/
/* Table: account_tbl                                           */
/*==============================================================*/
create table account_tbl
(
   id                             int                            not null,
   user_name                      varchar(100),
   pass_word                      varchar(100),
   qq                             varchar(100),
   email                          varchar(100),
   mobile                         varchar(20),
   primary key (id)
)
ENGINE = InnoDB;

/*==============================================================*/
/* Table: db_setting                                            */
/*==============================================================*/
create table db_setting
(
   db_type                        varchar(10),
   db_ip                          varchar(20),
   db_username                    varchar(30),
   db_password                    varchar(30),
   is_test_db                     int,
   is_operate_db                  int
)
ENGINE = InnoDB;

/*==============================================================*/
/* Table: download_setting                                      */
/*==============================================================*/
create table download_setting
(
   apply_task_thread_number       int,
   exec_task_thread_number        int
)
ENGINE = InnoDB;

/*==============================================================*/
/* Table: download_task                                         */
/*==============================================================*/
create table download_task
(
   id                             int                            not null,
   parent_page_url                varchar(2000),
   site_id                        int,
   xpath                          varchar(2000),
   curr_level                     int,
   total_level                    int,
   in_db_date                     date,
   last_be_task_time              date,
   primary key (id)
)
ENGINE = InnoDB;


/*==============================================================*/
/* Table: menu_tbl                                              */
/*==============================================================*/
create table menu_tbl
(
   id                             int                            not null,
   menu_name                      varchar(100),
   menu_desc                      varchar(1000),
   primary key (id)
)
ENGINE = InnoDB;

/*==============================================================*/
/* Table: page_parse_exception                                  */
/*==============================================================*/
create table page_parse_exception
(
   id                             int                            not null,
   download_task_id               int,
   parse_item_id                  int,
   error_message                  varchar(2000),
   primary key (id)
)
ENGINE = InnoDB;


/*==============================================================*/
/* Table: parse_category_tbl                                    */
/*==============================================================*/
create table parse_category_tbl
(
   id                             int                            not null,
   cat_name                       varchar(200),
   cat_desc                       varchar(1000),
   primary key (id)
)
ENGINE = InnoDB;

/*==============================================================*/
/* Table: parse_item_tbl                                        */
/*==============================================================*/
create table parse_item_tbl
(
   id                             int                            not null,
   parse_id                       int,
   save_to_column                 varchar(200),
   column_desc                    varchar(200),
   xpath                          varchar(1000),
   charactor                      varchar(100),
   not_charactor                  varchar(100),
   default_val                    varchar(200),
   is_number                      int,
   number_format                  varchar(20),
   is_date                        int,
   date_format                    varchar(20),
   is_string                      int,
   is_check_repeat_column         int,
   is_url                         int,
   src_reg_exp                    varchar(200),
   dest_reg_exp                   varchar(200),
   exec_javascript                varchar(2000),
   primary key (id)
)
ENGINE = InnoDB;


/*==============================================================*/
/* Table: parse_tbl                                             */
/*==============================================================*/
create table parse_tbl
(
   id                             int                            not null,
   category_id                    int,
   parse_name                     varchar(100),
   parse_desc                     varchar(1000),
   save_to_table                  varchar(200),
   primary key (id)
)
ENGINE = InnoDB;


/*==============================================================*/
/* Table: role_menu_tbl                                         */
/*==============================================================*/
create table role_menu_tbl
(
   role_id                        int,
   menu_id                        int
)
comment = "Relation between RoleTbl and MenuTbl"
ENGINE = InnoDB;

/*==============================================================*/
/* Table: role_tbl                                              */
/*==============================================================*/
create table role_tbl
(
   id                             int                            not null,
   role_name                      varchar(100),
   role_desc                      varchar(1000),
   primary key (id)
)
ENGINE = InnoDB;

/*==============================================================*/
/* Table: selenium_setting                                      */
/*==============================================================*/
create table selenium_setting
(
   browser_type                   varchar(20)
)
ENGINE = InnoDB;

/*==============================================================*/
/* Table: site_auth_tbl                                         */
/*==============================================================*/
create table site_auth_tbl
(
   id                             int                            not null,
   site_id                        int,
   login_page_url                 varchar(1000),
   login_user                     varchar(100),
   login_passwd                   varchar(100),
   login_user_xpath               varchar(1000),
   login_passwd_xpath             varchar(1000),
   login_submit_xpath             varchar(1000),
   primary key (id)
)
comment = "To save login account record info for browse blocked site page."
ENGINE = InnoDB;


/*==============================================================*/
/* Table: site_category_tbl                                     */
/*==============================================================*/
create table site_category_tbl
(
   id                             int                            not null,
   cat_name                       varchar(500),
   cat_desc                       varchar(1000),
   primary key (id)
)
ENGINE = InnoDB;

/*==============================================================*/
/* Table: site_tbl                                              */
/*==============================================================*/
create table site_tbl
(
   id                             int                            not null,
   category_id                    int,
   site_name                      varchar(200),
   site_desc                      varchar(1000),
   parse_id                       int,
   total_page_level               int,
   top_url                        varchar(2000),
   start_download_time            timestamp,
   download_duration_seconds      int,
   over_write_attributes          varchar(1000),
   download_task_reget_duration_d int,
   need_login                     int,
   login_check_return_url         varchar(1000),
   login_check_return_data        varchar(100),
   login_check_return_data_xpath  varchar(200),
   primary key (id)
)
ENGINE = InnoDB;


/*==============================================================*/
/* Table: site_url_parse_tbl                                    */
/*==============================================================*/
create table site_url_parse_tbl
(
   id                             int                            not null,
   site_id                        int,
   url_level                      int,
   xpath                          varchar(1000),
   url_charactor                  varchar(1000),
   not_url_charactor              varchar(1000),
   exec_javascript                varchar(2000),
   url_page_desc                  varchar(2000),
   primary key (id)
)
ENGINE = InnoDB;


/*==============================================================*/
/* Table: task_download_exception                               */
/*==============================================================*/
create table task_download_exception
(
   id                             int                            not null,
   download_task_id               int,
   error_message                  varchar(2000),
   primary key (id)
)
ENGINE = InnoDB;



alter table account_action_tbl add constraint FK_Reference_15 foreign key (menu_id)
      references menu_tbl (id) on delete restrict on update restrict;

alter table account_role_tbl add constraint FK_Reference_1 foreign key (role_id)
      references role_tbl (id) on delete restrict on update restrict;

alter table account_role_tbl add constraint FK_Reference_2 foreign key (account_id)
      references account_tbl (id) on delete restrict on update restrict;

alter table download_task add constraint FK_Reference_11 foreign key (site_id)
      references site_tbl (id) on delete restrict on update restrict;

alter table page_parse_exception add constraint FK_Reference_13 foreign key (download_task_id)
      references download_task (id) on delete restrict on update restrict;

alter table page_parse_exception add constraint FK_Reference_14 foreign key (parse_item_id)
      references parse_item_tbl (id) on delete restrict on update restrict;

alter table parse_item_tbl add constraint FK_Reference_9 foreign key (parse_id)
      references parse_tbl (id) on delete restrict on update restrict;

alter table parse_tbl add constraint FK_Reference_8 foreign key (category_id)
      references parse_category_tbl (id) on delete restrict on update restrict;

alter table role_menu_tbl add constraint FK_Reference_3 foreign key (role_id)
      references role_tbl (id) on delete restrict on update restrict;

alter table role_menu_tbl add constraint FK_Reference_4 foreign key (menu_id)
      references menu_tbl (id) on delete restrict on update restrict;

alter table site_auth_tbl add constraint FK_Reference_6 foreign key (site_id)
      references site_tbl (id) on delete restrict on update restrict;

alter table site_tbl add constraint FK_Reference_10 foreign key (parse_id)
      references parse_tbl (id) on delete restrict on update restrict;

alter table site_tbl add constraint FK_Reference_5 foreign key (category_id)
      references site_category_tbl (id) on delete restrict on update restrict;

alter table site_url_parse_tbl add constraint FK_Reference_7 foreign key (site_id)
      references site_tbl (id) on delete restrict on update restrict;

alter table task_download_exception add constraint FK_Reference_12 foreign key (download_task_id)
      references download_task (id) on delete restrict on update restrict;

