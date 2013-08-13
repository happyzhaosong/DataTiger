Ext.define('DC.store.DBTable', {
	extend: 'Ext.data.Store',
    model: 'DC.model.DBTable',//here must use string not new DC.model.ParseTpl(), because this is just definition not instance else following error will be shown 'Uncaught TypeError: Object #<Object> has no method 'read''. 
    autoLoad: false,
    remoteGroup: true,
    remoteSort: true,
    pageSize: pageSize,
    proxy: {
        type: 'ajax',
        url: dataTableListUrl,
        reader: {
            type: 'json',
            root: jsonRootDataTableList,
            totalProperty: jsonTotalResultCount
        },
        // sends single sort as multi parameter
        simpleSortMode: true,
        // sends single group as multi parameter
        simpleGroupMode: true,

        // This particular service cannot sort on more than one field, so grouping === sorting.
        groupParam: 'sort',
        groupDirectionParam: 'dir'
    },
    sorters: [{
        property: 'tableName',
        direction: 'ASC'
    }]
});

Ext.define('DC.store.DBTableColumn', {
	extend: 'Ext.data.Store',
    model: 'DC.model.DBTableColumn',//here must use string not new DC.model.ParseTpl(), because this is just definition not instance else following error will be shown 'Uncaught TypeError: Object #<Object> has no method 'read''. 
    autoLoad: false,    
    remoteGroup: true,
    remoteSort: true,
    pageSize: pageSize,
    proxy: {
        type: 'ajax',
        url: dataTableColumnListUrl,
        reader: {
            type: 'json',
            root: jsonRootDataTableColumnList,
            totalProperty: jsonTotalResultCount
        },
        // sends single sort as multi parameter
        simpleSortMode: true,
        // sends single group as multi parameter
        simpleGroupMode: true,

        // This particular service cannot sort on more than one field, so grouping === sorting.
        groupParam: 'sort',
        groupDirectionParam: 'dir'
    },
    sorters: [{
        property: 'columnName',
        direction: 'ASC'
    }]
});

Ext.define('DC.store.DataTableData', {
	extend: 'Ext.data.Store',
    model: 'DC.model.DataTableData',//here must use string not new DC.model.ParseTpl(), because this is just definition not instance else following error will be shown 'Uncaught TypeError: Object #<Object> has no method 'read''. 
    autoLoad: false,    
    remoteGroup: true,
    remoteSort: true,
    remoteFilter: true,
    pageSize: pageSize,    
    proxy: {
        type: 'ajax',
        url: dataTableDataListUrl,
        reader: {
            type: 'json',
            root: jsonRootDataTableDataList,
            totalProperty: jsonTotalResultCount
        },
        // sends single sort as multi parameter
        simpleSortMode: true,
        // sends single group as multi parameter
        simpleGroupMode: true,

        // This particular service cannot sort on more than one field, so grouping === sorting.
        groupParam: 'sort',
        groupDirectionParam: 'dir'
    }
});

Ext.define('DC.store.DataTableDataDetail', {
	extend: 'Ext.data.Store',
    model: 'DC.model.DataTableDataDetail',
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});