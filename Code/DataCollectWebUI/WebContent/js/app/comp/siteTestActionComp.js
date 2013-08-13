Ext.define('DC.comp.SiteTestActionPanel', {
	extend: 'Ext.form.Panel',
	alias: 'widget.SiteTestActionPanel',
	id: idSiteTestActionPanel,
    title: 'Site Parse Action Panel',
    autoScroll: false,	
    layout: 'border',
    defaults: {
        anchor: '100%',
    	flex: 1,
    	margin: 0
    },
    items:[{
    	xtype: 'SiteTestThreadListInfoPanel',
    	id: idSiteTestThreadListInfoPanel,
    	region: 'north',
		height: '100%'
    },{
    	xtype: 'SiteTestTaskListInfoPanel',
    	id: idSiteTestTaskListInfoPanel,
    	region: 'north',
		height: '80%'
    },{
    	xtype: 'SiteTestMQMessageListInfoPanel',
    	id: idSiteTestMQMessageListInfoPanel,
    	region: 'north',
		height: '100%'
    }],
    
    dockedItems: [{
        xtype: 'toolbar',
        dock: 'top',
        items: formToolGlobal.createTestWebSiteActionButtons()
    }],
    
    initPanelFieldValue : function()
	{
    	Ext.getCmp(idSiteTestThreadListInfoPanel).initThreadListPanel(Ext.getCmp(idSiteIdHidden).getValue(),"");
    	Ext.getCmp(idSiteTestThreadListInfoPanel).show();
    	
    	Ext.getCmp(idSiteTestTaskListInfoPanel).hide();
    	if(Ext.getCmp(idSiteTestTaskListInfoPanel + idSearchPanelSuffix))
    	{
    		Ext.getCmp(idSiteTestTaskListInfoPanel + idSearchPanelSuffix).hide();
    	}

    	Ext.getCmp(idSiteTestMQMessageListInfoPanel).hide();
	}	
});