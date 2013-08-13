var hiddenFieldsForm = Ext.create('Ext.form.Panel', {	
    hidden: true,
    defaultType: 'hiddenfield',
    items: [{
    	id: idSiteCatIdHidden,
        value: '-1'
    },
    {
    	id: idSiteIdHidden,
        value: '-1'
    },{
    	id: idParseTplCatIdHidden,
        value: '-1'
    },
    {
    	id: idParseTplIdHidden,
        value: '-1'
    },
    {
    	id: idParseTplItemIdHidden,
        value: '-1'
    },
    {
    	id: idDataTableNameHidden,
        value: ''
    }]
});