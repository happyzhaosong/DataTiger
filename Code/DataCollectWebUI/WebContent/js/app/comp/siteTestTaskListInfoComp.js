var idSiteTestTaskListInfoPanelStore =  new DC.store.SiteTestTaskListInfo();

Ext.define('DC.comp.SiteTestTaskListInfoPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.SiteTestTaskListInfoPanel',
    id: idSiteTestTaskListInfoPanel,
    title: 'List Test Task That Download This Web Site(Run Time and Run Time Error Message is created by the last thread that run this task.)',  
    region: 'center',
    stripeRows: true,
    frame:true,
	labelAlign:'right',
	autoScroll: false,
	floatable: false,
	collapsible: false,
    split: false,
    store: idSiteTestTaskListInfoPanelStore,
    selModel: Ext.create('Ext.selection.CheckboxModel',{}),
    columns: [
        { xtype: 'rownumberer', width: 50, sortable: false},
        { text: 'Table Id', dataIndex: 'id', flex: 1 , sortable: true, hideable: true},
        { text: 'Task Level', dataIndex: 'taskLevel', flex: 1 , sortable: true, hideable: true},
        { text: 'Parent Page Url', dataIndex: 'parentPageUrl', flex: 15 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderUrlLinkStyle},
        { text: 'Page Url', dataIndex: 'pageUrl', flex: 55 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderUrlLinkStyle},
        //{ text: 'Site Id', dataIndex: 'siteId', flex: 1 , sortable: true, hideable: true},
        //{ text: 'If Content Page', dataIndex: 'ifContentPage', flex: 1 , sortable: true, hideable: true},
        { text: 'In Db Time', dataIndex: 'inDbTime', flex: 3 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderTimeStr},
        { text: 'Task Apply Time', dataIndex: 'applyTime', flex: 3 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderTimeStr},
        //{ text: 'If Site Top Url', dataIndex: 'ifSiteTopUrl', flex: 1 , sortable: true, hideable: true},
        //{ text: 'If Test', dataIndex: 'ifTest', flex: 1 , sortable: true, hideable: true},
        { text: 'Thread Table Id', dataIndex: 'threadTableId', flex: 1 , sortable: true, hideable: true},
        { text: 'Run Time', dataIndex: 'runTime', flex: 3 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderTimeStr},
        { text: 'Task Run Delta Time', dataIndex: 'taskRunDeltaTime', flex: 3 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderTimeInSeconds},
        { text: 'Run Time Duration', dataIndex: 'durationInfo', flex: 5 , sortable: false, hideable: true}, 
        { text: 'ParsedOutUrlCount', dataIndex: 'parsedOutUrlCount', flex: 2 , sortable: true, hideable: true},        
        { text: 'AccessDeniedDate', dataIndex: 'accessDeniedDate', flex: 5 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderTimeStr},        
        { text: 'AccessDeniedThreadSleepTime', dataIndex: 'accessDeniedThreadSleepTime', flex: 2 , sortable: true, hideable: true},
        { text: 'ResetApplyTimeReason', dataIndex: 'resetApplyTimeReason', flex: 3 , sortable: false, hideable: true},
        { text: 'ResetApplyTimeTime', dataIndex: 'resetApplyTimeTime', flex: 1 , sortable: false, hideable: true, renderer: rendererToolGlobal.renderTimeStr},        
        { text: 'Run Time Error Message', dataIndex: 'errorMessage', flex: 5 , sortable: false, hideable: true, renderer: rendererToolGlobal.renderSummaryLink},
        //{ text: 'Useless Content Page', dataIndex: 'uselessContentPage', flex: 1 , sortable: true, hideable: true},

    ],
    buttons: formToolGlobal.createGridPanelAddEditDeleteButton("Task That Download This Web Site"),
    showAddEditWindow: function(windowTitle, recordObj)
    {    	
    	 Ext.Msg.alert('Operation Denied', "Can not add or edit download web site tasks.");      
    },
    
    listeners:{
    	cellClick:{
    		fn: function(obj,td,cIdx,record,tr,rIdx)
    		{
    			var data = utilToolGlobal.getRecordDataObj(record);
    			if(cIdx==4 || cIdx==5)
    			{
    				var url = '';
    				if(cIdx==4)
    				{
    					url = data.parentPageUrl;
    				}else if(cIdx==5)
    				{
    					url = data.pageUrl;
    				}
    				if(stringToolGlobal.isEmpty(url))
    				{
    					Ext.Msg.alert('Alert', "Download task page url can not be empty.");	
    				}else
    				{
    					window.open(url,'newWindow','height=800,width=1000,top=0,left=0,toolbar=yes,menubar=yes,scrollbars=yes, resizable=yes,location=yes, status=yes'); 
    				}    				
    			}else if(cIdx==16)
    			{
    				if(!stringToolGlobal.isEmpty(data.errorMessage))
    				{
    					Ext.Msg.alert('Cell Value', data.errorMessage).setAutoScroll(true);
    				}
    			}else if(cIdx!=0)
    			{
    				if(!stringToolGlobal.isEmpty(td.innerText))
    				{
    					Ext.Msg.alert('Cell Value', td.innerText).setAutoScroll(true);
    				}
    			}
    			
    			formToolGlobal.createSearchPanelItem(Ext.getCmp(idSiteTestTaskListInfoPanel + idSearchPanelSuffix), Ext.getCmp(idSiteTestTaskListInfoPanel), data);
    		}
    	},
    	hide:{
    		fn: function(objThis, eOpts)
    		{
    			Ext.getCmp(idSiteTestTaskListInfoPanel + idSearchPanelSuffix).hide();
    		}
    	},
    	show:{
    		fn: function(objThis, eOpts)
    		{
    			Ext.getCmp(idSiteTestTaskListInfoPanel + idSearchPanelSuffix).show();
    		}
    	}
    },
    
    deleteSelectedRecords: function(selectedRecords)
    {
    	var submitParams = new Object;
    	utilToolGlobal.addSubmitParamsWithDelIds(submitParams, utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));
    	utilToolGlobal.addWebSiteId(submitParams, Ext.getCmp(idSiteIdHidden).getValue());
    	formToolGlobal.submitAndReloadRecords(testTaskDeleteUrl, submitParams, this.getId(), "Delete Web Site Download Task", null, null);   	
    },
    
    deleteAllRecords: function()
    {
    	var submitParams = new Object;
    	utilToolGlobal.addWebSiteId(submitParams, Ext.getCmp(idSiteIdHidden).getValue());
    	formToolGlobal.submitAndReloadRecords(testTaskDeleteAllUrl, submitParams, this.getId(), "Delete All Web Site Download Task", null, null);   	
    },
    
    initTaskListPanel: function(siteId, threadTableId)
    {
    	var store = Ext.getCmp(idSiteTestTaskListInfoPanel).getStore();
    	store.getProxy().setExtraParam(dataWebSiteId,siteId);
    	store.getProxy().setExtraParam(dataTableId,threadTableId);
    	if(!store.isLoading())
    	{
			store.load({
			    scope: this,
			    callback: function(records, operation, success) {
			        // the operation object
			        // contains all of the details of the load operation
			        if(!success)
			        {
			        	if(operation)
			        	{
				        	Ext.Msg.alert('Error', operation.error);
			        	}
			        }
			    }
			});
    	}
    },
    
    bbar: Ext.create('Ext.PagingToolbar', {
        pageSize: pageSize,
        store: idSiteTestTaskListInfoPanelStore,
        displayInfo: true,
        plugins: Ext.create('Ext.ux.ProgressBarPager', {})
    })
});