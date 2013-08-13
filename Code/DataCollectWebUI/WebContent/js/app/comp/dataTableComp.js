var idDataTableListPanelStore = new DC.store.DBTable()

//Data table list panel defination
Ext.define('DC.comp.DataTableListPanel', {
	    extend: 'Ext.grid.Panel',
	    alias: 'widget.DataTableListPanel',
	    id: idDataTableListPanel,
	    title: 'List Parsed Out Data Table',  
	    region: 'center',
	    split: false,
	    store: idDataTableListPanelStore,
	    selModel: Ext.create('Ext.selection.CheckboxModel',{}),
	    columns: [
	  	    { xtype: 'rownumberer', width: 50, sortable: false},	              
	        { text: 'Name', dataIndex: 'tableName', flex: 1 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderUrlLinkStyle},        
	        { text: 'Description', dataIndex: 'tableDesc', flex: 1 , sortable: true, hideable: true},
	        { text: 'List Data Action', datqa: 'List Table Data', flex: 1 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderUrlLinkStyle}
	    ],
	    buttons: formToolGlobal.createGridPanelAddEditDeleteButton(" Parsed Out Data Table"),
	    listeners:{
	    	cellClick: {
	    		fn: function(obj,td,cIdx,record,tr,rIdx)
	    		{
    				var dataObj = utilToolGlobal.getRecordDataObj(record);
	    			if(cIdx==2)
	    			{
	    				this.hide();
	    				Ext.getCmp(idDataTableColumnListPanel).initSubPanel(dataObj, this.title);
	    				Ext.getCmp(idDataTableColumnListPanel).show();	    				
	    				Ext.getCmp(idDataTableDataPanel).hide();	    					    		    	
	    			}else if(cIdx==4)
	    			{
	    				if(dataObj.tableName)
	    				{		    		    	
		    		    	this.hide();
		    		    	Ext.getCmp(idDataTableColumnListPanel).hide();    		    	 
		    		    	Ext.getCmp(idDataTableDataPanel).initSubPanel(dataObj, this.title);
		    		    	Ext.getCmp(idDataTableNameHidden).setValue(dataObj.tableName);
		    				Ext.getCmp(idDataTableDataPanel).show();		    				
	    				}	    				
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
	    showAddEditWindow: function(windowTitle, recordObj)
	    {
	    	Ext.Msg.alert('Alert', "Can not add, edit or delete table from here, please ask db administrator to do that.");
	    },
	    deleteSelectedRecords: function(selectedRecords)
	    {
	    	Ext.Msg.alert('Alert', "Can not add, edit or delete table from here, please ask db administrator to do that.");
	    },
	    
	    bbar: Ext.create('Ext.PagingToolbar', {
	        pageSize: pageSize,
	        store: idDataTableListPanelStore,
	        displayInfo: true,
	        plugins: Ext.create('Ext.ux.ProgressBarPager', {})
	    })	    
});


var idDataTableColumnListPanelStore = new DC.store.DBTableColumn();
//Data table column list panel
Ext.define('DC.comp.DataTableColumnListPanel', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.DataTableColumnListPanel',
	id: idDataTableColumnListPanel,
    title: 'Table Column List',
    stripeRows: true,
    frame:true,
    layout: 'fit',
    //bodyStyle: 'padding: 20px',
    region: 'center',
    split: false,
    autoScroll: true,
    store: idDataTableColumnListPanelStore,
    selModel: Ext.create('Ext.selection.CheckboxModel',{}),    
    columns: [ 
        { xtype: 'rownumberer', width: 50, sortable: false},
        { text: 'Name', dataIndex: 'columnName', flex: 2 , sortable: true, hideable: false},
        { text: 'Type', dataIndex: 'columnType', flex: 1 , sortable: true, hideable: false},
        { text: 'Max Length', dataIndex: 'columnMaxLen', flex: 1 , sortable: true, hideable: false},
        { text: 'Description', dataIndex: 'columnDesc', flex: 5 , sortable: true, hideable: true}
    ],
    buttons: formToolGlobal.createGridPanelAddEditDeleteButton("Table Column"),
    listeners:{
    	cellClick: {
    		fn: function(obj,td,cIdx,record,tr,rIdx)
    		{
				if(!stringToolGlobal.isEmpty(td.innerText))
    			{
    				Ext.Msg.alert('Cell Value', td.innerText).setAutoScroll(true);    			
    			}
    		}
    	}
    },
    showAddEditWindow: function(windowTitle, recordObj)
    {
    	Ext.Msg.alert('Alert', "Can not add, edit or delete table column from here, please ask db administrator to do that.");
    },
    deleteSelectedRecords: function(selectedRecords)
    {
    	Ext.Msg.alert('Alert', "Can not add, edit or delete table column from here, please ask db administrator to do that.");
    },
    
    bbar: Ext.create('Ext.PagingToolbar', {
        pageSize: pageSize,
        store: idDataTableColumnListPanelStore,
        displayInfo: true,
        plugins: Ext.create('Ext.ux.ProgressBarPager', {})
    }),
	
	initSubPanel: function(dataObj, parentTitle)
	{
		Ext.getCmp(idDataTableColumnListPanel).getStore().getProxy().setExtraParam(dataDBTableName, dataObj.tableName);
		Ext.getCmp(idDataTableColumnListPanel).getStore().load();
		Ext.getCmp(idDataTableColumnListPanel).setTitle(parentTitle + seperatorNavTitle + dataObj.tableName + " Columns");
	}
});


//list all db tables in combox
Ext.define('DC.comp.TableListAllComboBox', {
     extend: 'Ext.form.field.ComboBox',     
     displayField: 'tableName',
     valueField: 'tableName',
     store: new DC.store.DBTable(),
     triggerAction : 'all',
     queryMode: 'remote',
     forceSelection: true,
     editable:false,
     emptyText:'Select Save To Table...',  
     alias:'widget.TableListAllComboBox',
     listConfig: {
         getInnerTpl: function() {
             return '<div title="{tableDesc}">{tableName}</div>';
         }
     }
});

//list one db table's columns in combox
Ext.define('DC.comp.TableColumnListComboBox', {
     extend: 'Ext.form.field.ComboBox',     
     displayField: 'columnName',
     valueField: 'columnName',
     store: new DC.store.DBTableColumn(),
     triggerAction : 'all',
     queryMode: 'remote',
     forceSelection: true,
     editable:false,
     emptyText:'Select Save To Column...',  
     alias:'widget.TableColumnListComboBox',
     listConfig: {
         getInnerTpl: function() {
        	 var ret = '<div title="{columnDesc}">{columnName}     {columnType}     {columnDesc}</div>';        	
             return ret;
         }
     },
     listeners:{
    	 select: {
    		 fn:function(combo, records, eOpts)
    		 {
    			 if(records)
    			 {
    				 var len = records.length;
    				 for(var i=0;i<len;i++)
    				 {
    					 var recordObj = records[i];
    				     var dataObj = utilToolGlobal.getRecordDataObj(recordObj);
    				     if(dataObj && (dataId==dataObj.columnName || dataColumnDownloadTaskPageUrl==dataObj.columnName || dataColumnDownloadTaskId==dataObj.columnName || dataColumnDownloadTaskLevel==dataObj.columnName || dataColumnDownloadTaskDataParseTime==dataObj.columnName || dataColumnDownloadTaskDataParseTimeNumber==dataObj.columnName || dataColumnDownloadTaskUselessContentPage==dataObj.columnName))
    				     {
    				    	 alert("This column is default column for every data table, do not need to parse, it is automate parse and save.");
    				    	 combo.select(combo.getStore().data.items[0]);
    				     }    				    	 
    				 }
    			 }    			
    		 }
    	 }
     }
});
