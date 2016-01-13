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

<div data-options="region:'west',split:true,title:'管理面板'" style="width:200px;padding:5px;">
  <ul class="easyui-tree"  data-options="animate:true,fit:true">
    <li data-options="state:'open'">
      <span>系统管理</span>
      <ul>
        <li>
          <span><a href="${path}/system/toIndexPage">欢迎页</a></span>
        </li>
        <li>
          <span><a href="${path}/user/toCurrentPage">当前用户</a></span>
        </li>
        <li>
          <span><a href="${path}/user/unLogin">退出系统</a></span>
        </li>
      </ul>
    </li>
    <li data-options="state:'open'">
      <span><a href="${path}/diagnoseTime/toListPage">出诊管理</a></span>
    </li>
    <li data-options="state:'open'">
      <span><a href="${path}/hospital/toListPage">医院管理</a></span>
    </li>
    <li data-options="state:'open'">
      <span><a href="${path}/subject/toListPage">科室管理</a></span>
    </li>
    <li data-options="state:'open'">
      <span><a href="${path}/doctor/toListPage">医生管理</a></span>
    </li>

  </ul>
</div>
