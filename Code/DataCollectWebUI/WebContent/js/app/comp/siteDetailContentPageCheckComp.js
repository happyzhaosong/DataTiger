var idSiteDetailContentPageCheckListPanelStore = new DC.store.SiteContentPageCheck();

//site page link url parse grid panel component
Ext.define('DC.comp.SiteDetailContentPageCheckListPanel', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.siteDetailContentPageCheckListPanel',
	id: idSiteDetailContentPageCheckListPanel,
    title: "Options For Check Whether This Page Is Content Page Or Not If Difficult to Check it When Parse Url Link",  
    autoScroll: true,
    split: false,
    store: idSiteDetailContentPageCheckListPanelStore,
    selModel: Ext.create('Ext.selection.CheckboxModel',{}),
    columns: [
  	    { xtype: 'rownumberer', width: 50, sortable: false},
        { text: 'Id', dataIndex: 'id', flex: 1 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderUrlLinkStyle},        
        { text: 'Search By Element Type', dataIndex: 'byEleType', flex: 10 , sortable: true, hideable: true},
        { text: 'Search By Element Value', dataIndex: 'byEleVal', flex: 10 , sortable: true, hideable: true},
        { text: 'Search By Tag Attribute', dataIndex: 'byTagAttribute', flex: 10 , sortable: true, hideable: true},
        { text: 'Regexp To Parse Value', dataIndex: 'parseValueRegExp', flex: 10 , sortable: true, hideable: true},
        { text: 'Parse String To Parse Value', dataIndex: 'parseValueString', flex: 10 , sortable: true, hideable: true},
        { text: 'Charactor', dataIndex: 'charactor', flex: 5 , sortable: true, hideable: true},
        { text: 'Not Charactor', dataIndex: 'notCharactor', flex: 5 , sortable: true, hideable: true}
    ],
    buttons: formToolGlobal.createGridPanelAddEditDeleteButton("Web Site Content Page Check"),
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
    	var formBtns = formToolGlobal.createAddEditFormWindowSaveCloseButton(windowTitle, this.getId(), jsonRootSiteContentPageCheckList, null, null);
  
		var formItems = [{
	    	xtype: 'hiddenfield',	        
	        value: Ext.getCmp(idSiteIdHidden).getValue(),
	        name: 'siteId'
	    },{
	    	xtype: 'hiddenfield',	        
	        value: '-1',
	        name: 'id'
	    },{
			xtype: 'WebDriverSearchByComboBox',			
			fieldLabel: 'Search Web Element By',
			name:'byEleType',
			allowBlank: false
		},{
	        grow : true,
	        fieldLabel: 'Search By Element Value',
	        name: 'byEleVal',
	        allowBlank: false,
			emptyText: emptyTextSingleValue
	    },{
	        grow : true,
	        fieldLabel: 'Search By Tag Attribute',
	        name: 'byTagAttribute',
	        allowBlank: true,
			emptyText: emptyTextMultipleValue,
	    },{
	    	grow : true,
			fieldLabel: 'Run this regexp to parse value',
			name:'parseValueRegExp',
			allowBlank: true,
			emptyText: 'Run this regexp on above settings parsed out text format is regexp1'+seperatorComplex+'regexp2'+seperatorComplex+'regexp3..., if one of regexp meet then return.'
		},{
	        grow : true,
	        fieldLabel: 'Run this parse string to parse value',
	        name: 'parseValueString',
	        allowBlank: true,
			emptyText: 'Run this string parse on above settings parsed out text if it is difficult to use regexp to parse, format is startStr1'+seperatorComplex1+'endStr1'+seperatorComplex+'startStr2'+seperatorComplex1+'endStr2'+seperatorComplex+'startStr3'+seperatorComplex1+'endStr3..., second parse string will be effect on first parse string parsed out text.'
	    },{
	        grow : true,
	        name : 'charactor',
	        fieldLabel: 'Content Page Charactor',
	        emptyText: emptyTextMultipleValue,
	        allowBlank: true
	    },{
	        grow : true,
	        name : 'notCharactor',
	        emptyText: emptyTextMultipleValue,
	        fieldLabel: 'Not Content Page Charactor',
	        allowBlank: true
	    }];
    	
		
		formToolGlobal.buildFormItemsIdByName(idSiteDetailContentPageCheckListPanel, formItems);
		
    	var window = formToolGlobal.createFormWithWindow(siteContentPageCheckSaveUrl, formItems, 'textareafield', formBtns, windowTitle);
    	window.width = 1000;
    	window.height = 500;
    	
		Ext.getCmp(idSiteDetailContentPageCheckListPanel + "byEleType").getStore().load();
		
    	if(recordObj!=null)
    	{
    		Ext.getCmp(idSiteDetailContentPageCheckListPanel + "id").setValue(recordObj.id);
    		Ext.getCmp(idSiteDetailContentPageCheckListPanel + "byEleType").select(recordObj.byEleType);
    		Ext.getCmp(idSiteDetailContentPageCheckListPanel + "byEleVal").setValue(recordObj.byEleVal);
    		Ext.getCmp(idSiteDetailContentPageCheckListPanel + "byTagAttribute").setValue(recordObj.byTagAttribute);
    		Ext.getCmp(idSiteDetailContentPageCheckListPanel + "parseValueRegExp").setValue(recordObj.parseValueRegExp);
    		Ext.getCmp(idSiteDetailContentPageCheckListPanel + "parseValueString").setValue(recordObj.parseValueString);
    		Ext.getCmp(idSiteDetailContentPageCheckListPanel + "charactor").setValue(recordObj.charactor);
    		Ext.getCmp(idSiteDetailContentPageCheckListPanel + "notCharactor").setValue(recordObj.notCharactor);    		
    	}
    	window.show();
    },
    deleteSelectedRecords: function(selectedRecords)
    {
    	var submitParams = utilToolGlobal.addSubmitParamsWithDelIds(null,utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));
    	var reloadPanelListUrl = utilToolGlobal.buildReloadPanelListUrl(Ext.getCmp(idSiteIdHidden).value,  this.getId());
    	formToolGlobal.submitAndReloadRecords(siteContentPageCheckDeleteUrl, submitParams,  this.getId(), "Delete Web Site Content Page Check Option", reloadPanelListUrl, null);
    },
    initContentPageCheckFieldValue : function (dataObj)
	{
		if(dataObj!=null && dataObj.contentPageCheckDtoList)
		{
			this.getStore().loadData(dataObj.contentPageCheckDtoList);
		}else
		{
			this.getStore().removeAll();
		}
	},
    
    bbar: Ext.create('Ext.PagingToolbar', {
        pageSize: pageSize,
        store: idSiteDetailContentPageCheckListPanelStore,
        displayInfo: true,
        plugins: Ext.create('Ext.ux.ProgressBarPager', {})
    })	
});