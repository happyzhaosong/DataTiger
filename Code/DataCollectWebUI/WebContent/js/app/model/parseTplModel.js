
Ext.define('DC.model.ParseTplItemAction', {
	 extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'int'},
        {name: 'parseItemId', type: 'int'},
        {name: 'byEleType', type: 'string'},
        {name: 'byEleVal', type: 'string'},
        {name: 'byEleAction', type: 'string'}
    ]
});



Ext.define('DC.model.ParseTplItem', {
	 extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'int'},
        {name: 'parseId', type: 'int'},
        {name: 'dataType', type: 'string'},  
        {name: 'ifCheckRepeatColumn', type: 'boolean'},
        {name: 'ifUrl', type: 'boolean'},
        {name: 'ifDirectGetText', type: 'boolean'},        
        {name: 'saveToTable', type: 'string'},
        {name: 'saveToColumn', type: 'string'},
        {name: 'columnDesc', type: 'string'},
        {name: 'pageUrlAsValue', type: 'boolean'},
        {name: 'useThisSettingUrlCharactor', type: 'string'},        
        {name: 'byEleType', type: 'string'},
        {name: 'byEleVal', type: 'string'},
        {name: 'byTagAttribute', type: 'string'}, 
        {name: 'parseValueRegExp', type: 'string'}, 
        {name: 'parseValueString', type: 'string'}, 
        {name: 'charactor', type: 'string'},  
        {name: 'notCharactor', type: 'string'},
        {name: 'defaultVal', type: 'string'},
        {name: 'numberFormat', type: 'string'},
        {name: 'dateFormat', type: 'string'},
        {name: 'srcRegExp', type: 'string'},         
        {name: 'destRegExp', type: 'string'},
        {name: 'regExpItemRelation', type: 'boolean'},
        {name: 'execJavascript', type: 'string'},
        {name: 'selected', type: 'boolean'}
    ]
});

