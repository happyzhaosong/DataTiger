<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<link href="<%=basePath%>css/dataSearchComp.css" rel="stylesheet">

<div id="demo" class="yui3-skin-sam hide-pg">
    <form id="searchForm">
        <input type="text" id="searchKeyword" name="searchKeyword" value="chocolate">
        <input type="submit" value="Search" class="yui3-button">
        
        <p class="searchKeywordTag">
        	<a href="baidu.com">费列罗</a>&nbsp;&nbsp;<a href="baidu.com">金帝</a>&nbsp;&nbsp;<a href="baidu.com">girl</a>
        </p>
    </form>
    <!-- 
	<ul class="searchKeywordTag">
	    <li>
	      	<a href="baidu.com">费列罗</a>
	    </li>
			         
	    <li>
	      	<a>金帝</a>
	    </li>
	</ul>
	 -->
 
    <div class="results"></div>
    <div class="paginator"></div>
    <div class="loading"></div>
</div>
<script src="<%=basePath%>js/dataSearchComp.js"></script>
<script>
YUI().use('DataSearchApp', function (Y) {
   
   	/* 
    Y.DataSearchApp.ATTRS = {
    	apiConfig: {
    	   value: {
    			api_key: '0c13dc70aa7eb3df87b3fee5caf37080',
                method: 'flickr.photos.search',
                safe_search: 1,
                sort: 'relevance',
                format: 'json',
                license: 4,
                per_page: 20,
                url:'http://api.flickr.com/services/rest/?'
    		}
    	}
    };
    */
    
    var dataSearch = new Y.DataSearchApp({
        container: '#demo'
    });
    
    dataSearch.url = 'http://api.flickr.com/services/rest/?';  
    dataSearch.containerTemplate = '<ul></ul>';
    dataSearch.dataItemTemplate = '<img src="http://farm{farm}.staticflickr.com/{server}/{id}_{secret}_q.jpg">';  
     
    dataSearch.render();
    
    Y.one('form .yui3-button').simulate('click');
});
</script>