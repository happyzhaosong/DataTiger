Ext.define('DC.tool.UtilTool', {
//	extends: 'Ext.Base',
	getIdListStrInArray: function(modelArray, seperator)
	{
		var ret = "";
		if(modelArray!=null)
		{
			var len = modelArray.length;
			for(var i=0;i<len;i++)
			{
				var modelObj = modelArray[i];
				if(modelObj instanceof Ext.data.Model)
				{
					if(modelObj.raw && modelObj.raw.id)
					{
						ret = ret + modelObj.raw.id;
						if(i<len-1)
						{
							ret = ret + seperator;
						}						
					}
				}
			}
		}
		return ret;
	},
	
	getThreadIdListStrInArray: function(modelArray, seperator)
	{
		var ret = "";
		if(modelArray!=null)
		{
			var len = modelArray.length;
			for(var i=0;i<len;i++)
			{
				var modelObj = modelArray[i];
				if(modelObj instanceof Ext.data.Model)
				{
					if(modelObj.getData() && modelObj.getData().threadId)
					{
						ret = ret + modelObj.getData().threadId;
						if(i<len-1)
						{
							ret = ret + seperator;
						}						
					}
				}
			}
		}
		return ret;
	},
	
	getParameterObjArrayByProps: function(obj) { 
	    var retArray = new Array;
	    for(var p in obj){ 
	        if(typeof(obj[p])!="function"){ 
	        	var tmpObj = new Object;
	        	tmpObj.cName = p;
	        	tmpObj.cValue = obj[p];
	        	retArray.push(tmpObj);
	        } 
	    } 
	    return retArray;
	},
	
	getParameterObjArrayByModelFields: function(modelName) { 
	    var retArray = new Array;
	    var model = Ext.ModelManager.getModel(modelName);
	    for(var field in model.getFields()){ 
	        if(field){ 
	        	var tmpObj = new Object;
	        	tmpObj.cName = field.name;
	        	tmpObj.cValue = field.name;
	        	retArray.push(tmpObj);
	        } 
	    } 
	    return retArray;
	},
	
	getParameterByProps: function(obj) { 
	    var props = "";
	    for(var p in obj){ 
	        if(typeof(obj[p])!="function"){ 
	        	props+= (p + seperatorEqual + obj[p] + seperatorAnd);
	        } 
	    } 
	    return props;
	},
	
	getObjPropValueByName: function(obj, propName)
	{
	    var ret = "";
	    for(var p in obj){ 
	        if(typeof(obj[p])!="function"){ 
	        	if(p==propName)
	        	{
	        		ret = obj[p];
	        		break;
	        	}
	        } 
	    } 
	    return ret;
	},
	
	createWebDriverBrowserTypeArray: function(size)
	{
		var ret = new Array;
		for(var i=0;i<5;i++)
		{
			var obj = new Object();
			if(i==0)
			{
				obj.typeName = browserTypeChrome;
				obj.typeLabel = 'Chrome';
			}else if(i==1)
			{
				obj.typeName = browserTypeFF10;
				obj.typeLabel = 'FireFox';
			}else if(i==2)
			{
				obj.typeName = browserTypeHU;
				obj.typeLabel = 'Html Unit';
			}
			ret.push(obj);
		}
		return ret;
	},
	
	createJavaScriptActionArray: function()
	{
		var ret = new Array;
		var actionClick = new Object();
		actionClick.actionName = jsActionClick;
		actionClick.actionValue = jsActionClick;
		ret.push(actionClick);
		return ret;
	},
	
	createConfigNumberArrayString: function(size)
	{
		var ret = new Array;
		for(i=0;i<size;i++)
		{
			var obj = new Object();
			obj.number = i;
			ret[i] = obj;
		}
		return ret;
	},
	
	createObjArray: function(className, size)
	{
		var ret = new Array;
		for(i=0;i<size;i++)
		{
			var obj = Ext.create(className);
			ret[i] = obj;
		}
		return ret;
	},
	
	getRecordDataObj: function(recordObj)
	{
		var dataObj = null;
		if(recordObj!=null)
		{
			dataObj = recordObj;
			if(recordObj.raw!=null)
			{
				dataObj = recordObj.raw;
			}else if(recordObj.data!=null)
			{
				dataObj = recordObj.data;
			}
		}
		return dataObj;
	},
	
	
	addSubmitParamsWithIds: function(submitParams, ids)
	{
		if(submitParams==null) submitParams = new Object;
		if(submitParams.params==null) submitParams.params = new Object;
		submitParams.params.DATA_IDS = ids;	
		return submitParams;
	},
	
	
	addSubmitParamsWithDelIds: function(submitParams, delIds)
	{
		if(submitParams==null) submitParams = new Object;
		if(submitParams.params==null) submitParams.params = new Object;
		submitParams.params.DATA_DELETE_IDS = delIds;	
		return submitParams;
	},
	
	addSubmitParamsWithThreadIds: function(submitParams, threadIds)
	{
		if(submitParams==null) submitParams = new Object;
		if(submitParams.params==null) submitParams.params = new Object;
		submitParams.params.DATA_THREAD_IDS = threadIds;	
		return submitParams;
	},
	
	addWebSiteId: function(submitParams, siteId)
	{
		if(submitParams==null) submitParams = new Object;
		if(submitParams.params==null) submitParams.params = new Object;
		submitParams.params.DATA_WEB_SITE_ID = siteId;
		return submitParams;
	},
	
	
	createPanelLoadObject: function(showPanelId)
	{
		var retObj = new Object;
		retObj.scope = this;
		retObj.callback = function(records, operation, success) {
			    //alert("ok");
				if(success)
				{
					Ext.getCmp(showPanelId).show();
				}else
				{
					Ext.Msg.alert('Load panel failed', operation.response.statusText);	
				}
	        };
	    return retObj;
	},
	
	ifObjectArrayType: function(obj)
	{
		var ret = false;
		if(Object.prototype.toString.apply(obj)=="[object Array]") ret = true;
		return ret;
	},
	
	ifObjectObjectType: function(obj)
	{
		var ret = false;
		if(Object.prototype.toString.apply(obj)=="[object Object]") ret = true;
		return ret;
	},
	
	hasArrayItems: function(obj)
	{
		var ret = false;
		if(obj.items)
		{
			ret = utilToolGlobal.ifObjectArrayType(obj.items);
		}
		return ret;
	},
	
	buildReloadPanelListUrl : function (idParamValue, loadPanelId)
	{
		var newUrl = Ext.getCmp(loadPanelId).getStore().getProxy().url;
		newUrl += seperatorAnd;
		newUrl += dataId;	
		newUrl += seperatorEqual;
		newUrl += idParamValue;
		return newUrl;
	},
	
	checkSessionTimeOut: function(json)
	{
		if(!json.success)
		{	
			if(json.code == errCodeSessionTimeOut)
			{
				Ext.Msg.alert('Session Timeout', json.message);
				window.location.href = loginUrl;
			}
		}
	},
	
	createOrderByDirectionComboBox: function()
	{
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
		
		return orderByDirectionComboBox;
	},
	
	createNumberComboBox: function(maxNumber, fieldLabel, emptyText)
	{
		var tmpStore = Ext.create('Ext.data.Store', {
		    fields: ['number']		    
		});
		
		var dataArray = new Array;
		for(var i=0;i<=maxNumber;i++)
		{
			dataArray.push({i:i});
		}
		tmpStore.data = dataArray;
			
		var retComboBox = Ext.create('Ext.form.field.ComboBox', {
		    fieldLabel: fieldLabel,
		    store: tmpStore,
		    queryMode: 'local',
		    forceSelection: true,
		    editable:false,
		    labelAlign: 'right',
		    displayField: 'number',
		    valueField: 'number',
		    emptyText:emptyText
		});
	
		return retComboBox;
	}
});