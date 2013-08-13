var idSiteCatPanelStore = new DC.store.SiteCategory(); 

//site category list panel defination
Ext.define('DC.comp.SiteCatPanel', {
	    extend: 'Ext.grid.Panel',
	    alias: 'widget.sitecatpanel',
	    id: idSiteCatPanel,
	    title: 'List Web Site Category',  
	    region: 'center',
	    split: false,
	    store: idSiteCatPanelStore,
	    selModel: Ext.create('Ext.selection.CheckboxModel',{}),
	    columns: [
	        { xtype: 'rownumberer', width: 50, sortable: false},
	        { text: 'Name', dataIndex: 'name', flex: 1 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderUrlLinkStyle},        
	        { text: 'Description', dataIndex: 'desc', flex: 2 , sortable: true, hideable: true}
	    ],
	    buttons: formToolGlobal.createGridPanelAddEditDeleteButton("Web Site Category"),
	    listeners:{
	    	cellClick: {
	    		fn: function(obj,td,cIdx,record,tr,rIdx)
	    		{
	    			if(cIdx==2)
	    			{
	    				Ext.getCmp(idSiteCatIdHidden).setValue(record.raw.id);
	    				var proxyUrl = siteListUrl + seperatorAnd + utilToolGlobal.getParameterByProps(record.raw);
	    				formToolGlobal.resetStoreProxyParams(Ext.getCmp(idSitePanel).getStore(), proxyUrl, jsonRootSiteList);
	    				Ext.getCmp(idSitePanel).getStore().load();
	    				Ext.getCmp(idSitePanel).setTitle(this.title + seperatorNavTitle + record.raw.name);
	    				this.hide();
	    				Ext.getCmp(idSitePanel).show();
	    			}else if(cIdx!=0)
	    			{
	    				Ext.Msg.alert('Cell Value', td.innerText).setAutoScroll(true);
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
    	        anchor: '100%',
    	        allowBlank: false
    	    },{
    	    	xtype:  'textareafield',
    	    	height: 100,
    	        grow : true,
    	        name : 'desc',
    	        fieldLabel: 'Description',
    	        anchor: '100%'
    	    }];
	    	
	    	formToolGlobal.buildFormItemsIdByName(idSiteCatPanel, formItems);
	    	
	    	var formBtns = formToolGlobal.createAddEditFormWindowSaveCloseButton(windowTitle, this.getId(), jsonRootSiteCatList, null, null); 
	    	var window = formToolGlobal.createFormWithWindow(siteCatSaveUrl, formItems, 'textfield', formBtns, windowTitle);
	    	
	    	var dataObj = utilToolGlobal.getRecordDataObj(recordObj);
	    	
	    	if(dataObj!=null)
	    	{
		    	Ext.getCmp(idSiteCatPanel+"id").setValue(dataObj.id);
		    	Ext.getCmp(idSiteCatPanel+"name").setValue(dataObj.name);
		    	Ext.getCmp(idSiteCatPanel+"desc").setValue(dataObj.desc);
    		}
	    	window.show();
	    },
	    deleteSelectedRecords: function(selectedRecords)
	    {
	    	var submitParams = utilToolGlobal.addSubmitParamsWithDelIds(null, utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));	    	
	    	formToolGlobal.submitAndReloadRecords(siteCatDeleteUrl, submitParams, this.getId(), "Delete Web Site Category", null, null);
	    },
	    
	    bbar: Ext.create('Ext.PagingToolbar', {
	        pageSize: pageSize,
	        store: idSiteCatPanelStore,
	        displayInfo: true,
	        plugins: Ext.create('Ext.ux.ProgressBarPager', {})
	    })	    
});


