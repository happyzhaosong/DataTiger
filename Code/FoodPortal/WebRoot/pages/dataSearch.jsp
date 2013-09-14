<%@ page language="java" import="java.util.*,com.food.common.util.FoodWebTool, com.food.common.constants.*, com.general.common.constants.*"  contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<link href="<%=basePath%>css/dataSearchComp.css" rel="stylesheet">
<script src="<%=basePath%>js/utilTool.js"></script>
<script src="<%=basePath%>js/dataSearchComp.js"></script>
<div id="demo" class="yui3-skin-sam hide-pg">
	<a name="top"></a>
    <!-- search form area-->
    <form id="searchForm">
        <input type="text" id="searchKeyword" name="searchKeyword" value="">
        <input type="reset" id="resetBtn" value="清空" class="yui3-button">
        <input type="submit" id="searchBtn" value="搜索" class="yui3-button">
        
        <ul id="searchKeywordTag" class="searchKeywordTag">
        	
        </ul>
    </form>
    
    <!-- order area -->
    <div id="commonContainer" class="commonContainer">
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
    	</select>
    	
    	<div id="searchIn" style="position: relative; left: 25; display: inline-block">
	    	<input type="checkbox" name="all" value="0">全选&nbsp;&nbsp;
	    	<input type="checkbox" name="taobao" value="1">淘宝
	    	<input type="checkbox" name="tmall" value="2">天猫
    	</div>
    </div>
 
    <div class="results"></div>
    <div class="paginator"></div>
    <div class="loading"></div>
</div>
<center><a href="#top" style="position: relative; top: 25;">回顶部</a></center>

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
    dataSearch.dataItemTemplate += '<div style="display: block;"><strong class="itemTextJiaGe">{jiaGe}</strong> <strong class="itemTextDanJia">{danJia}</strong> </div>';
    dataSearch.dataItemTemplate += '<div style="display: block;"><strong class="itemText">销量:{jiaoYi}</strong>  <strong class="itemTextFloatRight">评分:{pingFen}</strong></div>';
    dataSearch.dataItemTemplate += '<a href="{shopUrl}" target="_shop" style="color: #666;display: inline;" title="点击访问本店">{shopName}</a> <a href="{wangWangUrl}" target="_wangwang" title="点此可以直接和卖家交流选好的宝贝，或相互交流网购体验，还支持语音视频噢。" style="display: inline; float:right"><img src="img/wangwangContact.jpg" width="50px"/></a></div>';
    dataSearch.dataItemTemplate += '<image src="{shopLevelImgUrl}"/> <strong class="itemTextFloatRight">好评率:{haoPingLv}</strong>';

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
    
    Y.all('#searchIn input').on('click', function(e){ 
    	var currTarget = e.currentTarget;
    	if(currTarget._node.value==0)
    	{
    		if(currTarget._node.checked)
    		{
    			Y.all('#searchIn input').setAttribute("checked", "true");
    		}else
    		{
    			Y.all('#searchIn input').removeAttribute("checked");
    		}
    	}
    });    
});
</script>