var idSiteDetailPageLinkUrlParseListPanelStore = new DC.store.SitePageLinkParse();

//site page link url parse grid panel component
Ext.define('DC.comp.SiteDetailPageLinkUrlParseListPanel', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.sitedetailpagelinkurlparselistpanel',
	id: idSiteDetailPageLinkUrlParseListPanel,
    title: "Page Link URL Parse Option",  
    autoScroll: true,
    split: false,
    store: idSiteDetailPageLinkUrlParseListPanelStore,
    selModel: Ext.create('Ext.selection.CheckboxModel',{}),
    columns: [
  	    { xtype: 'rownumberer', width: 50, sortable: false},
        { text: 'Id', dataIndex: 'id', flex: 1 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderUrlLinkStyle},        
        { text: 'Search By Element Type', dataIndex: 'byEleType', flex: 1 , sortable: true, hideable: true},
        { text: 'Search By Element Value', dataIndex: 'byEleVal', flex: 1 , sortable: true, hideable: true},
        //{ text: 'Search By Tag Attribute', dataIndex: 'byTagAttribute', flex: 10 , sortable: true, hideable: true},
        { text: 'URL Charactor', dataIndex: 'urlCharactor', flex: 3 , sortable: true, hideable: true},
        { text: 'Not URL Charactor', dataIndex: 'notUrlCharactor', flex: 3 , sortable: true, hideable: true},
        //{ text: 'URL Match Regular Expression', dataIndex: 'urlMatchRegExp', flex: 5 , sortable: true, hideable: true},
        { text: 'Content Page Url Charactor', dataIndex: 'contentPageUrlCharactor', flex: 3 , sortable: true, hideable: true},
        { text: 'Run Regexp On Url', dataIndex: 'runRegexpOnUrl', flex: 3 , sortable: true, hideable: true},
        { text: 'Run String Find On Url', dataIndex: 'runStringFindOnUrl', flex: 3 , sortable: true, hideable: true},
        //{ text: 'Desc', dataIndex: 'urlPageDesc', flex: 3 , sortable: true, hideable: true}
    ],
    buttons: formToolGlobal.createGridPanelAddEditDeleteButton("Web Site Page Link Url Parse"),
    listeners:{
    	cellClick: {
    		fn: function(obj,td,cIdx,record,tr,rIdx)
    		{
    			if(cIdx>0 && !stringToolGlobal.isEmpty(td.innerText))
    			{
    				Ext.Msg.alert('Cell Value', td.innerText).setAutoScroll(true);
    			}
    		}
    	}
    },showAddEditWindow: function(windowTitle, recordObj)
    {    	
    	var formBtns = formToolGlobal.createAddEditFormWindowSaveCloseButton(windowTitle, this.getId(), jsonRootSitePageLinkParseList, null, null);
  
		var formItems = [{
	    	xtype: 'hiddenfield',	        
	        value: Ext.getCmp(idSiteIdHidden).getValue(),
	        name: 'siteId'
	    },{
	    	xtype: 'hiddenfield',	        
	        value: '-1',
	        name: 'id'
	    },{
	    	xtype: 'textareafield',
	    	height: 150,
	        grow : true,
	        fieldLabel: 'Click These Web Elements By XPath <br/>Before Parse URL. Format is urlCha1!@#xpath1,xpath2;<br/>urlCha2!@#xpath3,xpath4;<br/>urlCha3!@#xpath5,xpath6<br/>Only useful for parse url link in one page by a html tag.urlCha必须唯一,不能为空',
	        name: 'clickEleXPathBeforeParseUrl',
	        allowBlank: true,
			emptyText: 'For parse more element in taobao, tmall, jd etc. All values here should be xpath of elements which need to be clicked before parse url link in this page. If this field is empty then not click any element before parse url link. Format is urlCharactor1!@#xpath1,xpath2;urlCharactor2!@#xpath3,xpath4;urlCharactor3!@#xpath5,xpath6'
	    },{
			xtype: 'WebDriverSearchByComboBox',			
			fieldLabel: 'Search Web Element By',
			name:'byEleType',
			allowBlank: false
		},{
	        grow : true,
	        fieldLabel: 'Search By Element Value',
	        name: 'byEleVal',
	        allowBlank: false,
			emptyText: emptyTextSingleValue
	    },{
	        grow : true,
	        fieldLabel: 'Search By Tag Attribute',
	        name: 'byTagAttribute',
	        allowBlank: true,
			emptyText: emptyTextMultipleValue,
	    },{
	        grow : true,
	        name : 'urlXpathCharactor',
	        fieldLabel: 'URL XPath Charactor',
	        emptyText: emptyTextMultipleValue,
	        allowBlank: true
	    },{
	        grow : true,
	        name : 'notUrlXpathCharactor',
	        emptyText: emptyTextMultipleValue,
	        fieldLabel: 'Not URL XPath Charactor'
	    },{
	        grow : true,
	        name : 'urlCharactor',
	        fieldLabel: 'URL Charactor',
	        emptyText: emptyTextMultipleValue,
	        allowBlank: false
	    },{
	        grow : true,
	        name : 'notUrlCharactor',
	        emptyText: emptyTextMultipleValue,
	        fieldLabel: 'Not URL Charactor'
	    },{
	        grow : true,
	        name : 'urlMatchRegExp',
	        emptyText: emptyTextMultipleValue,
	        fieldLabel: 'URL Match Regular Expression'
	    },{
	        grow : true,
	        name : 'contentPageUrlCharactor',
	        fieldLabel: 'Content Page URL Charactor',
	        emptyText: emptyTextMultipleValue,
	        allowBlank: true
	    },{
	        grow : true,
	        name : 'runRegexpOnUrl',
	        fieldLabel: 'Run Regexp On Url',
	        emptyText: "在解析出来的url上运行的正则表达式，使用正则表达式运行之后的url作为页面的url, Some site url like taobao same url add some different string, so need to run regexp on such url to get real simple url. Format: srcRegExp1!@#replaceStr1;srcRegExp2!@#replaceStr2;...",
	        allowBlank: true
	    },{
	    	height: 150,
	        grow : true,
	        name : 'runStringFindOnUrl',
	        fieldLabel: 'Reserved Params On Url(url中只保留如下参数名字的参数,格式： urlCha1!@#reserveParam1,rP2;<br/>urlCha2!@#reserveParam3,rP4;<br/>urlCha3!@#reserveParam5,rP6;)<br/>urlCha必须唯一,不能为空<br/>特殊占位符：ALL_UNMATCHED_URL , RESERVE_ALL_URL_PARAMETERS',
	        emptyText: "如果正则表达式不好写，则用该字段进行解析，该字段的格式是  urlCha1!@#reserveParam1,reserveParam2;<br/>urlCha2!@#reserveParam3,reserveParam4;<br/>urlCha3!@#reserveParam5,reserveParam6;<br/>, 只保留匹配urlCha的url中的这些有效参数及值，其他的参数过滤掉，如果某一个 urlCha='ALL_UNMATCHED_URL'则匹配除匹配后之外的所有的url，对于taobao这样的网站因为taobao自动添加了很多无用的统计参数在url里面，所以需要过滤掉，只保留有用的参数. RESERVE_ALL_URL_PARAMETERS --- 保留所有的url参数",
	        allowBlank: true
	    },{
	        grow : true,
	        name : 'pageEncoding',
	        fieldLabel: 'Page Encoding'
	    },{
	        grow : true,
	        name : 'execJavascript',
	        fieldLabel: 'Execute Javascript'
	    },{
	        grow : true,
	        name : 'urlPageDesc',
	        fieldLabel: 'Description'
	    },{	 
	    	height: 25,
	    	disabled: true,
			fieldLabel: 'Parse Url Link Config Desc',
			name:'parseContentPageConfigDesc',
			emptyText: '如果在上面的解析基础上仍不能判断是否为正确的链接则用如下配置进行判断.**********'
		},{	 
			fieldLabel: '页面特征文字的XPath Value.' + urlToXpathFormatBr + 'urlCha必须唯一,不能为空',
			name:'urlPageChaXpathValue',
			emptyText: '在最上面解析出来的url对应的页面里面查询这样的xpath. ' + urlToXpathFormatNoBr + 'urlCha必须唯一,不能为空'
		},{
	        grow : true,
	        fieldLabel: 'XPath 对应的元素的  Attribute 名字',
	        name: 'urlPageChaAttribute',
	        allowBlank: true,
			emptyText: '上面的 XPath对应的元素的属性名字，多个属性名只得到第一个非空的属性值' + emptyTextMultipleValue
	    },{
	        grow : true,
	        fieldLabel: 'URL页面XPath元素的特征字符串',
	        name: 'urlPageCharactor',
	        allowBlank: true,
			emptyText: '如果本特征字符串不为空且上面的XPath对应的元素解析出来的值包含该特征字符串，则说明该url是我们想要的页面. ' + emptyTextMultipleValue
	    },{
	        grow : true,
	        fieldLabel: 'URL页面XPath元素的非特征字符串',
	        name: 'urlPageNotCharactor',
	        allowBlank: true,
			emptyText: '如果本非特征字符串不为空且上面的XPath对应的元素解析出来的值包含该非特征字符串，则说明该url不是我们想要的页面. ' + emptyTextMultipleValue
	    }];
    	
		
		formToolGlobal.buildFormItemsIdByName(idSiteDetailPageLinkUrlParseListPanel, formItems);
		
    	var window = formToolGlobal.createFormWithWindow(sitePageLinkParseSaveUrl, formItems, 'textareafield', formBtns, windowTitle);
    	window.width = 1000;
    	window.height = 500;
    	
		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "byEleType").getStore().load();
		
    	if(recordObj!=null)
    	{
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "id").setValue(recordObj.id);
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "clickEleXPathBeforeParseUrl").setValue(recordObj.clickEleXPathBeforeParseUrl);    		
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "byEleType").select(recordObj.byEleType);
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "byEleVal").setValue(recordObj.byEleVal);
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "byTagAttribute").setValue(recordObj.byTagAttribute);
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "urlXpathCharactor").setValue(recordObj.urlXpathCharactor);
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "notUrlXpathCharactor").setValue(recordObj.notUrlXpathCharactor);
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "urlCharactor").setValue(recordObj.urlCharactor);
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "notUrlCharactor").setValue(recordObj.notUrlCharactor);
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "urlMatchRegExp").setValue(recordObj.urlMatchRegExp);
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "contentPageUrlCharactor").setValue(recordObj.contentPageUrlCharactor);
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "pageEncoding").setValue(recordObj.pageEncoding);
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "runRegexpOnUrl").setValue(recordObj.runRegexpOnUrl);
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "runStringFindOnUrl").setValue(recordObj.runStringFindOnUrl);
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "execJavascript").setValue(recordObj.execJavascript);
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "urlPageDesc").setValue(recordObj.urlPageDesc);
    	}
    	window.show();
    },
    deleteSelectedRecords: function(selectedRecords)
    {
    	var submitParams = utilToolGlobal.addSubmitParamsWithDelIds(null,utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));
    	var reloadPanelListUrl = utilToolGlobal.buildReloadPanelListUrl(Ext.getCmp(idSiteIdHidden).value,  this.getId());
    	formToolGlobal.submitAndReloadRecords(sitePageLinkParseDeleteUrl, submitParams,  this.getId(), "Delete Web Site Page Link Url Parse", reloadPanelListUrl, null);
    },
    initPageLinkUrlParseFieldValue : function (dataObj)
	{
		if(dataObj!=null && dataObj.pageLinkParseDtoList)
		{
			this.getStore().loadData(dataObj.pageLinkParseDtoList);
		}else
		{
			this.getStore().removeAll();
		}
	},
	copySelectedRecords : function (selectedRecords)
	{
    	var submitParams = utilToolGlobal.addSubmitParamsWithIds(null,utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));
    	var reloadPanelListUrl = utilToolGlobal.buildReloadPanelListUrl(Ext.getCmp(idSiteIdHidden).value,  this.getId());
    	formToolGlobal.submitAndReloadRecords(sitePageLinkParseCopyUrl, submitParams,  this.getId(), "Copy Web Site Page Link Url Parse Config", reloadPanelListUrl, null);		
	},    
    bbar: Ext.create('Ext.PagingToolbar', {
        pageSize: pageSize,
        store: idSiteDetailPageLinkUrlParseListPanelStore,
        displayInfo: true,
        plugins: Ext.create('Ext.ux.ProgressBarPager', {})
    })	
});