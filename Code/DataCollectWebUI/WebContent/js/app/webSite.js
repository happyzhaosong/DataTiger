	
		Ext.onReady(function ()
		{	
			Ext.create('Ext.container.Viewport', {
				layout: 'border',
				items: [bannerPanel, 
				        {
							xtype:'leftnavmenu',
							id: idLeftNavMenu,
							/*
							split: false,
							collapsible: false,
							*/
							region: 'west',
					    	width: '10%',
					    	height: '100%'
					    }, {
					    	xtype: 'sitecatpanel',
					    	id: idSiteCatPanel,
					    	region: 'east',
					    	width: '90%',
					    	height: '100%'
					    },{
					    	xtype: 'sitepanel',
					    	id: idSitePanel,
					    	hidden:true,
					    	region: 'east',
					    	width: '90%',
					    	height: '100%'
					    }]					    
			});
			
			Ext.getCmp(idLeftNavMenu).getStore().load();
			Ext.getCmp(idSiteCatPanel).getStore().load();
		});

		

 
