<%@ page language="java" import="java.util.*,com.food.common.util.FoodWebTool, com.food.common.constants.*, com.general.common.constants.*"  contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<link href="<%=basePath%>css/dataSearchComp.css" rel="stylesheet">
<script src="<%=basePath%>js/utilTool.js"></script>
<script src="<%=basePath%>js/dataSearchComp.js"></script>
<div id="demo" class="yui3-skin-sam hide-pg">
	<a name="top"> </a>
    <!-- search form area-->
    <form id="searchForm">
        <input type="text" id="searchKeyword" name="searchKeyword" value="" maxlength="100">
        <input type="reset" id="resetBtn" value="清空" class="yui3-button">
        <input type="submit" id="searchBtn" value="搜索" class="yui3-button">
        
        <ul id="searchKeywordTag" class="searchKeywordTag">
        	
        </ul>
    </form>
    
    <!-- order area -->
    <div id="commonContainer" class="commonContainer">
    	排序：
    	<select id="orderByWithDierction1">
    		<option value="click_count<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">选择排序方式</option>
    		<option value="jia_ge_num<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">价格从低到高</option>
			<option value="jia_ge_num<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">价格从高到低</option>
			<option value="dan_jia_num<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">单价从低到高</option>
			<option value="dan_jia_num<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">单价从高到低</option>			
			<option value="ping_fen_num<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">评分从低到高</option>
			<option value="ping_fen_num<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">评分从高到低</option>
			<option value="hao_ping_lv_num<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">好评率从低到高</option>
			<option value="hao_ping_lv_num<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">好评率从高到低</option>
			<option value="jiao_yi_success_num<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">销量从低到高</option>		
			<option value="jiao_yi_success_num<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">销量从高到低</option>
			<option value="cu_xiao_jia_num<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">促销价从低到高</option>		
			<option value="cu_xiao_jia_num<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">促销价从高到低</option>				 		    		
    	</select>
    	
    	<div id="searchIn" style="position: relative; left: 25; display: inline-block">
	    	<input type="checkbox" name="all" value="0">全选&nbsp;&nbsp;
	    	<input type="checkbox" name="taobao" value="1">天猫
	    	<input type="checkbox" name="tmall" value="2">淘宝
	    	<input type="checkbox" name="jingdong" value="3">京东
    	</div>
    </div>
 
    <div class="results"></div>
    <div class="paginator"></div>
    <div class="loading"></div>
</div>

<center>
	<a href="pages/index.jsp#top" style="position: relative; top: 25;">回顶部</a>
</center>

