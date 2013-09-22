Ext.define('DC.tool.RendererTool', {	
	renderUrlLinkStyle: function(value, p, record) {
		if(value)
		{
		    return Ext.String.format(
		        '<a href="#" title="{0}">{0}</a>',
		        value
		    );
		}else
		{
			var dataObj = utilToolGlobal.getRecordDataObj(record);
			if(dataObj.tableName)
			{
			    return Ext.String.format(
				        '<a href="#" title="' + dataObj.tableName + '">List Table Data</a>'				        
				 );
			}else
			{
				return "";
			}
		}
	},
	
	
	renderUrlLink: function(value, p, record) {
	    return Ext.String.format(
	        '<a href="{0}" target="blank" title="{0}">{0}</a>',
	        value
	    );
	},
	
	renderThreadType: function(value, p, record) {
	    if(value==dThreadTypeGetWebPage)
	    {
	    	return dThreadTypeGetWebPageStr;
	    }

	    if(value==dThreadTypeParsePageData)
	    {
	    	return dThreadTypeParsePageDataStr;
	    }

	    if(value==dThreadTypeTester)
	    {
	    	return dThreadTypeTesterStr;
	    }

	    if(value==dThreadTypeWorker)
	    {
	    	return dThreadTypeWorkerStr;
	    }  
	},
	
	renderThreadAction: function(value, p, record) {
	    if(value=="DOWNLOAD_THREAD_ACTION_CREATE")
	    {
	    	return "Create";
	    }

	    if(value=="DOWNLOAD_THREAD_ACTION_START")
	    {
	    	return "Start";
	    }
	    
	    if(value=="DOWNLOAD_THREAD_ACTION_STOP")
	    {
	    	return "Stop";
	    }
	    
	    if(value=="DOWNLOAD_THREAD_ACTION_DELETE")
	    {
	    	return "Delete";
	    }
	},
	
	renderTimeStr: function(value, p, record) {
		if(value!="")
		{
			var date = new Date();
			date.setTime(value);
		    return date.toLocaleString();
		}else
		{
			return value;
		}
	},
	
	renderTimeInSeconds: function(value, p, record) {
		if(value!="")
		{
			if(value=="-1")
			{
				value = "<font color=red>not run</font>";
			}else
			{
				value = value/1000;
				value += " sec"
			}
		    return value;
		}else
		{
			return "0 sec";
		}
	},
	
	renderThreadStatus: function(value, p, record) {
		var data = utilToolGlobal.getRecordDataObj(record);
		if(data.stopTime!="")
		{
		    return "<font color=red>Stoped</font>";
		}else
		{
			return "<font color=green>Running</font>";
		}
	},
	
	renderBoolValue: function(value, p, record) {
		if(value)
		{
			return "<font color=green><bold>True</bold></font>";
		}else
		{
			return "<font color=red><bold>False</bold></font>";
		}				
	},
	
	renderDataTypeValue: function(value, p, record) {
		if("DATA_TYPE_STRING"==value)
		{
			return "String";
		}else if("DATA_TYPE_NUMBER"==value)
		{
			return "Number";
		}else if("DATA_TYPE_BOOLEAN"==value)
		{
			return "Boolean";
		} 
			
	}
});