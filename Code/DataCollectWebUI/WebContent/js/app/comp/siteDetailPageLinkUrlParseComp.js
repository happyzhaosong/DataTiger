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
	    	height:100,
	        grow : true,
	        fieldLabel: 'Click These Web Elements By XPath Before Parse URL. Format is urlCharactor1=xpath1,xpath2;<br/>urlCharactor2=xpath3,xpath4;<br/>urlCharactor3=xpath5,xpath6',
	        name: 'clickEleXPathBeforeParseUrl',
	        allowBlank: true,
			emptyText: 'For parse more element in taobao, tmall, jd etc. All values here should be xpath of elements which need to be clicked before parse url link in this page. If this field is empty then not click any element before parse url link. Format is urlCharactor1=xpath1,xpath2;urlCharactor2=xpath3,xpath4;urlCharactor3=xpath5,xpath6'
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
	        grow : true,
	        name : 'runStringFindOnUrl',
	        fieldLabel: 'Reserved Params On Url(url中只保留如下参数名字的参数)',
	        emptyText: "如果正则表达式不好写，则用该字段进行解析，该字段的格式是   reserveParam1;reserveParam2;reserveParam3;..., 只保留url中的这些有效参数及值，其他的参数过滤掉，对于taobao这样的网站因为taobao自动添加了很多无用的统计参数在url里面，所以需要过滤掉，只保留有用的参数. RESERVE_ALL_URL_PARAMETERS --- 保留所有的url参数",
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
	    }];
    	
		
		formToolGlobal.buildFormItemsIdByName(idSiteDetailPageLinkUrlParseListPanel, formItems);
		
    	var window = formToolGlobal.createFormWithWindow(sitePageLinkParseSaveUrl, formItems, 'textareafield', formBtns, windowTitle);
    	window.width = 1000;
    	window.height = 500;
    	
		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "byEleType").getStore().load();
		
    	if(recordObj!=null)
    	{
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "id").setValue(recordObj.id);
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "byEleType").select(recordObj.byEleType);
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "byEleVal").setValue(recordObj.byEleVal);
    		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel + "byTagAttribute").setValue(recordObj.byTagAttribute);
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