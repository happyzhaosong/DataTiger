//site page basic information panel
Ext.define('DC.comp.SiteDetailBasicInfoPanel', {
	    extend: 'Ext.panel.Panel',
	    alias: 'widget.sitedetailbasicinfopanel',
	    id: idSiteDetailBasicInfoPanel,
	    bodyStyle: 'padding: 20px',
		title: 'Site Basic Info',
		border: false,
		autoScroll: true,
		defaults:{
			width:800,
			labelWidth:300,
			labelAlign:'right'
		},
		items: formToolGlobal.buildFormItemsIdByName(idSiteDetailBasicInfoPanel,
		[{
			xtype: 'textfield',
			fieldLabel: 'Web Site Name',
			name: 'name',
			allowBlank: false			
		}, {
			xtype: 'textarea',
			fieldLabel: 'Web Site Desc',
			name: 'desc'
		}, {
			xtype: 'textarea',
			fieldLabel: 'Top Page Urls',
			name: 'topUrl',
			emptyText: emptyTextMultipleValue,
			allowBlank: false
		}, {
			xtype: 'checkboxfield',
			fieldLabel: 'Show Image In Browser To Parse Image Url(Some website like jingdong need to show image to parse img url)',
			name: 'showImgInBrowser'
		}, {
			xtype: 'checkboxfield',
			fieldLabel: 'Multi country website(Need put "$Country$" place holder in site top page url for replace). If use multiple country then use country iso name to replace the $Country$ placeholder in top url and create multiple download task when start download and parse.',
			name: 'multiCountry'
		}, {
			xtype: 'textarea',
			fieldLabel: 'Place Holders(Need put "$PlaceHolders$" place holder in site top page url for replace)',
			name: 'placeHolders',
			emptyText: emptyTextMultipleValue
		}, {
			xtype: 'parsetplallcombobox',			
			fieldLabel: 'Content Page Parse Template',
			name:'parseId',
			allowBlank: false
		}, {
			xtype: 'WebDriverBrowserTypeComboBox',			
			fieldLabel: 'Browse Web Page Used Browser',
			name:'browserType',
			allowBlank: false
		}, {
			xtype: 'numbercomboboxdayhour',
			fieldLabel: 'Start Download Time(hour in one day)',
			name:'startDownloadTime',
			allowBlank: false
		}, {
			xtype: 'numbercombobox1000',			
			fieldLabel: 'Download Time Duration(seconds)',
			name:'downloadDurationSeconds',
			allowBlank: false
		}, {
			xtype: 'numbercombobox1000',			
			fieldLabel: 'Not Top Level Page Redownload Duration Time(seconds)',
			name:'downloadTaskRegetDuration',
			allowBlank: false
		},{
			xtype: 'textarea',
	        name : 'needScrollPageUrlCharactor',
	        fieldLabel: 'Need Scroll Page Url Charactor',
	        emptyText: "'All' means all pages need scroll when parsing. This field data should not same with below filed" + emptyTextMultipleValue,
	        allowBlank: true
	    },{
	    	xtype: 'textarea',
	        name : 'notNeedScrollPageUrlCharactor',
	        fieldLabel: 'Not Need Scroll Page Url Charactor',
	        emptyText: "'All' means all pages need not scroll when parsing. This field data should not same with above filed." + emptyTextMultipleValue,
	        allowBlank: true
	    },{
			xtype: 'textarea',
	        name : 'setHighLevelTaskUrlCharactor',
	        fieldLabel: 'Set High Task Level Url Charactor(set task level to 3)',
	        emptyText: "If parsed out url link meet this url charactor, then set it's related task level to 3, this way can run this task early next time." + emptyTextMultipleValue,
	        allowBlank: true
	    },{
			xtype: 'textarea',
	        name : 'setMiddleLevelTaskUrlCharactor',
	        fieldLabel: 'Set Middle Task Level Url Charactor(set task level to 2)',
	        emptyText: "If parsed out url link meet this url charactor, then set it's related task level to 2, this way can run this task early next time." + emptyTextMultipleValue,
	        allowBlank: true
	    }, {
			xtype: 'textarea',
			fieldLabel: 'Access Denied Page Element Xpath',
			name: 'accessDeniedPageElementXpath',
			emptyText: emptyTextMultipleValue
		}, {
			xtype: 'textarea',
			fieldLabel: 'Access Denied Page Element Value',
			name: 'accessDeniedPageElementValue',
			emptyText: emptyTextMultipleValue
		}, {
			xtype: 'textarea',
			fieldLabel: 'Alert Text To Reset Download Task',
			name: 'alertTextToResetDTask',
			emptyText: emptyTextMultipleValue
		}, {
			xtype: 'textfield',
			fieldLabel: 'Web Site Test Status',
			readOnly:true,
			name: 'siteStatus'
		}, {
			xtype: 'textarea',
			fieldLabel: 'Over Write Parsed Out Attributes In Content Page',
			name: 'overWriteAttributes'
		}]),
		
		getSaveBasicInfoParams: function()
		{
			var params = {
					categoryId: Ext.getCmp(idSiteCatIdHidden).getValue(),
					id: Ext.getCmp(idSiteIdHidden).getValue(),
			        name: Ext.getCmp(idSiteDetailBasicInfoPanel + "name").value,			        
			        desc: Ext.getCmp(idSiteDetailBasicInfoPanel + "desc").value,
			        topUrl: Ext.getCmp(idSiteDetailBasicInfoPanel + "topUrl").value,
			        showImgInBrowser: Ext.getCmp(idSiteDetailBasicInfoPanel + "showImgInBrowser").value,
			        multiCountry: Ext.getCmp(idSiteDetailBasicInfoPanel + "multiCountry").value,
			        placeHolders: Ext.getCmp(idSiteDetailBasicInfoPanel + "placeHolders").value,
			        parseId: Ext.getCmp(idSiteDetailBasicInfoPanel + "parseId").value,			        
			        browserType: Ext.getCmp(idSiteDetailBasicInfoPanel + "browserType").value,
			        startDownloadTime: Ext.getCmp(idSiteDetailBasicInfoPanel + "startDownloadTime").value,
			        downloadDurationSeconds: Ext.getCmp(idSiteDetailBasicInfoPanel + "downloadDurationSeconds").value,
			        downloadTaskRegetDuration: Ext.getCmp(idSiteDetailBasicInfoPanel + "downloadTaskRegetDuration").value,
			        
			        needScrollPageUrlCharactor: Ext.getCmp(idSiteDetailBasicInfoPanel + "needScrollPageUrlCharactor").value,
			        notNeedScrollPageUrlCharactor: Ext.getCmp(idSiteDetailBasicInfoPanel + "notNeedScrollPageUrlCharactor").value,

			        setHighLevelTaskUrlCharactor: Ext.getCmp(idSiteDetailBasicInfoPanel + "setHighLevelTaskUrlCharactor").value,
			        setMiddleLevelTaskUrlCharactor: Ext.getCmp(idSiteDetailBasicInfoPanel + "setMiddleLevelTaskUrlCharactor").value,

			        accessDeniedPageElementXpath: Ext.getCmp(idSiteDetailBasicInfoPanel + "accessDeniedPageElementXpath").value,
			        accessDeniedPageElementValue: Ext.getCmp(idSiteDetailBasicInfoPanel + "accessDeniedPageElementValue").value,
			        
			        alertTextToResetDTask: Ext.getCmp(idSiteDetailBasicInfoPanel + "alertTextToResetDTask").value,
			        			        
			        siteStatus: Ext.getCmp(idSiteDetailBasicInfoPanel + "siteStatus").value,
			        overWriteAttributes: Ext.getCmp(idSiteDetailBasicInfoPanel + "overWriteAttributes").value,
			        
			        needLogin: Ext.getCmp(idSiteDetailBasicInfoPanel + "needLogin").value,
			        loginCheckReturnUrl: Ext.getCmp(idSiteDetailBasicInfoPanel + "loginCheckReturnUrl").value,
			        loginCheckReturnData: Ext.getCmp(idSiteDetailBasicInfoPanel + "loginCheckReturnData").value,
			        loginCheckReturnDataXPath: Ext.getCmp(idSiteDetailBasicInfoPanel + "loginCheckReturnDataXPath").value
			     };
			return params;
		},
		
		verifyAndSaveSiteBasicInfoFieldValue: function(layout)
		{
			var optionObjArr = new Array;
			optionObjArr[0] = layout;
			formToolGlobal.makeAjaxCall(siteSaveUrl, this.getSaveBasicInfoParams(), this, optionObjArr);
		},
		formSuccessFN: function(form, action, optionObjArr)
		{
			var layout = optionObjArr[0];
			var json = formToolGlobal.getFormResponseJson(action); 
			if(!json.success)
			{
				Ext.Msg.alert('Add Web Site Basic Info Failed', json.message);	    		
			}else if (layout.getNext()) {
				Ext.getCmp(idSiteIdHidden).setValue(json.JSON_ROOT_SITE_LIST[0].id);
				Ext.getCmp(idSiteParseWizPreBtn).setDisabled(false);
				Ext.getCmp(idSitePanel).getStore().load();
				layout.next();
			}
		},
		formFailFN: function(form, action, optionObjArr)
		{
			var json = formToolGlobal.getFormResponseJson(action); 
			Ext.Msg.alert('Add Web Site Basic Info Failed', json.message);
		},
		initSiteBasicInfoFieldValue: function(dataObj)
		{
			if(dataObj!=null)
			{
				Ext.getCmp(idSiteDetailBasicInfoPanel + "name").setValue(dataObj.name);
				Ext.getCmp(idSiteDetailBasicInfoPanel + "desc").setValue(dataObj.desc);
				Ext.getCmp(idSiteDetailBasicInfoPanel + "topUrl").setValue(dataObj.topUrl);
				Ext.getCmp(idSiteDetailBasicInfoPanel + "showImgInBrowser").setValue(dataObj.showImgInBrowser);
				Ext.getCmp(idSiteDetailBasicInfoPanel + "multiCountry").setValue(dataObj.multiCountry);
				Ext.getCmp(idSiteDetailBasicInfoPanel + "placeHolders").setValue(dataObj.placeHolders);
				
				Ext.getCmp(idSiteDetailBasicInfoPanel + "parseId").getStore().load();
				Ext.getCmp(idSiteDetailBasicInfoPanel + "parseId").select(dataObj.parseId);
				
				Ext.getCmp(idSiteDetailBasicInfoPanel + "browserType").select(dataObj.browserType);
				
				Ext.getCmp(idSiteDetailBasicInfoPanel + "startDownloadTime").select(dataObj.startDownloadTime);		
				Ext.getCmp(idSiteDetailBasicInfoPanel + "downloadDurationSeconds").select(dataObj.downloadDurationSeconds);		
				Ext.getCmp(idSiteDetailBasicInfoPanel + "downloadTaskRegetDuration").select(dataObj.downloadTaskRegetDuration);

				Ext.getCmp(idSiteDetailBasicInfoPanel + "needScrollPageUrlCharactor").setValue(dataObj.needScrollPageUrlCharactor);
				Ext.getCmp(idSiteDetailBasicInfoPanel + "notNeedScrollPageUrlCharactor").setValue(dataObj.notNeedScrollPageUrlCharactor);

				Ext.getCmp(idSiteDetailBasicInfoPanel + "setHighLevelTaskUrlCharactor").setValue(dataObj.setHighLevelTaskUrlCharactor);
				Ext.getCmp(idSiteDetailBasicInfoPanel + "setMiddleLevelTaskUrlCharactor").setValue(dataObj.setMiddleLevelTaskUrlCharactor);
				
				Ext.getCmp(idSiteDetailBasicInfoPanel + "accessDeniedPageElementXpath").setValue(dataObj.accessDeniedPageElementXpath);
				Ext.getCmp(idSiteDetailBasicInfoPanel + "accessDeniedPageElementValue").setValue(dataObj.accessDeniedPageElementValue);

				Ext.getCmp(idSiteDetailBasicInfoPanel + "alertTextToResetDTask").setValue(dataObj.alertTextToResetDTask);				
				
				Ext.getCmp(idSiteDetailBasicInfoPanel + "siteStatus").setValue(dataObj.siteStatus);
				Ext.getCmp(idSiteDetailBasicInfoPanel + "overWriteAttributes").setValue(dataObj.overWriteAttributes);
			}
		}
});
