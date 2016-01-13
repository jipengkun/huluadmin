<%--
  Created by IntelliJ IDEA.
  User: huangyiwei
  Date: 2015/9/22
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/page/pub/common.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>葫芦后台管理</title>
	<jsp:include page="../pub/head.jsp"></jsp:include>
</head>
<body class="easyui-layout">
<jsp:include page="../pub/top.jsp"></jsp:include>
<jsp:include page="../pub/left.jsp"></jsp:include>
<jsp:include page="../pub/right.jsp"></jsp:include>
<jsp:include page="/WEB-INF/page/pub/bottom.jsp"></jsp:include>
<div data-options="region:'center',title:'欢迎'">
	欢迎${sessionScope.USER_SESSION_KEY.name}登陆葫芦后台管理系统
</div>
</body>
</html>