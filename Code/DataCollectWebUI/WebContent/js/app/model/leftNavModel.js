Ext.define('DC.model.LeftNavMenu', {
     extend: 'Ext.data.Model',
     fields: [
         {name: 'id', type: 'number'},
         {name: 'name', type: 'string'},
         {name: 'action', type: 'string'},
         {name: 'desc', type: 'string'},
         {name: 'menuUrl', type: 'string'}
     ]
});