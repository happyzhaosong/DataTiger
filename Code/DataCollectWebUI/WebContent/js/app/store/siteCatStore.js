Ext.define('DC.store.SiteCategory', {
	extend: 'Ext.data.Store',
    model: 'DC.model.SiteCategory',
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: siteCatListUrl,
        reader: {
            type: 'json',
            root: jsonRootSiteCatList
        }
    }
});
