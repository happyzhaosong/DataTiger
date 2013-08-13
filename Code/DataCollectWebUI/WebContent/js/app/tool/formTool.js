Ext.define('DC.tool.FormTool', {
	
	/**/
	createDataTableDataListToolBar: function()
	{
		var retArray = new Array;
		var setTaskLevelBtn = Ext.create('Ext.button.Split',{
			  		text: 'Set Related Download Task Level For Selected Records',
	          		tooltip: 'Set data related download task level and wait for the next time parse to verify new parse template settings.',
	          		tooltipType: 'title',
	          		// handle a click on the button itself
	          		handler: function() {	
	          			var selectedRecords = Ext.getCmp(idDataTableDataListPanel).getSelectionModel().getSelection();          			
	          			var downloadTaskId = "";
	          			if(selectedRecords)
	          			{
	          				var len = selectedRecords.length;
	          				if(len==0)
	          				{
	          					alert("Please select at least one record to do this action.");
	          				}else
	          				{
		          				for(var i=0;i<len;i++)
		          				{
		          					var recordObj = selectedRecords[i];
		          					var dataObj = utilToolGlobal.getRecordDataObj(recordObj);
		          					if(dataObj.download_task_id && !stringToolGlobal.isEmpty(dataObj.download_task_id))
		          					{
		          						downloadTaskId = downloadTaskId + dataObj.download_task_id;
		          						
		          						if(i<len-1)
		          						{
		          							downloadTaskId = downloadTaskId + seperatorComma;
		          						}
		          					}          					
		          				}
		          				
		          				if(stringToolGlobal.isEmpty(downloadTaskId))
		          				{
		          					alert("Selected record's download_task_id is empty, please check.");
		          				}else
		          				{
		          					var taskLevel = prompt('Input task level', 'Task level can be from 0 - 5');
		    		    			
		    		    			var regExp = new RegExp("[0-5]","i");
		    		    			if(!regExp.test(taskLevel))
		    		    			{
		    		    				alert('Task level must between 0 - 5');
		    		    			}else
		    		    			{
				          				var submitParams = utilToolGlobal.addSubmitParamsWithDelIds(null, downloadTaskId);
					          	    	submitParams.params.DOWNLOAD_TASK_LEVEL = taskLevel;
					              		formToolGlobal.submitAndReloadRecords(testTaskSetLevelUrl, submitParams, null, "Set Related Download Task Level to 5", null, null);
		    		    			}
		    		    		}
	          				}
		              		
	          			}
	          		}
		});
		
		retArray.push(setTaskLevelBtn);
		
		
		var setTaskUslessOrNotBtn = Ext.create('Ext.button.Split',{
	          		text: 'Set Related Download Task Useless Or Not',
	          		tooltip: 'Set data related download task uselsee or not, if set useless then will not download and parse that url again until set it to useful.',
	          		tooltipType: 'title',
	          		// handle a click on the button itself
	          		handler: function() {	
	          			var selectedRecords = Ext.getCmp(idDataTableDataListPanel).getSelectionModel().getSelection();          			
	          			var downloadTaskIds = "";
	          			var dataTableIds = "";
	          			if(selectedRecords)
	          			{
	          				var len = selectedRecords.length;
	          				if(len==0)
	          				{
	          					alert("Please select at least one record to do this action.");
	          				}else
	          				{
		          				for(var i=0;i<len;i++)
		          				{
		          					var recordObj = selectedRecords[i];
		          					var dataObj = utilToolGlobal.getRecordDataObj(recordObj);
		          					if(dataObj.download_task_id && !stringToolGlobal.isEmpty(dataObj.download_task_id))
		          					{
		          						downloadTaskIds = downloadTaskIds + dataObj.download_task_id;
		          						dataTableIds = dataTableIds + dataObj.id;

		          						if(i<len-1)
		          						{
		          							downloadTaskIds = downloadTaskIds + seperatorComma;
		          							dataTableIds = dataTableIds + seperatorComma;
		          						}
		          					}          					
		          				}
		          				
		          				if(stringToolGlobal.isEmpty(downloadTaskIds))
		          				{
		          					alert("Selected record's download_task_id is empty, please check.");
		          				}else
		          				{
		          					var useless = prompt('Set this data to useless or not ', '1: useless, 0: useful');
		    		    			
		    		    			var regExp = new RegExp("[0-1]","i");
		    		    			if(!regExp.test(useless))
		    		    			{
		    		    				alert('Value must be 0 or 1');
		    		    			}else
		    		    			{
				          				var submitParams = utilToolGlobal.addSubmitParamsWithDelIds(null, downloadTaskIds);
					          	    	submitParams.params.DOWNLOAD_TASK_USELESS = useless;
					          	    	submitParams.params.DATA_DB_TABLE_NAME = Ext.getCmp(idDataTableNameHidden).getValue();
					          	    	submitParams.params.DATA_TABLE_ID = dataTableIds;
					              		formToolGlobal.submitAndReloadRecords(testTaskSetUselessUrl, submitParams, null, "Set Related Download Task Useless to " + useless, null, null);
		    		    			}
		    		    		}
	          				}
		              		
	          			}
	          		}
		});
		
		retArray.push(setTaskUslessOrNotBtn);
		
		return retArray;
	},	
	
	/*
	 * searchPanelId: created search panel id.
	 * listPanelId: when user click search button, this list grid panel will be loaded.
	 * height: search panel height.
	 * region: search panel region in border layout.
	 * searchPanelItemLayout: the item layout in search panel.
	 * */
	createSearchPanel: function(searchPanelId, title, height, region, searchPanelItemLayout)
	{
		var searchPanel = Ext.create('Ext.panel.Panel',{
				id:searchPanelId,
				autoScroll: true,
				title: title,
				split:false,
	    		floatable: false,
	    		collapsible: false,
	    		height: height,
	    		region: region,
	    		hidden: false,
	    		layout: {
	    		    type: searchPanelItemLayout,
	    		    align: 'left',
	    		    padding: '30 30 30 30'
	    		}
		});		
		return searchPanel;
	},
	/*
	 * searchPanelObj: search panel object.
	 * listPanelObj: when search button clicked, this list panel will reload.
	 * dataOrModelObj: if dataOrModelObj is not null Object then this is object when user click the record link in grid list, 
	 *                 if dataOrModelObj is null then get the search key checkbox value from listPanel model.
	 * */
	createSearchPanelItem: function(searchPanelObj, listPanelObj, dataOrModelObj)
	{
		if(searchPanelObj)
		{
			searchPanelObj.setTitle('Search Panel');		
			//if(dataOrModelObj)
			{
				var paramObjArr = null;

				if(dataOrModelObj==null)
				{
					paramObjArr = utilToolGlobal.getParameterObjArrayByModelFields(listPanelObj.getStore().model);
				}else
				{
				    paramObjArr = utilToolGlobal.getParameterObjArrayByProps(dataOrModelObj);
				}
				
				searchPanelObj.removeAll(true);
				var itemArr = new Array;
				
				//search key column
				var searchKeyComboBox = new DC.comp.TableColumnComboBox();
				searchKeyComboBox.getStore().loadData(paramObjArr);
				searchKeyComboBox.allowBlank = false;
				searchKeyComboBox.labelAlign = "right";
				itemArr.push(searchKeyComboBox);
				
				//search key value compare operator
				var operatorStore = Ext.create('Ext.data.Store', {
				    fields: ['operator'],
				    data : [
				        {"operator":"="},
				        {"operator":"!="},
				        {"operator":"like"},
				        {"operator":"not like"},
				        {"operator":">"},
				        {"operator":"<"},
				        {"operator":">="},
				        {"operator":"<="}
				    ]
				});
				var operatorComboBox = Ext.create('Ext.form.field.ComboBox', {
				    fieldLabel: 'Compare Operator',
				    store: operatorStore,
				    queryMode: 'local',    
				    forceSelection: true,
				    editable:false,
				    labelAlign: 'right',
				    displayField: 'operator',
				    valueField: 'operator',
				    emptyText:'Select one operator'
				});
				operatorComboBox.allowBlank = false;
				operatorComboBox.labelAlign = "right";
				itemArr.push(operatorComboBox);	

				//search key value
				var searchValueTextBox = Ext.create('Ext.form.field.Text');
				searchValueTextBox.fieldLabel = "Search Value";
				searchValueTextBox.labelAlign = "right";
				searchValueTextBox.width = 250;
				searchValueTextBox.size = 30;
				searchValueTextBox.allowBlank = true;
				itemArr.push(searchValueTextBox);
				
				//order by column
				var orderByComboBox = new DC.comp.TableColumnComboBox();				
				orderByComboBox.fieldLabel = "Order By";				
				orderByComboBox.getStore().loadData(paramObjArr);
				searchValueTextBox.allowBlank = false;
				itemArr.push(orderByComboBox);
				
				//order by direction
				var dirStore = Ext.create('Ext.data.Store', {
				    fields: ['dir'],
				    data : [
				        {"dir":"ASC"},
				        {"dir":"DESC"}				       
				    ]
				});
				var orderByDirectionComboBox = Ext.create('Ext.form.field.ComboBox', {
				    fieldLabel: 'Order Direction',
				    store: dirStore,
				    queryMode: 'local',
				    forceSelection: true,
				    editable:false,
				    labelAlign: 'right',
				    displayField: 'dir',
				    valueField: 'dir',
				    emptyText:'Select one order direction'
				});
				orderByDirectionComboBox.allowBlank = false;
				orderByDirectionComboBox.labelAlign = "right";
				itemArr.push(orderByDirectionComboBox);					
				
				
				//search button and panel
				var searchButton = Ext.create('Ext.button.Button', {
				    text: 'Search',
				    handler: function() {
				        var searchKey = searchKeyComboBox.getValue();
				        var searchOperator = operatorComboBox.getValue();
				        var searchVal = searchValueTextBox.getValue();
				        var orderBy = orderByComboBox.getValue();
				        var orderDirection = orderByDirectionComboBox.getValue();
				        
				        if(stringToolGlobal.isEmpty(searchKey))
	    				{
	    					Ext.Msg.alert('Alert', "Please select a search by column.");	
	    				}else if(stringToolGlobal.isEmpty(searchOperator))
	    				{
	    					Ext.Msg.alert('Alert', "Please select a search operator.");	
	    				}else
	    				{
	    					orderDirection = stringToolGlobal.isEmptySetDefault(orderDirection, 'DESC');
	    					
	    					if(listPanelObj && listPanelObj.getStore())
	    					{
	    						listPanelObj.getStore().getProxy().setExtraParam(pageFilter, searchKey);
	    						listPanelObj.getStore().getProxy().setExtraParam(pageFilterOperator, searchOperator);
	    						listPanelObj.getStore().getProxy().setExtraParam(pageFilterValue, encodeURIComponent(searchVal));
	    						
	    						listPanelObj.getStore().getProxy().setExtraParam(pageSort, orderBy);
	    						listPanelObj.getStore().getProxy().setExtraParam(pageDir, orderDirection);
	    						
	    						listPanelObj.getStore().getProxy().setExtraParam(actionSubFieldName, "");
				    			listPanelObj.getStore().getProxy().setExtraParam(downloadTaskLevel, 0);
				    			
	    				    	listPanelObj.getStore().load();
	    					}else
	    					{
	    						Ext.Msg.alert('Alert', "List data panel dose not exist.");	
	    					}
	    				}
				    }
				});	
				
				
				//reset search result related download task button
				var resetSearchResultDownloadTaskButton = Ext.create('Ext.button.Button', {
				    text: 'Reset Result Related Download Task',
				    tooltip: 'Reset search condition result related download task, set apply_time to "" and task level to user input task level. If search result count is too much, may take a long time, and this is an asynchronous process.',
	          		tooltipType: 'title',
				    handler: function() {
				        var searchKey = searchKeyComboBox.getValue();
				        var searchOperator = operatorComboBox.getValue();
				        var searchVal = searchValueTextBox.getValue();
				        var orderBy = orderByComboBox.getValue();
				        var orderDirection = orderByDirectionComboBox.getValue();
				        
				        if(stringToolGlobal.isEmpty(searchKey))
	    				{
	    					Ext.Msg.alert('Alert', "Please select a search by column.");	
	    				}else if(stringToolGlobal.isEmpty(searchOperator))
	    				{
	    					Ext.Msg.alert('Alert', "Please select a search operator.");	
	    				}else
	    				{
	    					orderDirection = stringToolGlobal.isEmptySetDefault(orderDirection, 'DESC');
	    					
	    					if(listPanelObj && listPanelObj.getStore())
	    					{
	    						listPanelObj.getStore().getProxy().setExtraParam(pageFilter, searchKey);
	    						listPanelObj.getStore().getProxy().setExtraParam(pageFilterOperator, searchOperator);
	    						listPanelObj.getStore().getProxy().setExtraParam(pageFilterValue, encodeURIComponent(searchVal));
	    						
	    						listPanelObj.getStore().getProxy().setExtraParam(pageSort, orderBy);
	    						listPanelObj.getStore().getProxy().setExtraParam(pageDir, orderDirection);
	    						
	    						Ext.MessageBox.confirm('Confirm Dialog', 'Do you want to reset search result apply time to empty string and task level?', confirmReset, this);
	    						function confirmReset(btn, text, opt)
	    				    	{
	    				    		if(btn == 'yes')  
	    				    		{
	    	    						listPanelObj.getStore().getProxy().setExtraParam(actionSubFieldName, actionResetAll);  	    						
	    	    						 
	    	    						var resetApplyTimeStr = "false";
	    	    						if(window.confirm("确定重置 task applytime 为 ''么?"))
	    	    						{
	    	    							resetApplyTimeStr = "true";
	    	    						}	    	    						
	    	    						listPanelObj.getStore().getProxy().setExtraParam(resetApplyTime, resetApplyTimeStr);	   
	    	    						
	    	    						var taskLevel = prompt('Input task level', 'Task level can be from 0 - 5');
			    		    			var regExp = new RegExp("[0-5]","i");
			    		    			if(!regExp.test(taskLevel))
			    		    			{
			    		    				alert('Task level must between 0 - 5');
			    		    				return;
			    		    			}else
			    		    			{
		    	    						listPanelObj.getStore().getProxy().setExtraParam(downloadTaskLevel, taskLevel);
			    		    			}
	    				    		}else
	    				    		{
	    				    			listPanelObj.getStore().getProxy().setExtraParam(actionSubFieldName, "");
	    				    			listPanelObj.getStore().getProxy().setExtraParam(downloadTaskLevel, 0);
	    				    		}
	    				    		listPanelObj.getStore().load();
	    				    		listPanelObj.getStore().getProxy().setExtraParam(actionSubFieldName, "");
	    				    		listPanelObj.getStore().load();
	    				    	}
	    					}else
	    					{
	    						Ext.Msg.alert('Alert', "List data panel dose not exist.");	
	    					}
	    				}
				    }
				});	
				
				//delete search result data
				var deleteSearchResultDataButton = Ext.create('Ext.button.Button', {
				    text: 'Delete Search Result Data ',
				    tooltip: 'Delete search condition result data. If search result count is too much, may take a long time, and this is an asynchronous process.',
	          		tooltipType: 'title',
				    handler: function() {
				        var searchKey = searchKeyComboBox.getValue();
				        var searchOperator = operatorComboBox.getValue();
				        var searchVal = searchValueTextBox.getValue();
				        var orderBy = orderByComboBox.getValue();
				        var orderDirection = orderByDirectionComboBox.getValue();
				        
				        if(stringToolGlobal.isEmpty(searchKey))
	    				{
	    					Ext.Msg.alert('Alert', "Please select a search by column.");	
	    				}else if(stringToolGlobal.isEmpty(searchOperator))
	    				{
	    					Ext.Msg.alert('Alert', "Please select a search operator.");	
	    				}else
	    				{
	    					orderDirection = stringToolGlobal.isEmptySetDefault(orderDirection, 'DESC');
	    					
	    					if(listPanelObj && listPanelObj.getStore())
	    					{
	    						listPanelObj.getStore().getProxy().setExtraParam(pageFilter, searchKey);
	    						listPanelObj.getStore().getProxy().setExtraParam(pageFilterOperator, searchOperator);
	    						listPanelObj.getStore().getProxy().setExtraParam(pageFilterValue, encodeURIComponent(searchVal));
	    						
	    						listPanelObj.getStore().getProxy().setExtraParam(pageSort, orderBy);
	    						listPanelObj.getStore().getProxy().setExtraParam(pageDir, orderDirection);
	    						
	    						Ext.MessageBox.confirm('Confirm Dialog', 'Do you want to delete all search result data?', confirmReset, this);
	    						function confirmReset(btn, text, opt)
	    				    	{
	    				    		if(btn == 'yes')  
	    				    		{
	    	    						listPanelObj.getStore().getProxy().setExtraParam(actionSubFieldName, actionDeleteAll);  						
	    				    		}else
	    				    		{
	    				    			listPanelObj.getStore().getProxy().setExtraParam(actionSubFieldName, "");
	    				    		}
	    				    		listPanelObj.getStore().load();
	    				    		listPanelObj.getStore().getProxy().setExtraParam(actionSubFieldName, "");
	    				    		listPanelObj.getStore().load();
	    				    	}
	    					}else
	    					{
	    						Ext.Msg.alert('Alert', "List data panel dose not exist.");	
	    					}
	    				}
				    }
				});	

				itemArr.push(searchButton);	
				itemArr.push(resetSearchResultDownloadTaskButton);
				itemArr.push(deleteSearchResultDataButton);
				
				searchPanelObj.items.addAll(itemArr);
				
				searchPanelObj.doLayout();
			}
		}else
		{
			Ext.Msg.alert('Alert', "Search panel dose not exist.");	
		}
	},
	
    createFormWithWindow: function(formUrl, formItems, formItemDefaultType, formBtns, windowTitle)
    {
    	var form = Ext.create('Ext.form.Panel', {
    		bodyPadding: 10,
    		padding: 1,
    		border: false,
    	    url: formUrl,
    		autoScroll: true,
    		layout: 'vbox',
    	    defaultType: formItemDefaultType,
    	    defaults:{
				width:800,
	    		labelWidth:200,
	    		labelAlign:'right',
				anchor: '100%',
				padding: 1
    	    },
    	    items: formItems,
    	    buttons: formBtns
    	});
    	
    	return this.createWindow(windowTitle, form);
    },
    
    createWindow: function(windowTitle, windowItems)
    {
    	var window = Ext.create('Ext.window.Window', {
    		title: windowTitle,
    		height: 200,
    		width: 600,
    		modal: true,
    		closable: true,
    		resizable: true,
    		autoDestroy: true,
    		autoScroll: true,
    		closeAction: 'destroy',
    		layout: 'fit',
    		items: [windowItems],
    	    listeners: {
    	        close: {    	            
    	            fn: function(panel, eOpts){ 
    	            	if(panel && panel.items)
    	            	{
    	            		var itemSize = panel.items.length;
    	            		for(var i=0;i<itemSize;i++)
    	            		{
    	            			if(panel.items[i])
    	            			{
    	            				panel.items[i].close();
    	            			}
    	            		}
    	            	}
    	            	console.log('click el'); 
    	            }
    	        }
    	    }
    	});    	
    	return window;
    },
    
    buildFormItemsIdByName: function(itemIdPrefix, formItems)
    {
    	if(formItems!=null)
    	{
    		var size = formItems.length;
    		for(var i=0;i<size;i++)
    		{
    			var formItem = formItems[i];
    			if(utilToolGlobal.hasArrayItems(formItem))
    			{
    				formToolGlobal.buildFormItemsIdByName(itemIdPrefix, formItem.items);
    			}else
    			{
    				if(!formItem.id)
    				{
    					if(formItem.name && formItem.name!="")
    					{
    						formItem.id = itemIdPrefix + formItem.name;
    					}
    				}    				
    			}    			
    		}
    	}
    	return formItems;
    },
	makeAjaxCall: function(url, params, obj, optionObjArr)
	{
		/*
		Ext.Ajax.request({
		    url: url,
		    params: params,
		    success: function(response){
		    	obj.ajaxSuccessFN(response, optionObjArr);        
		    },
		    failure: function(response){
		    	obj.ajaxFailFN(response, optionObjArr);      
		    } 
		});
		*/
		var ajaxForm = Ext.create('Ext.form.Panel', {
			url: url,
		    hidden: true
		});
		
		var submitParams = new Object;
		submitParams.params = params;
		formToolGlobal.submitFormCall(ajaxForm.getForm(), obj, submitParams, optionObjArr);
	},
	
	submitFormCall: function(form, obj, submitParams, optionObjArr)
	{
		if (form.isValid()) {
			if(submitParams==null)
			{
				submitParams = new Object;
			}
			var submitEmptyText = (submitParams.submitEmptyText==null?false:submitParams.submitEmptyText);
			var waitMsg = (submitParams.waitMsg==null?loadMessage:submitParams.waitMsg);
			var url = (submitParams.url==null?'':submitParams.url);
			var params = (submitParams.params==null?'':submitParams.params);
			
			var submitObj = new Object;			
			if(submitEmptyText!=null) submitObj.submitEmptyText = submitEmptyText;
			if(waitMsg!='') submitObj.waitMsg = waitMsg;
			if(url!='') submitObj.url = url;
			if(params!='') submitObj.params = params;
			submitObj.success = function(form, action) {
				var json = formToolGlobal.getFormResponseJson(action);
				utilToolGlobal.checkSessionTimeOut(json);
				
				if(obj.formSuccessFN)
				{
					obj.formSuccessFN(form, action, optionObjArr);
				}else
				{
					Ext.Msg.alert('No formSuccessFN', "You need to define formSuccessFN for your submit.");
				}
	        };
			submitObj.failure = function(form, action) {
				var json = formToolGlobal.getFormResponseJson(action);
				utilToolGlobal.checkSessionTimeOut(json);

				if(obj.formFailFN)
				{
					obj.formFailFN(form, action, optionObjArr);
				}else
				{
					Ext.Msg.alert('No formFailFN', "You need to define formFailFN for your submit.");
				}
	        };
	        
	        submitObj.timeout = timeOutClientOperation;	        
	        form.submit(submitObj);
	    }
	},
	
	/*
	 * delCheckField: Field name in record data to check then add the record to deleted records.
	 * delCheckNullValue: true---if delCheckField is null or '' then get it for delete, false---if delCheckField is not null or not '' then get it for delete.
	 * */
	createGridPanelAddEditDeleteButton: function(title, delCheckField, delCheckNullValue)
	{
		var btnArr = new Array;
		var addBtn = new Object;
		var editBtn = new Object;
		var deleteBtn = new Object;
		var deleteAllBtn = new Object;
		var copyBtn = new Object;
		
		addBtn.text = 'Add';
		addBtn.handler = function(obj, event, eventOpts)
		{
			this.up('grid').showAddEditWindow("Add " + title, null);
		};
		
		editBtn.text = 'Edit';
		editBtn.handler = function(obj, event, eventOpts)
		{
			var selectedRecords = this.up('grid').getSelectionModel().getSelection();
			if(selectedRecords.length!=1){
				Ext.Msg.alert("Edit " + title + " Failed", "You must select only one record to edit.");   	        		
			}else{
				this.up('grid').showAddEditWindow("Edit " + title, selectedRecords[0].raw);	        		
			}
		};
		
		deleteBtn.text = 'Delete';
		deleteBtn.tooltip = 'Delete selected records.'
		deleteBtn.tooltipType = 'title';
		deleteBtn.handler = function(obj, event, eventOpts)
		{
			var selectedRecords = this.up('grid').getSelectionModel().getSelection();
			if(selectedRecords.length==0){
				Ext.Msg.alert("Delete " + title + " Failed", "You must select at least one record to delete.");   	        		
			}else{
				var newSelectedRecords = new Array;
				if(delCheckField!=null && delCheckField!='')
				{
					var len = selectedRecords.length;
					for(var i=0;i<len;i++)
					{
						var selRec = selectedRecords[i];
						if('stopTime'==delCheckField)
						{
							var checkDataValue = utilToolGlobal.getRecordDataObj(selRec).stopTime;
							if(delCheckNullValue)
							{
								if(checkDataValue==null || checkDataValue=='')
								{
									newSelectedRecords[i] = selRec;
								}
							}else
							{
								if(checkDataValue!=null && checkDataValue!='')
								{
									newSelectedRecords[i] = selRec;
								}
							}
						}
					}					
				}else
				{
					newSelectedRecords = selectedRecords;
				}		
				
				if(newSelectedRecords.length==0){
					var alertTitle = "Delete " + title + " Failed";
					var alertMsg = "You must select at least one record which match following condition to delete. delCheckField:" + delCheckField + " , delCheckNullValue:";
					if(delCheckNullValue)
					{
						alertMsg += " is null or empty string.";
					}else
					{
						alertMsg += " is not null or not empty string.";
					}					
					Ext.Msg.alert(alertTitle, alertMsg);   	        		
				}else{
					Ext.MessageBox.confirm('Confirm Dialog', 'Are you sure delete all selected records?', confirmDelete, this);
					function confirmDelete(btn, text, opt)
			    	{
			    		if(btn == 'yes')  
			    		{
							this.up('grid').deleteSelectedRecords(newSelectedRecords);
			    		}
			    	}
				}
			}
		};
		
		
		deleteAllBtn.text = 'Delete All';
		deleteAllBtn.tooltip = 'Delete all records.'
		deleteAllBtn.tooltipType = 'title';
		deleteAllBtn.handler = function(obj, event, eventOpts)
		{
			Ext.MessageBox.confirm('Confirm Dialog', 'Are you sure delete all records?', confirmDelete, this);
			function confirmDelete(btn, text, opt)
			{
				if(btn == 'yes')  
				{
					if(this.up('grid').deleteAllRecords)
					{
						this.up('grid').deleteAllRecords();
					}else
					{
						alert("Delete all function not defined. The function name should be deleteAllRecords()"); 
					}
			    }
			}
		};
		
		copyBtn.text = 'Copy Config';
		copyBtn.handler = function(obj, event, eventOpts)
		{
			var selectedRecords = this.up('grid').getSelectionModel().getSelection();
			if(selectedRecords.length!=1){
				Ext.Msg.alert("Copy  " + title + " Failed", "You must select only one record to copy.");   	        		
			}else{
				Ext.MessageBox.confirm('Confirm Dialog', 'Are you sure copy the config settings?', confirmCopy, this);
				function confirmCopy(btn, text, opt)
				{
					if(btn == 'yes')  
					{
						if(this.up('grid').copySelectedRecords)
						{
							this.up('grid').copySelectedRecords(selectedRecords);
						}else
						{
							alert("Copy config function not defined. The function name should be copySelectedRecords(selectedRecords)"); 
						}
				    }
				}
			}
		};
		
		btnArr.push(addBtn);
		btnArr.push(editBtn);
		btnArr.push(deleteBtn);
		btnArr.push(deleteAllBtn);
		btnArr.push(copyBtn);
				
		return btnArr;
	},
	
	createAddEditFormWindowSaveCloseButton: function(title, gridPanelId, addedDataJsonRootKey, submitParams, optionObjArr)
	{
		var btnArr = new Array;
		var saveBtn = new Object;
		var closeBtn = new Object;
		
		saveBtn.text = 'Save';
		saveBtn.formBind = true;
		saveBtn.disabled = true;
		saveBtn.handler = function(obj, event, eventOpts)
		{
			var form = this.up('form').getForm();
	        formToolGlobal.submitFormCall(form,this,submitParams,optionObjArr);    
		};
		saveBtn.formSuccessFN = function(form, action, optionObjArr)
	    {
	    	var json = formToolGlobal.getFormResponseJson(action);
	    	var dataObjArr = utilToolGlobal.getObjPropValueByName(json, addedDataJsonRootKey);
	    	if(title.indexOf("Edit")==0)
	    	{
	    		var selObjIdx = Ext.getCmp(gridPanelId).getStore().find('id', dataObjArr[0].id);
	    		Ext.getCmp(gridPanelId).getStore().removeAt(selObjIdx);
	    		Ext.getCmp(gridPanelId).getStore().insert(selObjIdx, dataObjArr);
	    	}else
	    	{
	    		Ext.getCmp(gridPanelId).getStore().loadData(dataObjArr,true);
	    	}
	    	this.up('window').close();
	    };
	    saveBtn.formFailFN = function(form, action, optionObjArr)
	    {
	    	var tmpTitle = "";
	    	if(title.indexOf("Add")==0)
	    	{
	    		tmpTitle = 'Add '+title+' Failed';
	    	}else
	    	{
	    		tmpTitle = 'Edit '+title+' Failed';
	    	}
	    	
	    	Ext.Msg.alert(tmpTitle, formToolGlobal.getFormResponseJson(action).message);
	    	this.up('window').close();
	    };
		
		closeBtn.text = 'Close';
		closeBtn.handler = function() {
			Ext.getCmp(gridPanelId).getStore().reload();
	    	this.up('window').close();
	    };
		
		btnArr.push(saveBtn);
		btnArr.push(closeBtn);
		return btnArr;
	},
	
	
	submitAndReloadRecords: function(url, submitParams, gridPanelId, title, loadDataUrl, jsonRoot)
	{
		var submitForm = Ext.create('Ext.form.Panel', {
			url: url,
		    hidden: true,
		    formSuccessFN: function(form, action, optionObjArr)
	        {
		    	var json = formToolGlobal.getFormResponseJson(action);
		    	if(json.success)
		    	{
		    		Ext.Msg.alert(title+' Success', json.message);
		    	}else
		    	{
		    		Ext.Msg.alert(title+' Failed', json.message);	
		    	}
		    	
		    	if(gridPanelId && gridPanelId!='')
		    	{
			    	formToolGlobal.resetStoreProxyParams(Ext.getCmp(gridPanelId).getStore(), loadDataUrl, jsonRoot);
			    	Ext.getCmp(gridPanelId).getStore().load();
		    	}
	        },
	        formFailFN: function(form, action, optionObjArr)
	        {
	        	 Ext.Msg.alert(title+' Failed', formToolGlobal.getFormResponseJson(action).message);
	        }
		});
		
		formToolGlobal.submitFormCall(submitForm.getForm(), submitForm, submitParams, null);
	},
	
	resetStoreProxyParams: function(store, proxyUrl, jsonRoot)
	{
		if(store!=null)
		{
			var proxy = store.getProxy();
			if(proxyUrl!=null && proxyUrl!="")
			{
				proxy.url = proxyUrl;
			}
			
			if(jsonRoot!=null && jsonRoot!="")
			{
				proxy.setReader({type: 'json', root: jsonRoot});
			}
		}
	},
	
	
    createTestWebSiteActionButtons: function()
	{
		var btnArr = new Array;

		// thread related action 
		var stopThreadBtn = new Object;
		stopThreadBtn.text = 'Stop Running Thread';
		stopThreadBtn.tooltip = 'Only can stop threads which started by the login user.'
		stopThreadBtn.tooltipType = 'title';
		stopThreadBtn.handler = function(obj, event, eventOpts)
		{
			var newSelectedRecords = new Array;
			var selectedRecords = Ext.getCmp(idSiteTestThreadListInfoPanel).getSelectionModel().getSelection();
			if(selectedRecords.length==0){
				Ext.Msg.alert("Stop Thread Failed", "You must select at least one running thread to stop.");   	        		
			}else{				
				var len = selectedRecords.length;
				for(var i=0;i<len;i++)
				{
					var selRec = selectedRecords[i];
					var checkDataValue = utilToolGlobal.getRecordDataObj(selRec).stopTime;
					if(checkDataValue==null || checkDataValue=='')
					{
						newSelectedRecords[i] = selRec;
    				}
				}
			}
			if(newSelectedRecords.length==0){
				Ext.Msg.alert("Stop Thread Failed", "You must select at least one running thread to stop.");
			}else
			{				
		    	var submitParams = new Object;
		    	utilToolGlobal.addSubmitParamsWithDelIds(submitParams, utilToolGlobal.getIdListStrInArray(newSelectedRecords, seperatorComma));
		    	utilToolGlobal.addSubmitParamsWithThreadIds(submitParams,utilToolGlobal.getThreadIdListStrInArray(newSelectedRecords, seperatorComma));
		    	utilToolGlobal.addWebSiteId(submitParams, Ext.getCmp(idSiteIdHidden).getValue());
		    	formToolGlobal.submitAndReloadRecords(testThreadStopUrl, submitParams, idSiteTestThreadListInfoPanel, "Stop Web Site Thread", null, null);	
			}			
		};
		
		var startThreadBtn = new Object;
		startThreadBtn.text = 'Start Download And Parse';
		startThreadBtn.tooltip = 'Download pages by config and parsed out the data then save to db by the config.';
		startThreadBtn.tooltipType = 'title';
		startThreadBtn.handler = function(obj, event, eventOpts)
		{
			var threadCount = prompt('Input wanted start thread count', 'Should be from 1 - 15');
			
			//var regExp = new RegExp("[123456789]","i");
			//var regExp = new RegExp("^[1-10]$");			
			//if(!regExp.test(threadCount))
			if(threadCount<1 || threadCount>15)
			{
				alert('Started thread count must between 1 - 15');
			}else
			{
				var params = Ext.getCmp(idSiteDetailBasicInfoPanel).getSaveBasicInfoParams();
				params.CREATE_THREAD_COUNT = threadCount;
				var optionObjArr = new Array;
				formToolGlobal.makeAjaxCall(testGetWebPagesInUrlSendMessageUrl, params, this, optionObjArr);
			}
		};		
		startThreadBtn.formSuccessFN = function(form, action, optionObjArr)
		{
			var json = formToolGlobal.getFormResponseJson(action);
			if(!json.success)
			{
				Ext.Msg.alert('Download And Parse Failed', json.message);	
			}else{
				Ext.getCmp(idSiteTestThreadListInfoPanel).getStore().load();
				Ext.Msg.alert('Download And Parse Success', json.message);	
			}
		};		
		startThreadBtn.formFailFN = function(form, action, optionObjArr)
		{
			var json = formToolGlobal.getFormResponseJson(action); 
			Ext.Msg.alert('Test Get Web Pages In URL Send Messages Failed', json.message);
			utilToolGlobal.checkSessionTimeOut(json);
		}
		
		var threadBtnGroup = Ext.create('Ext.button.Split', {
		    text: 'List Thread',
		    tooltip: 'List all of every user started threads that download this website.',
		    tooltipType: 'title',
		    // handle a click on the button itself
		    handler: function() {
		    	Ext.getCmp(idSiteTestThreadListInfoPanel).initThreadListPanel(Ext.getCmp(idSiteIdHidden).getValue(),"");
		    	Ext.getCmp(idSiteTestThreadListInfoPanel).show();	    	
		    	Ext.getCmp(idSiteTestTaskListInfoPanel).hide();
		    },
		    menu: new Ext.menu.Menu({
		        items: [stopThreadBtn, startThreadBtn]
		    })
		});


		// task related action 
		var resetTaskBtn = new Object;
		resetTaskBtn.text = 'Reset Selected Download Tasks';
		resetTaskBtn.tooltip = 'Reset selected download tasks, set applytime to "". After this action, you should click "List All Download Tasks" button to refresh the download tasks list page.';
		resetTaskBtn.tooltipType = 'title';
		resetTaskBtn.handler = function(obj, event, eventOpts)
		{
			Ext.MessageBox.confirm('Confirm', 'Are you sure reset selected download tasks?', confirmReset, this);
			function confirmReset(btn, text, opt)
	    	{
	    		if(btn == 'yes')  
	    		{
	    	    	var selectedRecords = Ext.getCmp(idSiteTestTaskListInfoPanel).getSelectionModel().getSelection();
	    	    	if(selectedRecords.length==0){
	    				alert("You must select at least one download task to reset.");   	        		
	    			}else
	    			{
	    		    	var submitParams = utilToolGlobal.addSubmitParamsWithDelIds(null, utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));	    	
		    			formToolGlobal.submitAndReloadRecords(testTaskResetUrl, submitParams, idSiteTestTaskListInfoPanel, "Reset selected download tasks", null, null);	    			
		    			
		    			Ext.getCmp(idSiteTestThreadListInfoPanel).hide(); 
		    			Ext.getCmp(idSiteTestMQMessageListInfoPanel).hide();
	    			}
	    		}
	    	}
		};
		
		
		var resetAllTaskBtn = new Object;
		resetAllTaskBtn.text = 'Reset All Download Tasks';
		resetAllTaskBtn.tooltip = 'Reset all download tasks, set applytime to "". After this action, you should click "List All Download Tasks" button to refresh the download tasks list page.';
		resetAllTaskBtn.tooltipType = 'title';
		resetAllTaskBtn.handler = function(obj, event, eventOpts)
		{
			Ext.MessageBox.confirm('Confirm', 'Are you sure reset all download tasks?', confirmReset, this);
			function confirmReset(btn, text, opt)
	    	{
	    		if(btn == 'yes')  
	    		{
	    			var submitParams = new Object;
	    	    	utilToolGlobal.addWebSiteId(submitParams, Ext.getCmp(idSiteIdHidden).getValue());
	    	    	formToolGlobal.submitAndReloadRecords(testTaskResetUrl, submitParams, idSiteTestTaskListInfoPanel, "Reset all download tasks", null, null);
	    			
	    			Ext.getCmp(idSiteTestThreadListInfoPanel).hide(); 
	    			Ext.getCmp(idSiteTestMQMessageListInfoPanel).hide();
	    		}
	    	}
		};
		
		
		var setTaskLevelBtn = new Object;
		setTaskLevelBtn.text = 'Set Selected Task Level';
		setTaskLevelBtn.tooltip = 'Set selected download tasks level. Big level tasks will be run first.';
		setTaskLevelBtn.tooltipType = 'title';
		setTaskLevelBtn.handler = function(obj, event, eventOpts)
		{
			var confirmDlg = Ext.MessageBox.confirm('Confirm', 'Are you sure set selected tasks level?', confirmReset, this);						

			function confirmReset(btn, text, opt)
	    	{
	    		if(btn == 'yes')  
	    		{
	    			var selectedRecords = Ext.getCmp(idSiteTestTaskListInfoPanel).getSelectionModel().getSelection();
	    	    	if(selectedRecords.length==0){
	    				alert("You must select at least one download task to set it level.");   	        		
	    			}else
	    			{
		    			var taskLevel = prompt('Input task level', 'Task level can be from 0 - 5');
		    			
		    			var regExp = new RegExp("[0-5]","i");
		    			if(!regExp.test(taskLevel))
		    			{
		    				alert('Task level must between 0 - 5');
		    			}else
		    			{
		    				var submitParams = utilToolGlobal.addSubmitParamsWithDelIds(null, utilToolGlobal.getIdListStrInArray(selectedRecords, seperatorComma));
				    	    submitParams.params.DOWNLOAD_TASK_LEVEL = taskLevel;
			    			formToolGlobal.submitAndReloadRecords(testTaskSetLevelUrl, submitParams, idSiteTestTaskListInfoPanel, "Set selected download tasks level to " + taskLevel, null, null);	    			
				    			
				    		Ext.getCmp(idSiteTestThreadListInfoPanel).hide(); 
				    		Ext.getCmp(idSiteTestMQMessageListInfoPanel).hide();
		    			}
	    			}
	    		}
	    	}
		};
		
		
		
		var setAllTaskLevelBtn = new Object;
		setAllTaskLevelBtn.text = 'Set All Task Level';
		setAllTaskLevelBtn.tooltip = 'Set all download tasks level. Big level tasks will be run first.';
		setAllTaskLevelBtn.tooltipType = 'title';
		setAllTaskLevelBtn.handler = function(obj, event, eventOpts)
		{
			var confirmDlg = Ext.MessageBox.confirm('Confirm', 'Are you sure set all tasks level?', confirmReset, this);						

			function confirmReset(btn, text, opt)
	    	{
	    		if(btn == 'yes')  
	    		{
	    			var taskLevel = prompt('Input task level', 'Task level can be from 0 - 5');
		    			
		    		var regExp = new RegExp("[0-5]","i");
		    		if(!regExp.test(taskLevel))
		    		{
		    			alert('Task level must between 0 - 5');
		    		}else
		    		{
				    	var submitParams = new Object;
				        utilToolGlobal.addWebSiteId(submitParams, Ext.getCmp(idSiteIdHidden).getValue());
				        submitParams.params.DOWNLOAD_TASK_LEVEL = taskLevel;
				   	    formToolGlobal.submitAndReloadRecords(testTaskSetLevelUrl, submitParams, idSiteTestTaskListInfoPanel, "Set all download tasks level to " + taskLevel, null, null);
				    			
				   		Ext.getCmp(idSiteTestThreadListInfoPanel).hide(); 
				   		Ext.getCmp(idSiteTestMQMessageListInfoPanel).hide();
		    		}
	    		}
	    	}
		};
		
		
		var taskBtnGroup = Ext.create('Ext.button.Split', {
		    text: 'List All Download Tasks',
		    tooltip: 'List all download tasks of this website. To see each thread\'s download tasks, you can click each thread table id column in thread list panel.',
		    tooltipType: 'title',
		    // handle a click on the button itself
		    handler: function() {
		    	Ext.getCmp(idSiteTestThreadListInfoPanel).hide();
				Ext.getCmp(idSiteTestMQMessageListInfoPanel).hide();
				Ext.getCmp(idSiteTestTaskListInfoPanel).initTaskListPanel(Ext.getCmp(idSiteIdHidden).getValue(),'');
				Ext.getCmp(idSiteTestTaskListInfoPanel).show();
		    },
		    menu: new Ext.menu.Menu({
		        items: [resetAllTaskBtn, resetTaskBtn, setTaskLevelBtn, setAllTaskLevelBtn]
		    })
		});
		
		
		// message queue related action 
		var listThreadMqBtn = new Object;
		listThreadMqBtn.text = 'List Thread MQ Message';
		listThreadMqBtn.tooltip = 'List selected thread related mq message.';
		listThreadMqBtn.tooltipType = 'title';
		listThreadMqBtn.handler = function(obj, event, eventOpts)
		{
			var selectedRecords = Ext.getCmp(idSiteTestThreadListInfoPanel).getSelectionModel().getSelection();
			
			if(selectedRecords)
			{
				if(selectedRecords.length!=1){
					Ext.Msg.alert("Alert", "You must select only one thread to list it's related MQ message.");   	        		
				}else{
					
					var data = utilToolGlobal.getRecordDataObj(selectedRecords[0]);					
					Ext.getCmp(idSiteTestThreadListInfoPanel).hide();    	
					Ext.getCmp(idSiteTestTaskListInfoPanel).hide();
					Ext.getCmp(idSiteTestMQMessageListInfoPanel).initTaskListPanel(Ext.getCmp(idSiteIdHidden).getValue(),data.id);
					Ext.getCmp(idSiteTestMQMessageListInfoPanel).show()
				}
			}
		};

		
		var mqBtnGroup = Ext.create('Ext.button.Split', {
		    //renderTo: Ext.getBody(),
		    text: 'List All MQ Message',
		    tooltip: 'List all thread related mq message.',
		    tooltipType: 'title',
		    // handle a click on the button itself
		    handler: function() {
		    	Ext.getCmp(idSiteTestThreadListInfoPanel).hide();    	
				Ext.getCmp(idSiteTestTaskListInfoPanel).hide();
				Ext.getCmp(idSiteTestMQMessageListInfoPanel).initTaskListPanel(Ext.getCmp(idSiteIdHidden).getValue(),'');
				Ext.getCmp(idSiteTestMQMessageListInfoPanel).show();
		    },
		    menu: new Ext.menu.Menu({
		        items: [listThreadMqBtn]
		    })
		});
		

		// more other action 
		var otherBtnGroup = Ext.create('Ext.button.Split', {
		    text: 'Change To Test Passed',
		    tooltip: 'Change this website parse status to passed.',
		    tooltipType: 'title',
		    // handle a click on the button itself
		    handler: function() {
		    	
		    },
		    menu: new Ext.menu.Menu({
		        items: []
		    })
		});

		
		btnArr.push(threadBtnGroup);
		btnArr.push(taskBtnGroup);
		btnArr.push(mqBtnGroup);
		btnArr.push(otherBtnGroup);

		return btnArr;
	},
	getFormResponseText: function(action)
	{
		var ret = "";
		if(action.result && action.result.message)
        {
     	   ret = action.result.message;                            	   
        }else if(action.response && action.response.responseText)
        {
     	   ret = action.response.responseText;
        }  
		return ret;
	},
	getFormResponseJson: function(action)
	{
		var jsonRet = new Object;
		if(action.result)
		{
			jsonRet = action.result;
		}else if(action.response) 
		{
			if(action.response.tomeout)
			{
				jsonRet.success = false;
				jsonRet.message = "Client operation time out, maybe server down or not responsible.";
			}else if(action.response.responseText)
			{
				jsonRet = Ext.JSON.decode(action.response.responseText);
			}
		}
		return jsonRet;
	}
});