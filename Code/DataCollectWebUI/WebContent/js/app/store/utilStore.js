Ext.define('DC.store.JavaScriptAction', {
	extend: 'Ext.data.Store',
    fields: [{name: 'actionName', type: 'string'},{name: 'actionValue', type: 'string'}],
    autoLoad: true,
    data : utilToolGlobal.createJavaScriptActionArray()
});

Ext.define('DC.store.Number24', {
	extend: 'Ext.data.Store',
    fields: [{name: 'number', type: 'int'}],
    autoLoad: true,
    data : utilToolGlobal.createConfigNumberArrayString(24)
});

Ext.define('DC.store.Number1000', {
	extend: 'Ext.data.Store',
	fields: [{name: 'number', type: 'int'}],
    autoLoad: true,
    data : utilToolGlobal.createConfigNumberArrayString(1000)
});

//list all parse template
Ext.define('DC.store.ParseTplAll', {
	extend: 'Ext.data.Store',
    model: 'DC.model.ParseTpl',//here must use string not new DC.model.ParseTpl(), because this is just definition not instance else following error will be shown 'Uncaught TypeError: Object #<Object> has no method 'read''. 
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: parseTplListAllUrl,
        reader: {
            type: 'json',
            root: jsonRootParseTplList
        }
    }
});

//list all web driver search by element type
Ext.define('DC.store.WebDriverSearcyByTypeAll', {
	extend: 'Ext.data.Store',
    model: 'DC.model.WebDriverSearchByType',//here must use string not new DC.model.ParseTpl(), because this is just definition not instance else following error will be shown 'Uncaught TypeError: Object #<Object> has no method 'read''. 
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: webDriverSearchByTypeListUrl,
        reader: {
            type: 'json',
            root: jsonRootWebDriverSearchByTypeList
        }
    }
});


//list all data type
Ext.define('DC.store.DataTypeComboBox', {
	extend: 'Ext.data.Store',
    model: 'DC.model.DataTypeComboBox',//here must use string not new DC.model.ParseTpl(), because this is just definition not instance else following error will be shown 'Uncaught TypeError: Object #<Object> has no method 'read''. 
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: dataTypeListUrl,
        reader: {
            type: 'json',
            root: jsonRootDataTypeList
        }
    }
});

//list web site used web driver browser type
Ext.define('DC.store.WebDriverBrowserType', {
	extend: 'Ext.data.Store',
	model: 'DC.model.WebDriverBrowserType',
    autoLoad: true,
    data : utilToolGlobal.createWebDriverBrowserTypeArray()
});

Ext.define('DC.store.TableColumnComboBox', {
	extend: 'Ext.data.Store',
    fields: [{name: 'cName', type: 'string'}],
    autoLoad: false    
});