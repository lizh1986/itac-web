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
		<script type="text/javascript" src="../js/role.js"></script>
	</head>
	<body onresize="resize()>
		<div>
			<span style="margin-bottom:12px;">Name:</span>
			<input id="queryName" style="line-height:20px;border:1px;">
			<a href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'" onclick="queryByName()">Search</a>
			<a href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-add'" onclick="addRole()">Add</a>
			<a href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-remove'" onclick="deleteRoles()">Delete</a>
		</div>
		
		<div>
			<table id="roleTable"></table>
		</div>
		
		<div id="addRoleDialog" style="width:400px;height:320px;display:none; padding: 20px 20px 10px 20px;">
			<table style="width:340px;height:200px;">
				<tr style="padding: 20px 20px 10px 20px;">
					<td style="text-align:right;width:100px;padding-right:10px;">
						<label id="addRoleName">Role Name</label>
					</td>
					<td>
						<input id="addRoleNameValue" type="text" style="width:200px" value="">
					</td>
				</tr>
				<tr style="padding: 20px 20px 10px 20px;">
					<td style="text-align:right;width:100px;padding-right:10px;">
						<label>User Group</label>
					</td>
					<td>
						<input id="userGroup" name="userGroup" class="easyui-combobox" value="Please choose" style="width:200px">
					</td>
				</tr>
				<tr style="height: 70px;padding: 20px 20px 10px 20px;">
					<td style="text-align:right;width:100px;padding-right:10px;">
						<label id="addRoleDesc">Description</label>
					</td>
					<td>
						<textarea id="addRoleDescValue" style="width:200px;height:70px;" spellcheck="false"></textarea>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="modifyRoleDialog" style="width:400px;height:320px;display:none; padding: 20px 20px 10px 20px;">
			<table style="width:340px;height:200px;">
				<tr style="padding: 20px 20px 10px 20px;">
					<td style="text-align:right;width:100px;padding-right:10px;">
						<label id="modifyRoleName">Role Name</label>
					</td>
					<td>
						<input id="modifyRoleNameValue" type="text" style="width:200px" value="">
					</td>
				</tr>
				<tr style="padding: 20px 20px 10px 20px;">
					<td style="text-align:right;width:100px;padding-right:10px;">
						<label>User Group</label>
					</td>
					<td>
						<input id="updateUserGroup" name="updateUserGroup" class="easyui-combobox" value="Please choose" style="width:200px">
					</td>
				</tr>
				<tr style="height: 70px;padding: 20px 20px 10px 20px;">
					<td style="text-align:right;width:100px;padding-right:10px;">
						<label id="modifyRoleDesc">Description</label>
					</td>
					<td>
						<textarea id="modifyRoleDescValue" style="width:200px;height:70px;" spellcheck="false"></textarea>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="assignRoleDialog" style="display:none; width: 390px; height: 390px; padding: 10px 20px;">
			<ul id="permissiontree" class="easyui-tree" data-options="checkbox:true,lines:true"></ul>
		</div>
	</body>
</html>