Ext.define('DC.model.WebDriverBrowserType', {
	 extend: 'Ext.data.Model',
  fields: [
      {name: 'typeName', type: 'string'},
      {name: 'typeLabel', type: 'string'}
  ]
});

Ext.define('DC.model.WebDriverSearchByType', {
	 extend: 'Ext.data.Model',
  fields: [
      {name: 'typeName', type: 'string'},
      {name: 'typeLabel', type: 'string'}
  ]
});

Ext.define('DC.model.DataTypeComboBox', {
	 extend: 'Ext.data.Model',
 fields: [
     {name: 'typeName', type: 'string'},
     {name: 'typeLabel', type: 'string'}
 ]
});

Ext.define('DC.model.ParseTpl', {
	 extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'int'},
        {name: 'categoryId', type: 'int'},
        {name: 'name', type: 'string'},
        {name: 'desc', type: 'string'},       
        {name: 'saveToTable', type: 'string'},       
        {name: 'parseTplItemList', type: 'string'},
        {name: 'selected', type: 'boolean'}
    ]
});