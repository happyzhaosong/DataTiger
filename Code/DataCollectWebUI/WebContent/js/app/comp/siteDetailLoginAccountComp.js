//site login account form include grid panel component
Ext.define('DC.comp.SiteDetailLoginAccountListPanel', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.sitedetailloginaccountlistpanel',
	id: idSiteDetailLoginAccountListPanel,
    title: 'Login Account List',
    //bodyStyle: 'padding: 20px',
    width: 1000,
    height: 250,
    split: false,
    autoScroll: true,
    store: new DC.store.SiteLoginAccount(),
    selModel: Ext.create('Ext.selection.CheckboxModel',{}),
    columns: [  
        { xtype: 'rownumberer', width: 50, sortable: false},
        { text: 'Login Page URL', dataIndex: 'loginPageUrl', flex: 10 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderUrlLinkStyle},
        { text: 'Login Username', dataIndex: 'loginUser', flex: 5 , sortable: true, hideable: true},
        { text: 'Login Password', dataIndex: 'loginPasswd', flex: 5 , sortable: true, hideable: true},
        { text: 'Username XPath', dataIndex: 'loginUserXpath', flex: 5 , sortable: true, hideable: true},
        { text: 'Password XPath', dataIndex: 'loginPasswdXpath', flex: 1 , sortable: true, hideable: true},
        { text: 'Submit Button XPath', dataIndex: 'loginSubmitXpath', flex: 3 , sortable: true, hideable: true}
    ],
    buttons: formToolGlobal.createGridPanelAddEditDeleteButton("Web Site Login Account"),
    showAddEditWindow: function(windowTitle, recordObj)
    {
    	var formBtns = formToolGlobal.createAddEditFormWindowSaveCloseButton(windowTitle, this.getId(), jsonRootSiteLoginAccountList, null, null);

    	var formItems = [{
	    	xtype: 'hiddenfield',	        
	        value: Ext.getCmp(idSiteIdHidden).value,
	        name: 'siteId'
	    },{
	    	xtype: 'hiddenfield',	        
	        value: '-1',
	        name: 'id'
	    },{
	        grow : true,
	        fieldLabel: 'Login Page URL',
	        name: 'loginPageUrl'
	    },{
	        grow : true,
	        name : 'loginUser',
	        fieldLabel: 'Login Username'
	    },{
	        grow : true,
	        name : 'loginPasswd',
	        fieldLabel: 'Login Password'
	    },{
	        grow : true,
	        name : 'loginUserXpath',
	        fieldLabel: 'Username XPath'
	    },{
	        grow : true,
	        name : 'loginPasswdXpath',
	        fieldLabel: 'Password XPath'
	    },{
	        grow : true,
	        name : 'loginSubmitXpath',
	        fieldLabel: 'Submit Button XPath'
	    }];
    	
    	formToolGlobal.buildFormItemsIdByName(idSiteDetailLoginAccountListPanel, formItems);
    	
    	var window = formToolGlobal.createFormWithWindow(siteLoginAccountSaveUrl, formItems, 'textareafield', formBtns, windowTitle);
    	window.width = 1000;
    	window.height = 500;
    	
    	if(recordObj!=null)
    	{
    		Ext.getCmp(idSiteDetailLoginAccountListPanel + "id").setValue(recordObj.id);
    		Ext.getCmp(idSiteDetailLoginAccountListPanel + "loginPageUrl").setValue(recordObj.loginPageUrl);
    		Ext.getCmp(idSiteDetailLoginAccountListPanel + "loginUser").setValue(recordObj.loginUser);
    		Ext.getCmp(idSiteDetailLoginAccountListPanel + "loginPasswd").setValue(recordObj.loginPasswd);
    		Ext.getCmp(idSiteDetailLoginAccountListPanel + "loginUserXpath").setValue(recordObj.loginUserXpath);
    		Ext.getCmp(idSiteDetailLoginAccountListPanel + "loginPasswdXpath").setValue(recordObj.loginPasswdXpath);
    		Ext.getCmp(idSiteDetailLoginAccountListPanel + "loginSubmitXpath").setValue(recordObj.loginSubmitXpath);    		
    	}
    	window.show();
    },
    deleteSelectedRecords: function(selectedRecords)
    {
    	var submitParams = utilToolGlobal.addSubmitParamsWithDelIds(null,utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));
    	var reloadPanelListUrl = utilToolGlobal.buildReloadPanelListUrl(Ext.getCmp(idSiteIdHidden).value, this.getId());
    	formToolGlobal.submitAndReloadRecords(siteLoginAccountDeleteUrl, submitParams, this.getId(), "Delete Web Site Login Account", reloadPanelListUrl, null);
    }
});


