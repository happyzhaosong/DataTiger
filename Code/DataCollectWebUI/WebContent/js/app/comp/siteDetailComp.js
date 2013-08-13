Ext.define('DC.comp.SiteDetailPanel', {
	extend: 'Ext.form.Panel',
	alias:'widget.sitedetailpanel',
	title: 'Site Parse Config Panel',
	id: '',
	layout: 'card',
	deferredRender: true,
	items: [{
			xtype:'sitedetailbasicinfopanel',
			id: idSiteDetailBasicInfoPanel
		},{
			xtype:'sitedetailpagelinkurlparselistpanel',
			id: idSiteDetailPageLinkUrlParseListPanel
		},{
			xtype:'siteDetailContentPageCheckListPanel',
			id: idSiteDetailContentPageCheckListPanel
		},{
			xtype:'sitedetailloginaccountpanel',
			id: idSiteDetailLoginAccountPanel
	}],
	bbar: ['->', {
			xtype: 'button',
			id: idSiteParseWizPreBtn,
			//formBind: true, //only enabled once the form is valid
            disabled: true,
			text: 'Previous',
			handler: function(btn){
				var layout = btn.up("form").getLayout();
				var activeItem = layout.activeItem;
				var activeIndex = btn.up("form").items.indexOf(activeItem);
				if(activeIndex==1)
				{
					btn.setDisabled(true);
				}else// if(activeIndex==2 || activeIndex==3)
				{
					btn.setDisabled(false);
				}
				Ext.getCmp(idSiteParseWizNextBtn).setDisabled(false);
				Ext.getCmp(idSiteParseWizNextBtn).setVisible(true);
				Ext.getCmp(idSiteParseWizFinishBtn).setVisible(false);
				if (layout.getPrev()) {
					layout.prev();
				}
			}
	}, {
			xtype: 'button',
			id: idSiteParseWizNextBtn,
			formBind: true, //only enabled once the form is valid
            disabled: true,
			text: 'Next',
			handler: function(btn){
				var layout = btn.up("form").getLayout();
				var activeItem = layout.activeItem;
				var activeIndex = btn.up("form").items.indexOf(activeItem);
				if(activeIndex==0)
				{
					btn.setVisible(true);
					Ext.getCmp(idSiteParseWizFinishBtn).setVisible(false);
					btn.up("form").items.items[0].verifyAndSaveSiteBasicInfoFieldValue(layout);
				}else
				{
					if(activeIndex==2)
					{
						btn.setDisabled(true);
						Ext.getCmp(idSiteParseWizFinishBtn).setVisible(true);
					}
					
					if (layout.getNext()) {
						layout.next();
					}
				}				
			}
	}, {
		xtype: 'button',
		id: idSiteParseWizFinishBtn,
		formBind: true, //only enabled once the form is valid
        disabled: true,
        hidden: true,
		text: 'Save',
		handler: function(btn){
			btn.up("form").items.items[2].verifyAndSaveLoginAccountFieldValue();
		}
	},{
		xtype: 'button',
	    disabled: false,
		text: 'Close',
		handler: function(btn){
			this.up('window').close();			
		}
    }],
	initSubPanel: function(dataObj)
	{
		Ext.getCmp(idSiteDetailBasicInfoPanel).initSiteBasicInfoFieldValue(dataObj);
		Ext.getCmp(idSiteDetailPageLinkUrlParseListPanel).initPageLinkUrlParseFieldValue(dataObj);
		Ext.getCmp(idSiteDetailContentPageCheckListPanel).initContentPageCheckFieldValue(dataObj);		
		Ext.getCmp(idSiteDetailLoginAccountPanel).initLoginAccountFieldValue(dataObj);
	}
});