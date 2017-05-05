<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta content="IE=11.0000" http-equiv="X-UA-Compatible">
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE/9/10/11" >
		<title>iTAC-Web</title>
		
		<link rel="stylesheet" type="text/css" href="../css/showLoading.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="../css/ggyr.css">
		
		<script type="text/javascript" src="../easyui/jquery.min.js"></script>
		<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="../easyui/locale/easyui-lang-en.js"></script>
		
		<script type="text/javascript" src="../js/jquery.showLoading.min.js"></script>
		
		<script type="text/javascript" src="../js/common.js"></script>
		<script type="text/javascript" src="../js/kiton3f.js"></script>
	</head>
	<body  onresize="resize()>
		<div id="searchTable">
			<label id="fromLbl">From:</label>
			<input id="fromDate" type="text">
			<label id="toLbl">To:</label>
			<input id="toDate" type="text">
			<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">Search</a>
			<a href="#" class="easyui-linkbutton" plain="true" onclick="doExport()">Export</a>
		</div>
		<div>
			<table id="kitInfoTable"></table>
		</div>
	</body>
</html>