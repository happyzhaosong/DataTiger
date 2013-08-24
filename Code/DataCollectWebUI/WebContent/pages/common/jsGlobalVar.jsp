<%@ page import="com.data.collect.common.util.*,com.data.collect.common.constants.*,com.data.collect.common.dto.*,com.data.collect.client.logic.*,java.util.*,com.data.collect.server.manager.*"%>
<script type="text/javascript">
var loadMessage = "Loading...";

var seperatorLeftBracket = "<%=Constants.SEPERATOR_LEFT_BRACKET%>";
var seperatorRightBracket = "<%=Constants.SEPERATOR_RIGHT_BRACKET%>";

var seperatorLeftBrace = "<%=Constants.SEPERATOR_LEFT_BRACE%>";
var seperatorRightBrace = "<%=Constants.SEPERATOR_RIGHT_BRACE%>";

var seperatorSingleQuotes = "<%=Constants.SEPERATOR_SINGLE_QUOTES%>";
var seperatorDoubleQuotes = "\<%=Constants.SEPERATOR_DOUBLE_QUOTES%>";

var seperatorBackSlash = "\<%=Constants.SEPERATOR_BACK_SLASH%>";
		
var seperatorComma = "<%=Constants.SEPERATOR_COMMA%>";
var seperatorColon = "<%=Constants.SEPERATOR_COLON%>";

var seperatorEqual = "<%=Constants.EQUAL_MARK%>";
var seperatorAnd = "<%=Constants.AND_MARK%>";
var seperatorQuestion = "<%=Constants.QUESTION_MARK%>";
var seperatorNavTitle = "<%=Constants.SEPERATOR_NAVIGATION_TITLE%>";
var seperatorComplex = "<%=Constants.SEPERATOR_COMPLEX %>";
var seperatorComplex1 = "<%=Constants.SEPERATOR_COMPLEX1 %>";


//json root element definition
var jsonTotalResultCount = "<%=Constants.JSON_TOTAL_RESULT_COUNT%>";

//web site json root key
var jsonRootSiteList = "<%=Constants.JSON_ROOT_SITE_LIST%>";
var jsonRootSiteCatList = "<%=Constants.JSON_ROOT_SITE_CATEGORY_LIST%>";
var jsonRootSiteContentPageCheckList = "<%=Constants.JSON_ROOT_SITE_CONTENT_PAGE_CHECK_LIST %>";
var jsonRootSitePageLinkParseList = "<%=Constants.JSON_ROOT_SITE_PAGE_LINK_PARSE_LIST %>";
var jsonRootSiteLoginAccountList = "<%=Constants.JSON_ROOT_SITE_LOGIN_ACCOUNT_LIST%>";
var jsonRootSiteTestThreadList = "<%=Constants.JSON_ROOT_THREAD_LIST %>";
var jsonRootSiteTestTaskList = "<%=Constants.JSON_ROOT_TASK_LIST %>";
var jsonRootSiteTestMQMessageList = "<%=Constants.JSON_ROOT_MQ_MESSAGE_LIST %>";

//parse template json root key
var jsonRootParseTplCatList = "<%=Constants.JSON_ROOT_PARSE_TEMPLATE_CATEGORY_LIST %>";
var jsonRootParseTplList = "<%=Constants.JSON_ROOT_PARSE_TEMPLATE_LIST%>";
var jsonRootParseTplItemList = "<%=Constants.JSON_ROOT_PARSE_TEMPLATE_ITEM_LIST%>";
var jsonRootParseTplItemActionList = "<%=Constants.JSON_ROOT_PARSE_TEMPLATE_ITEM_ACTION_LIST%>";

//db talbe json root key
var jsonRootDataTableList = "<%=Constants.JSON_ROOT_DATA_TABLE_LIST %>";
var jsonRootDataTableDataList = "<%=Constants.JSON_ROOT_DATA_TABLE_DATA_LIST %>"; 
var jsonRootDataTableColumnList = "<%=Constants.JSON_ROOT_DATA_TABLE_COLUMN_LIST %>";

//web driver search by type root key
var jsonRootWebDriverSearchByTypeList = "<%=Constants.JSON_ROOT_WEB_DRIVER_SEARCH_BY_TYPE_LIST %>";
//data type list root key
var jsonRootDataTypeList = "<%=Constants.JSON_ROOT_DATA_TYPE_LIST %>";

