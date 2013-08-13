Ext.define('DC.store.ParseTplItemAction', {
	extend: 'Ext.data.Store',
    model: 'DC.model.ParseTplItemAction',//here must use string not new DC.model.ParseTpl(), because this is just definition not instance else following error will be shown 'Uncaught TypeError: Object #<Object> has no method 'read''. 
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: parseTplItemActionListUrl,
        reader: {
            type: 'json',
            root: jsonRootParseTplItemActionList
        }
    }
});


Ext.define('DC.store.ParseTplItem', {
	extend: 'Ext.data.Store',
    model: 'DC.model.ParseTplItem',//here must use string not new DC.model.ParseTpl(), because this is just definition not instance else following error will be shown 'Uncaught TypeError: Object #<Object> has no method 'read''. 
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: parseTplItemListUrl,
        reader: {
            type: 'json',
            root: jsonRootParseTplItemList
        }
    }
});