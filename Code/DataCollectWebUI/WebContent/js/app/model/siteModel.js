Ext.define('DC.model.SiteContentPageCheck', {
	 extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'siteId', type: 'string'},
        {name: 'byEleType', type: 'string'},
        {name: 'byEleVal', type: 'string'},
        {name: 'byTagAttribute', type: 'string'},
        {name: 'parseValueRegExp', type: 'string'},
        {name: 'parseValueString', type: 'string'},
        {name: 'charactor', type: 'string'},         
        {name: 'notCharactor', type: 'string'}  
    ]
});


Ext.define('DC.model.SitePageLinkParse', {
	 extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'siteId', type: 'string'},
        {name: 'byEleType', type: 'string'},
        {name: 'byEleVal', type: 'string'},
        {name: 'byTagAttribute', type: 'string'},
        {name: 'urlCharactor', type: 'string'},         
        {name: 'notUrlCharactor', type: 'string'},  
        {name: 'urlMatchRegExp', type: 'string'},
        {name: 'execJavascript', type: 'string'},
        {name: 'urlPageDesc', type: 'string'},
        {name: 'pageEncoding', type: 'string'},
        {name: 'contentPageUrlCharactor', type: 'string'},   
        {name: 'runRegexpOnUrl', type: 'string'},           
        {name: 'runStringFindOnUrl', type: 'string'},           
        {name: 'selected', type: 'string'}
    ]
});

Ext.define('DC.model.SiteLoginAccount', {
	 extend: 'Ext.data.Model',
   fields: [
       {name: 'id', type: 'string'},
       {name: 'siteId', type: 'string'},
       {name: 'loginPageUrl', type: 'string'},
       {name: 'loginUser', type: 'string'},         
       {name: 'loginPasswd', type: 'string'},  
       {name: 'loginUserXpath', type: 'string'},
       {name: 'loginPasswdXpath', type: 'string'},
       {name: 'loginSubmitXpath', type: 'string'},       
       {name: 'selected', type: 'string'}
   ]
});

Ext.define('DC.model.Site', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'int'},
        {name: 'categoryId', type: 'int'},  
        {name: 'parseId', type: 'int'},
        {name: 'startDownloadTime', type: 'int'},
        {name: 'downloadDurationSeconds', type: 'int'},  
        {name: 'downloadTaskRegetDuration', type: 'int'},
        
        {name: 'name', type: 'string'},
        {name: 'desc', type: 'string'},
        {name: 'topUrl', type: 'string'},
        {name: 'overWriteAttributes', type: 'string'},
        {name: 'loginCheckReturnUrl', type: 'string'},
        {name: 'loginCheckReturnData', type: 'string'},
        {name: 'loginCheckReturnDataXPath', type: 'string'},
        {name: 'pageLinkParseDtoList', type: 'string'},
        {name: 'siteLoginAccountDtoList', type: 'string'},
        
        {name: 'needLogin', type: 'bool'},
        {name: 'testPassed', type: 'bool'},
        {name: 'siteStatus', type: 'bool'},
        {name: 'selected', type: 'bool'},
        
        {name: 'accessDeniedPageElementXpath', type: 'string'},
        {name: 'accessDeniedPageElementValue', type: 'string'},
        
        {name: 'setHighLevelTaskUrlCharactor', type: 'string'},
        {name: 'setMiddleLevelTaskUrlCharactor', type: 'string'},
        
        {name: 'testPassedString', type: 'string'},
        {name: 'siteStatusString', type: 'string'}        
    ]
});

Ext.define('DC.model.SiteTestThreadListInfo', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'int'},
        {name: 'threadId', type: 'string'},
        {name: 'userId', type: 'string'},
        {name: 'threadType', type: 'string'},        
        {name: 'startTime', type: 'string'},
        {name: 'stopTime', type: 'string'},  
        {name: 'stopReason', type: 'string'}         
    ]
});

Ext.define('DC.model.SiteTestTaskListInfo', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'int'},
        {name: 'parentPageUrl', type: 'string'},
        {name: 'pageUrl', type: 'string'},
        {name: 'siteId', type: 'int'},
        {name: 'ifContentPage', type: 'string'},        
        {name: 'inDbTime', type: 'string'},
        {name: 'applyTime', type: 'string'},  
        {name: 'ifSiteTopUrl', type: 'string'}, 
        {name: 'threadTableId', type: 'string'},
        {name: 'ifTest', type: 'string'},
        {name: 'runTime', type: 'string'},
        {name: 'taskRunDeltaTime', type: 'string'},
        {name: 'durationInfo', type: 'string'},
        {name: 'parsedOutUrlCount', type: 'int'},    
        {name: 'accessDeniedDate', type: 'string'},
        {name: 'accessDeniedThreadSleepTime', type: 'int'},
        {name: 'resetApplyTimeReason', type: 'string'},
        {name: 'resetApplyTimeTime', type: 'string'},        
        {name: 'errorMessage', type: 'string'},
        {name: 'taskLevel', type: 'int'},
        {name: 'uselessContentPage', type: 'int'}
    ]
});

Ext.define('DC.model.SiteTestMQMessageListInfo', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'int'},
        {name: 'downloadThreadType', type: 'string'},
        {name: 'siteId', type: 'int'},
        {name: 'userId', type: 'string'},        
        {name: 'action', type: 'string'},
        {name: 'threadTableIds', type: 'string'},  
        {name: 'threadIds', type: 'string'}, 
        {name: 'sendTime', type: 'string'},
        {name: 'receiveTime', type: 'string'},
        {name: 'finishTime', type: 'string'},
        {name: 'failReason', type: 'string'}       
    ]
});