//action definition
var actionFieldName = "<%=Constants.ACTION%>";
var actionSubFieldName = "<%=Constants.ACTION_SUB%>";
var actionResetAll = "<%=Constants.RESET_ALL%>";
var actionDeleteAll = "<%=Constants.DELETE_ALL%>";
var actionLogin = "<%=Constants.ACTION_LOGIN%>";
var actionLogout = "<%=Constants.ACTION_LOGOUT%>";
var actionWebSiteCategoryList = "<%=Constants.ACTION_WEB_SITE_CATEGORY + Constants.LIST %>";
var actionParseTplCategoryList = "<%=Constants.ACTION_PARSE_TEMPLATE_CATEGORY + Constants.LIST %>";
var actionDataTableList = "<%=Constants.ACTION_DATA_TABLE + Constants.LIST %>";


//action url definition
var loginUrl = "<%=WebTool.getLoginPageURL(request, false) %>";
var logoutUrl = "<%=WebTool.getLogoutPageURL(request) %>";
var doLoginUrl = "<%=WebTool.getBaseURL(request) %>";
var webSiteUrl = "<%=WebTool.getWebSitePageURL(request) %>";
var parseTemplateUrl = "<%=WebTool.getParseTemplatePageURL(request) %>";
var dataTableUrl = "<%=WebTool.getDataTablePageURL(request) %>";


var menuListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_MENU + Constants.LIST) %>";

//web site category
var siteCatListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_CATEGORY + Constants.LIST) %>";
var siteCatSaveUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_CATEGORY + Constants.SAVE) %>";
var siteCatDeleteUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_CATEGORY + Constants.DELETE) %>";

//web site
var siteListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE + Constants.LIST) %>";
var siteSaveUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE + Constants.SAVE) %>";
var siteDeleteUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE + Constants.DELETE) %>";
var siteCopyUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE + Constants.COPY) %>";

//web site content page check
var siteContentPageCheckListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_CONTENT_PAGE_CHECK + Constants.LIST) %>";
var siteContentPageCheckSaveUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_CONTENT_PAGE_CHECK + Constants.SAVE) %>";
var siteContentPageCheckDeleteUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_CONTENT_PAGE_CHECK + Constants.DELETE) %>";

//web site page link parse
var sitePageLinkParseListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_PAGE_LINK_PARSE + Constants.LIST) %>";
var sitePageLinkParseSaveUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_PAGE_LINK_PARSE + Constants.SAVE) %>";
var sitePageLinkParseDeleteUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_PAGE_LINK_PARSE + Constants.DELETE) %>";
var sitePageLinkParseCopyUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_PAGE_LINK_PARSE + Constants.COPY) %>";


//web site login account
var siteLoginAccountListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_LOGIN_ACCOUNT + Constants.LIST) %>";
var siteLoginAccountSaveUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_LOGIN_ACCOUNT + Constants.SAVE) %>";
var siteLoginAccountDeleteUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_LOGIN_ACCOUNT + Constants.DELETE) %>";

//web site parse template category
var parseTplCatListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_PARSE_TEMPLATE_CATEGORY + Constants.LIST) %>";
var parseTplCatSaveUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_PARSE_TEMPLATE_CATEGORY + Constants.SAVE) %>";
var parseTplCatDeleteUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_PARSE_TEMPLATE_CATEGORY + Constants.DELETE) %>";

//web site parse template
var parseTplListAllUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_PARSE_TEMPLATE + Constants.LIST_ALL) %>";

var parseTplListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_PARSE_TEMPLATE + Constants.LIST) %>";
var parseTplSaveUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_PARSE_TEMPLATE + Constants.SAVE) %>";
var parseTplDeleteUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_PARSE_TEMPLATE + Constants.DELETE) %>";
var parseTplCopyUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_PARSE_TEMPLATE + Constants.COPY) %>";


//web site parse template item
var parseTplItemListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_PARSE_TEMPLATE_ITEM + Constants.LIST) %>";
var parseTplItemSaveUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_PARSE_TEMPLATE_ITEM + Constants.SAVE) %>";
var parseTplItemDeleteUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_PARSE_TEMPLATE_ITEM + Constants.DELETE) %>";
var parseTplItemCopyUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_PARSE_TEMPLATE_ITEM + Constants.COPY) %>";


//web site parse template item action
var parseTplItemActionListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_PARSE_TEMPLATE_ITEM_ACTION + Constants.LIST) %>";
var parseTplItemActionSaveUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_PARSE_TEMPLATE_ITEM_ACTION + Constants.SAVE) %>";
var parseTplItemActionDeleteUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_PARSE_TEMPLATE_ITEM_ACTION + Constants.DELETE) %>";

