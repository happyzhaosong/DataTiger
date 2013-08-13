var idSitePanelStore = new DC.store.Site();

Ext.define('DC.comp.SitePanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.sitepanel',
    id: idSitePanel,
    title: 'List Web Site',  
    region: 'center',
    split: false,
    store: idSitePanelStore,
    selModel: Ext.create('Ext.selection.CheckboxModel',{}),
    columns: [
        { xtype: 'rownumberer', width: 50, sortable: false},
        { text: 'Name', dataIndex: 'name', flex: 2 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderUrlLinkStyle},
        { text: 'Site Status', dataIndex: 'siteStatusString', flex: 1 , sortable: true, hideable: true},
        { text: 'Test Passed', dataIndex: 'testPassedString', flex: 1 , sortable: true, hideable: true},
        { text: 'Description', dataIndex: 'desc', flex: 5 , sortable: true, hideable: true}
    ],
    buttons: formToolGlobal.createGridPanelAddEditDeleteButton("Web Site"),
    listeners:{
    	cellClick:{
    		fn: function(obj,td,cIdx,record,tr,rIdx)
    		{
    			if(cIdx==2)
    			{
    				Ext.getCmp(idSiteIdHidden).setValue(record.raw.id);
    				this.showAddEditWindow("Edit Web Site", record);
    			}else if(cIdx!=0)
    			{
    				Ext.Msg.alert('Cell Value', td.innerText).setAutoScroll(true);
    			}
    		}
    	}
    },
    showAddEditWindow: function(windowTitle, recordObj)
    {	
    	var siteParsePanel = Ext.create('Ext.tab.Panel', {
    			region: 'center', 
    			autoScroll: false,
    			margins: '0 5 5 0',
    			items: [{
    				xtype:'sitedetailpanel',
    				id:idSiteDetailPanel
    			},{
    				xtype:'SiteTestActionPanel',
    				id:idSiteTestActionPanel    					
    			}]			
    	});


   	    //only task list info panel need search function
    	var taskListPanelSearchPanel = formToolGlobal.createSearchPanel(idSiteTestTaskListInfoPanel + idSearchPanelSuffix, 'Search Panel', '20%', 'south', 'hbox');
    	Ext.getCmp(idSiteTestActionPanel).add(taskListPanelSearchPanel);        
    	
    	var window = formToolGlobal.createWindow(windowTitle, siteParsePanel);
    	window.autoScroll = false;
    	window.height = 600;
    	window.width = 1110;
    	
    	Ext.getCmp(idSiteTestActionPanel).initPanelFieldValue();
    	
    	var dataObj = utilToolGlobal.getRecordDataObj(recordObj);
   		Ext.getCmp(idSiteDetailPanel).initSubPanel(dataObj);
   		if(dataObj==null)
   		{
   			Ext.getCmp(idSiteIdHidden).setValue("-1");
   		}
   		window.show();	
    },
    deleteSelectedRecords: function(selectedRecords)
    {
    	var submitParams = utilToolGlobal.addSubmitParamsWithDelIds(null, utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));
    	formToolGlobal.submitAndReloadRecords(siteDeleteUrl, submitParams, this.getId(), "Delete Web Site", null, null);
    },
    copySelectedRecords: function(selectedRecords)
    {
    	var submitParams = utilToolGlobal.addSubmitParamsWithIds(null,utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));	    	
    	var reloadPanelListUrl = utilToolGlobal.buildReloadPanelListUrl(Ext.getCmp(idSiteCatIdHidden).value, this.getId());
    	formToolGlobal.submitAndReloadRecords(siteCopyUrl, submitParams, this.getId(), "Copy Web Site", reloadPanelListUrl, null);
    },
    
    bbar: Ext.create('Ext.PagingToolbar', {
        pageSize: pageSize,
        store: idSitePanelStore,
        displayInfo: true,
        plugins: Ext.create('Ext.ux.ProgressBarPager', {})
    }) 
});