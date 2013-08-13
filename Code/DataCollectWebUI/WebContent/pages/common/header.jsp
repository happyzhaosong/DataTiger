<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="com.data.collect.common.util.*,com.data.collect.common.constants.*,com.data.collect.common.dto.*,com.data.collect.client.logic.*,java.util.*,com.data.collect.server.manager.*"%>

<link rel="stylesheet" type="text/css" href="<%=WebTool.getExtJSBaseURL(request)%>resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=WebTool.getExtJSBaseURL(request)%>ux/css/CheckHeader.css">
<script type="text/javascript" src="<%=WebTool.getExtJSBaseURL(request)%>ext-all-debug.js"></script>
<script type="text/javascript" src="<%=WebTool.getExtJSBaseURL(request)%>ux/CheckColumn.js"></script>
<script type="text/javascript" src="<%=WebTool.getExtJSBaseURL(request)%>ux/ProgressBarPager.js"></script>

<%@ include file="/pages/common/jsGlobalVar.jsp" %>

<script type="text/javascript">
Ext.require(['Ext.*',
             'Ext.grid.*',
             'Ext.data.*',
             'Ext.util.*',
             'Ext.grid.PagingScroller',
             'Ext.tip.QuickTipManager',
    		 'Ext.ux.ProgressBarPager'
          ]);

var bannerPanel = Ext.create('Ext.panel.Panel', {
	region: 'north',
	//margins: 5,
	height: 50,
	xtype: 'container',
	bodyStyle: 'background:#003366;',
	layout: 'fit',		
	items:[{
		xtype: 'label',		        
        text: 'Data Collect',
        margin: '0 0 50 10',
        cls: 'bannerText'	        
	}]
});

Ext.tip.QuickTipManager.init();
Ext.Ajax.timeout = 120000; //2 minutes

</script>


<script type="text/javascript" src="<%=WebTool.getAppJSBaseURL(request)%>tool/stringTool.js"></script>
<script type="text/javascript" src="<%=WebTool.getAppJSBaseURL(request)%>tool/utilTool.js"></script>
<script type="text/javascript" src="<%=WebTool.getAppJSBaseURL(request)%>tool/formTool.js"></script>
<script type="text/javascript" src="<%=WebTool.getAppJSBaseURL(request)%>tool/rendererTool.js"></script>

<script type="text/javascript">
var stringToolGlobal = new DC.tool.StringTool();
var utilToolGlobal = new DC.tool.UtilTool();
var formToolGlobal = new DC.tool.FormTool();
var rendererToolGlobal = new DC.tool.RendererTool();
</script>

<script type="text/javascript" src="<%=WebTool.getAppJSBaseURL(request)%>comp/hiddenFieldComp.js"></script>

<script type="text/javascript" src="<%=WebTool.getAppJSBaseURL(request)%>model/utilModel.js"></script>
<script type="text/javascript" src="<%=WebTool.getAppJSBaseURL(request)%>store/utilStore.js"></script>
<script type="text/javascript" src="<%=WebTool.getAppJSBaseURL(request)%>comp/utilComp.js"></script>

<script type="text/javascript" src="<%=WebTool.getAppJSBaseURL(request)%>model/dataTableModel.js"></script>
<script type="text/javascript" src="<%=WebTool.getAppJSBaseURL(request)%>store/dataTableStore.js"></script>
<script type="text/javascript" src="<%=WebTool.getAppJSBaseURL(request)%>comp/dataTableComp.js"></script>