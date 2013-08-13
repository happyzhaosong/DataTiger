	
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
							collapsible: true,
							*/
							region: 'west',
					    	width: '10%',
					    	height: '100%'
					    },{
					    	xtype: 'DataTableListPanel',
					    	id: idDataTableListPanel,
					    	region: 'east',
					    	width: '90%',
					    	height: '100%'
					    },{
					    	xtype: 'DataTableColumnListPanel',
					    	id: idDataTableColumnListPanel,
					    	hidden:true,
					    	region: 'east',
					    	width: '90%',
					    	height: '100%'
					    },{
					    	xtype: 'DataTableDataPanel',
					    	id: idDataTableDataPanel,
					    	hidden:true,					    	
					    	region: 'east',
					    	width: '90%',
					    	height: '100%'
					    }]					    
			});
			
			Ext.getCmp(idLeftNavMenu).getStore().load();
			Ext.getCmp(idDataTableListPanel).getStore().load();
		});

		

 
