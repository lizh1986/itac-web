<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lenovo.itac.entity.LoginUserInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta content="IE=11.0000" http-equiv="X-UA-Compatible">
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE/9/10/11" >
		<title>iTAC-Web</title>
	
		<link rel="shortcut icon" href="image/logo.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="css/main.css">
		
		<script type="text/javascript" src="easyui/jquery.min.js"></script>
		<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="easyui/locale/easyui-lang-en.js"></script>
		
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/main.js"></script>
		<script type="text/javascript">
			function showcontent(language){
				$('#content').html('Introduction to ' + language + ' language');
			}
			$(function(){
				showcontent('java');
			});
		</script>
	</head>
	<body style="border: none; visibility: visible; width: 100%; height: 100%;">
		<div id="home-layout" class="easyui-layout" fit="true">
			<div region="north" style="height:100px; width: 100%; overflow-y: hidden;">
				<div class="header" style="background: url('image/banner.jpg') repeat;">
					<div style="text-align: right; padding-right: 20px; padding-top: 43px; padding-bottom: 4px;">	
						<span style="color: #FDFDFD" id="loginuserInfo">Hi, <%= ((LoginUserInfo)session.getAttribute("user")).getLoginName() %></span>
						<span style="color: #FDFDFD" id="timeInfo"></span>
						<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
						<a href="javascript:void(0)" onclick="logout()" style="color: #FDFDFD; text-decoration: none;">Logout</a>
					</div>
					<div class="main-title" style="top: 10px; font-size: 30px; color: #FDFDFD; margin-left: 20px;">
						iTAC-Web
					</div>
				</div>
				<div id="topmenu" class="topmenu" style="height: 38px; background: url('image/maintop.png'); font-size: 14px; font-weight: bold;">
				&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:addTab('Home','jsp/welcome.jsp')">Home</a>
				</div>
			</div>
			
			<!-- 页面底部信息 -->
			<div region="south" style="height: 20px;">
				<center>CopyRight</center>
			</div>
			
			<div region="west" split="true" title="Navigator" style="width:15%;">
				<ul id="menuTree" class="easyui-tree" data-options="animate:true"></ul>
			</div>
			
			<div region="center">
				<div id="first-tab" class="easyui-tabs" fit="true">
					<div title="Home" style="overflow-y: hidden;" data-options="closable:true">
						<iframe width='100%' height='100%' id='iframe' name='iframe' frameborder='0' scrolling='no' src='jsp/welcome.jsp'></iframe>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>