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
		$.messager.alert("Login","User name or password can not be empty.","Warning");
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
			}else
			{
				$.messager.alert("Login", resp.msg, "Warning");
			}
		},
		error:function(event, request, settings)
		{
			$.messager.alert("Login","Failed to connect to server.","error");
		}
	});
}