<%--
  Created by IntelliJ IDEA.
  User: huangyiwei
  Date: 2015/9/22
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/page/pub/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
  var path = "${path}";
  var jsPath = "${jsPath}";
  var cssPath = "${cssPath}";
  var imagesPath = "${imagesPath}";
  var ossFilePath = "${ossFilePath}";
</script>

<link rel="stylesheet" type="text/css" href="${jsPath}/jquery-easyui-1.4.3/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css" href="${jsPath}/jquery-easyui-1.4.3/themes/icon.css">

<script type="text/javascript" src="${jsPath}/jquery.min.v.1.11.3.js"></script>
<script type="text/javascript" src="${jsPath}/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${jsPath}/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${jsPath}/ajaxupload.js"></script>
<script type="text/javascript" src="${jsPath}/validate.js"></script>
<script type="text/javascript" src="${jsPath}/myEasyui.js"></script>
<script type="text/javascript" src="${jsPath}/dateFormat.js"></script>