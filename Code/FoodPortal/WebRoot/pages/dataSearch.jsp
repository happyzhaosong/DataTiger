<%@ page language="java" import="java.util.*,com.food.common.util.FoodWebTool, com.food.common.constants.*, com.general.common.constants.*"  contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<link href="<%=basePath%>css/dataSearchComp.css" rel="stylesheet">
<script src="<%=basePath%>js/utilTool.js"></script>
<script src="<%=basePath%>js/dataSearchComp.js"></script>
<div id="demo" class="yui3-skin-sam hide-pg">
    <!-- search form area-->
    <form id="searchForm">
        <input type="text" id="searchKeyword" name="searchKeyword" value="">
        <input type="reset" id="resetBtn" value="清空" class="yui3-button">
        <input type="submit" id="searchBtn" value="搜索" class="yui3-button">
        
        <ul id="searchKeywordTag" class="searchKeywordTag">
        	
        </ul>
    </form>
    
    <!-- order area -->
    <div class="commonContainer">
    	排序：
    	<select id="orderByWithDierction1">
    		<option value="jia_ge_num<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">价格从低到高</option>
			<option value="jia_ge_num<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">价格从高到低</option>
			<option value="dan_jia_num<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">单价从低到高</option>
			<option value="dan_jia_num<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">单价从高到低</option>
			<option value="jiao_yi_success_num<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">销量从低到高</option>		
			<option value="jiao_yi_success_num<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">销量从高到低</option>
			<option value="ping_fen_num<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">评分从低到高</option>
			<option value="ping_fen_num<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">评分从高到低</option>
			<option value="hao_ping_lv_num<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">好评率从低到高</option>
			<option value="hao_ping_lv_num<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">好评率从高到低</option>			
			<option value="cu_xiao_jia_num<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">促销价从低到高</option>
			<option value="cu_xiao_jia_num<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">促销价从高到低</option>	 		    		
    	</select>    
    </div>
 
    <div class="results"></div>
    <div class="paginator"></div>
    <div class="loading"></div>
</div>

<script>
YUI().use('DataSearchApp', function (Y) {
    
    Y.one('#resetBtn').hide();
    
    var dataSearch = new Y.DataSearchApp({
        container: '#demo'
    });
    
    dataSearch.url = '<%=FoodWebTool.getActionURL(request, FoodConstants.ACTION_SEARCH)%>';  
    dataSearch.containerTemplate = '<ul class="yui3-g" style="padding:2px"></ul>';
    
    dataSearch.dataItemTemplate = '<li class="yui3-u-1-4" style="height:300px">';    
    dataSearch.dataItemTemplate += '<div class="resultsItemDiv">';
    dataSearch.dataItemTemplate += '<a href="{itemUrl}" title="{biaoTi}" target="_detail"><img width="100%" height="180px" src="{imgUrl}"><div>{biaoTiSummary}</a>';
    dataSearch.dataItemTemplate += '<div style="display: block;"><strong style="font-family: arial;color: #f40">{jiaGe}</strong> <strong style="font-family: arial; font-size: 12px; float: right">评分：{pingFen}</strong></div>';
    dataSearch.dataItemTemplate += '<a href="{shopUrl}" target="_shop" style="color: #666;display: block;">{shopName}</a>';
    dataSearch.dataItemTemplate += '<image src="{shopLevelImgUrl}"/>';    
    dataSearch.dataItemTemplate += '</div>';
    dataSearch.dataItemTemplate += '</li>';
    
    dataSearch.systemErrorMessageBusy = '<%=GeneralConstants.ERROR_MESSAGE_SYSTEM_BUSY%>';
    dataSearch.searchNoResultInfoPrefix = '<%=GeneralConstants.SEARCH_NO_RESULT_INFO_PREFIX%>';
    dataSearch.searchNoResultInfoSuffix = '<%=GeneralConstants.SEARCH_NO_RESULT_INFO_SUFFIX%>';
    
    dataSearch.searchKeywordTagView.url = '<%=FoodWebTool.getActionURL(request, FoodConstants.ACTION_SEARCH_GET_FOOD_SEARCH_KEYWORDS)%>';  
 
    dataSearch.render();       
        
    Y.one('#orderByWithDierction1').on('change', function(e){      
    	Y.one('#searchBtn').simulate('click');	
    });    
          
    Y.one('#searchBtn').simulate('click');
});
</script>