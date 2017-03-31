<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta content="IE=11.0000" http-equiv="X-UA-Compatible">
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE/9/10/11" >
		<title>iTAC-Web</title>
		
		<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="../css/agedmo.css">
		
		<script type="text/javascript" src="../easyui/jquery.min.js"></script>
		<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="../easyui/locale/easyui-lang-en.js"></script>
		
		<script type="text/javascript" src="../js/common.js"></script>
		<script type="text/javascript" src="../js/mo.js"></script>
	</head>
	<body onresize="resize()>
		<div id="searchTable">
			<span style="margin-bottom:12px;">MO:</span>
			<textarea id="mos" style="height: 16px;resize:none" spellcheck="false"></textarea>
			<a href="#" class="easyui-linkbutton" plain="true" onclick="openDialog()">Batch</a>
			<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">Search</a>
			<a href="#" class="easyui-linkbutton" plain="true" onclick="doExportMo()">Export Mo</a>
			<a href="#" class="easyui-linkbutton" plain="true" onClick="searchSns()">Pending SN</a>
		</div>
		
		<div>
			<table id="moTable"></table>
		</div>
		
		<div id="searchDialog" style="display:none; padding: 20px 20px 10px 20px;">
			<label id="searchLabel">Please input MOs:</label><br>
			<textarea id="searchText" style="width: 340px; height: 150px; margin-top: 10px; margin-bottom: 10px;" spellcheck="false"></textarea>
			<span style="margin-right: 15px; margin-top: 20px;">Tip: MOs are seperated by new line break.</span>
		</div>
		
		<div id="snDialog" class="easyui-dialog" style="width: 670px; height: 300px;"  
         data-options="closed:true,resizable:true,modal:true,title:'Pending SN List'">
         	<div>
         		<a href="#" class="easyui-linkbutton" plain="true" onclick="doExportSns()">Export</a>
         	</div>
         	<div>
         		<table id="snGrid" class="easyui-datagrid" style="width:655px;height:auto;">
	         		<thead>  
		                <tr>  
		                    <th field="mo" align="center" width="120" sortable="true">MO</th>  
		                    <th field="sn" align="center" width="140" sortable="true">SN</th>  
		                    <th field="workStep" align="center" width="120" sortable="false">Work Step</th>  
		                    <th field="lastWsTime" align="center" width="240" sortable="false">Last Work Step Time</th>  
		                </tr>  
		            </thead>  
		        </table>
         	</div>
         	
        </div>  
	</body>
</html>