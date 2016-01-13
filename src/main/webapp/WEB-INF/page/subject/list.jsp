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
	<title>葫芦后台管理-科室管理</title>
	<jsp:include page="/WEB-INF/page/pub/head.jsp"></jsp:include>
</head>
<body class="easyui-layout">
<jsp:include page="/WEB-INF/page/pub/top.jsp"></jsp:include>
<jsp:include page="/WEB-INF/page/pub/left.jsp"></jsp:include>
<jsp:include page="/WEB-INF/page/pub/right.jsp"></jsp:include>
<jsp:include page="/WEB-INF/page/pub/bottom.jsp"></jsp:include>
<div data-options="region:'center'">
	<table id="subjectDateGrid" idField="id" style="width:100%;height:100%;" title="科室管理">
		<thead>
		<tr>
			<th data-options="field:'subjectName',width:160">科室名称</th>
		</tr>
		</thead>
	</table>
</div>
<div id="addSubjectWindow" class="easyui-window" title="添加科室" style="width:430px;height:140px;padding:5px;background: #fafafa;"
	 data-options="resizable:false,modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false,shadow:true">
	<div data-options="region:'center'" style="padding:10px;">
		<form id="addSubjectForm" method="post">
			<table cellpadding="2">
				<tr>
					<td>科室名称：</td>
					<td>
						<input class="easyui-textbox" style="width:300px;" type="text" name="subjectName"
							   data-options="required:true,validType:['remote[\'${path}/subject/validateName\',\'name\']']">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false" style="text-align:right;padding:5px 30px 0 0;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="submitNewSubjectInfo()" style="width:80px">确认</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-clear'" href="javascript:void(0)" onclick="clearNewSubjectInfo()" style="width:80px">重填</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="cancelNewSubjectInfo()" style="width:80px">取消</a>
	</div>
</div>
<div id="editSubjectWindow" class="easyui-window" title="修改科室信息" style="width:430px;height:140px;padding:5px;background: #fafafa;"
	 data-options="resizable:false,modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false,shadow:true">
	<div data-options="region:'center'" style="padding:10px;">
		<form id="editSubjectForm" method="post">
            <input type="hidden" name="id" id="subjectId" data-options="required:true">
			<table cellpadding="2">
				<tr>
					<td>科室名称：</td>
					<td>
						<input class="easyui-textbox" style="width:300px;" type="text" name="subjectName"
							   data-options="required:true,delay:600,validType:['remoteValidataSubjectName']">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false" style="text-align:right;padding:5px 30px 0 0;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="submitEditSubjectInfo()" style="width:80px">确认</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-clear'" href="javascript:void(0)" onclick="clearEditSubjectInfo()" style="width:80px">重填</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="cancelEditSubjectInfo()" style="width:80px">取消</a>
	</div>
</div>
<script type="text/javascript">
	$(function(){
        $('#subjectDateGrid').datagrid({
			singleSelect:true,
			url:'${path}/subject/showList',
			method:'post',
			fit:true,
			border:false,
			rownumbers:true,
			striped:true,
			loadFilter:function(data){
				return validateLogin(data);
			},
			tools:[
				{
					iconCls:'icon-add',
					handler:function(){alertAddSubjectForm()}
				},{
					iconCls:'icon-edit',
					handler:function(){alertEditSubjectForm()}
				},{
					iconCls:'icon-remove',
					handler:function(){removeSubjectForm()}
				}],
            onDblClickCell: function(index,field,value){
                alertEditSubjectForm();
            },onLoadError: function(){
				$.messager.alert('错误',"加载数据失败，请稍后再试。。。",'error');
			}
        });
		$.extend($.fn.validatebox.defaults.rules, {
			remoteValidataSubjectName:{
				validator: function (value) {
					var checkR=$.ajax({
						async : false,
						cache : false,
						type : 'post',
						url : '${path}/subject/validateName',
						data : {
							'subjectId': $("#subjectId").val(),
							'name' : value
						}
					}).responseText;
					return checkR==="true";
				},
				message: '该名称已经使用'
			}

		});
	});
	function submitNewSubjectInfo(){
		$('#addSubjectForm').form({
			url:path + "/subject/add",
			onSubmit: function(param){
				return $(this).form('validate');
			},
			success:function(data){
				validateLogin(data);
                if(data == "success"){
                    $.messager.alert('消息',"添加成功！", "info");
					$('#subjectDateGrid').datagrid("reload");
					$('#addSubjectWindow').window('close');
                }else{
                    $.messager.alert('错误',"添加失败！",'error');
                }
			}
		});
		// submit the form
		$('#addSubjectForm').submit();
	}
	function clearNewSubjectInfo(){
		$('#addSubjectForm').form("clear");
	}
	function cancelNewSubjectInfo(){
        clearNewSubjectInfo();
        $('#addSubjectWindow').window('close');
	}
	function alertAddSubjectForm(){
		clearNewSubjectInfo();
		$('#addSubjectWindow').window('open');
	}
	function alertEditSubjectForm(){
		var subjectItem = $("#subjectDateGrid").datagrid("getSelected");
        if(subjectItem){
			$("#editSubjectForm").form("clear");
			$("#editSubjectWindow").window("open");
			$("#editSubjectForm").form({
				onBeforeLoad:function(param){
					showLoading("editSubjectWindow");
				},
				onLoadSuccess: function (data) {
					validateLogin(data);
					hideLoading("editSubjectWindow");
				}
			});
            $("#editSubjectForm").form("load","${path}/subject/getInfoById/?id=" + subjectItem.id);
        }else{
            $.messager.alert('错误',"请选择一条需要修改的数据！",'error');
        }
	}
    function submitEditSubjectInfo(){
        $('#editSubjectForm').form({
            url:path + "/subject/update",
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
                $('#editSubjectWindow').window("close");
                $('#subjectDateGrid').datagrid("reload");
            }
        });
        // submit the form
        $('#editSubjectForm').submit();
    }
    function clearEditSubjectInfo(){
		var subjectItem = $("#subjectDateGrid").datagrid("getSelected");
		$("#editSubjectForm").form("load","${path}/subject/getInfoById/?id=" + subjectItem.id);
    }
    function cancelEditSubjectInfo(){
        $('#editSubjectWindow').window('close');
    }
    function removeSubjectForm(){
        var subjectItem = $("#subjectDateGrid").datagrid("getSelected");
        if(subjectItem){
            $.messager.confirm('确认', '是否真的要删除这条数据？', function(r){
                if (r){
                    $.get(path + "/subject/delete", {"id":subjectItem.id}, function(data){
						validateLogin(data);
                        if(data == "success"){
                            $('#subjectDateGrid').datagrid("reload");
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