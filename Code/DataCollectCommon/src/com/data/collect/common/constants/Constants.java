package com.data.collect.common.constants;

public class Constants {
	
	//servlet path
	public static final String DATA_COLLECT_SERVLET_NAME = "/action.do";
	public static final String PATH_APP_JS = "/js/app/";
	public static final String PATH_EXT_JS = "/js/ext-4.1.1a/";

	
	//javascript action names
	public static final String JAVA_SCRIPT_ACTION_CLICK = "JAVA_SCRIPT_ACTION_CLICK";
	
	
	//default connection timeout
	public static final int TIME_OUT_EXT_JS_CLIENT_OPERATION_SECOND_600 = 600;
	public static final int TIME_OUT_ACTIVEMQ_SEND_SECOND_300 = 300;
	public static final int TIME_OUT_ACTIVEMQ_CLOSE_SECOND_300 = 300;
	public static final int TIME_OUT_WEBUI_SESSION_MILLI_SECOND_60000 = 60000;

	
	//page title
	public static final String PAGE_TITLE_PREFIX = "Data Collect ";	
	public static final String PAGE_TITLE_LOGIN = PAGE_TITLE_PREFIX + "Login";	
	public static final String PAGE_TITLE_INDEX = PAGE_TITLE_PREFIX + "Index";	
	
	//page path
	public static final String PAGE_LOGIN = "/login.jsp";
	public static final String PAGE_FOLDER = "/pages";
	public static final String PAGE_WEB_SITE = PAGE_FOLDER + "/webSite.jsp";
	public static final String PAGE_PARSE_TEMPLATE = PAGE_FOLDER + "/parseTemplate.jsp";
	public static final String PAGE_DATA_TABLE = PAGE_FOLDER + "/dataTable.jsp";

	
	//action and parameter
	public static final String QUESTION_MARK = "?";
	public static final String AND_MARK = "&";
	public static final String EQUAL_MARK = "=";
	public static final String NOT_EQUAL_MARK = "!=";

	
	public static final String ACTION = "ACTION";
	public static final String ACTION_SUB = "ACTION_SUB";
	public static final String ACTION_ADD = "ACTION_ADD";
	public static final String ACTION_EDIT = "ACTION_EDIT";
	public static final String ACTION_LOGIN = "ACTION_LOGIN";
	public static final String ACTION_LOGOUT = "ACTION_LOGOUT";
	
	public static final String LIST = "_LIST";	
	public static final String LIST_ALL = "_LIST_ALL";
	public static final String RESET = "_RESET";
	public static final String RESET_ALL = "_RESET_ALL";
	public static final String SET_LEVEL = "_SET_LEVEL";
	public static final String SET_USELESS = "_SET_USELESS";
	public static final String SAVE = "_SAVE";
	public static final String DELETE = "_DELETE";
	public static final String COPY = "_COPY";
	public static final String DELETE_ALL = "_DELETE_ALL";
	public static final String STOP = "_STOP";
	public static final String DOWNLOAD_AND_PARSE = "_DOWNLOAD_AND_PARSE";
	
	/**************************************ACTION_WEB_SITE******************************************/
	public static final String ACTION_WEB_SITE = "ACTION_WEB_SITE";
	
	public static final String ACTION_WEB_SITE_CATEGORY = "ACTION_WEB_SITE_CATEGORY";
	
	public static final String ACTION_WEB_SITE_PAGE_LINK_PARSE = "ACTION_WEB_SITE_PAGE_LINK_PARSE";
	
	public static final String ACTION_WEB_SITE_CONTENT_PAGE_CHECK = "ACTION_WEB_SITE_CONTENT_PAGE_CHECK";

	public static final String ACTION_WEB_SITE_LOGIN_ACCOUNT = "ACTION_WEB_SITE_LOGIN_ACCOUNT";
	
	/******************************************ACTION_WEB_SITE_TEST**********************************************/
	public static final String ACTION_WEB_SITE_TEST = "ACTION_WEB_SITE_TEST";	
	
