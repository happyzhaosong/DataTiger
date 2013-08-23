<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<link href="<%=basePath%>css/dataSearchComp.css" rel="stylesheet">

<div id="demo" class="yui3-skin-sam hide-pg">
    <form id="searchForm">
        <input type="text" id="searchKeyword" name="searchKeyword" value="chocolate">
        <input type="submit" value="Search" class="yui3-button">
        
        <p id="searchKeywordTag" class="searchKeywordTag">
        	
        </p>
    </form>
 
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
    
    dataSearch.url = 'http://api.flickr.com/services/rest/?';  
    dataSearch.containerTemplate = '<ul class="yui3-g" style="padding:8px"></ul>';
    dataSearch.dataItemTemplate = '<li class="yui3-u-1-4" style="height:300px"><div class="resultsItemDiv"><img width="100%" src="http://farm{farm}.staticflickr.com/{server}/{id}_{secret}_q.jpg"><div>标题:{title}<br/>ID:{id}</div></div></li>';  
     
    dataSearch.searchKeywordTagView.url = 'www.baidu.com';
     
    dataSearch.render();
        
    Y.one('form .yui3-button').simulate('click');    
});
</script>