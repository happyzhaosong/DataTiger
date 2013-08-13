Ext.define('DC.comp.LeftNavMenu', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.leftnavmenu',
    title: 'Main Menu',
    id: idLeftNavMenu,
    region: 'west',
    split: false,
    store: new DC.store.LeftNavMenu(),
    columns: [
        { text: '', dataIndex: 'name', flex: 1 , sortable: false, hideable: false}
    ],
    height: 200,
    width: 150,
    listeners:{
    	cellClick:{
    		fn: function(obj,td,cIdx,record,tr,rIdx)
    		{
    			var data = utilToolGlobal.getRecordDataObj(record);
    			if(actionWebSiteCategoryList==data.action)
    			{   
    				window.top.location = webSiteUrl;				
    			}else if(actionParseTplCategoryList==data.action)
    			{
    				window.top.location = parseTemplateUrl;
    			}else if(actionDataTableList==data.action)
    			{
    				window.top.location = dataTableUrl;
    			}else if(actionLogout==data.action)
    			{
    				window.top.location = logoutUrl;
    			}
    		}
    	}
    }
});