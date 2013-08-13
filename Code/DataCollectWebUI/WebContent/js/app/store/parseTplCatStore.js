Ext.define('DC.store.ParseTplCategory', {
	extend: 'Ext.data.Store',
    model: 'DC.model.ParseTplCategory',//here must use string not new DC.model.ParseTpl(), because this is just definition not instance else following error will be shown 'Uncaught TypeError: Object #<Object> has no method 'read''. 
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: parseTplCatListUrl,
        reader: {
            type: 'json',
            root: jsonRootParseTplCatList
        }
    }
});