	public static final String ACTION_WEB_SITE_TEST_THREAD = "ACTION_WEB_SITE_TEST_THREAD";	
	
	public static final String ACTION_WEB_SITE_TEST_MQMESSAGE = "ACTION_WEB_SITE_TEST_MQMESSAGE";
	
	public static final String ACTION_WEB_SITE_TEST_TASK = "ACTION_WEB_SITE_TEST_TASK";
		
	/**************************************ACTION_PARSE_TEMPLATE_CATEGORY******************************************/
	public static final String ACTION_PARSE_TEMPLATE_CATEGORY = "ACTION_PARSE_TEMPLATE_CATEGORY";
	
	/**************************************ACTION_PARSE_TEMPLATE******************************************/
	public static final String ACTION_PARSE_TEMPLATE = "ACTION_PARSE_TEMPLATE";
	
	/**************************************ACTION_PARSE_TEMPLATE_ITEM******************************************/
	public static final String ACTION_PARSE_TEMPLATE_ITEM = "ACTION_PARSE_TEMPLATE_ITEM";
	
	/**************************************ACTION_PARSE_TEMPLATE_ITEM******************************************/
	public static final String ACTION_PARSE_TEMPLATE_ITEM_ACTION = "ACTION_PARSE_TEMPLATE_ITEM_ACTION";
	
	/**************************************ACTION_DB_TABLE******************************************/
	public static final String ACTION_DATA_TABLE = "ACTION_DATA_TABLE";

	public static final String ACTION_DATA_TABLE_DATA = "ACTION_DATA_TABLE_DATA";
		
	public static final String ACTION_DATA_TABLE_COLUMN = "ACTION_DATA_TABLE_COLUMN";	
	
	/**************************************Web Driver Related******************************************/
	public static final String ACTION_WEB_DRIVER_SEARCH_BY_TYPE_LIST = "ACTION_WEB_DRIVER_SEARCH_BY_TYPE_LIST";
	
	/**************************************ACTION_ROLE******************************************/
	public static final String ACTION_ROLE = "ACTION_ROLE";
	
	
	/**************************************ACTION_MENU******************************************/
	public static final String ACTION_MENU = "ACTION_MENU";
	
	/**************************************ACTION_USER******************************************/
	public static final String ACTION_USER = "ACTION_USER";
	
	/**************************************ACTION_SETTING******************************************/
	public static final String ACTION_SETTING = "ACTION_SETTING";
	
	/**************************************ACTION_ANALYTIC******************************************/
	public static final String ACTION_ANALYTIC = "ACTION_ANALYTIC";
		
	//data list
	/**************************************DATA_WEB_SITE******************************************/
	public static final String DATA_THREAD_TYPE = "DATA_THREAD_TYPE";
	public static final String DATA_WEB_SITE_ID = "DATA_WEB_SITE_ID";
	public static final String DATA_TABLE_ID = "DATA_TABLE_ID";
	
	public static final String DATA_IDS = "DATA_IDS";
	public static final String DATA_DELETE_IDS = "DATA_DELETE_IDS";
	public static final String DATA_THREAD_IDS = "DATA_THREAD_IDS";
	public static final String DATA_DB_TABLE_NAME = "DATA_DB_TABLE_NAME";
	public static final String DATA_DB_SEARCH_COLUMN = "DATA_DB_SEARCH_COLUMN";
	public static final String DATA_DB_SEARCH_VALUE = "DATA_DB_SEARCH_VALUE";
	//public static final String DATA_TABLE_SUMMARY_COLUMN_NAME = "DATA_TABLE_SUMMARY_COLUMN_NAME";


	
	/**************************************DATA_MENU******************************************/
	
	
	/**************************************DATA_PARSE_TEMPLATE******************************************/
	
	
	/**************************************DATA_PARSE_TEMPLATE******************************************/
	
	
	/**************************************DATA_PARSE_TEMPLATE_ITEM******************************************/
	
	
	/************************************Data Type List**********************************************/
	public static final String dataTypeNumberList[] = {"int","long","double"};
	public static final String dataTypeDateList[] = {"yyyy-MM-dd hh:mm:ss"};
	
