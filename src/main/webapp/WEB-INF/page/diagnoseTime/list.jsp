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
	<title>葫芦后台管理-出诊管理</title>
	<jsp:include page="/WEB-INF/page/pub/head.jsp"></jsp:include>
</head>
<body class="easyui-layout">
<jsp:include page="/WEB-INF/page/pub/top.jsp"></jsp:include>
<jsp:include page="/WEB-INF/page/pub/left.jsp"></jsp:include>
<jsp:include page="/WEB-INF/page/pub/right.jsp"></jsp:include>
<jsp:include page="/WEB-INF/page/pub/bottom.jsp"></jsp:include>
<div data-options="region:'center'">
	<table id="diagnoseOrderDateGrid" class="easyui-datagrid" idField="id" style="width:100%;height:100%;" title="出诊管理">
		<thead>
		<tr>
			<th data-options="field:'doctorHeadImg',width:80,align:'center',formatter:doctorHeadImgFormatter">头像</th>
			<th data-options="field:'doctorName',width:100,formatter:doctorNameFormatter">姓名</th>
			<th data-options="field:'doctorMobile',width:80,formatter:doctorMobileFormatter">联系方式</th>
			<th data-options="field:'doctorSubject',width:80,formatter:doctorSubjectFormatter">科室</th>
			<th data-options="field:'diagnoseTime',width:70,formatter:diagnoseTimeFormatter">出诊时间</th>
			<th data-options="field:'doctorAddress',width:200,formatter:doctorAddressFormatter">出诊地址</th>
			<th data-options="field:'numBalance',width:100">剩余可挂号数量</th>
			<th data-options="field:'diagnoseUseCount',width:120,formatter:diagnoseUseCountFormatter">当前预约挂号数量</th>
			<th data-options="field:'diagnoseFirstOrderTime',width:140,formatter:diagnoseFirstOrderTimeFormatter">首位下单时间</th>
		</tr>
		</thead>
	</table>
</div>
<div id="tb" style="padding:3px">
	<span>医生名称：</span>
	<input class="easyui-combobox" id="serchByDoctorName" style="line-height:18px;border:1px solid #ccc"
		   data-options="width:118,
			   url:'${path}/doctor/showDoctorNameComboBoxList',
			   method:'post',
				valueField:'id',
				textField:'text'" >&nbsp;
	<span>挂号状态：</span>
	<select class="easyui-combobox" id="serchByDiagnoseTimeState"  style="line-height:18px;border:1px solid #ccc"
			data-options="panelHeight:'auto',editable:false">
		<option value="1">全部</option>
		<option value="2">挂号中</option>
		<option value="3">已完成</option>
	</select>&nbsp;
	<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" onclick="doSearch()">查询</a>
</div>
<div id="showOrderListWindow" class="easyui-window" title="出诊时间" style="width:515px;height:400px;background: #fafafa;"
	 idField="id" data-options="resizable:false,modal:true,closed:true,collapsible:false,
	 	minimizable:false,maximizable:false,shadow:true">
	<table id="showOrderListDateGrid" class="easyui-datagrid" style="width:100%;height:100%;"
		   data-options="singleSelect:true,fit:true,border:false,nowrap:false">
		<thead>
		<tr>
			<th data-options="field:'createTime',width:180,align:'center',formatter:orderCreateTimeFormatter">下单日期</th>
			<th data-options="field:'userId',width:100,formatter:orderUserNameFormatter">姓名</th>
			<th data-options="field:'amount',width:100">金额</th>
			<th data-options="field:'paystat',width:80,align:'center',formatter:orderStateFormatter">订单状态</th>
		</tr>
		</thead>
	</table>
