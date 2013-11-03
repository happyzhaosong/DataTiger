var idDataTableDataListPanelStore = new DC.store.DataTableData()
//Data table list panel defination
Ext.define('DC.comp.DataTableDataListPanel', {
	    extend: 'Ext.grid.Panel',
	    alias: 'widget.DataTableDataListPanel',
	    id: idDataTableDataListPanel,
	    title: 'List Data Table Data',
	    stripeRows: true,
	    frame:true,
	    layout: 'fit',
	    region: 'center',
	    split: false,
	    autoScroll: true,
	    store: idDataTableDataListPanelStore,
	    selModel: Ext.create('Ext.selection.CheckboxModel',{}),
	    columns: [
	  	    { xtype: 'rownumberer', width: 50, sortable: false},	              
	        { text: 'Table Id', dataIndex: 'ID', flex: 1 , sortable: true, hideable: false, renderer: rendererToolGlobal.renderUrlLinkStyle},
	        { text: 'Download Task Id', dataIndex: dataColumnDownloadTaskId, flex: 1 , sortable: true, hideable: false, renderer: rendererToolGlobal.renderUrlLinkStyle},        
	        { text: 'Download Task Page Url', dataIndex: dataColumnDownloadTaskPageUrl, flex: 3 , sortable: true, hideable: false, renderer: rendererToolGlobal.renderUrlLinkStyle},        
	        { text: 'Download Task Level', dataIndex: dataColumnDownloadTaskLevel, flex: 1 , sortable: true, hideable: false},       
	        { text: 'Download Task Data Parse Time', dataIndex: dataColumnDownloadTaskDataParseTime, flex: 2 , sortable: true, hideable: false},        
	        { text: 'Download Task Useless Content Page', dataIndex: dataColumnDownloadTaskUselessContentPage, flex: 1 , sortable: true, hideable: false, renderer: rendererToolGlobal.renderBoolValue}        
	    ],
	    buttons: formToolGlobal.createGridPanelAddEditDeleteButton(" Parsed Out Data Table Data "),	
	    dockedItems: [{
	        xtype: 'toolbar',
	        dock: 'top',
	        items: formToolGlobal.createDataTableDataListToolBar()
	    }],
	    listeners:{
	    	cellClick: {
	    		fn: function(obj,td,cIdx,record,tr,rIdx)
	    		{
       				var dataObj = utilToolGlobal.getRecordDataObj(record);

	    			if(cIdx==2)
	    			{
	    				var paramObjArr = utilToolGlobal.getParameterObjArrayByProps(dataObj);    				 
	    				Ext.getCmp(idDataTableDataDetailPanel).getStore().loadData(paramObjArr);
	    				
	    				formToolGlobal.createSearchPanelItem(Ext.getCmp(idDataTableDataSearchPanel), Ext.getCmp(idDataTableDataListPanel), dataObj);
	    			}else if(cIdx==4)
	    			{
	     				if(stringToolGlobal.isEmpty(dataObj.DOWNLOAD_TASK_PAGE_URL))
	    				{
	    					Ext.Msg.alert('Alert', "Download task page url can not be empty.");	
	    				}else
	    				{
	    					window.open(dataObj.DOWNLOAD_TASK_PAGE_URL,'newWindow','height=800,width=1000,top=0,left=0,toolbar=yes,menubar=yes,scrollbars=yes, resizable=yes,location=yes, status=yes'); 
	    				} 
	    			}else
	    			{
	    				if(cIdx>0 && !stringToolGlobal.isEmpty(td.innerText))
	    				{
	    					Ext.Msg.alert('Cell Value', td.innerText).setAutoScroll(true);
	    				}
	    			}
	    		}
	    	}
	    },
	    showAddEditWindow: function(windowTitle, recordObj)
	    {
	    	Ext.Msg.alert('Alert', "Can not add, edit parsed out data table data.");
	    },
	    deleteSelectedRecords: function(selectedRecords)
	    {
	    	Ext.Msg.alert('Alert', "Can not add, edit or delete table from here, please ask db administrator to do that.");
	    },
	    deleteAllRecords: function(selectedRecords)
	    {
	    	Ext.Msg.alert('Alert', "You can delete the data which has not been updated for the delta time you set since last update time, implement???");
	    },
	    
	    bbar: Ext.create('Ext.PagingToolbar', {
	        pageSize: pageSize,
	        store: idDataTableDataListPanelStore,
	        displayInfo: true,
	        plugins: Ext.create('Ext.ux.ProgressBarPager', {})
	    }),
	    
	    initPanel: function(dataObj)
	    {
	    	dataTableDataListUrl = dataTableDataListUrl + seperatorAnd + dataDBTableName + seperatorEqual + dataObj.tableName;
	    	formToolGlobal.resetStoreProxyParams(this.getStore(), dataTableDataListUrl, null);
	    	this.getStore().load();	    	
	    }
});


var idDataTableDataDetailPanelStore = new DC.store.DataTableDataDetail();
Ext.define('DC.comp.DataTableDataDetailPanel', {
    extend: 'Ext.grid.Panel',
    id:idDataTableDataDetailPanel,
    alias: 'widget.DataTableDataDetailPanel',
    title: 'Show Selected Table Data Detail Information',
    //split: true,
    floatable: true,
    store: idDataTableDataDetailPanelStore,
    selModel: Ext.create('Ext.selection.CheckboxModel',{}),
    columns: [
  	    { xtype: 'rownumberer', width: 50, sortable: false},	              
        { text: 'Column Name', dataIndex: 'cName', flex: 1 , sortable: true, hideable: false},
        { text: 'Column Value', dataIndex: 'cValue', flex: 2 , sortable: true, hideable: false}
    ],
    listeners:{
    	cellClick: {
    		fn: function(obj,td,cIdx,record,tr,rIdx)
    		{
    			if(cIdx>0 && td.innerText.trim()!='')
    			{
    				Ext.Msg.alert('Cell Value', td.innerText).setAutoScroll(true);
    			}
    		}
    	}
    }
});


Ext.define('DC.comp.DataTableDataPanel', {
    extend: 'Ext.panel.Panel',
	id: idDataTableDataPanel,
    alias: 'widget.DataTableDataPanel',
    layout: {
        type: 'border'
    },
    items: [{
		xtype:'DataTableDataListPanel',
		id:idDataTableDataListPanel,
		split: true,
		floatable: false,
		collapsible: true,
		region: 'north',
		height: '60%'
	},{
		xtype:'DataTableDataDetailPanel',			
		id:idDataTableDataDetailPanel,
		split: false,
		floatable: false,
		collapsible: false,
		region: 'west',
		hidden: false,
		width: '65%',
	},{
		xtype:'panel',
		id:idDataTableDataSearchPanel,
		split: false,
		floatable: false,
		collapsible: false,
		region: 'east',
		autoScroll: true,
		hidden: false,
		width: '35%',
		layout: {
		    type: 'vbox',
		    align: 'center',
		    padding: '30 30 30 30'
		}
	}],
	
	initSubPanel: function(dataObj, parentTitle)
	{
		this.setTitle(parentTitle + seperatorNavTitle + dataObj.tableName + " Data");	
		Ext.getCmp(idDataTableDataListPanel).initPanel(dataObj);
	}
});