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
	<title>葫芦后台管理-医生管理</title>
	<jsp:include page="/WEB-INF/page/pub/head.jsp"></jsp:include>
</head>
<body class="easyui-layout">
<jsp:include page="/WEB-INF/page/pub/top.jsp"></jsp:include>
<jsp:include page="/WEB-INF/page/pub/left.jsp"></jsp:include>
<jsp:include page="/WEB-INF/page/pub/right.jsp"></jsp:include>
<jsp:include page="/WEB-INF/page/pub/bottom.jsp"></jsp:include>
<div data-options="region:'center'">
	<table id="doctorDateGrid" style="width:100%;height:100%;" title="医生管理"  idField="id">
		<thead>
		<tr>
			<th data-options="field:'doctorHeadpic',width:80,align:'center',formatter:doctorHeadImgFormatter">头像</th>
			<th data-options="field:'doctorName',width:100">姓名</th>
			<th data-options="field:'mobile',width:80">联系方式</th>
			<th data-options="field:'subject',width:80,formatter:subjectFormatter">科室</th>
			<th data-options="field:'doctorIntroduction',width:400,formatter:doctorIntroductionFormatter">简介</th>
			<th data-options="field:'id',width:70,align:'center',formatter:diagnoseTimeFormatter">出诊时间</th>
			<th data-options="field:'address',width:200,align:'center'">出诊地址</th>
		</tr>
		</thead>
	</table>
</div>
<div id="addDoctorWindow" class="easyui-window" title="添加医生" style="width:550px;height:350px;padding:5px;background: #fafafa;"
	 data-options="resizable:false,modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false,shadow:true">
	<div data-options="region:'center'" style="padding:10px;">
		<form id="addDoctorForm" method="post">
			<input id="addDoctorHeadpicName" name="doctorHeadpic" type="hidden" value="" >
			<table cellpadding="2">
				<tr>
					<td rowspan="4">
						<img id="addDoctorHeadImg" src="${imagesPath}/doctor_head_img.png" style="width:90px;height:90px;"><br /><br />
						<a id="uploadAddDoctorHeadImg" href="#" class="easyui-linkbutton" style="width:90px;">上传头像</a>
					</td>
					<td>姓名：</td>
					<td><input class="easyui-textbox" style="width:100px;" type="text" name="doctorName" data-options="required:true"></td>
					<td>出诊价格(元)：</td>
					<td><input class="easyui-numberbox" style="width:118px;" value="0" type="text" name="price" data-options="required:true,min:0,precision:0"></td>
				</tr>
				<tr>
					<td>联系方式：</td>
					<td colspan="3">
						<input class="easyui-textbox" style="width:310px;" type="text" name="mobile"
							data-options="required:true,delay:600,validType:['mobile','remoteValidataDoctorMobile']">
					</td>
				</tr>
				<tr>
					<td>名医类型:</td>
					<td>
						<select class="easyui-combobox" name="doctorType"
								data-options="required:true,panelHeight:'auto'">
							<option value="1">国医</option>
							<option value="2">专家</option>
						</select>
					</td>
					<td>科室类别:</td>
					<td>
						<input class="easyui-combobox" name="subjectId"
							data-options="
								width:118,
								url:'${path}/subject/showComboBoxList/?currentId=-1',
								method:'post',
								valueField:'id',
								textField:'text',
								panelHeight:'auto',
								required:true,
								editable:false
						">
					</td>
				</tr>
				<tr>
					<td>所属医院：</td>
					<td colspan="3">
						<input class="easyui-combobox" name="hospitalId" style="width:310px;"
							   data-options="
								url:'${path}/hospital/showComboBoxList/?currentId=-1',
								method:'post',
								valueField:'id',
								textField:'text',
								panelHeight:'180',
								required:true,
								editable:false
						">
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>简介：</td>
					<td colspan="3"><input class="easyui-textbox" style="width:310px;height:100px;" name="doctorIntroduction" data-options="multiline:true,required:true" style="height:60px"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>擅长：</td>
					<td colspan="3"><input class="easyui-textbox" style="width:310px;height:100px;" name="doctorAdept" data-options="multiline:true,required:true" style="height:60px"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>出诊地址：</td>
					<td colspan="3">
						<input class="easyui-textbox" style="width:310px;height:100px;" name="address" data-options="multiline:true,required:true" style="height:60px">
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>经纬度：</td>
					<td colspan="3">
						经度：<input class="easyui-numberbox" style="width:150px;" type="text" name="addressLongitude"
								  data-options="required:true,precision:10"><br />
						纬度：<input class="easyui-numberbox" style="width:150px;" type="text" name="addressLatitude"
								  data-options="required:true,precision:10">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false" style="text-align:right;padding:5px 30px 0 0;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="submitNewDoctorInfo()" style="width:80px">确认</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-clear'" href="javascript:void(0)" onclick="clearNewDoctorInfo()" style="width:80px">重填</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="cancelNewDoctorInfo()" style="width:80px">取消</a>
	</div>
