<%@page import="com.food.common.util.FoodWebTool, com.food.common.constants.*, com.general.common.constants.* "%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<link href="<%=basePath%>css/dataSearchComp.css" rel="stylesheet">

<div id="demo" class="yui3-skin-sam hide-pg">
    <!-- search form area-->
    <form id="searchForm">
        <input type="text" id="searchKeyword" name="searchKeyword" value="巧克力">
        <input type="submit" id="searchBtn" value="搜索" class="yui3-button">
        
        <p id="searchKeywordTag" class="searchKeywordTag">
        	
        </p>
    </form>
    
    <!-- order area -->
    <div class="commonContainer">
    	排序：
    	<select id="orderByWithDierction1">
    		<option value="jia_ge<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">价格从低到高</option>
			<option value="jia_ge<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">价格从高到低</option>
			<option value="dan_jia<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">单价从低到高</option>
			<option value="dan_jia<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">单价从高到低</option>
			<option value="xiao_liang<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">销量从低到高</option>		
			<option value="xiao_liang<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">销量从高到低</option>
			<option value="ping_fen<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">评分从低到高</option>
			<option value="ping_fen<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">评分从高到低</option>
			<option value="cu_xiao_jia<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">促销价从低到高</option>
			<option value="cu_xiao_jia<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">促销价从高到低</option>	 		    		
    	</select>    
    </div>
 
    <div class="results"></div>
    <div class="paginator"></div>
    <div class="loading"></div>
</div>
<script src="<%=basePath%>js/dataSearchComp.js"></script>
<script>
YUI().use('DataSearchApp', function (Y) {
    
    var dataSearch = new Y.DataSearchApp({
        container: '#demo'
    });
    
    dataSearch.url = '<%=FoodWebTool.getActionURL(request, FoodConstants.ACTION_SEARCH)%>';  
    dataSearch.containerTemplate = '<ul class="yui3-g" style="padding:2px"></ul>';
    dataSearch.dataItemTemplate = '<li class="yui3-u-1-4" style="height:300px"><div class="resultsItemDiv"><img width="100%" src="http://farm{farm}.staticflickr.com/{server}/{id}_{secret}_q.jpg"><div>标题:{title}<br/>ID:{id}</div></div></li>';  
     
    dataSearch.searchKeywordTagView.url = 'www.baidu.com';
     
    dataSearch.render();
        
    Y.one('#searchBtn').simulate('click');    
});
</script>