<script>
YUI().use('DataSearchApp', function (Y) {
    
    Y.one('#resetBtn').hide();
    
    var dataSearch = new Y.DataSearchApp({
        container: '#demo'
    });
    
        
    dataSearch.url = '<%=FoodWebTool.getActionURL(request, FoodConstants.ACTION_SEARCH)%>';  
    dataSearch.containerTemplate = '<ul class="yui3-g" style="padding:2px"></ul>';
    
  	var emptyRowHtmlCode = '<image src="" height="16px" style="display: block"/>';
  	var divPrefix = '<div style="display: block;">';
  	var divSuffix = '</div>';
  
    var templatePrefix = '<li class="yui3-u-1-4" style="height:325px">';    
	templatePrefix += '<div class="resultsItemDiv">';
	
	var templateSuffix = '</div>';
    templateSuffix += '</li>';
    
    var imgItem = '<a href="<%=FoodWebTool.getActionURL(request, FoodConstants.ACTION_SEARCH_CLICK_ITEM)%>id={id}&itemUrl={itemUrl}" title="{biaoTi}" target="_detail"><img width="100%" height="180px" src="{imgUrl}" /></a>';
    var biaoTiItem = '<a href="<%=FoodWebTool.getActionURL(request, FoodConstants.ACTION_SEARCH_CLICK_ITEM)%>id={id}&itemUrl={itemUrl}" title="{biaoTi}" target="_detail" class="biaoTiClass">{biaoTiSummary}</a>';
    var jiaGeItem = '<strong class="itemTextJiaGe">{jiaGe}</strong>';
    var danJiaItem = '<strong class="itemTextDanJia" title="{danJia}"">{danJiaSummary}</strong>';
    var xiaoLiangItem = '<strong class="itemText">销量:{jiaoYiSuccess}</strong>';
    var pingFenItem = '<strong class="itemTextFloatRight">评分:{pingFen}</strong>';
    var shopNameItem = '<a href="{shopUrl}" target="_shop" class="itemTextShopName" style="display:inline" title="{shopName}">{shopNameSummary}</a>';
    var wangWangItem = '<a href="{wangWangUrl}" target="_wangwang" title="点此可以直接和卖家交流选好的宝贝，或相互交流网购体验，还支持语音视频噢。" style="display: inline; float:right"><img src="img/wangwangContact.jpg" width="50px"/></a>'; 
	var shangPinLaiYuanItem = '<image src="img/mall_{shangPinLaiYuan}.jpg" height="16px"/>';
	var shopLevelImgItem = '<image src="{shopLevelImgUrl}" height="16px"/>';
	var haoPingLvItem = '<strong class="itemTextFloatRight">好评率:{haoPingLv}</strong>';  
	var shangChengPeiSongItem = '<strong class="shangChengPeiSongItem_{shangChengPeiSong}">京东配送</strong>';
	var cuXiaoJiaItem = '<a href="<%=FoodWebTool.getActionURL(request, FoodConstants.ACTION_SEARCH_CLICK_ITEM)%>id={id}&itemUrl={itemUrl}" title="{cuXiaoJia}" target="_detail" class="cuXiaoClass">{cuXiaoJiaSummary}</a>';
	
	templateSuffix = divPrefix + shangPinLaiYuanItem + divSuffix + templateSuffix; 
    
    //taobao template
    dataSearch.dataItemTemplate = templatePrefix;
    dataSearch.dataItemTemplate += divPrefix + imgItem + divSuffix;
    dataSearch.dataItemTemplate += divPrefix + biaoTiItem + divSuffix;
    dataSearch.dataItemTemplate += divPrefix + jiaGeItem + danJiaItem + divSuffix;
    dataSearch.dataItemTemplate += divPrefix + cuXiaoJiaItem + divSuffix;
    dataSearch.dataItemTemplate += divPrefix + xiaoLiangItem + pingFenItem + divSuffix;
    dataSearch.dataItemTemplate += divPrefix + shopNameItem + wangWangItem + divSuffix;
    dataSearch.dataItemTemplate += divPrefix + shopLevelImgItem + haoPingLvItem + divSuffix;       
    dataSearch.dataItemTemplate += templateSuffix;    
        
    //tmall template
    dataSearch.dataItemTemplateTMall = templatePrefix;
    dataSearch.dataItemTemplateTMall += divPrefix + imgItem + divSuffix;
    dataSearch.dataItemTemplateTMall += divPrefix + biaoTiItem + divSuffix;
    dataSearch.dataItemTemplateTMall += divPrefix + jiaGeItem + danJiaItem + divSuffix;
    dataSearch.dataItemTemplateTMall += divPrefix + cuXiaoJiaItem + divSuffix;    
    dataSearch.dataItemTemplateTMall += divPrefix + xiaoLiangItem + pingFenItem + divSuffix;
    dataSearch.dataItemTemplateTMall += divPrefix + shopNameItem + wangWangItem + divSuffix;
    dataSearch.dataItemTemplateTMall += templateSuffix;
    
    //jing dong template
    dataSearch.dataItemTemplateJingDong = templatePrefix;
    dataSearch.dataItemTemplateJingDong += divPrefix + imgItem + divSuffix;
    dataSearch.dataItemTemplateJingDong += divPrefix + biaoTiItem + divSuffix;
    dataSearch.dataItemTemplateJingDong += divPrefix + jiaGeItem + pingFenItem + divSuffix;
    dataSearch.dataItemTemplateJingDong += divPrefix + cuXiaoJiaItem + divSuffix;
    dataSearch.dataItemTemplateJingDong += divPrefix + shopNameItem + haoPingLvItem + divSuffix;
    dataSearch.dataItemTemplateJingDong += shangChengPeiSongItem;
    dataSearch.dataItemTemplateJingDong += templateSuffix;  
    
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
    			Y.all('#searchIn input').set('checked', true);
    		}else
    		{
    			Y.all('#searchIn input').set('checked', false);
    		}
    	}
    });
});
</script>