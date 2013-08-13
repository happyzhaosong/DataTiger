Ext.define('DC.store.LeftNavMenu', {
     extend: 'Ext.data.Store',
     //storeId:'navLinkStore',
     model:'DC.model.LeftNavMenu',
     autoLoad: false,
     proxy: {
        type: 'ajax',
        url: menuListUrl,
        reader: {
            type: 'json',
            root: 'JSON_MENU_LIST'
        }
     }
});