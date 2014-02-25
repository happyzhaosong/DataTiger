<%@ page language="java" import="java.util.*,com.general.common.constants.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <base href="<%=basePath%>">    
    <title>比购网---货比三家,一网打尽  bigounet.com</title>
	<%@ include file="/pages/common/header.jsp" %>	
  </head>
<body class="yui3-skin-sam">
  <!-- <h1>淘宝广告位</h1> -->
  <%//@ include file="/pages/common/menuTop.jsp" %>
  <%@ include file="/pages/dataSearch.jsp" %>
  <%@ include file="/pages/common/footer.jsp" %>
</body>
</html>
