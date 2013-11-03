Ext.define('DC.model.DBTable', {
	 extend: 'Ext.data.Model',
    fields: [
        {name: 'tableName', type: 'string'},
        {name: 'tableDesc', type: 'string'}
    ]
});

Ext.define('DC.model.DBTableColumn', {
	 extend: 'Ext.data.Model',
   fields: [
       {name: 'tableName', type: 'string'},
       {name: 'columnName', type: 'string'},
       {name: 'columnType', type: 'string'},
       {name: 'columnMaxLen', type: 'string'},
       {name: 'columnDesc', type: 'string'}
   ]
});

Ext.define('DC.model.DataTableData', {
	 extend: 'Ext.data.Model',
  fields: [
      {name: 'ID' , type: 'int'},
      {name: dataColumnDownloadTaskId , type: 'int'},
      {name: dataColumnDownloadTaskPageUrl , type: 'string'},
      {name: dataColumnDownloadTaskLevel , type: 'int'},
      {name: dataColumnDownloadTaskDataParseTime , type: 'string'},
      {name: dataColumnDownloadTaskUselessContentPage , type: 'int'}
  ]
});

Ext.define('DC.model.DataTableDataDetail', {
	 extend: 'Ext.data.Model',
 fields: [
     {name: 'cName', type: 'string'},
     {name: 'cValue', type: 'string'}
 ]
});