var idSiteTestThreadListInfoPanelStore =  new DC.store.SiteTestThreadListInfo();

Ext.define('DC.comp.SiteTestThreadListInfoPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.SiteTestThreadListInfoPanel',
    id: idSiteTestThreadListInfoPanel,
    title: 'List Test Thread That Download This Web Site',  
    region: 'center',
	//width:800,
	//height:400,
	labelAlign:'right',
	autoScroll: true,	
    split: false,
    store: idSiteTestThreadListInfoPanelStore,
    selModel: Ext.create('Ext.selection.CheckboxModel',{}),
    columns: [
        { xtype: 'rownumberer', width: 30, sortable: false},
        { text: 'Table Id', dataIndex: 'id', flex: 1 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderUrlLinkStyle},
        { text: 'Thread Id', dataIndex: 'threadId', flex: 1 , sortable: true, hideable: true},
        { text: 'Status', dataIndex: '', flex: 2 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderThreadStatus},
        { text: 'User Id', dataIndex: 'userId', flex: 1 , sortable: true, hideable: true},
        { text: 'Type', dataIndex: 'threadType', flex: 3 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderThreadType},
        { text: 'Start Time', dataIndex: 'startTime', flex: 3 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderTimeStr},
        { text: 'Stop Time', dataIndex: 'stopTime', flex: 3 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderTimeStr},
        { text: 'Stop Reason', dataIndex: 'stopReason', flex: 5 , sortable: true, hideable: true}
    ],
    buttons: formToolGlobal.createGridPanelAddEditDeleteButton("Thread That Download This Web Site"),
    showAddEditWindow: function(windowTitle, recordObj)
    {    	
    	 Ext.Msg.alert('Operation Denied', "Can not add or edit download web site threads.");      
    },
    
    listeners:{
    	cellClick:{
    		fn: function(obj,td,cIdx,record,tr,rIdx)
    		{
    			if(cIdx==2)
    			{
    				var data = utilToolGlobal.getRecordDataObj(record);
    				/*if(data.stopTime!='')
    				{
    					Ext.Msg.alert('Test Thread Status', "This is a stop test thread and has no related task with it.");	
    				}else
    				{*/		//formToolGlobal.createSearchPanelItem(searchPanel, Ext.getCmp(listPanelId), dataObj);

    			    	Ext.getCmp(idSiteTestThreadListInfoPanel).hide();	    	
    			    	Ext.getCmp(idSiteTestTaskListInfoPanel).initTaskListPanel(data.siteId, data.id);
    			    	Ext.getCmp(idSiteTestTaskListInfoPanel).show();
    				//}
    			}else if(cIdx!=0)
    			{
    				if(!stringToolGlobal.isEmpty(td.innerText))
    				{
    					Ext.Msg.alert('Cell Value', td.innerText).setAutoScroll(true);
    				}
    			}
    		}
    	}
    },
    
    deleteSelectedRecords: function(selectedRecords)
    {
    	//alert("Can not delete download web site threads.");
    	if(selectedRecords)
    	{
    		var len = selectedRecords.length;
    		for(var i=0;i<len;i++)
    		{
				var data = utilToolGlobal.getRecordDataObj(selectedRecords[i]);
				if(stringToolGlobal.isEmpty(data.stopTime))
				{
					alert("Can not delete running download web site threads, please stop them first.");
					return;
				}
    		}
    		
	    	var submitParams = utilToolGlobal.addSubmitParamsWithDelIds(null, utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));	    	
	    	formToolGlobal.submitAndReloadRecords(testThreadDeleteUrl, submitParams, this.getId(), "Delete Web Site Download Threads", null, null);
    	}
    },
    
    deleteAllRecords: function()
    {
		alert("Can only delete this user started, this web site, stopped threads.");
    	var submitParams = new Object;
    	utilToolGlobal.addWebSiteId(submitParams, Ext.getCmp(idSiteIdHidden).getValue());
    	formToolGlobal.submitAndReloadRecords(testThreadDeleteAllUrl, submitParams, this.getId(), "Delete All This Web Site This User Download Thread", null, null);   	
    },
    
    initThreadListPanel: function(siteId, threadType)
    {
    	Ext.getCmp(idSiteTestThreadListInfoPanel).getStore().getProxy().setExtraParam(dataWebSiteId,siteId); 
    	Ext.getCmp(idSiteTestThreadListInfoPanel).getStore().getProxy().setExtraParam(dataThreadType,threadType);    	
		Ext.getCmp(idSiteTestThreadListInfoPanel).getStore().load()
    },
    
    bbar: Ext.create('Ext.PagingToolbar', {
        pageSize: pageSize,
        store: idSiteTestThreadListInfoPanelStore,
        displayInfo: true,
        plugins: Ext.create('Ext.ux.ProgressBarPager', {})
    })
});