//data table and column item url
var dataTableListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_DATA_TABLE + Constants.LIST) %>";
var dataTableColumnListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_DATA_TABLE_COLUMN + Constants.LIST) %>";
var dataTableDataListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_DATA_TABLE_DATA + Constants.LIST) %>";

//test get web pages in url
//web site test and product action url
var testThreadListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_TEST_THREAD + Constants.LIST) %>";
var testThreadDeleteUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_TEST_THREAD + Constants.DELETE) %>";
var testThreadDeleteAllUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_TEST_THREAD + Constants.DELETE_ALL) %>";
var testThreadStopUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_TEST_THREAD + Constants.STOP) %>";
var testGetWebPagesInUrlSendMessageUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_TEST_MQMESSAGE + Constants.DOWNLOAD_AND_PARSE) %>";

var testTaskListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_TEST_TASK + Constants.LIST) %>";
var testTaskResetUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_TEST_TASK + Constants.RESET) %>";
var testTaskDeleteUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_TEST_TASK + Constants.DELETE) %>";
var testTaskDeleteAllUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_TEST_TASK + Constants.DELETE_ALL) %>";
var testTaskSetLevelUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_TEST_TASK + Constants.SET_LEVEL) %>";
var testTaskSetUselessUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_TEST_TASK + Constants.SET_USELESS) %>";


var testMQMessageListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_TEST_MQMESSAGE + Constants.LIST) %>";
var testMQMessageDeleteUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_SITE_TEST_MQMESSAGE + Constants.DELETE) %>";


//get all web driver search by element type
var webDriverSearchByTypeListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_WEB_DRIVER_SEARCH_BY_TYPE_LIST) %>";
//get all data type
var dataTypeListUrl = "<%=WebTool.getActionURL(request, Constants.ACTION_DATA_TABLE_COLUMN + Constants.DATA_TYPE + Constants.LIST_ALL) %>";

//id definition
//global hidden field
var idSiteCatIdHidden = "idSiteCatIdHidden";
var idSiteIdHidden = "idSiteIdHidden";

var idParseTplCatIdHidden = "idParseTplCatIdHidden";
var idParseTplIdHidden = "idParseTplIdHidden";
var idParseTplItemIdHidden = "idParseTplItemIdHidden";

var idDataTableNameHidden = "idDataTableNameHidden";

//left navigation menu
var idLeftNavMenu = "idLeftNavMenu";


//web site category panel
var idSiteCatPanel = "idSiteCatPanel";


//web site panel
var idSitePanel = "idSitePanel";

//web site parse wizard panel
var idSiteDetailPanel = "idSiteDetailPanel";

var idSiteParseWizPreBtn = "idSiteParseWizPreBtn";
var idSiteParseWizNextBtn = "idSiteParseWizNextBtn";
var idSiteParseWizFinishBtn = "idSiteParseWizFinishBtn";


//web site basic information page 
var idSiteDetailBasicInfoPanel = "idSiteDetailBasicInfoPanel";

//web site page link parse option
var idSiteDetailPageLinkUrlParseListPanel = "idSiteDetailPageLinkUrlParseListPanel";

//web site content page check option
var idSiteDetailContentPageCheckListPanel = "idSiteDetailContentPageCheckListPanel";

//web site login account panel
var idSiteDetailLoginAccountPanel = "idSiteDetailLoginAccountPanel";
var idSiteDetailLoginAccountListPanel = "idSiteDetailLoginAccountListPanel";

//web site test action panel
var idSiteTestActionPanel = "idSiteTestActionPanel";
var idSiteTestThreadListInfoPanel = "idSiteTestThreadListInfoPanel";
var idSiteTestTaskListInfoPanel = "idSiteTestTaskListInfoPanel";
var idSiteTestMQMessageListInfoPanel = "idSiteTestMQMessageListInfoPanel";


//parse template category panel
var idParseTplCatPanel = "idParseTplCatPanel";

//parse template panel
var idParseTplPanel = "idParseTplPanel";

//parse template detail panel, detail template item panel
var idParseTplDetailPanel = "idParseTplDetailPanel";
var idParseTplDetailPanelItemListPanel = "idParseTplDetailPanelItemListPanel";
//var idParseTplDetailPanelItemActionListPanel = "idParseTplDetailPanelItemActionListPanel";

