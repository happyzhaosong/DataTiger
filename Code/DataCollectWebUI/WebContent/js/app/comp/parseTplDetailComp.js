var idParseTplDetailPanelItemListPanelStore = new DC.store.ParseTplItem();

//site login account form include grid panel component
Ext.define('DC.comp.ParseTplItemListPanel', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.ParseTplItemListPanel',	
    title: 'Parse Template Item List',
    width: 950,
    height: 500,
    split: false,
    autoScroll: true,
    store: idParseTplDetailPanelItemListPanelStore,
    selModel: Ext.create('Ext.selection.CheckboxModel',{}),
    columns: [
   	    { xtype: 'rownumberer', width: 50, sortable: false},              
        //{ text: 'Save To Table', dataIndex: 'saveToTable', flex: 10 , sortable: true, hideable: true},
        { text: 'Save To Column', dataIndex: 'saveToColumn', flex: 3 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderUrlLinkStyle},
        { text: 'Data Type', dataIndex: 'dataType', flex: 1 , sortable: true, hideable: true , renderer: rendererToolGlobal.renderDataTypeValue},
        { text: 'UseThisSettingUrlCharactor', dataIndex: 'useThisSettingUrlCharactor', flex: 1 , sortable: true, hideable: true},
        { text: 'ByEleType', dataIndex: 'byEleType', flex: 1 , sortable: true, hideable: true},
        { text: 'ByEleVal', dataIndex: 'byEleVal', flex: 1 , sortable: true, hideable: true},
        { text: 'DirectGetText', dataIndex: 'ifDirectGetText', flex: 1 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderBoolValue},   
        { text: 'ByTagAttribute', dataIndex: 'byTagAttribute', flex: 1 , sortable: true, hideable: true},        
        //{ text: 'ParseValueString', dataIndex: 'parseValueString', flex: 2 , sortable: true, hideable: true},
        { text: 'Check Repeat Column', dataIndex: 'ifCheckRepeatColumn', flex: 1 , sortable: true, hideable: true, renderer: rendererToolGlobal.renderBoolValue},       
        //{ text: 'Charactors', dataIndex: 'charactor', flex: 2 , sortable: true, hideable: true},
        //{ text: 'Not Charactors', dataIndex: 'notCharactor', flex: 2 , sortable: true, hideable: true},
        //{ text: 'Default Value', dataIndex: 'defaultVal', flex: 1 , sortable: true, hideable: true},
        { text: 'Description', dataIndex: 'columnDesc', flex: 3 , sortable: true, hideable: true}
    ],
    buttons: formToolGlobal.createGridPanelAddEditDeleteButton("Parse Template Items"),
    listeners:{
    	cellClick:{
    		fn: function(obj,td,cIdx,record,tr,rIdx)
    		{
    			if(cIdx==1 || cIdx==2)
    			{
    				this.showAddEditWindow("Edit Parse Template Item", record.raw);
    			}else if(cIdx!=0)
    			{
    				Ext.Msg.alert('Cell Value', td.innerText).setAutoScroll(true);
    			}
    		}
    	}
    },
    
    showAddEditWindow: function(windowTitle, recordObj)
    {
    	if(Ext.getCmp(idParseTplIdHidden).getValue()=="-1")
    	{
    		Ext.Msg.alert('Add Parse Template Item Failed', "You should first save parse template and then can add it's item.");	
    		return;
    	}
    	
    	if(windowTitle.indexOf("Edit")!=0 && Ext.getCmp(idParseTplDetailPanel + "saveToTable").getValue()==null || Ext.getCmp(idParseTplDetailPanel + "saveToTable").getValue()=="")
    	{
    		Ext.Msg.alert('Add Parse Template Item Failed', "You should first select a table for add or edit parse item column.");	
    		return;
    	}
    	
    	var formBtns = formToolGlobal.createAddEditFormWindowSaveCloseButton(windowTitle, this.getId(), jsonRootParseTplItemList, null, null);

    	var formItems = [{
	    	xtype: 'hiddenfield',	        
	        value: '-1',
	        name: 'id'
	    },{
	    	xtype: 'hiddenfield',	        
	        value: Ext.getCmp(idParseTplIdHidden).value,
	        name: 'parseId'
	    },{
	    	xtype: 'hiddenfield',	        
	        value: Ext.getCmp(idParseTplDetailPanel+"saveToTable").getValue(),
	        name: 'saveToTable'
	    },{
	    	xtype: 'TableColumnListComboBox',			
	    	fieldLabel: 'Save To Column',
	    	name:'saveToColumn',
	    	allowBlank: false
	    },{
	        grow : true,
	        name : 'columnDesc',
	        fieldLabel: 'Description'
	    },{
			xtype: 'checkboxfield',
	        grow : true,
	        name : 'pageUrlAsValue',
	        fieldLabel: 'Save Page Url As Value'
	    },{
			xtype: 'ParseTplItemActionPanel',//use xtype, not new, because new need to destroy, but xtype, framwork will destroy it automatically.
			name: 'itemActionListPanel',
			autoScroll: true,
			width:800
		},{
	        grow : true,
	        fieldLabel: 'Use This Setting Url Charactor',
	        name: 'useThisSettingUrlCharactor',
	        allowBlank: true,
			emptyText: "If content page url match this url charactor then use this setting to parse it. If this value is empty then all url can use this parse setting to parse data. This field can have multiple value seperated by ;, this way can make different item parse according to different page url in one parse item settings. For example(taobao and tmall only has little different item parse settings, so can use this method to reduce parse settings count)"
	    },{
			xtype: 'WebDriverSearchByComboBox',			
			fieldLabel: 'Parse Web Element By',
			name:'byEleType',
			allowBlank: false
		},{
	        grow : true,
	        fieldLabel: 'Parse By Element Value',
	        name: 'byEleVal',
	        allowBlank: false,
			emptyText: emptyTextMultipleValue
	    },{
			xtype: 'checkboxfield',
	        grow : true,
	        name : 'ifDirectGetText',
	        fieldLabel: 'Use WebElement getText to Direct Get Text(If not check, then use getInnerHtml to get text and parse)'
	    },{
	        grow : true,
	        fieldLabel: 'Parse By Tag Attribute',
	        name: 'byTagAttribute',
	        allowBlank: true,
			emptyText: 'element attribute can be multiple seperated by ; all return value will be added with one string',
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
			emptyText: 'Run this string parse on above settings parsed out text if it is difficult to use regexp to parse, format is startStr1'+seperatorComplex1+'endStr1'+seperatorComplex+'startStr2'+seperatorComplex1+'endStr2'+seperatorComplex+'startStr3'+seperatorComplex1+'endStr3..., second parse string will be effect on first parse string parsed out text. Because item data will contain ; some time, so not use ; as seperator.'
	    },{
	        grow : true,
	        name : 'charactor',
	        fieldLabel: 'Charactors',
	        emptyText: emptyTextMultipleValue
	    },{
	        grow : true,
	        name : 'notCharactor',
	        fieldLabel: 'Not Charactors',
	        emptyText: emptyTextMultipleValue
	    },{
	        grow : true,
	        name : 'defaultVal',
	        fieldLabel: 'Default Value'
	    },{
	    	xtype : 'DataTypeComboBox',
	    	name: 'dataType',
	        fieldLabel : 'Data Type',
	    	allowBlank: false
	    },{
	        grow : true,
	        fieldLabel: 'Date Format',
	        name: 'dateFormat'
	    },{
	        grow : true,
	        name : 'numberFormat',
	        fieldLabel: 'Number Format'
	    },{
			xtype: 'checkboxfield',
	        grow : true,
	        name : 'ifCheckRepeatColumn',
	        fieldLabel: 'Check Repeat'
	    },{
	        grow : false,
	        disabled: true,
	        fieldLabel: 'Check Repeat Description',
	        emptyText: 'use unique index on table(download_task, data_tao_bao_jie)s check repeat column, and catch Duplicate Exception when insert new data, this way can reduce check duplicate and synchronized method in java'
	    },{
			xtype: 'checkboxfield',
	        grow : true,
	        name : 'ifUrl',
	        fieldLabel: 'If URL Value'
	    },{
	        grow : true,
	        name : 'srcRegExp',
	        fieldLabel: 'Src Regular Exp',
	        emptyText: 'Regular expression which will be runned on parsed out data value.eg(^Developer$'
	    },{
	        grow : true,
	        name : 'destRegExp',
	        fieldLabel: 'Dest Regular Exp Or Value',
	        emptyText: 'Use this value to replace Src Regular Exp place holder.eg(use space to replace data value from value begin to :)'
	    },{
			xtype: 'checkboxfield',
	        grow : true,
	        name : 'regExpItemRelation',
	        fieldLabel: 'Pattern Match Relation(check---and relation, uncheck---or relation)'
	    },{
	        grow : false,
	        disabled: true,
	        fieldLabel: 'Reg Exp Description',
	        emptyText: 'check means and (replace multiple times according to number of src reg exp), uncheck means or (if one of src reg exp match then return)'
	    },{
	        grow : true,
	        name : 'execJavascript',
	        fieldLabel: 'Execute JavaScript',
	        emptyText: 'If need run javascript on parsed out data value then input it here.'
	    }];
    	
    	formToolGlobal.buildFormItemsIdByName(this.getId(), formItems);
    	
    	var window = formToolGlobal.createFormWithWindow(parseTplItemSaveUrl, formItems, 'textareafield', formBtns, windowTitle);
    	
    	Ext.getCmp(this.getId() + "dataType").getStore().load();
    	
    	window.width = 1000;
    	window.height = 500;
    	if(recordObj!=null)
    	{
    		Ext.getCmp(this.getId() + "id").setValue(recordObj.id);

    		Ext.getCmp(idParseTplItemIdHidden).setValue(recordObj.id);
    		/*
			Ext.getCmp(idParseTplDetailPanelItemListPanel + "saveToColumn").getStore().getProxy().setExtraParam(pageLimit, pageSize);
			Ext.getCmp(idParseTplDetailPanelItemListPanel + "saveToTable").getStore().load();
			Ext.getCmp(idParseTplDetailPanelItemListPanel + "saveToTable").select(recordObj.saveToTable);
    		 */
    		
			Ext.getCmp(this.getId() + "saveToColumn").getStore().getProxy().setExtraParam(pageLimit, pageSize);
			Ext.getCmp(this.getId() + "saveToColumn").getStore().getProxy().setExtraParam(dataDBTableName, recordObj.saveToTable);
			Ext.getCmp(this.getId() + "saveToColumn").getStore().load();
			Ext.getCmp(this.getId() + "saveToColumn").select(recordObj.saveToColumn);
			
    		Ext.getCmp(this.getId() + "columnDesc").setValue(recordObj.columnDesc);
    		Ext.getCmp(this.getId() + "pageUrlAsValue").setValue(recordObj.pageUrlAsValue);
    		
			Ext.getCmp(this.getId() + "itemActionListPanel").getStore().getProxy().setExtraParam(dataId, recordObj.id);
			Ext.getCmp(this.getId() + "itemActionListPanel").getStore().load();    		
    		
			Ext.getCmp(this.getId() + "useThisSettingUrlCharactor").setValue(recordObj.useThisSettingUrlCharactor);
						
    		Ext.getCmp(this.getId() + "byEleType").getStore().load();
    		Ext.getCmp(this.getId() + "byEleType").select(recordObj.byEleType);
    		Ext.getCmp(this.getId() + "byEleVal").setValue(recordObj.byEleVal);
    		
    		Ext.getCmp(this.getId() + "ifDirectGetText").setValue(recordObj.ifDirectGetText);
    		
    		Ext.getCmp(this.getId() + "byTagAttribute").setValue(recordObj.byTagAttribute);
    		Ext.getCmp(this.getId() + "parseValueRegExp").setValue(recordObj.parseValueRegExp);
    		Ext.getCmp(this.getId() + "parseValueString").setValue(recordObj.parseValueString);

    		Ext.getCmp(this.getId() + "charactor").setValue(recordObj.charactor);
    		Ext.getCmp(this.getId() + "notCharactor").setValue(recordObj.notCharactor);
    		Ext.getCmp(this.getId() + "defaultVal").setValue(recordObj.defaultVal);
    		
    		Ext.getCmp(this.getId() + "dataType").setValue(recordObj.dataType);
    		
    		Ext.getCmp(this.getId() + "dateFormat").setValue(recordObj.dateFormat);
    		Ext.getCmp(this.getId() + "numberFormat").setValue(recordObj.numberFormat);
    		Ext.getCmp(this.getId() + "ifCheckRepeatColumn").setValue(recordObj.ifCheckRepeatColumn);
    		Ext.getCmp(this.getId() + "ifUrl").setValue(recordObj.ifUrl);
    		Ext.getCmp(this.getId() + "srcRegExp").setValue(recordObj.srcRegExp);
    		Ext.getCmp(this.getId() + "destRegExp").setValue(recordObj.destRegExp);
    		Ext.getCmp(this.getId() + "regExpItemRelation").setValue(recordObj.regExpItemRelation);
    		Ext.getCmp(this.getId() + "execJavascript").setValue(recordObj.execJavascript);
    	}else
    	{
    		Ext.getCmp(this.getId() + "saveToColumn").getStore().getProxy().setExtraParam(dataDBTableName, Ext.getCmp(idParseTplDetailPanel+"saveToTable").getValue());
    		Ext.getCmp(this.getId() + "saveToColumn").getStore().load();
    	}
    		
    	window.show();
    },
    deleteSelectedRecords: function(selectedRecords)
    {
    	var submitParams = utilToolGlobal.addSubmitParamsWithDelIds(null,utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));	    	
    	var reloadPanelListUrl = utilToolGlobal.buildReloadPanelListUrl(Ext.getCmp(idParseTplIdHidden).value, this.getId());
    	formToolGlobal.submitAndReloadRecords(parseTplItemDeleteUrl, submitParams, this.getId(), "Delete Parse Template Item", reloadPanelListUrl, null);
    },
    copySelectedRecords: function(selectedRecords)
    {
    	var submitParams = utilToolGlobal.addSubmitParamsWithIds(null,utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));	    	
    	var reloadPanelListUrl = utilToolGlobal.buildReloadPanelListUrl(Ext.getCmp(idParseTplIdHidden).value, this.getId());
    	formToolGlobal.submitAndReloadRecords(parseTplItemCopyUrl, submitParams, this.getId(), "Copy Parse Template Item", reloadPanelListUrl, null);
    }/*,
    
    bbar: Ext.create('Ext.PagingToolbar', {
        pageSize: pageSize,
        store: idParseTplDetailPanelItemListPanelStore,
        displayInfo: true,
        plugins: Ext.create('Ext.ux.ProgressBarPager', {})
    })*/
});