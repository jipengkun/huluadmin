<%--
  Created by IntelliJ IDEA.
  User: huangyiwei
  Date: 15/10/26
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/page/pub/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<head>
  <title>葫芦后台管理-用户登录</title>
  <jsp:include page="/WEB-INF/page/pub/head.jsp"></jsp:include>
  <link rel="stylesheet" type="text/css" href="${cssPath}/login.css">
</head>
<body>
  <div class="container">
    <div class="login">
      <h1>用户登录</h1>
      <form id="userLoginForm" method="post">
        <p><input type="text" name="phone" value="" placeholder="手机"  class="easyui-validatebox" data-options="required:true,validType:'mobile'"></p>
        <p><input type="password" name="password" value="" placeholder="密码"  class="easyui-validatebox" data-options="required:true,validType:'minLength[6]'"></p>
        <p>
          <table  style="border-collapse:collapse">
            <tr>
              <td>
                <img id="validateCodeImg" src="${path}/system/validateCode" style="width:165px;height:42px;" />
              </td>
              <td>
                <input type="text" id="validateCodeTxt" name="validateCode" value="" placeholder="验证码"  style="width:105px;margin-left:10px;"
                       class="easyui-validatebox" data-options="required:true,validType:'minLength[6]'">
              </td>
            </tr>
          </table>
        </p>
        <p class="remember_me">
          <label>
            <input type="checkbox" name="rememberMe" id="remember_me" disabled="disabled">
            记住登录(未实现)
          </label>
        </p>
        <p class="submit"><input type="submit" name="commit" onclick="submitLoginInfo()" value="登录"></p>
      </form>
    </div>

    <div class="login-help">
      <p>忘记密码? <a href="javascript:void(0)" onclick="notImplement()">点击修改</a>.</p>
    </div>
  </div>
  <div style="text-align:center;">
    <p>Copyright © 2015 www.hulumaker.com</p>
  </div>
  <script type="text/javascript">
    $(function(){
      changeSize();
      $(window).resize(function(){
        changeSize();
      });
      $('#validateCodeImg').click(function() {
        changeValidateCode();
      });
    });

    function changeSize(){
      var marginTop = ($(document).height() - 400) / 2;
      marginTop = marginTop < 0 ? 0 : marginTop;
      $(".container").css("marginTop", marginTop + "px");
    }

    function changeValidateCode(){
      $('#validateCodeImg').hide().attr('src',path + '/system/validateCode?' + Math.floor(Math.random() * 100)).fadeIn();
    }

    var loginTimeout;
    function submitLoginInfo(){
      $('#userLoginForm').form({
        url:path + "/user/login",
        onSubmit: function(){
          if($(this).form('validate')) {
            $.messager.progress({
              title: '请等待',
              msg: '正在登陆...'
            });
            loginTimeout = setTimeout(function(){
              $.messager.progress('close');
              $.messager.alert('错误',"登陆失败，服务器无响应，请稍后再试！",'error');
            },20000)
            return true;
          }else{
            return false;
          }
        },
        success:function(data){
          clearTimeout(loginTimeout);
          $.messager.progress('close');
          if(data == "success"){
            window.location.href = path + "/system/toIndexPage";
          }else if(data == "nameOrPwdError"){
            $.messager.alert('错误',"用户名或者密码错误！",'error');
            $('#userLoginForm').form("clear");
            changeValidateCode();
          }else if(data == "validateCodeError"){
            $.messager.alert('错误',"验证码输入错误！",'error');
            $("#validateCodeTxt").val("");
            changeValidateCode();
          }
        }
      });
      // submit the form
      $('#addSubjectForm').submit();
    }

    function notImplement(){
      $.messager.alert('提示',"该功能暂未实现！",'info');
    }


  </script>
</body>
</html>