	//jdbc my sql
	public static final String SERVER_TYPE_MY_SQL = "SERVER_TYPE_MY_SQL";
	
	public static final String JDBC_MY_SQL_CLASS = "org.gjt.mm.mysql.Driver";
	
	
	//session
	public static final String SESSION_LOGIN_USER_DTO = "SESSION_LOGIN_USER_DTO";
	public static final String SESSION_LOGIN_USER_ROLE_DTO = "SESSION_LOGIN_USER_ROLE_DTO";
	
	
	//error
	public static final String ERROR_CODE = "ERROR_CODE";	
	public static final String ERROR_MESSAGE = "ERROR_MESSAGE";	
	public static final int ERROR_CODE_NO_ERROR = -1;	
	public static final int ERROR_CODE_URL_CAN_NOT_CONNECT = 1002;
	public static final int ERROR_CODE_SESSION_TIME_OUT = 1001;
	
	
	//date format
	public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";
			
	//start date
	public static final String DATE_START_TIME = "DATE_START_TIME";
	
	//end date
	public static final String DATE_END_TIME = "DATE_END_TIME";
	
	public static final String WEB_ARRTIBUTE_INNER_HTML = "innerHTML";
	public static final String WEB_ARRTIBUTE_INNER_TEXT = "innerText";
	public static final String WEB_ARRTIBUTE_OUTER_HTML = "outerHTML";
	public static final String WEB_ARRTIBUTE_OUTER_TEXT = "outerText";

	
	
	//all value
	public static final String VALUE_ALL = "VALUE_ALL";
	public static final String ID = "id";
	public static final String ID_NAME = "name";
	public static final String ID_DESC = "desc";
	public static final String ID_CATEGORY_ID = "categoryId";	
	public static final String ID_CATEGORY_NAME = "categoryName";
	public static final String ID_WEB_SITE_CATEGORY_ID = "ID_WEB_SITE_CATEGORY_ID";	
	
	
	//page content char set
	public static final String PAGE_CHAR_SET_UTF8 = "UTF-8";
	public static final String PAGE_CHAR_SET_GB2312 = "GB2312";
	public static final String PAGE_CHAR_SET_GBK = "GBK";
	public static final String PAGE_CHAR_SET_ISO_8859_1 = "ISO-8859-1";
	public static final String PAGE_CHAR_SET = PAGE_CHAR_SET_UTF8;
	
	
	//error message
	public static final String ERROR_MESSAGE_USER_NOT_LOGGEDIN = "User not loggedin.";
	public static final String ERROR_MESSAGE_USER_NAME_CAN_NOT_EMPTY = "User name can not be empty.";
	public static final String ERROR_MESSAGE_PASSWORD_CAN_NOT_EMPTY = "Password can not be empty.";
	public static final String ERROR_MESSAGE_USER_NAME_PASSWORD_NOT_CORRECT = "User name or password is not correct.";
	public static final String ERROR_WEB_ELEMENT_NOT_FOUND_BY_XPATH = "Web element not find by xpath.";

	public static final String FIELD_NAME_SERIAL_VERSION_UID = "serialVersionUID";
	
	public static final String CLASS_TYPE_STRING = "class java.lang.String";
	public static final String CLASS_TYPE_OBJECT = "CLASS_TYPE_OBJECT";
	public static final String CLASS_TYPE_INT = "int";
	public static final String CLASS_TYPE_LONG = "long";
	public static final String CLASS_TYPE_DOUBLE = "double";
	public static final String CLASS_TYPE_BOOLEAN = "boolean";
	public static final String CLASS_TYPE_LIST_PREFIX = "java.util.List";
	
