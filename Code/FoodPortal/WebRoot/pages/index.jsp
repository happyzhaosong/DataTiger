<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>最爱巧克力</title>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">	
	<%@ include file="/pages/common/header.jsp" %>
  </head>
<body class="yui3-skin-sam">
  <h1>淘宝广告位</h1>
  <%@ include file="/pages/common/menuTop.jsp" %>
  <%@ include file="/pages/dataQkl.jsp" %>
</body>
</html>