Ext.define('DC.comp.SiteDetailLoginAccountPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.sitedetailloginaccountpanel',
    id: idSiteDetailLoginAccountPanel,
    bodyStyle: 'padding: 50px',
	title: 'Login Account Info',
	autoScroll: true,
	border: false,
	layout: 'vbox',
	defaults:{
		width:800,
		labelWidth:200,
		labelAlign:'right'
	},
	items: formToolGlobal.buildFormItemsIdByName(idSiteDetailBasicInfoPanel,
	[{
		xtype: 'checkboxfield',
		fieldLabel: 'If Need Login',
		name: 'needLogin'
	}, {
		xtype: 'textarea',
		fieldLabel: 'Check If Need Login URL',
		name: 'loginCheckReturnUrl'
	}, {
		xtype: 'textarea',
		fieldLabel: 'If Need Login Page Contained Charactor',
		name: 'loginCheckReturnData'
	},{
		xtype: 'textarea',			
		fieldLabel: 'If Need Login Page Contained Charactor\'s Element\'s XPath',
		name:'loginCheckReturnDataXPath'
	},{
		xtype: 'sitedetailloginaccountlistpanel',//use xtype, not new, because new need to destroy, but xtype, framwork will destroy it automatically.
		id: idSiteDetailLoginAccountListPanel
	}]),
	initLoginAccountFieldValue: function(dataObj)
	{
		if(dataObj!=null)
		{
			Ext.getCmp(idSiteDetailBasicInfoPanel + "needLogin").setValue(dataObj.needLogin);
			Ext.getCmp(idSiteDetailBasicInfoPanel + "loginCheckReturnUrl").setValue(dataObj.loginCheckReturnUrl);
			Ext.getCmp(idSiteDetailBasicInfoPanel + "loginCheckReturnData").setValue(dataObj.loginCheckReturnData);
			Ext.getCmp(idSiteDetailBasicInfoPanel + "loginCheckReturnDataXPath").setValue(dataObj.loginCheckReturnDataXPath);
			if(dataObj.siteLoginAccountDtoList)
			{
				Ext.getCmp(idSiteDetailLoginAccountListPanel).getStore().loadData(dataObj.siteLoginAccountDtoList);
			}else
			{
				Ext.getCmp(idSiteDetailLoginAccountListPanel).getStore().removeAll();
			}
		}else
		{
			Ext.getCmp(idSiteDetailLoginAccountListPanel).getStore().removeAll();
		}
	},
	verifyAndSaveLoginAccountFieldValue: function()
	{
		var params = Ext.getCmp(idSiteDetailBasicInfoPanel).getSaveBasicInfoParams();
		var optionObjArr = new Array;
		formToolGlobal.makeAjaxCall(siteSaveUrl, params, this, optionObjArr);
	},
	formSuccessFN: function(form, action, optionObjArr)
	{
		var json = formToolGlobal.getFormResponseJson(action);  
		if(!json.success)
		{
			Ext.Msg.alert('Add Web Site Login Account Failed', json.message);	    		
		}else{
			Ext.getCmp(idSitePanel).getStore().load();
			//this.up('window').close();
		}
	},
	formFailFN: function(form, action, optionObjArr)
	{
		var json = formToolGlobal.getFormResponseJson(action); 
		Ext.Msg.alert('Add Web Site Login Account Failed', json.message);
	}
});