	public static final String DATA_TYPE = "DATA_TYPE";
	public static final String DATA_TYPE_STRING = "DATA_TYPE_STRING";
	public static final String DATA_TYPE_NUMBER = "DATA_TYPE_NUMBER";
	public static final String DATA_TYPE_BOOLEAN = "DATA_TYPE_BOOLEAN";
	public static final String DATA_TYPE_DATE = "DATA_TYPE_DATE";
	public static final String DATA_TYPE_DATE_TIME = "DATA_TYPE_DATE_TIME";
	
	
	public static final String CLASS_METHOD_PREFIX_SET = "set";
	public static final String CLASS_METHOD_PREFIX_GET = "get";
	public static final String CLASS_METHOD_PREFIX_IS = "is";
	
	public static final String SEPERATOR_COMPLEX = "!@#";
	public static final String SEPERATOR_COMPLEX1 = "#@!";	
	public static final String SEPERATOR_COMMA = ",";
	public static final String SEPERATOR_SEMICOLON = ";";
	public static final String SEPERATOR_COLON = ":";
	public static final String SEPERATOR_LEFT_BRACE = "{";
	public static final String SEPERATOR_RIGHT_BRACE = "}";
	public static final String SEPERATOR_LEFT_BRACKET = "[";
	public static final String SEPERATOR_RIGHT_BRACKET = "]";
	public static final String SEPERATOR_SINGLE_QUOTES = "'";
	public static final String SEPERATOR_DOUBLE_QUOTES = "\"";
	public static final String SEPERATOR_SLASH = "/";
	public static final String SEPERATOR_BACK_SLASH = "\\";
	public static final String SEPERATOR_NAVIGATION_TITLE = " > ";
	public static final String SEPERATOR_SPACE = " ";
	public static final String SEPERATOR_UNDER_LINE = "_";
	public static final String RESERVE_ALL_URL_PARAMETERS = "RESERVE_ALL_URL_PARAMETERS";
	
	
	public static final String JSON_SUCCESS = "success";	
	public static final String JSON_EXIST = "exist";	
	public static final String JSON_CODE = "code";	
	public static final String JSON_MESSAGE = "message";
	public static final String JSON_USER_ACCOUNT = "JSON_USER_ACCOUNT";
	public static final String JSON_USER_ROLE = "JSON_USER_ROLE";
	public static final String JSON_MENU_LIST = "JSON_MENU_LIST";
	public static final String JSON_TOTAL_RESULT_COUNT = "JSON_TOTAL_RESULT_COUNT";
	
	public static final String JSON_ROOT_SITE_CATEGORY_LIST = "JSON_ROOT_SITE_CATEGORY_LIST";
	
	public static final String JSON_ROOT_SITE_LIST = "JSON_ROOT_SITE_LIST";	
	public static final String JSON_ROOT_SITE_PAGE_LINK_PARSE_LIST = "JSON_ROOT_SITE_PAGE_LINK_PARSE_LIST";
	public static final String JSON_ROOT_SITE_CONTENT_PAGE_CHECK_LIST = "JSON_ROOT_SITE_CONTENT_PAGE_CHECK_LIST";	
	public static final String JSON_ROOT_SITE_LOGIN_ACCOUNT_LIST = "JSON_ROOT_SITE_LOGIN_ACCOUNT_LIST";
	
	public static final String JSON_ROOT_PARSE_TEMPLATE_CATEGORY_LIST = "JSON_ROOT_PARSE_TEMPLATE_CATEGORY_LIST";
	public static final String JSON_ROOT_PARSE_TEMPLATE_LIST = "JSON_ROOT_PARSE_TEMPLATE_LIST";	
	public static final String JSON_ROOT_PARSE_TEMPLATE_ITEM_LIST = "JSON_ROOT_PARSE_TEMPLATE_ITEM_LIST";
	public static final String JSON_ROOT_PARSE_TEMPLATE_ITEM_ACTION_LIST = "JSON_ROOT_PARSE_TEMPLATE_ITEM_ACTION_LIST";
	
