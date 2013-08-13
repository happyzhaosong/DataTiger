	
		Ext.onReady(function ()
		{	
			Ext.create('Ext.container.Viewport', {
				layout: 'border',
				items: [bannerPanel, 
				        {
							xtype:'leftnavmenu',
							id: idLeftNavMenu,
							/*
							split: true,
							floatable: false,
							collapsible: true,
							*/
							region: 'west',
					    	width: '10%',
					    	height: '100%'
					    },{
					    	xtype: 'parsetplcatpanel',
					    	id: idParseTplCatPanel,
					    	/*
					    	split: true,
					    	floatable: false,
							collapsible: true,
							*/
					    	region: 'east',
					    	width: '90%',
					    	height: '100%'
					    },{
					    	xtype: 'parsetplpanel',
					    	id: idParseTplPanel,
					    	/*
					    	split: true,
					    	floatable: false,
							collapsible: true,
							*/
					    	hidden:true,
					    	region: 'east',
					    	width: '90%',
					    	height: '100%'
					    }]					    
			});
			
			Ext.getCmp(idLeftNavMenu).getStore().load();
			Ext.getCmp(idParseTplCatPanel).getStore().load();
		});

		

 
