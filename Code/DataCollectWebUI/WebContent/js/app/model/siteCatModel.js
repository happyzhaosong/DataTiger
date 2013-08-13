Ext.define('DC.model.SiteCategory', {
     extend: 'Ext.data.Model',
     fields: [
         {name: 'id', type: 'int'},
         {name: 'name', type: 'string'},
         {name: 'desc', type: 'string'},
         {name: 'selected', type: 'bool'}
     ]
});