	public static final String JSON_ROOT_DATA_TABLE_LIST = "JSON_ROOT_DATA_TABLE_LIST";
	public static final String JSON_ROOT_DATA_TABLE_DATA_LIST = "JSON_ROOT_DATA_TABLE_DATA_LIST";
	public static final String JSON_ROOT_DATA_TABLE_COLUMN_LIST = "JSON_ROOT_DATA_TABLE_COLUMN_LIST";
	public static final String JSON_ROOT_DATA_TYPE_LIST = "JSON_ROOT_DATA_TYPE_LIST";

	
	
	public static final String JSON_ROOT_THREAD_LIST = "JSON_ROOT_THREAD_LIST";
	public static final String JSON_ROOT_TASK_LIST = "JSON_ROOT_TASK_LIST";
	public static final String JSON_ROOT_MQ_MESSAGE_LIST = "JSON_ROOT_MQ_MESSAGE_LIST";

	public static final String JSON_ROOT_ACTIVE_MQ_MESSAGE = "JSON_ROOT_ACTIVE_MQ_MESSAGE";
	
	public static final String JSON_ROOT_WEB_DRIVER_SEARCH_BY_TYPE_LIST = "JSON_ROOT_WEB_DRIVER_SEARCH_BY_TYPE_LIST";	
	
	public static final String SERIAL_VERSION_UID = "serialVersionUID";
	
	/************************************Downoad Related Constants**********************************************/
	/*
	 *The work this thread implement. 
	 * */
	//1---all type thread
	public static final int DOWNLOAD_THREAD_TYPE_ALL = 1;
	//2---Get web page in top url page	 
	public static final int DOWNLOAD_THREAD_TYPE_GET_WEB_PAGE = 2;	
	//3---Parse result in xml format
	public static final int DOWNLOAD_THREAD_TYPE_PARSE_PAGE_DATA = 3;	
	//4---Tester thread
	public static final int DOWNLOAD_THREAD_TYPE_TESTER = 4;
	//5---Worker thread
	public static final int DOWNLOAD_THREAD_TYPE_WORKER = 5;
	
	public static final String DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_PAGE_URL = "download_task_page_url";
	public static final String DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_ID = "download_task_id";
	public static final String DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_LEVEL = "download_task_level";
	public static final String DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_DATA_PARSE_TIME = "download_task_data_parse_time";
	public static final String DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_DATA_PARSE_TIME_NUMBER = "download_task_data_parse_time_number";	
	public static final String DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_USELESS_CONTENT_PAGE = "download_task_useless_content_page";	

	public static final String RESET_APPLY_TIME = "RESET_APPLY_TIME";	
	public static final String DOWNLOAD_TASK_LEVEL = "DOWNLOAD_TASK_LEVEL";	
	public static final String DOWNLOAD_TASK_USELESS = "DOWNLOAD_TASK_USELESS";	

	
	public static final String DOWNLOAD_THREAD_ACTION_START = "DOWNLOAD_THREAD_ACTION_START";		
	public static final String DOWNLOAD_THREAD_ACTION_STOP = "DOWNLOAD_THREAD_ACTION_STOP";	
	public static final String DOWNLOAD_THREAD_ACTION_CREATE = "DOWNLOAD_THREAD_ACTION_CREATE";	
	public static final String DOWNLOAD_THREAD_ACTION_DELETE = "DOWNLOAD_THREAD_ACTION_DELETE";
	
	public static final long DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND = 1000;
	
