var idParseTplCatPanelStore = new DC.store.ParseTplCategory();

Ext.define('DC.comp.ParseTplCatPanel', {
	    extend: 'Ext.grid.Panel',
	    alias: 'widget.parsetplcatpanel',
	    id: idParseTplCatPanel,
	    title: 'List Parse Template Category',  
	    region: 'center',
	    split: false,
	    store: idParseTplCatPanelStore,
	    selModel: Ext.create('Ext.selection.CheckboxModel',{}),
	    columns: [
	  	    { xtype: 'rownumberer', width: 50, sortable: false},
	        { text: 'Name', dataIndex: 'name', flex: 1 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderUrlLinkStyle},        
	        { text: 'Description', dataIndex: 'desc', flex: 2 , sortable: true, hideable: true}
	    ],
	    buttons: formToolGlobal.createGridPanelAddEditDeleteButton("Parse Template Category"),
	    listeners:{
	    	cellClick: {
	    		fn: function(obj,td,cIdx,record,tr,rIdx)
	    		{
	    			if(cIdx==2)
	    			{	    				
	    				Ext.getCmp(idParseTplCatIdHidden).setValue(record.raw.id);
	    				var proxyUrl = parseTplListUrl + seperatorAnd + utilToolGlobal.getParameterByProps(record.raw);
	    				formToolGlobal.resetStoreProxyParams(Ext.getCmp(idParseTplPanel).getStore(), proxyUrl, jsonRootParseTplList);
	    				Ext.getCmp(idParseTplPanel).getStore().load();    				
	    				Ext.getCmp(idParseTplPanel).setTitle(this.title + seperatorNavTitle + record.raw.name);
	    				//Ext.getCmp(idParseTplCatPanel).hide();
	    				this.hide();
	    				Ext.getCmp(idParseTplPanel).show();	    				
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
	    	var formItems = [{
    	    	xtype: 'hiddenfield',
    	        name: 'id',
    	        value: '-1'
    	    },{
    	        fieldLabel: 'Name',
    	        name: 'name',
    	        maxLength: 100,
    	        allowBlank: false
    	    },{
    	    	xtype : 'textareafield',
    	    	height: 100,
    	        grow : true,
    	        name : 'desc',
    	        fieldLabel: 'Description'
    	    }];
	    	
	    	formToolGlobal.buildFormItemsIdByName(idParseTplCatPanel, formItems);
	    	
	    	var formBtns = formToolGlobal.createAddEditFormWindowSaveCloseButton(windowTitle, this.getId(), jsonRootParseTplCatList, null, null);
	    	
	    	var window = formToolGlobal.createFormWithWindow(parseTplCatSaveUrl, formItems, 'textfield', formBtns, windowTitle);
	    	
	    	var dataObj = utilToolGlobal.getRecordDataObj(recordObj);
	    	if(dataObj!=null)
	    	{
		    	Ext.getCmp(idParseTplCatPanel + "id").setValue(dataObj.id);
		    	Ext.getCmp(idParseTplCatPanel + "name").setValue(dataObj.name);
		    	Ext.getCmp(idParseTplCatPanel + "desc").setValue(dataObj.desc);
	    	}
	    	window.show();
	    },
	    deleteSelectedRecords: function(selectedRecords)
	    {
	    	var submitParams = utilToolGlobal.addSubmitParamsWithDelIds(null,utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));	    	
	    	formToolGlobal.submitAndReloadRecords(parseTplCatDeleteUrl, submitParams, this.getId(), "Delete Parse Template Category", null, null);
	    },
	    
	    bbar: Ext.create('Ext.PagingToolbar', {
	        pageSize: pageSize,
	        store: idParseTplCatPanelStore,
	        displayInfo: true,
	        plugins: Ext.create('Ext.ux.ProgressBarPager', {})
	    })	    
});