</div>
<div id="editDoctorWindow" class="easyui-window" title="修改医生信息" style="width:550px;height:350px;padding:5px;background: #fafafa;"
	 data-options="resizable:false,modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false,shadow:true">
	<div data-options="region:'center'" style="padding:10px;">
		<form id="editDoctorForm" method="post">
			<input name="id" id="doctorId" type="hidden" value="" >
			<input id="editDoctorHeadpicName" name="doctorHeadpic" type="hidden" value="" >
			<table cellpadding="2">
				<tr>
					<td rowspan="4">
						<img id="editDoctorHeadImg" src="${imagesPath}/doctor_head_img.png" style="width:90px;height:90px;"><br /><br />
						<a id="uploadEditDoctorHeadImg" href="#" class="easyui-linkbutton" style="width:90px;">上传头像</a>
					</td>
					<td>姓名：</td>
					<td><input class="easyui-textbox" style="width:100px;" type="text" name="doctorName" data-options="required:true"></td>
					<td>出诊价格(元)：</td>
					<td><input class="easyui-numberbox" style="width:118px;" value="0" type="text" name="price" data-options="required:true,min:0,precision:0"></td>
				</tr>
				<tr>
					<td>联系方式：</td>
					<td colspan="3">
						<input class="easyui-textbox" style="width:310px;" type="text" name="mobile"
							data-options="required:true,delay:600,validType:['mobile','remoteValidataDoctorMobile']">
					</td>
				</tr>
				<tr>
					<td>名医类型:</td>
					<td>
						<select class="easyui-combobox" name="doctorType"
								data-options="required:true,panelHeight:'auto'">
							<option value="1">国医</option>
							<option value="2">专家</option>
						</select>
					</td>
					<td>科室类别:</td>
					<td>
						<input class="easyui-combobox" name="subjectId"
							   data-options="
								width:118,
								url:'${path}/subject/showComboBoxList',
								method:'post',
								valueField:'id',
								textField:'text',
								panelHeight:'auto',
								required:true,
								editable:false
						">
					</td>
				</tr>
				<tr>
					<td>所属医院：</td>
					<td colspan="3">
						<input class="easyui-combobox" name="hospitalId" style="width:310px;"
							   data-options="
								url:'${path}/hospital/showComboBoxList',
								method:'post',
								valueField:'id',
								textField:'text',
								panelHeight:'180',
								required:true,
								editable:false
						">
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>简介：</td>
					<td colspan="3"><input class="easyui-textbox" style="width:310px;height:100px;" name="doctorIntroduction" data-options="multiline:true,required:true" style="height:60px"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>擅长：</td>
					<td colspan="3"><input class="easyui-textbox" style="width:310px;height:100px;" name="doctorAdept" data-options="multiline:true,required:true" style="height:60px"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>出诊地址：</td>
					<td colspan="3"><input class="easyui-textbox" style="width:310px;height:100px;" name="address" data-options="multiline:true,required:true" style="height:60px"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>经纬度：</td>
					<td colspan="3">
						经度：<input class="easyui-numberbox" style="width:150px;" type="text" name="addressLongitude"
								  data-options="required:true,precision:10"><br />
						纬度：<input class="easyui-numberbox" style="width:150px;" type="text" name="addressLatitude"
								  data-options="required:true,precision:10">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false" style="text-align:right;padding:5px 30px 0 0;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="submitEditDoctorInfo()" style="width:80px">确认</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-clear'" href="javascript:void(0)" onclick="clearEditDoctorInfo()" style="width:80px">重填</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="cancelEditDoctorInfo()" style="width:80px">取消</a>
	</div>
