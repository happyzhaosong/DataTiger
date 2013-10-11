var idParseTplPanelStore = new DC.store.ParseTplAll();

//list all parse template in grid panel
Ext.define('DC.comp.ParseTplPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.parsetplpanel',
    id: idParseTplPanel,
    title: 'List Parse Template',  
    region: 'center',
    split: false,
    store: idParseTplPanelStore,
    selModel: Ext.create('Ext.selection.CheckboxModel',{}),
    columns: [
  	    { xtype: 'rownumberer', width: 50, sortable: false},
        { text: 'Name', dataIndex: 'name', flex: 1 , sortable: false, hideable: false, renderer: rendererToolGlobal.renderUrlLinkStyle},
        { text: 'Save To Table', dataIndex: 'saveToTable', flex: 1 , sortable: false, hideable: false},
        { text: 'Description', dataIndex: 'desc', flex: 2 , sortable: false, hideable: false}                
    ],
    buttons: formToolGlobal.createGridPanelAddEditDeleteButton("Parse Template"),
    listeners:{
    	cellClick:{
    		fn: function(obj,td,cIdx,record,tr,rIdx)
    		{
    			if(cIdx==2)
    			{
    				Ext.getCmp(idParseTplIdHidden).setValue(record.raw.id);
    				this.showAddEditWindow("Edit Parse Template : " + record.raw.name, record);
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
    	var formItems = formToolGlobal.buildFormItemsIdByName(idParseTplDetailPanel,
    			[{
    		    	xtype: 'hiddenfield',	        
    		        value: Ext.getCmp(idParseTplIdHidden).value,
    		        name: 'id'
    		    },{
    		    	xtype: 'hiddenfield',	        
    		        value: Ext.getCmp(idParseTplCatIdHidden).value,
    		        name: 'categoryId'
    		    },{
    				xtype: 'textfield',
    				fieldLabel: 'Parse Template Name',
    				name: 'name',
    		    	allowBlank: false
    			}, {
    				xtype: 'textarea',
    				fieldLabel: 'Parse Template Description',
    				name: 'desc'
    			},{
    				xtype: 'TableListAllComboBox',			
    				fieldLabel: 'Save To Table',
    				name:'saveToTable',
    		    	allowBlank: false
    			},{
    				xtype: 'ParseTplItemListPanel',//use xtype, not new, because new need to destroy, but xtype, framwork will destroy it automatically.
    				name: 'itemListPanel',
    				autoScroll: false,
    				width:900
    			}]);
    	
    	var formBtns = formToolGlobal.createAddEditFormWindowSaveCloseButton(windowTitle, this.getId(), jsonRootParseTplList, null, null);
    		
    	var window = formToolGlobal.createFormWithWindow(parseTplSaveUrl, formItems, "textarea", formBtns, windowTitle);
    	window.height = 600;
    	window.width = 1000;
    	window.closable = true;
    	
    	var dataObj = utilToolGlobal.getRecordDataObj(recordObj);
		if(dataObj!=null)
		{
			Ext.getCmp(idParseTplCatIdHidden).setValue(dataObj.categoryId);
			Ext.getCmp(idParseTplIdHidden).setValue(dataObj.id);
			Ext.getCmp(idParseTplDetailPanel+"name").setValue(dataObj.name);
			Ext.getCmp(idParseTplDetailPanel+"desc").setValue(dataObj.desc);
			Ext.getCmp(idParseTplDetailPanel+"saveToTable").getStore().load();
			Ext.getCmp(idParseTplDetailPanel+"saveToTable").select(dataObj.saveToTable);
			if(dataObj.parseTplItemList)
			{
				Ext.getCmp(window.down("grid").getId()).getStore().loadData(dataObj.parseTplItemList);				
			}else
			{
				Ext.getCmp(window.down("grid").getId()).getStore().removeAll();
			}
		}else
		{
			Ext.getCmp(idParseTplIdHidden).setValue("-1");
			Ext.getCmp(window.down("grid").getId()).getStore().removeAll();
		}

   		window.show();	
    },
    deleteSelectedRecords: function(selectedRecords)
    {
    	var submitParams = utilToolGlobal.addSubmitParamsWithDelIds(null,utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));
    	formToolGlobal.submitAndReloadRecords(parseTplDeleteUrl, submitParams, this.getId(), "Delete Parse Template", null, null);
    },
    copySelectedRecords: function(selectedRecords)
    {
    	var submitParams = utilToolGlobal.addSubmitParamsWithIds(null,utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));
    	var reloadPanelListUrl = utilToolGlobal.buildReloadPanelListUrl(Ext.getCmp(idParseTplCatIdHidden).value,  this.getId());
    	formToolGlobal.submitAndReloadRecords(parseTplCopyUrl, submitParams, this.getId(), "Copy Parse Template", reloadPanelListUrl, null);
    },
    bbar: Ext.create('Ext.PagingToolbar', {
        pageSize: pageSize,
        store: idParseTplPanelStore,
        displayInfo: true,
        plugins: Ext.create('Ext.ux.ProgressBarPager', {})
    })	  
});