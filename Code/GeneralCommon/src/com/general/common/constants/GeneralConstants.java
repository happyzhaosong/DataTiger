package com.general.common.constants;

public class GeneralConstants {
	
	//servlet path	
	public static final String PATH_APP_JS = "/js/app/";
	public static final String PATH_EXT_JS = "/js/ext-4.1.1a/";

	
	//javascript action names
	public static final String JAVA_SCRIPT_ACTION_CLICK = "JAVA_SCRIPT_ACTION_CLICK";
	
	
	//default connection timeout
	public static final int TIME_OUT_EXT_JS_CLIENT_OPERATION_SECOND_600 = 600;
	public static final int TIME_OUT_ACTIVEMQ_SEND_SECOND_300 = 300;
	public static final int TIME_OUT_ACTIVEMQ_CLOSE_SECOND_300 = 300;
	public static final int TIME_OUT_WEBUI_SESSION_MILLI_SECOND_60000 = 60000;

		
	//page path
	public static final String PAGE_LOGIN = "/login.jsp";
	public static final String PAGE_FOLDER = "/pages";
		
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
	
	public static final String ACTION_SEARCH = "ACTION_SEARCH";
	public static final String ACTION_SEARCH_GET_FOOD_SEARCH_KEYWORDS = "ACTION_SEARCH_GET_FOOD_SEARCH_KEYWORDS";
		
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
	

	//data list
	/**************************************DATA_WEB_SITE******************************************/
	public static final String DATA_IDS = "DATA_IDS";
	public static final String DATA_DELETE_IDS = "DATA_DELETE_IDS";
	public static final String DATA_DB_TABLE_NAME = "DATA_DB_TABLE_NAME";
	public static final String DATA_DB_SEARCH_COLUMN = "DATA_DB_SEARCH_COLUMN";
	public static final String DATA_DB_SEARCH_VALUE = "DATA_DB_SEARCH_VALUE";
	
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
	
	
	//all value
	public static final String VALUE_ALL = "VALUE_ALL";
	public static final String ID = "id";
	public static final String ID_NAME = "name";
	public static final String ID_DESC = "desc";
	
	//page content char set
	public static final String PAGE_CHAR_SET_UTF8 = "UTF-8";
	public static final String PAGE_CHAR_SET_GB2312 = "GB2312";
	public static final String PAGE_CHAR_SET_GBK = "GBK";
	public static final String PAGE_CHAR_SET_ISO_8859_1 = "ISO-8859-1";
	public static final String PAGE_CHAR_SET = PAGE_CHAR_SET_UTF8;
	
	
	//error message

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
	
	public static final String JSON_SUCCESS = "success";	
	public static final String JSON_EXIST = "exist";	
	public static final String JSON_CODE = "code";	
	public static final String JSON_MESSAGE = "message";
	public static final String JSON_USER_ACCOUNT = "JSON_USER_ACCOUNT";
	public static final String JSON_USER_ROLE = "JSON_USER_ROLE";
	public static final String JSON_TOTAL_RESULT_COUNT = "JSON_TOTAL_RESULT_COUNT";
	
	public static final String JSON_SEARCH_KEYWORD_LIST = "JSON_SEARCH_KEYWORD_LIST";
		
	public static final String SERIAL_VERSION_UID = "serialVersionUID";
	
	/************************************Downoad Related Constants**********************************************/	
	
	public static final int LOOP_COUNT_6 = 6;
	public static final int LOOP_COUNT_10 = 10;
		
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
		
	public static final int DOWNLOAD_THREAD_TYPE_GET_WEB_PAGE = 2;	
	
	public static final String DOWNLOAD_THREAD_ACTION_START = "DOWNLOAD_THREAD_ACTION_START";		

	public static final String JSON_ROOT_ACTIVE_MQ_MESSAGE = "JSON_ROOT_ACTIVE_MQ_MESSAGE";
	
	public static final String ORDER_BY_ASC_SUFFIX = "_asc";
	
	public static final String ORDER_BY_DESC_SUFFIX = "_desc";
	
	public static final String ORDER_BY_ASC = "asc";
	
	public static final String ORDER_BY_DESC = "desc";
	
	
	public static final String SEARCH_NO_RESULT_INFO_PREFIX = "没有找到与  \"";
	public static final String SEARCH_NO_RESULT_INFO_SUFFIX = "\" 相关的信息";
	
	public static final String ERROR_MESSAGE_SYSTEM_BUSY = "系统忙，请稍后访问。";
	
	public static final int SEARCH_DATA_IN_XIU_HAO_CHI = 1;
	public static final int SEARCH_DATA_IN_XIU_HAO_PU = 2;
	
	public static final String SEARCH_LAST_SEARCH_TIME_SAVED_IN_SESSION = "SEARCH_LAST_SEARCH_TIME_SAVED_IN_SESSION";
	public static final String SEARCH_LAST_SEARCH_ACTION = "SEARCH_LAST_SEARCH_ACTION";
	
	public static final long SEARCH_FREQUENT_DURATION_IN_SECONDS = 2;
}
