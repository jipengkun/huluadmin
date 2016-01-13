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

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>葫芦后台管理-医院管理</title>
	<jsp:include page="/WEB-INF/page/pub/head.jsp"></jsp:include>
</head>
<body class="easyui-layout">
<jsp:include page="/WEB-INF/page/pub/top.jsp"></jsp:include>
<jsp:include page="/WEB-INF/page/pub/left.jsp"></jsp:include>
<jsp:include page="/WEB-INF/page/pub/right.jsp"></jsp:include>
<jsp:include page="/WEB-INF/page/pub/bottom.jsp"></jsp:include>
<div data-options="region:'center'">
	<table id="hospitalDateGrid" idField="id" style="width:100%;height:100%;" title="医院管理">
		<thead>
		<tr>
			<th data-options="field:'name',width:270">医院名称</th>
			<th data-options="field:'arbitration',width:100">医院资质</th>
			<th data-options="field:'address',width:200,align:'right'">医院地址</th>
			<th data-options="field:'description',width:300,align:'right'">医院简介</th>
		</tr>
		</thead>
	</table>
</div>
<div id="addHospitalWindow" class="easyui-window" title="添加医院" style="width:430px;height:350px;padding:5px;background: #fafafa;"
		   data-options="resizable:false,modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false,shadow:true">
	<div data-options="region:'center'" style="padding:10px;">
		<form id="addHospitalForm" method="post">
			<table cellpadding="2">
				<tr>
					<td>医院名称：</td>
					<td>
						<input class="easyui-textbox" style="width:300px;" type="text" name="name"
							   data-options="required:true,validType:['remoteValidataHospitalName']">
					</td>
				</tr>
				<tr>
					<td>医院资质：</td>
					<td><input class="easyui-textbox" style="width:300px;" type="text" name="arbitration" data-options="required:true"></td>
				</tr>
				<tr>
					<td>医院地址：</td>
					<td><input class="easyui-textbox" style="width:300px;" type="text" name="address" data-options="required:true"></td>
				</tr>
				<tr>
					<td>医院简介：</td>
					<td><input class="easyui-textbox" style="width:300px;height:160px;" type="text" name="description" data-options="multiline:true,required:true"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false" style="text-align:right;padding:5px 30px 0 0;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="submitNewHospitalInfo()" style="width:80px">确认</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-clear'" href="javascript:void(0)" onclick="clearNewHospitalInfo()" style="width:80px">重填</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="cancelNewHospitalInfo()" style="width:80px">取消</a>
	</div>
