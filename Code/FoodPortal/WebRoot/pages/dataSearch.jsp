<%@ page language="java" import="java.util.*,com.food.common.util.FoodWebTool, com.food.common.constants.*, com.general.common.constants.*"  contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<link href="<%=basePath%>css/dataSearchComp.css" rel="stylesheet">
<script src="<%=basePath%>js/utilTool.js"></script>
<script src="<%=basePath%>js/dataSearchComp.js"></script>
<div id="demo" class="yui3-skin-sam hide-pg">
	<a name="top"> </a>
    <!-- search form area-->
    <form id="searchForm">
    	<input type="hidden" id="orderByWithDierction1Value" value="click_count<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>" />
        <input type="text" id="searchKeyword" name="searchKeyword" value="" maxlength="100">        
        <input type="submit" id="searchBtn" value="搜索" class="yui3-button">
        <input type="reset" id="resetBtn" value="清空" class="yui3-button">
        
        <ul id="searchKeywordTag" class="searchKeywordTag">
        	
        </ul>
    </form>
    
    
    <div id="commonContainer" class="commonContainer">
    	<!-- searchin area -->
    	<div style="margin-bottom:8px">
	    	范围：
	    	<div id="searchIn" style="position: relative; left: 15; display: inline-block">
		    	<input type="checkbox" name="all" value="0">所有&nbsp;&nbsp;
		    	<input type="checkbox" name="taobao" value="1">天猫
		    	<input type="checkbox" name="tmall" value="2">淘宝
		    	<input type="checkbox" name="jingdong" value="3">京东
		    	<input type="checkbox" name="jingdong" value="4">当当
		    	<input type="checkbox" name="jingdong" value="5">我买网
	    	</div>
    	</div>
    
    
	    <!-- order area -->
	    <div>
	    	 排序：
	    	<ul id="orderByWithDierction1" class="searchKeywordTag">
	        	<li><a href="#" title="价格从低到高" data="jia_ge_num<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">价格↑</a>&nbsp;&nbsp;</li>
	        	<li><a href="#" title="单价从低到高" data="dan_jia_num<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">单价↑</a>&nbsp;&nbsp;</li>
	        	<li><a href="#" title="促销价从低到高" data="cu_xiao_jia_num<%=GeneralConstants.ORDER_BY_ASC_SUFFIX%>">促销价↑</a>&nbsp;&nbsp;</li>
	        	<li><a href="#" title="评分从高到低" data="ping_fen_num<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">评分↓</a>&nbsp;&nbsp;</li>
	        	<li><a href="#" title="好评从高到低" data="hao_ping_lv_num<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">好评↓</a>&nbsp;&nbsp;</li>
	        	<li><a href="#" title="销量从高到低" data="jiao_yi_success_num<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">销量↓</a>&nbsp;&nbsp;</li>
	        	<li><a href="#" title="送积分从高到低" data="song_ji_fen_num<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">送积分↓</a>&nbsp;&nbsp;</li>
	        	<li><a href="#" title="访问量从高到低" data="click_count<%=GeneralConstants.ORDER_BY_DESC_SUFFIX%>">访问量↓</a>&nbsp;&nbsp;</li>
	        </ul>
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
    var xiaoLiangItem = '<strong class="itemText">{jiaoYiSuccess}</strong>';
    var pingFenItem = '<strong class="itemTextFloatRight">{pingFen}</strong>';
    var shopNameItem = '<a href="{shopUrl}" target="_shop" class="itemTextShopName" style="display:inline" title="{shopName}">{shopNameSummary}</a>';
    var wangWangItem = '<a href="{wangWangUrl}" target="_wangwang" title="点此可以直接和卖家交流选好的宝贝，或相互交流网购体验，还支持语音视频噢。" style="display: inline; float:right"><img src="img/wangwangContact.jpg" width="50px"/></a>'; 
	var shangPinLaiYuanItem = '<image src="img/mall_{shangPinLaiYuan}.jpg" height="16px"/>';
	var shopLevelImgItem = '<image src="{shopLevelImgUrl}" height="16px"/>';
	var haoPingLvItem = '<strong class="itemTextFloatRight">{haoPingLv}</strong>';  
	var shangChengPeiSongItem = '<strong class="shangChengPeiSongItem_{shangChengPeiSong}">京东配送</strong>';
	var cuXiaoJiaItem = '<a href="<%=FoodWebTool.getActionURL(request, FoodConstants.ACTION_SEARCH_CLICK_ITEM)%>id={id}&itemUrl={itemUrl}" title="{cuXiaoJia}" target="_detail" class="cuXiaoClass">{cuXiaoJiaSummary}</a>';
	var songJiFenItem = '<strong class="itemText">{songJiFen}</strong>';
	
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
    
    
    //dang dang template
    dataSearch.dataItemTemplateDangDang = templatePrefix;
    dataSearch.dataItemTemplateDangDang += divPrefix + imgItem + divSuffix;
    dataSearch.dataItemTemplateDangDang += divPrefix + biaoTiItem + divSuffix;
    dataSearch.dataItemTemplateDangDang += divPrefix + jiaGeItem + pingFenItem + divSuffix;
    dataSearch.dataItemTemplateDangDang += divPrefix + cuXiaoJiaItem + divSuffix;
    dataSearch.dataItemTemplateDangDang += divPrefix + shopNameItem + haoPingLvItem + divSuffix;
    dataSearch.dataItemTemplateDangDang += templateSuffix;
    
    
    //womai template
    dataSearch.dataItemTemplateWoMai = templatePrefix;
    dataSearch.dataItemTemplateWoMai += divPrefix + imgItem + divSuffix;
    dataSearch.dataItemTemplateWoMai += divPrefix + biaoTiItem + divSuffix;
    dataSearch.dataItemTemplateWoMai += divPrefix + jiaGeItem + pingFenItem + divSuffix;
    dataSearch.dataItemTemplateWoMai += divPrefix + cuXiaoJiaItem + divSuffix;    
    dataSearch.dataItemTemplateWoMai += divPrefix + songJiFenItem + haoPingLvItem + divSuffix;
    dataSearch.dataItemTemplateWoMai += templateSuffix;   
    
    
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
    	}else
    	{
    		if(!currTarget._node.checked)
    		{
    			Y.one('#searchIn input[value="0"]').set('checked', false);
    		}else
    		{
    		    var total = 5;
    		    var checkedAll = true;
    		    for(var i=1;i<=5;i++)
    		    {
    				checkedAll = checkedAll & Y.one('#searchIn input[value="' + i + '"]')._node.checked;
    			}
    			
    			if(checkedAll)
    			{
    				Y.one('#searchIn input[value="0"]').set('checked', true);
    			}
    		}
    	}
    });
    
    
    Y.one('#searchKeyword').plug(Y.Plugin.AutoComplete, {
        resultHighlighter: 'phraseMatch',
        queryDelay: 500,
        requestTemplate: '<%=GeneralConstants.ACTION_PARAM_FILTER_KEYWORD%>={query}',
        source: '<%=FoodWebTool.getActionURL(request, FoodConstants.ACTION_SEARCH_FILTER_KEYWORD)%>',        
        on: {
        	query: function(inputValue, query){
        		inputValue.query = encodeURI(inputValue.query);
        	}
        },
        
        resultTextLocator: 'searchKeyword',
        
        resultListLocator: function (response) {
            // Makes sure an array is returned even on an error.
            if (response.error || !response.success || !response.exist || !response.JSON_RELATED_SEARCH_KEYWORD_LIST || response.JSON_RELATED_SEARCH_KEYWORD_LIST.length==0) {
                return [];
            }
            
            return response.JSON_RELATED_SEARCH_KEYWORD_LIST;
        },
        
        resultFormatter: function (query, results){
        	var template = '{search_keyword} {result_count}';
        	
        	return Y.Array.map(results, function (result) {
			    var data = result.raw;
			
			    // Use string substitution to fill out the tweet template and
			    // return an HTML string for this result.
			    return Y.Lang.sub(template, {
			      search_keyword : data.searchKeyword,
			      result_count   : data.searchResultCount
			    });
	  		});        
       }
        
    });
});
</script>