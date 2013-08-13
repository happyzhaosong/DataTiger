Ext.define('DC.store.SiteContentPageCheck', {
	extend: 'Ext.data.Store',
    model: 'DC.model.SiteContentPageCheck',//here must use string not new DC.model.ParseTpl(), because this is just definition not instance else following error will be shown 'Uncaught TypeError: Object #<Object> has no method 'read''. 
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: siteContentPageCheckListUrl,
        reader: {
            type: 'json',
            root: jsonRootSiteContentPageCheckList
        }
    }
});

Ext.define('DC.store.SitePageLinkParse', {
	extend: 'Ext.data.Store',
    model: 'DC.model.SitePageLinkParse',//here must use string not new DC.model.ParseTpl(), because this is just definition not instance else following error will be shown 'Uncaught TypeError: Object #<Object> has no method 'read''. 
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: sitePageLinkParseListUrl,
        reader: {
            type: 'json',
            root: jsonRootSitePageLinkParseList
        }
    }
});

Ext.define('DC.store.SiteLoginAccount', {
	extend: 'Ext.data.Store',
    model: 'DC.model.SiteLoginAccount',//here must use string not new DC.model.ParseTpl(), because this is just definition not instance else following error will be shown 'Uncaught TypeError: Object #<Object> has no method 'read''. 
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: siteLoginAccountListUrl,
        reader: {
            type: 'json',
            root: jsonRootSiteLoginAccountList
        }
    }
});

Ext.define('DC.store.Site', {
	extend: 'Ext.data.Store',
    model: 'DC.model.Site',
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: siteListUrl,
        reader: {
            type: 'json',
            root: jsonRootSiteList
        }
    }
});


Ext.define('DC.store.SiteTestThreadListInfo', {
	extend: 'Ext.data.Store',
    model: 'DC.model.SiteTestThreadListInfo',
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: testThreadListUrl,
        reader: {
            type: 'json',
            root: jsonRootSiteTestThreadList
        }
    }
});


Ext.define('DC.store.SiteTestTaskListInfo', {
	extend: 'Ext.data.Store',
    model: 'DC.model.SiteTestTaskListInfo',
    autoLoad: false,
    remoteGroup: true,
    remoteSort: true,
    pageSize: pageSize,
    proxy: {
        type: 'ajax',
        url: testTaskListUrl,
        reader: {
            type: 'json',
            root: jsonRootSiteTestTaskList,
            totalProperty: jsonTotalResultCount
        }
    },
    sorters: [{
        property: 'id',
        direction: 'ASC'
    }]
});


Ext.define('DC.store.SiteTestMQMessageListInfo', {
	extend: 'Ext.data.Store',
    model: 'DC.model.SiteTestMQMessageListInfo',
    autoLoad: false,
    remoteGroup: true,
    // allow the grid to interact with the paging scroller by buffering
    //buffered: true,
    //leadingBufferZone: 100,
    //pageSize: pageSize,
    proxy: {
        type: 'ajax',
        url: testMQMessageListUrl,
        reader: {
            type: 'json',
            root: jsonRootSiteTestMQMessageList,
            totalProperty: jsonTotalResultCount
        }//,
        // sends single sort as multi parameter
        //simpleSortMode: true,
        // sends single group as multi parameter
        //simpleGroupMode: true,

        // This particular service cannot sort on more than one field, so grouping === sorting.
        //groupParam: 'sort',
        //groupDirectionParam: 'dir'
    }/*,
    sorters: [{
        property: 'sendTime',
        direction: 'ASC'
    }],
    listeners: {

        // This particular service cannot sort on more than one field, so if grouped, disable sorting
        groupchange: function(store, groupers) {
            var sortable = !store.isGrouped(),
                headers = grid.headerCt.getVisibleGridColumns(),
                i, len = headers.length;
            
            for (i = 0; i < len; i++) {
                headers[i].sortable = (headers[i].sortable !== undefined) ? headers[i].sortable : sortable;
            }
        },

        // This particular service cannot sort on more than one field, so if grouped, disable sorting
        beforeprefetch: function(store, operation) {
            if (operation.groupers && operation.groupers.length) {
                delete operation.sorters;
            }
        }
    }*/
});