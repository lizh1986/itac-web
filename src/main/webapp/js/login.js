$(document).ready(function(){
	$("#login-div").keyup(function(event){
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
		$.messager.alert("Login","User name or password can not be empty.","Warning");
		return;
	}
	
	var loginInfo = {
		"loginName":loginName,
		"password":password
	};
	
	$('body').showLoading();
	$.ajax({
		type:"post",
		url:"login",
		data:loginInfo,
		success:function(resp)
		{
			$('body').hideLoading();
			if (resp.code == 200)
			{
				$("#login-user").empty();
				$("#login-pwd").empty();
				window.location.href = "home";
			}else
			{
				$.messager.alert("Login", resp.msg, "Warning");
			}
		},
		error:function(event, request, settings)
		{
			$('body').hideLoading();
			$.messager.alert("Login","Failed to connect to server.","error");
		}
	});
}