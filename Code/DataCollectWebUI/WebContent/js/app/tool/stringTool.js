Ext.define('DC.tool.StringTool', {
//extends: 'Ext.Base',

removeStrInStr: function(srcStr, delStr, seperator)
{
	var ret = "";
	var strArr = srcStr.split(seperator);
	var len = strArr.length;
	for(var i=0;i<len;i++)
	{
		var str = strArr[i];
		if(str.trim()!="" && str!=delStr)
		{
			ret = ret + str + seperator ;
		}
	}
	return ret;
},

containStrCount: function(srcStr, containStr)
{
	var strArr = srcStr.split(containStr);
	var len = strArr.length;
	if(len>=1)
	{
		return len-1;
	}else
	{
		return len;
	}
},

buildUrlParameterPair: function(key, val)
{
	var ret = "";
	ret += seperatorAnd;
	ret += key;
	ret += seperatorEqual;
	ret += val;
	return ret;
},

isEmpty: function(val)
{
	if(val==null || val=="")
	{
		return true;
	}else
	{
		val = val.replace(/(^\s*)|(\s*$)/g, "");  
		if(val=="")
		{
			return true;
		}else
		{
			return false;
		}
	}
},

isEmptySetDefault: function(val, defaultVal)
{
	if(val==null || val=="")
	{
		val = defaultVal;
	}
	return val;
}
});