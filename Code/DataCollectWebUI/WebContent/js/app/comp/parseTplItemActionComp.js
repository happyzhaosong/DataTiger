
//list one parse template item action in grid panel
Ext.define('DC.comp.ParseTplItemActionPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.ParseTplItemActionPanel',
    title: 'List Parse Template Item Action',  
    region: 'center',
    split: false,
    store: new DC.store.ParseTplItemAction(),
    selModel: Ext.create('Ext.selection.CheckboxModel',{}),
    columns: [
  	    { xtype: 'rownumberer', width: 50, sortable: false},
        { text: 'Search Element By Type', dataIndex: 'byEleType', flex: 1 , sortable: false, hideable: false, renderer: rendererToolGlobal.renderUrlLinkStyle},
        { text: 'Search Element By Value', dataIndex: 'byEleVal', flex: 1 , sortable: false, hideable: false},
        { text: 'Action Take On The Element', dataIndex: 'byEleAction', flex: 1 , sortable: false, hideable: false}                
    ],
    buttons: formToolGlobal.createGridPanelAddEditDeleteButton("Parse Template Item Action"),
    listeners:{
    	cellClick:{
    		fn: function(obj,td,cIdx,record,tr,rIdx)
    		{
    			/*
    			if(cIdx==2)
    			{
    				Ext.getCmp(idParseTplIdHidden).setValue(record.raw.id);
    				this.showAddEditWindow("Edit Parse Template", record);
    			}else if(cIdx!=0)
    			{
    				if(!stringToolGlobal.isEmpty(td.innerText))
    				{
    					Ext.Msg.alert('Cell Value', td.innerText).setAutoScroll(true);
    				}
    			}
    			*/
    		}
    	}
    },
    showAddEditWindow: function(windowTitle, recordObj)
    {    
    	var formItems = formToolGlobal.buildFormItemsIdByName(this.getId(),
    			[{
    		    	xtype: 'hiddenfield',	        
    		        value: -1,
    		        name: 'id'
    		    },{
    		    	xtype: 'hiddenfield',	        
    		        value: Ext.getCmp(idParseTplItemIdHidden).getValue(),
    		        name: 'parseItemId'
    		    },{
    				xtype: 'WebDriverSearchByComboBox',			
    				fieldLabel: 'Search Element By Type',
    				name:'byEleType',
    				allowBlank: false
    			},{
    		        grow : true,
    		        fieldLabel: 'Search Element By Value',
    		        name: 'byEleVal',
    		        allowBlank: false,
    				emptyText: emptyTextSearchByValue
    		    },{
    				xtype: 'JavaScriptAction',			
    				fieldLabel: 'Action Take On The Element',
    				name:'byEleAction',
    		    	allowBlank: false
    			}]);
    	
    	var formBtns = formToolGlobal.createAddEditFormWindowSaveCloseButton(windowTitle, this.getId(), jsonRootParseTplItemActionList, null, null);
    		
    	var window = formToolGlobal.createFormWithWindow(parseTplItemActionSaveUrl, formItems, "textarea", formBtns, windowTitle);
    	window.height = 200;
    	window.width = 600;
    	window.closable = true;
    	window.autoScroll = false;
    	
		var dataObj = utilToolGlobal.getRecordDataObj(recordObj);
		if(dataObj!=null)
		{
			Ext.getCmp(this.getId()+"id").setValue(dataObj.id);
			
			Ext.getCmp(this.getId()+"byEleType").getStore().load();
			Ext.getCmp(this.getId()+"byEleType").select(dataObj.byEleType);
			
			Ext.getCmp(this.getId()+"byEleVal").setValue(dataObj.byEleVal);
			
			Ext.getCmp(this.getId()+"byEleAction").getStore().load();
			Ext.getCmp(this.getId()+"byEleAction").select(dataObj.byEleAction);
		}else
		{
			Ext.getCmp(this.getId()+"id").setValue(-1);
		}

   		window.show();
    },
    deleteSelectedRecords: function(selectedRecords)
    {
    	var submitParams = utilToolGlobal.addSubmitParamsWithDelIds(null,utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));
    	formToolGlobal.submitAndReloadRecords(parseTplItemActionDeleteUrl, submitParams, this.getId(), "Delete Parse Template Item Action", null, null);
    }	  
});