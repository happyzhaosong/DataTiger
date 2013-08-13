Ext.define('DC.comp.JavaScriptAction', {
     extend: 'Ext.form.field.ComboBox',
     store: new DC.store.JavaScriptAction(),     
     displayField: 'actionName',
     valueField: 'actionValue',
     queryMode: 'local',
     forceSelection: true,
     editable:false,
     emptyText:'Select 1 action',  
     alias:'widget.JavaScriptAction'
});


Ext.define('DC.comp.NumberComboBoxDayHour', {
     extend: 'Ext.form.field.ComboBox',
     store: new DC.store.Number24(),     
     displayField: 'number',
     valueField: 'number',
     queryMode: 'local',
     forceSelection: true,
     editable:false,
     emptyText:'Select hour in one day...',  
     alias:'widget.numbercomboboxdayhour'
});

Ext.define('DC.comp.NumberComboBox1000', {
    extend: 'Ext.form.field.ComboBox',
    store: new DC.store.Number1000(),     
    displayField: 'number',
    valueField: 'number',
    queryMode: 'local',
    forceSelection: true,
    editable:false,
    emptyText:'Select seconds...',  
    alias:'widget.numbercombobox1000'
});

//list all parse template in combox
Ext.define('DC.comp.ParseTplAllComboBox', {
     extend: 'Ext.form.field.ComboBox',     
     displayField: 'name',
     valueField: 'id',
     store: new DC.store.ParseTplAll(),
     triggerAction : 'all',
     queryMode: 'remote',
     forceSelection: true,
     editable:false,
     emptyText:'Select Parse Template...',  
     alias:'widget.parsetplallcombobox'
});

//list all web driver search by element type
Ext.define('DC.comp.WebDriverSearchByComboBox', {
     extend: 'Ext.form.field.ComboBox',     
     displayField: 'typeLabel',
     valueField: 'typeName',
     store: new DC.store.WebDriverSearcyByTypeAll(),
     triggerAction : 'all',
     queryMode: 'remote',
     forceSelection: true,
     editable:false,
     emptyText:'Search web element by...',  
     alias:'widget.WebDriverSearchByComboBox'
});

//list all web driver browser type
Ext.define('DC.comp.WebDriverBrowserTypeComboBox', {
     extend: 'Ext.form.field.ComboBox',     
     displayField: 'typeLabel',
     valueField: 'typeName',
     store: new DC.store.WebDriverBrowserType(),
     triggerAction : 'all',
     queryMode: 'local',
     forceSelection: true,
     editable:false,
     emptyText:'Browse web page by...',  
     alias:'widget.WebDriverBrowserTypeComboBox'
});

//list all data type of parse out data
Ext.define('DC.comp.DataTypeComboBox', {
    extend: 'Ext.form.field.ComboBox',
    store: new DC.store.DataTypeComboBox(),     
    displayField: 'typeLabel',
    valueField: 'typeName',
    queryMode: 'local',
    forceSelection: true,
    editable:false,
    emptyText:'Select one data type...',  
    alias:'widget.DataTypeComboBox'
});

Ext.define('DC.comp.TableColumnComboBox', {
    extend: 'Ext.form.field.ComboBox',
    store: new DC.store.TableColumnComboBox(),     
    displayField: 'cName',
    valueField: 'cName',
    queryMode: 'local',
    forceSelection: true,
    editable:false,
    fieldLabel: 'Search By',
    labelAlign: 'right',
    emptyText:'Select one table column...',  
    alias:'widget.TableColumnComboBox'
});