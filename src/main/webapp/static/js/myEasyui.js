/**
 * Created by huangyiwei on 15/10/14.
 */
function showLoading(id){
    $("<div class=\"datagrid-mask\"></div>").css({
        display:"block",width:"100%",height:"100%"
    }).appendTo("#" + id);
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("#" + id).css({
        display:"block",left:($("#" + id).width() - 120) / 2,top:($("#" + id).height() - 40) / 2
    });
}
function hideLoading(id){
    $("#" + id).children("div.datagrid-mask-msg").remove();
    $("#" + id).children("div.datagrid-mask").remove();
}

function validateLogin(data){
    if(data != null && data.errorMsg != null) {
        if(data.errorMsg == "current user is not login."){
            $.messager.alert('', '当前操作需要登录，是否现在登陆？', 'warning', function () {
                window.location.href = path + "/";
                return {total:0,rows:0};
            });
        }
    }
    return data;
}