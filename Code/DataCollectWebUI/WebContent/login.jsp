<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=<%=Constants.PAGE_CHAR_SET %>">
<title><%=Constants.PAGE_TITLE_LOGIN%></title>
</head>
<%@ include file="/pages/common/header.jsp" %>
<%@ page session="false"%><%/*add this tag to avoid "java.lang.IllegalStateException: Cannot create a session after the response has been committed" exception. http://www.docin.com/p-121049259.html*/ %>
<script type="text/javascript" src="<%=WebTool.getAppJSBaseURL(request)%>login.js"></script>
<body>
<%
String errMsg = (String)request.getParameter(Constants.ERROR_MESSAGE);
if(!StringTool.isEmpty(errMsg))
{
%>
<script type="text/javascript">
alert("<%=errMsg %>");
</script>
<%
}
%>
</body>
</html>