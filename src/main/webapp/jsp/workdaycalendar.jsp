<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="../css/showLoading.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="../fullcalendar/fullcalendar.css">
		<script type="text/javascript" src="../easyui/jquery.min.js"></script>
		<script src='../fullcalendar/fullcalendar.js'></script>
		<script type="text/javascript" src="../js/workdaycalendar.js"></script>
		<script type="text/javascript" src="../js/jquery.showLoading.min.js"></script>
		<title>iTAC-Web</title>
		<style>
			body {
				margin: 40px 10px;
				padding: 0;
				font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
				font-size: 14px;
			}
			
			#calendar {
				max-width: 55%;
				margin: 0 auto;
			}
			
			.fc-grid .fc-day-number {
				font-size: 24px;
				font-family: Arial;
				font-weight: 600;
				padding: 12px 12px 0px 12px;
				line-height: 23px;
				height: 23px;
				color: #FFFFFF;
			}
			
			.fc-content {
				clear: both;
				zoom: 1;
				background: #37ABEC;
			}
			
			.fc-today {
				height: 100%;
				background-color: #6B8E23;
			}
		</style>
	</head>
	<body onresize="resize()">
		<div id='calendar'></div>
	</body>
</html>