</div>
<div id="editHospitalWindow" class="easyui-window" title="修改医院信息" style="width:430px;height:350px;padding:5px;background: #fafafa;"
	 data-options="resizable:false,modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false,shadow:true">
	<div data-options="region:'center'" style="padding:10px;">
		<form id="editHospitalForm" method="post">
			<input type="hidden" name="id" id="hospitalId" data-options="required:true">
			<table cellpadding="2">
				<tr>
					<td>医院名称：</td>
					<td>
						<input class="easyui-textbox" style="width:300px;" type="text" name="name"
							   data-options="required:true,delay:600,validType:['remoteValidataHospitalName']">
					</td>
				</tr>
				<tr>
					<td>医院资质：</td>
					<td><input class="easyui-textbox" style="width:300px;" type="text" name="arbitration" data-options="required:true"></td>
				</tr>
				<tr>
					<td>医院地址：</td>
					<td><input class="easyui-textbox" style="width:300px;" type="text" name="address" data-options="required:true"></td>
				</tr>
				<tr>
					<td>医院简介：</td>
					<td><input class="easyui-textbox" style="width:300px;height:160px;" type="text" name="description" data-options="multiline:true,required:true"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false" style="text-align:right;padding:5px 30px 0 0;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="submitEditHospitalInfo()" style="width:80px">确认</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-clear'" href="javascript:void(0)" onclick="clearEditHospitalInfo()" style="width:80px">重填</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="cancelEditHospitalInfo()" style="width:80px">取消</a>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$('#hospitalDateGrid').datagrid({
			url:'${path}/hospital/showList',
			method:'post',
			singleSelect:true,
			fit:true,
			border:false,
			rownumbers:true,
			striped:true,
			loadFilter:function(data){
				return validateLogin(data);
			},
			tools:[{
				iconCls:'icon-add',
				handler:function(){alertAddHospitalForm()}
			},{
				iconCls:'icon-edit',
				handler:function(){alertEditHospitalForm()}
			},{
				iconCls:'icon-remove',
				handler:function(){removeHospitalForm()}
			}],
			onDblClickCell: function(index,field,value){
				alertEditHospitalForm();
			},onLoadError: function(){
				$.messager.alert('错误',"加载数据失败，请稍后再试。。。",'error');
			}
		});
		$.extend($.fn.validatebox.defaults.rules, {
			remoteValidataHospitalName:{
				validator: function (value) {
					var checkR=$.ajax({
						async : false,
						cache : false,
						type : 'post',
						url : '${path}/hospital/validateName',
						data : {
							'hospitalId': $("#hospitalId").val(),
							'name' : value
						}
					}).responseText;
					return checkR==="true";
				},
				message: '该名称已经使用'
			}

		});
	});
	function submitNewHospitalInfo(){
		$('#addHospitalForm').form({
			url:path + "/hospital/add",
			onSubmit: function(param){
				return $(this).form('validate');
			},
			success:function(data){
				validateLogin(data);
				if(data == "success"){
					$.messager.alert('消息',"添加成功！", "info");
					$('#addHospitalWindow').window('close');
					$('#hospitalDateGrid').datagrid("reload");
				}else{
					$.messager.alert('错误',"添加失败！",'error');
				}
			}
		});
		// submit the form
		$('#addHospitalForm').submit();
	}
	function clearNewHospitalInfo(){
		$('#addHospitalForm').form("clear");
	}
	function cancelNewHospitalInfo(){
		clearNewHospitalInfo();
		$('#addHospitalWindow').window('close');
	}
	function alertAddHospitalForm(){
		clearNewHospitalInfo();
		$('#addHospitalWindow').window('open');
	}
	function alertEditHospitalForm(){
		var hospitalItem = $("#hospitalDateGrid").datagrid("getSelected");
		if(hospitalItem){
			$("#editHospitalForm").form("clear");
			$("#editHospitalWindow").window("open");
			$("#editHospitalForm").form({
				onBeforeLoad:function(param){
					showLoading("editHospitalWindow");
				},
				onLoadSuccess: function (data) {
					validateLogin(data);
					hideLoading("editHospitalWindow");
				}
			});
			$("#editHospitalForm").form("load","${path}/hospital/getInfoById/?id=" + hospitalItem.id);
		}else{
			$.messager.alert('错误',"请选择一条需要修改的数据！",'error');
		}
	}
	function submitEditHospitalInfo(){
		$('#editHospitalForm').form({
			url:path + "/hospital/update",
			onSubmit: function(param){
				return $(this).form('validate');
			},
			success:function(data){
				validateLogin(data);
				if(data == "success"){
					$.messager.alert('消息',"修改成功！",'info');
				}else{
					$.messager.alert('错误',"修改失败！",'error');
				}
				$('#editHospitalWindow').window("close");
				$('#hospitalDateGrid').datagrid("reload");
			}
		});
		// submit the form
		$('#editHospitalForm').submit();
	}
	function clearEditHospitalInfo(){
		var hospitalItem = $("#hospitalDateGrid").datagrid("getSelected");
		$("#editHospitalForm").form("load","${path}/hospital/getInfoById/?id=" + hospitalItem.id);
	}
	function cancelEditHospitalInfo(){
		$('#editHospitalWindow').window('close');
	}
	function removeHospitalForm(){
		var subjectItem = $("#hospitalDateGrid").datagrid("getSelected");
		if(subjectItem){
			$.messager.confirm('确认', '是否真的要删除这条数据？', function(r){
				if (r){
					$.get(path + "/hospital/delete", {"id":subjectItem.id}, function(data){
						validateLogin(data);
						if(data == "success"){
							$('#hospitalDateGrid').datagrid("reload");
							$.messager.alert('信息',"删除成功！",'info');
						}else{
							$.messager.alert('错误',"删除失败！",'error');
						}
					});
				}
			});
		}else{
			$.messager.alert('错误',"请选择一条需要删除的数据！",'error');
		}
	}
</script>
</body>
</html>