var idSiteTestMQMessageListInfoPanelStore = new DC.store.SiteTestMQMessageListInfo();

Ext.define('DC.comp.SiteTestMQMessageListInfoPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.SiteTestMQMessageListInfoPanel',
    id: idSiteTestMQMessageListInfoPanel,
    title: 'List All MQ Message',  
    region: 'center',
	labelAlign:'right',
	autoScroll: true,	
    split: false,
    store: idSiteTestMQMessageListInfoPanelStore,
    selModel: Ext.create('Ext.selection.CheckboxModel',{}),
    columns: [
        { xtype: 'rownumberer', width: 30, sortable: false},
        { text: 'Table Id', dataIndex: 'id', flex: 1 , sortable: true, hideable: true},
        { text: 'Download Thread Type', dataIndex: 'downloadThreadType', flex: 3 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderThreadType},
        { text: 'Site Id', dataIndex: 'siteId', flex: 1 , sortable: true, hideable: true},
        { text: 'User Id', dataIndex: 'userId', flex: 1 , sortable: true, hideable: true},
        { text: 'Action', dataIndex: 'action', flex: 1 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderThreadAction},
        { text: 'Thread Table Ids', dataIndex: 'threadTableIds', flex: 1 , sortable: true, hideable: true},
        { text: 'Thread Ids', dataIndex: 'threadIds', flex: 1 , sortable: true, hideable: true},
        { text: 'Send Time', dataIndex: 'sendTime', flex: 3 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderTimeStr},
        { text: 'Receive Time', dataIndex: 'receiveTime', flex: 3 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderTimeStr},
        { text: 'Finish Time', dataIndex: 'finishTime', flex: 3 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderTimeStr},
        { text: 'Fail Reason', dataIndex: 'failReason', flex: 3 , sortable: true, hideable: true}
    ],
    buttons: formToolGlobal.createGridPanelAddEditDeleteButton(" MQ Message "),
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
    	 Ext.Msg.alert('Operation Denied', "Can not add or edit download web site tasks.");      
    },
    
    listeners:{
    	cellClick:{
    		fn: function(obj,td,cIdx,record,tr,rIdx)
    		{
    			if(cIdx==3)
    			{
    				var data = utilToolGlobal.getRecordDataObj(record);
    				/*
    				if(stringToolGlobal.isEmpty(data.pageUrl))
    				{
    					Ext.Msg.alert('Alert', "Download task page url can not be empty.");	
    				}else
    				{
    					window.open(data.pageUrl,'newwindow','height=800,width=1000,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no'); 
    				} 
    				*/   				
    			}
    		}
    	}
    },
    
    deleteSelectedRecords: function(selectedRecords)
    {
    	var submitParams = new Object;
    	utilToolGlobal.addSubmitParamsWithDelIds(submitParams, utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));
    	utilToolGlobal.addWebSiteId(submitParams, Ext.getCmp(idSiteIdHidden).getValue());
    	formToolGlobal.submitAndReloadRecords(testTaskDeleteUrl, submitParams, this.getId(), "Web Site Download Task", null, null);   	
    },
    initTaskListPanel: function(siteId, threadTableId)
    {
    	Ext.getCmp(idSiteTestMQMessageListInfoPanel).getStore().getProxy().setExtraParam(dataWebSiteId,siteId);
    	Ext.getCmp(idSiteTestMQMessageListInfoPanel).getStore().getProxy().setExtraParam(dataTableId,threadTableId);
		Ext.getCmp(idSiteTestMQMessageListInfoPanel).getStore().load()
    },
    
    bbar: Ext.create('Ext.PagingToolbar', {
        pageSize: pageSize,
        store: idSiteTestMQMessageListInfoPanelStore,
        displayInfo: true,
        plugins: Ext.create('Ext.ux.ProgressBarPager', {})
    })
});