	public static final String WEB_DRIVER_SEARCH_BY_TYPE_ID = "Id";
	public static final String WEB_DRIVER_SEARCH_BY_TYPE_NAME = "Name";
	public static final String WEB_DRIVER_SEARCH_BY_TYPE_CLASS_NAME = "Class_Name";
	public static final String WEB_DRIVER_SEARCH_BY_TYPE_TAG_NAME = "Tag_Name";
	public static final String WEB_DRIVER_SEARCH_BY_TYPE_LINK_TEXT = "Link_Text";
	public static final String WEB_DRIVER_SEARCH_BY_TYPE_PARTIAL_LINK_TEXT = "Partial_Link_Text";
	public static final String WEB_DRIVER_SEARCH_BY_TYPE_CSS_SECLECTOR = "Css_Selector";
	public static final String WEB_DRIVER_SEARCH_BY_TYPE_XPATH = "XPath";
	
	public static final String WEB_DRIVER_BROWSER_TYPE_CHROME = "WEB_DRIVER_BROWSER_TYPE_CHROME";
	public static final String WEB_DRIVER_BROWSER_TYPE_FIREFOX_10 = "WEB_DRIVER_BROWSER_TYPE_FIREFOX_10";
	public static final String WEB_DRIVER_BROWSER_TYPE_HTML_UNIT = "WEB_DRIVER_BROWSER_TYPE_HTML_UNIT";
	
	public static final String WEB_ELEMENT_TAG_NAME_A = "a"; 
	
	
	public static final int LOOP_COUNT_6 = 6;
	public static final int LOOP_COUNT_10 = 10;
	
	public static final String MQ_MESSAGE_NOT_SEND = "MQ_MESSAGE_NOT_SEND";
	public static final String MQ_MESSAGE_RECEIVED = "MQ_MESSAGE_RECEIVED";
	public static final String MQ_MESSAGE_NOT_RECEIVED = "MQ_MESSAGE_NOT_RECEIVED";
	public static final String MQ_MESSAGE_TASK_FINISHED = "MQ_MESSAGE_TASK_FINISHED";
	
	public static final String DATA_TABLE_PREFIX = "data_";
	
	public static final String COUNTRY_ISO_CODE0 = "am bh bw cm cf ci eg gw gn gq in il jo ke kw mg ml ma mu mz ne ng om qa sa sn za tn ug ae ";
	public static final String COUNTRY_ISO_CODE1 = "au cn hk id jp kr my nz ph sg tw th vn ap be bg cz dk de ee es fr gr hr ie it lv li lt lu ";
	public static final String COUNTRY_ISO_CODE2 = "mk hu mt md me nl no at pl pt ro ru sk si ch fi se tr uk ai ag ar bb bz bm bo br vg ky cl ";
	public static final String COUNTRY_ISO_CODE3 = "co cr dm do ec sv gd gt gy hn jm mx ms ni pa py pe kn lc vc sr bs tt tc uy ve la ca us";
	
	public static final String PLACE_HOLDER_COUNTRY = "\\$Country\\$";
	public static final String PLACE_HOLDER_VALUE = "\\$PlaceHolders\\$";
	
	public static final String DB_ORDER_ASC = "ASC";
	public static final String DB_ORDER_DESC = "DESC";
	
	public static final String DEFAULT_PAGE_SIZE = "100";
	
	public static final long MIN_RECORD_DURATION_TIME = 2000;
	
	public static final String WEB_ELEMENT_GET_DATA_FROM_TEXT = "WEB_ELEMENT_GET_DATA_FROM_TEXT";
	public static final String WEB_ELEMENT_GET_DATA_FROM_INNER_HTML = "WEB_ELEMENT_GET_DATA_FROM_INNER_HTML";
	public static final String WEB_ELEMENT_GET_DATA_FROM_OUTTER_HTML = "WEB_ELEMENT_GET_DATA_FROM_OUTTER_HTML";	
	
	public static final String TEXT_COPY_OF = "Copy of ";
	
	public static final int TASK_LEVEL_HIGHT = 3;
	public static final int TASK_LEVEL_MIDDLE = 2;

	public static final String ACTION_SEARCH = "ACTION_SEARCH";
	
	/* food search portal related constants */
	
	/* food search portal related constants */
}
