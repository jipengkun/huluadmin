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
$(function(){
  $("#loading").fadeOut();
});
</script>

<div id='loading' style='position:absolute;width:100%;height:100%;top:0;left:0;background-color:#f3f3f3;z-index:999;'>
  &nbsp;
</div>

<div data-options="region:'north',border:false" style="height:60px;background:#2d3e50;color:#ffffff;padding:10px;font-size: 1.5em;">
  葫芦后台管理
</div>