</div>
<div id="editDiagnoseTimeWindow" class="easyui-window" title="出诊时间" style="width:515px;height:400px;background: #fafafa;"
	 idField="id" data-options="resizable:false,modal:true,closed:true,collapsible:false,
	 	minimizable:false,maximizable:false,shadow:true">
	<div class="easyui-layout"  data-options="fit:true">
		<div data-options="region:'west',border:false" style="width:80px;text-align:center;padding:10px 0 0 0;">
			<img  id="editDiagnoseTimeDoctorHeadPic"
				  style="width:60px; height:60px" src="${imagesPath}/doctor_head_img.png">
		</div>
		<div data-options="region:'center',border:false" style="padding:10px;">
			<form id="diagnoseTimeForm" method="post">
				<input type="hidden" name="doctorId" id="diagnoseTimeDoctorId">
				<div data-options="region:'north',border:false" style="text-align: right;">
					日期：<input class="easyui-datebox" id="diagnoseTimeVisDate" name="visDate" style="width:100px;" id="diagnoseTimeVisDate"
							  data-options="required:true,editable:false,validType:'remoteValidataDiagnoseTime'">&nbsp;
					时间：<select class="easyui-combobox" name="visTime"
							   data-options="panelHeight:'auto',editable:false,required:true,value:''">
							<option value="1">上午</option>
							<option value="2">下午</option>
							<option value="3">全天</option>
						</select>&nbsp;
					名额：<input class="easyui-numberbox" style="width:100px;" type="text" name="totality"
							  data-options="required:true,min:1">
				</div>
				<div data-options="region:'south',border:false" style="padding:15px 0px 0 0;text-align: right;">
					<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" href="javascript:void(0)" onclick="submitDiagnoseTimeInfo()" style="width:80px">添加</a>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-clear'" href="javascript:void(0)" onclick="clearDiagnoseTimeInfo()" style="width:80px">重填</a>
				</div>
			</form>
		</div>
		<div data-options="region:'south',border:false" style="height:280px;">
			<table id="doctorDiagnoseTimeDateGrid" class="easyui-datagrid" style="width:100%;height:100%;"
				   data-options="singleSelect:true,fit:true,border:false,nowrap:false">
				<thead>
				<tr>
					<th data-options="field:'visDate',width:160,align:'center'">日期</th>
					<th data-options="field:'visTime',width:100,formatter:diagnoseTimeTimeFormatter">时间</th>
					<th data-options="field:'totality',width:100">名额</th>
					<th data-options="field:'id',width:80,align:'center',formatter:diagnoseTimeOperationFormatter">操作</th>
				</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		//上传图片
		new AjaxUpload('#uploadAddDoctorHeadImg', {
			action: path + '/doctor/uploadDoctorHeadImg',
			name: 'doctorHeadImg',
			responseType: 'json',
			onSubmit : function(file , ext){
//				if (ext && /^(jpg|png|bmp)$/.test(ext.toLowerCase())){
				if (ext && /^(png)$/.test(ext.toLowerCase())){
					this.setData({
						'picName': file
					});
				} else {
					alert("请上传格式为png的图片！");
					return false;
				}
			},
			onComplete : function(file,response){
				validateLogin(response);
				if(response.error) {
//					alert(response.error);
					$.messager.alert('错误',"上传头像发生错误！",'error');
					return;
				}
				$('#addDoctorHeadImg').attr('src',ossFilePath + "/hlzy/doctorpic/" + response.fileName);
				$("#addDoctorHeadpicName").val(response.fileName);
			}
		});
		new AjaxUpload('#uploadEditDoctorHeadImg', {
			action: path + '/doctor/uploadDoctorHeadImg',
			name: 'doctorHeadImg',
			responseType: 'json',
			onSubmit : function(file , ext){
//				if (ext && /^(jpg|png|bmp)$/.test(ext.toLowerCase())){
				if (ext && /^(png)$/.test(ext.toLowerCase())){
					this.setData({
						'picName': file
					});
				} else {
					alert("请上传格式为png的图片！");
					return false;
				}
			},
			onComplete : function(file,response){
				validateLogin(response);
				if(response.error) {
//					alert(response.error);
					$.messager.alert('错误',"上传头像发生错误！",'error');
					return;
				}
				$('#editDoctorHeadImg').attr('src',ossFilePath + "/hlzy/doctorpic/" + response.fileName);
				$("#editDoctorHeadpicName").val(response.fileName);
			}
		});
		$('#doctorDateGrid').datagrid({
			singleSelect:true,
			url:'${path}/doctor/showListForPage',
			method:'post',
			fit:true,
			border:false,
			nowrap:false,
			loadFilter:function(data){
				return validateLogin(data);
			},
			tools:[
				{
					iconCls:'icon-add',
					handler:function(){alertAddDoctorForm()}
				},{
					iconCls:'icon-edit',
					handler:function(){alertEditDoctorForm()}
				},{
					iconCls:'icon-remove',
					handler:function(){removeDoctorForm()}
				}],
			rownumbers:true,
			pagination:true,
			pagePosition:'both',
			striped:true,
			onDblClickCell: function(index,field,value){
				alertEditDoctorForm();
			},onLoadSuccess: function(data) {
				if(data.total > 0){
					$("a.diagnoseTimeBtn").linkbutton();
				}
			},onLoadError: function(){
				$.messager.alert('错误',"加载数据失败，请稍后再试。。。",'error');
			}
		});
		$.extend($.fn.validatebox.defaults.rules, {
			remoteValidataDoctorMobile:{
				validator: function (value) {
					var checkR=$.ajax({
						async : false,
						cache : false,
						type : 'post',
						url : '${path}/doctor/validateMobile',
						data : {
							'doctorId': $("#doctorId").val(),
							'mobile' : value
						}
					}).responseText;
					return checkR==="true";
				},
				message: '该手机已经被使用'
			},remoteValidataDiagnoseTime:{
				validator: function (value) {
					var checkR=$.ajax({
						async : false,
						cache : false,
						type : 'post',
						url : '${path}/diagnoseTime/validateDiagnoseTime',
						data : {
							'doctorId': $("#diagnoseTimeDoctorId").val(),
							'diagnoseTimeVisDate' : value
						}
					}).responseText;
					return checkR==="true";
				},
				message: '该日期已有出诊计划'
			}
		});
	});
	function doctorHeadImgFormatter(value,row,index){
		if('' != value && null != value)
			value = '<img style="width:60px; height:60px" src="' +  ossFilePath + '/hlzy/doctorpic/' + value + '">';
		return  value;
	}
	function doctorIntroductionFormatter(value,row,index){
		return  '<div style="width:100%;height:80px;overflow-y:auto;">'+value+'</div>';
	}
	function subjectFormatter(value,row,index){
		if(value.subjectName){
			return value.subjectName
		}else{
			return "-";
		}
	}
	function diagnoseTimeFormatter(value,row,index){
		return '<a class="diagnoseTimeBtn" ' +
				'href="javascript:void(0)" style="width:50px;" onclick="alertDiagnoseTimeForm(\''+value+'\')">查看</a>';
	}
	function diagnoseTimeOperationFormatter(value,row,index){
		return '<a class="diagnoseTimeDeleteBtn" ' +
				'href="javascript:void(0)" style="width:50px;" onclick="removeDiagnoseTimeForm(\''+value+'\')">删除</a>';
	}
	function diagnoseTimeTimeFormatter(value,row,index){
		var timeStr = "-";
		if(value){
			if(value == "1"){
				timeStr = "上午";
			}else if(value == "2"){
				timeStr = "下午";
			}else if(value == "3"){
				timeStr = "全天";
			}
		}
		return timeStr;
	}
	function submitNewDoctorInfo(){
		$('#addDoctorForm').form({
			url:path + "/doctor/add",
			onSubmit: function(param){
				if($("#addDoctorHeadpicName").val() == ""){
					$.messager.alert('错误',"请上传医生头像！",'error');
					return false;
				}else{
					return $(this).form('validate');
				}
			},
			success:function(data){
				validateLogin(data);
				if(data == "success"){
					$.messager.alert('消息',"添加成功！", "info");
					$('#doctorDateGrid').datagrid("load");
					$('#addDoctorWindow').window('close');
				}else{
					$.messager.alert('错误',"添加失败！",'error');
				}
			}
		});
		// submit the form
		$('#addDoctorForm').submit();
	}
	function clearNewDoctorInfo(){
		$('#addDoctorForm').form("clear");
	}
	function cancelNewDoctorInfo(){
		$('#addDoctorWindow').window('close');
	}
	function alertAddDoctorForm(){
		clearNewDoctorInfo();
		$("#addDoctorWindow").window("open");
	}
	function alertEditDoctorForm(){
//		$("#editDoctorWindow").window("open");
		var doctorItem = $("#doctorDateGrid").datagrid("getSelected");
		if(doctorItem){
			$("#editDoctorForm").form("clear");
			$("#editDoctorWindow").window("open");
			$("#editDoctorForm").form({
				onBeforeLoad:function(param){
					showLoading("editDoctorWindow");
				},
				onLoadSuccess: function (data) {
					validateLogin(data);
					hideLoading("editDoctorWindow");
					$("#editDoctorHeadImg").attr("src", ossFilePath + "/hlzy/doctorpic/" + data.doctorHeadpic);
				},onLoadError: function(){
					hideLoading("editDoctorWindow");
					$.messager.alert('错误',"加载数据失败，请稍后再试。。。",'error');
				}
			});
			$("#editDoctorForm").form("load","${path}/doctor/getInfoById/?id=" + doctorItem.id);
		}else{
			$.messager.alert('错误',"请选择一条需要修改的数据！",'error');
		}
	}
	function clearEditDoctorInfo(){
		var doctorItem = $("#doctorDateGrid").datagrid("getSelected");
		$("#editDoctorForm").form("load","${path}/doctor/getInfoById/?id=" + doctorItem.id);
	}
	function cancelEditDoctorInfo(){
		clearEditDoctorInfo();
		$('#editDoctorWindow').window('close');
	}
	function submitEditDoctorInfo(){
		$('#editDoctorForm').form({
			url:path + "/doctor/update",
			onSubmit: function(param){
				if($("#editDoctorHeadpicName").val() == ""){
					$.messager.alert('错误',"请上传医生头像！",'error');
					return false;
				}else{
					return $(this).form('validate');
				}
			},
			success:function(data){
				validateLogin(data);
				if(data == "success"){
					$.messager.alert('消息',"修改成功！", "info");
					$('#doctorDateGrid').datagrid("reload");
					$('#editDoctorWindow').window('close');
				}else{
					$.messager.alert('错误',"修改失败！",'error');
				}
			}
		});
		// submit the form
		$('#editDoctorForm').submit();
	}
	function alertDiagnoseTimeForm(id){
		$('#diagnoseTimeForm').form("clear");
		$("#diagnoseTimeDoctorId").val(id);
		$("#editDiagnoseTimeWindow").window("open");
		$("#doctorDiagnoseTimeDateGrid").datagrid({
			method: "post",
			url: path + "/diagnoseTime/showListByDoctorId",
			loadFilter:function(data){
				return validateLogin(data);
			},
			queryParams: {
				doctorId: id
			},onLoadSuccess: function (data) {
				if(data.total > 0){
					$("a.diagnoseTimeDeleteBtn").linkbutton();
				}else{
					$.messager.alert('消息',"没有出诊记录，请添加！", "info");
				}
			},onLoadError: function(){
				$.messager.alert('错误',"加载数据失败，请稍后再试。。。",'error');
			}
		});
		var rowIndex = $('#doctorDateGrid').datagrid("getRowIndex",id);
		var doctorItem = $('#doctorDateGrid').datagrid("getRows")[rowIndex];
		if(doctorItem){
			$("#editDiagnoseTimeDoctorHeadPic").attr("src",ossFilePath + "/hlzy/doctorpic/" + doctorItem.doctorHeadpic);
		}
	}
	function submitDiagnoseTimeInfo(){
		$('#diagnoseTimeForm').form({
			url:path + "/diagnoseTime/add",
			onSubmit: function(param){
				return $(this).form('validate');
			},
			success:function(data){
				validateLogin(data);
				if(data == "success"){
					$.messager.alert('消息',"添加成功！", "info");
					$('#doctorDiagnoseTimeDateGrid').datagrid("load");
				}else{
					$.messager.alert('错误',"添加失败！",'error');
				}
			}
		});
		// submit the form
		$('#diagnoseTimeForm').submit();
	}
	function clearDiagnoseTimeInfo(){
		var id = $("#diagnoseTimeDoctorId").val();
		$('#diagnoseTimeForm').form("clear");
		$("#diagnoseTimeDoctorId").val(id);
	}
	function removeDiagnoseTimeForm(id){
		$.messager.confirm('确认', '是否真的要删除这条数据？', function(r){
			if (r){
				$.get(path + "/diagnoseTime/delete", {"id":id}, function(data){
					validateLogin(data);
					if(data == "success"){
						$('#doctorDiagnoseTimeDateGrid').datagrid("load");
						$.messager.alert('信息',"删除成功！",'info');
					}else{
						$.messager.alert('错误',"删除失败！",'error');
					}
				});
			}
		});
	}
	function removeDoctorForm(){
		var doctorItem = $("#doctorDateGrid").datagrid("getSelected");
		if(doctorItem){
			$.messager.confirm('确认', '是否真的要删除这条数据？', function(r){
				if (r){
					$.get(path + "/doctor/delete", {"id":doctorItem.id}, function(data){
						validateLogin(data);
						if(data == "success"){
							$('#doctorDateGrid').datagrid("reload");
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