//parsed out data table list panel
var idDataTableListPanel = "idDataTableListPanel";
//parsed out data table column list panel
var idDataTableColumnListPanel = "idDataTableColumnListPanel";
//parsed out data table data list panel
var idDataTableDataPanel = "idDataTableDataPanel";
//parsed out data table data list panel
var idDataTableDataListPanel = "idDataTableDataListPanel";
//parsed out data table data 1 list detail panel
var idDataTableDataDetailPanel = "idDataTableDataDetailPanel";
//search function panel
var idDataTableDataSearchPanel = "idDataTableDataSearchPanel";
//for search panel suffix
var idSearchPanelSuffix = "SearchPanel";


//data param id name definition
var dataColumnDownloadTaskPageUrl = "<%=Constants.DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_PAGE_URL %>";
var dataColumnDownloadTaskId = "<%=Constants.DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_ID %>";
var dataColumnDownloadTaskLevel = "<%=Constants.DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_LEVEL %>";
var dataColumnDownloadTaskDataParseTime = "<%=Constants.DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_DATA_PARSE_TIME %>";
var dataColumnDownloadTaskDataParseTimeNumber = "<%=Constants.DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_DATA_PARSE_TIME_NUMBER %>";
var dataColumnDownloadTaskUselessContentPage = "<%=Constants.DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_USELESS_CONTENT_PAGE %>";

var downloadTaskLevel = "<%=Constants.DOWNLOAD_TASK_LEVEL %>";
var resetApplyTime = "<%=Constants.RESET_APPLY_TIME %>";



var dataId = "<%=Constants.ID %>";

var dataWebSiteId = "<%=Constants.DATA_WEB_SITE_ID %>";
var dataTableId = "<%=Constants.DATA_TABLE_ID %>";
var dataThreadType = "<%=Constants.DATA_THREAD_TYPE %>";
var dataDBTableName = "<%=Constants.DATA_DB_TABLE_NAME %>";

var dThreadTypeGetWebPage = "<%=Constants.DOWNLOAD_THREAD_TYPE_GET_WEB_PAGE%>";
var dThreadTypeParsePageData = "<%=Constants.DOWNLOAD_THREAD_TYPE_PARSE_PAGE_DATA%>";
var dThreadTypeTester = "<%=Constants.DOWNLOAD_THREAD_TYPE_TESTER%>";
var dThreadTypeWorker = "<%=Constants.DOWNLOAD_THREAD_TYPE_WORKER%>";

var dThreadTypeGetWebPageStr = "Get web page thread";
var dThreadTypeParsePageDataStr = "Parse page data thread";
var dThreadTypeTesterStr = "Tester thread";
var dThreadTypeWorkerStr = "Worker thread";

//error code definitation
var errCodeSessionTimeOut = "<%=Constants.ERROR_CODE_SESSION_TIME_OUT%>";
var timeOutClientOperation = "<%=Constants.TIME_OUT_EXT_JS_CLIENT_OPERATION_SECOND_600%>";

var emptyTextMultipleValue = "Can have multiple value seperated by semicolon(;). Diferrent values are or relationship.";
var emptyTextSingleValue = "Can only have one value not seperated by semicolon(;).";
var emptyTextSearchByValue = "Format is urlCharacotr1!@#xpath1#@!xpath2;urlCharacotr2!@#xpath1#@!xpath2;. Meaning search this element by what? tag, xpath, id, name, css selector etc, usually use id or name to get outer text then use regexp or parse string to parse needed value, this way webdriver can run fast and avoid hang, id or name or xpath can be multiple seperated by ;, you can set url specified search by value";



//spider used browser type that catch web pages from web site.
var browserTypeHU = "<%=Constants.WEB_DRIVER_BROWSER_TYPE_HTML_UNIT%>";
var browserTypeFF10 = "<%=Constants.WEB_DRIVER_BROWSER_TYPE_FIREFOX_10%>";
var browserTypeChrome = "<%=Constants.WEB_DRIVER_BROWSER_TYPE_CHROME%>";

var pageSize = "<%=Constants.DEFAULT_PAGE_SIZE%>";

var pageLimit = "limit";
var pageSort = "sort";
var pageDir = "dir";
var pageFilter = "filter";
var pageFilterOperator = "filterOperator";
var pageFilterValue = "filterValue";

//javascript actions
var jsActionClick = "<%=Constants.JAVA_SCRIPT_ACTION_CLICK%>";

</script>


<style type="text/css"  >  
      .bannerText{font-size: 30px; color: #ffffff}
      .width100{width: 100%}
</style>  