</div>
<script type="text/javascript">
	$(function(){
		$('#diagnoseOrderDateGrid').datagrid({
			singleSelect:true,
			url:'${path}/diagnoseTime/showListForPage',
			method:'post',
			fit:true,
			border:false,
			toolbar:"#tb",
			rownumbers:true,
			pagination:true,
			pagePosition:'both',
			striped:true,
			loadFilter:function(data){
				return validateLogin(data);
			},
			queryParams: {
				serchByDoctorName: $('#serchByDoctorName').combobox('getValue'),
				serchByDiagnoseTimeState: $('#serchByDiagnoseTimeState').combobox('getValue')
			},onLoadSuccess: function (data) {
				if(data.total > 0){
					$("a.getDiagnoseFirstOrderTimeBtn").linkbutton();
				}else{
				}
			},
			onLoadError: function(){
				$.messager.alert('错误',"加载数据失败，请稍后再试。。。",'error');
			}
		});

	});
	function doSearch(){
		$('#diagnoseOrderDateGrid').datagrid('load',{
			serchByDoctorName: $('#serchByDoctorName').combobox('getValue'),
			serchByDiagnoseTimeState: $('#serchByDiagnoseTimeState').combobox('getValue')
		});
	}
	function doctorHeadImgFormatter(value,row,index){
		if(row.doctorBean.doctorHeadpic)
			value = '<img style="width:60px; height:60px" src="' +  ossFilePath + '/hlzy/doctorpic/' + row.doctorBean.doctorHeadpic + '">';
		return  value;
	}
	function doctorNameFormatter(value,row,index){
		if(row.doctorBean.doctorName)
			value = row.doctorBean.doctorName;
		return  value;
	}
	function doctorMobileFormatter(value,row,index){
		if(row.doctorBean.mobile)
			value = row.doctorBean.mobile;
		return  value;
	}
	function doctorSubjectFormatter(value,row,index){
		if(row.doctorBean.subject)
			value = row.doctorBean.subject.subjectName;
		return  value;
	}
	function diagnoseTimeFormatter(value,row,index){
		var diagnoseDateTimeStr = "";
		diagnoseDateTimeStr = (new Date(row.visDate)).Format("yyyy-MM-dd") + "<br>";
		var diagnoseTime = row.visTime;
		if(diagnoseTime == 1){
			diagnoseDateTimeStr = diagnoseDateTimeStr + "上午";
		}else if(diagnoseTime == 2){
			diagnoseDateTimeStr = diagnoseDateTimeStr + "下午";
		}else if(diagnoseTime == 3){
			diagnoseDateTimeStr = diagnoseDateTimeStr + "全天";
		}
		return  diagnoseDateTimeStr;
	}
	function doctorAddressFormatter(value,row,index){
		if(row.doctorBean.address)
			value = '<div style="width:100%;height:80px;overflow-y:auto;">' + row.doctorBean.address + '</div>';
		return  value;
	}
	function diagnoseUseCountFormatter(value,row,index){
		if(row.totality){
			value = row.totality - row.numBalance;
			if(row.numBalance == 0){
				value = value + "（已满）"
			}
		}
		return  "<a href='javascript:void(0)' onclick='alertShowOrderListWindow("+ row.id +")'>" + value + "</a>";
	}
	function diagnoseFirstOrderTimeFormatter(value,row,index){
		if(value){
			return  value;
		}else{
			return  "<a href='javascript:void(0)' class='getDiagnoseFirstOrderTimeBtn' onclick='getDiagnoseFirstOrderTime("+ row.id +")'>查看</a>";
		}
	}
	function orderCreateTimeFormatter(value,row,index){
		return (new Date(value)).Format("yyyy年MM月dd日hh时mm分ss秒");
	}
	function orderUserNameFormatter(value,row,index){
		return row.userBean.name;
	}
	function orderStateFormatter(value,row,index){
		var orderState = "";
		if(value == 1){
			orderState = "支付成功";
		}else if(value == 2){
			orderState = "支付失败";
		}else if(value == 3){
			orderState = "未支付";
		}else if(value == 4){
			orderState = "支付中";
		}else{
			orderState = "未知";
		}
		return orderState;
	}
	function alertShowOrderListWindow(id){
		$("#showOrderListWindow").window("open");
		$("#showOrderListDateGrid").datagrid({
			method: "post",
			url: path + "/order/findOrderByTimeId",
			rownumbers:true,
			loadFilter:function(data){
				return validateLogin(data);
			},
			queryParams: {
				timeId: id
			},onLoadSuccess: function (data) {
				if(data.total > 0){

				}else{
					$.messager.alert('消息',"该出诊记录下暂无订单！", "info");
				}
			},onLoadError: function(){
				$.messager.alert('错误',"加载数据失败，请稍后再试。。。",'error');
			}
		});
	}
	function getDiagnoseFirstOrderTime(id){
		$.ajax({
			type: 'post',
			url: path + "/order/findFirstOrderByTimeId",
			data: {
				timeId:id
			},
			success: function(data, textStatus, jqXHR){
				validateLogin(data);
				var result = "";
				if(data){
					result = (new Date(data.createTime)).Format("yyyy-MM-dd");
				}else{
					result = "暂无数据";
				}
				var rowIndex = $('#diagnoseOrderDateGrid').datagrid("getRowIndex",id);
				$('#diagnoseOrderDateGrid').datagrid('updateRow',{
					index:rowIndex,
					row:{
						diagnoseFirstOrderTime:result
					}
				});
			},
			dataType: "json"
		});
	}
</script>
</body>
</html>