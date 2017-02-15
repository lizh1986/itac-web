$(document).ready(function(){
	$("body").keyup(function(event){
		if (event.keyCode == 13) {
			login();
		}
	});
	
	$(function(){
		$("#login-user").focus();
	});
});

function login() {
	var loginName = $("#login-user").val();
	var password = $("#login-pwd").val();
	if (JUDGE.isNull(loginName) || JUDGE.isNull(password)) {
		$.messager.alert("提示消息","用户名、密码不能为空!","info");
		return;
	}
	
	var loginInfo = {
		"loginName":loginName,
		"password":password
	};
	
	$.ajax({
		type:"post",
		url:"login",
		data:loginInfo,
		success:function(resp)
		{
			if (resp.code == 200)
			{
				$("#login-user").empty();
				$("#login-pwd").empty();
				window.location.href = "home";
			}else if (resp.code == 201)
			{
				$.messager.alert("提示消息","用户名和密码不能为空", "info");
			} else if (resp.code == 202) {
				$.messager.alert("提示消息","用户名或密码不正确", "info");
			} else if (resp.code == 404) {
				$.messager.alert("提示消息","认证失败。", "info");
			}
		},
		error:function(event, request, settings)
		{
			$.messager.alert("错误消息","请求失败","error");
		}
	});
}