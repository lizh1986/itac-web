
function resize() {
	$("#roleTable").treegrid("resize", {
		width: function() {
			return document.documentElement.clientWidth * 0.96;
		},
		height: function() {
			return document.documentElement.clientHeight * 0.88;
		}
	});
}

$(function() {
	var width = document.documentElement.clientWidth * 0.96;
	var height = document.documentElement.clientHeight * 0.88;
	$("#roleTable").datagrid({
		title: "",
		width:width,
		height: height,
		singleSelect: false,
		rownumbers: true,
		queryParams: {},
		method:"post",
		url:'../role/getRoles',
		loadFilter:function(data){
			if(data){
				var datas = {
					rows: data.data,
					total: data.total
				};
				return datas;
			}
		},
		pagination:true,
		striped: true,
		pageSize: 20,
		pageList: [20, 50, 100],
		fitColumns: true,
		columns: [[
		   { 
			   field:'ck',
			   checkbox:true 
		   },{
	     	   field:"name",
	     	   title:"Name",
	     	   sortable: true,
	     	   width:150
		   }, {
	     	   field:"description",
	     	   title:"Description",
	     	   width:150
	        }, {
	     	   field:"id",
	    	   title:"Operation",
	    	   width:150,
	    	   formatter: function(value, row, index) {
	    		   var update = '<a href="javascript:void(0)" style="padding-left:5px;padding-right:5px;" onclick="modifyRole(\'' + row.id + '\')">modify</a>';
	    		   var assign = '<a href="javascript:void(0)" style="padding-left:5px;padding-right:5px;" onclick="assign(\'' + row.id + '\')">Assign</a>';
	    		   var operations = update + "|" + assign;
	    		   return operations;
	    	   }
	       }
        ]]
	});
});

function addRole() {
	$("#addRoleDialog").dialog({
		title: "Add Role",
		buttons: [{
			text: "Save",
			handler: saveRole
		},{
			text: "Cancel",
			handler: closeDialog
		}]
	});
	
	$('#userGroup').combobox({
	    valueField:'id',
	    textField:'text',
	    url: "../role/getAllUserGroups",
	    editable:false,
	    loadFilter: function(resp) {
	    	if (resp.data) {
	    		var response = [];
	    		for (var i = 0; i < resp.data.length; i++) {
	    			var item = new Object();
	    			item.id = resp.data[i].id;
	    			item.text = resp.data[i].name;
	    			response.push(item);
	    		}
	    		return response;
	    	} else {
	    		return data;
	    	}
	    }
	});
}

function deleteRoles() {
    var ids = [];
    var rows = $('#roleTable').datagrid('getSelections');
    
    for(var i=0; i<rows.length; i++){
    	ids.push(rows[i].id);
    }
    
    if (ids.length == 0) {
    	$.messager.alert("Warning", "Please select at least one record.");
    	return;
    }
    
    $('body').showLoading();
    $.ajax({
    	type:"post",
		url:"../role/deleteRoles",
		dataType: "json",
		data: {
			"ids": ids.join(","),
		},
		success: function(resp) {
			$('body').hideLoading();
			if (resp.code == 001) {
				$.messager.alert("Error", "Param is null.");
			} else {
				$.messager.alert("Info", "Delete roles Success.");
				var queryParams = {};
				$("#roleTable").datagrid("options").queryParams = queryParams;
				$("#roleTable").datagrid("load");
			}
		},
		error: function(event, request, settings) {
			$('body').hideLoading();
			$.messager.alert("Error","Bad request to delete role.","error");
		}
    });
    
}

function modifyRole(role) {
	var userRole;
	$('body').showLoading();
	$.ajax({
		type:"post",
		url:"../role/getRole",
		dataType: "json",
		data: {
			"id": role
		},
		success: function(resp) {
			$('body').hideLoading();
			if (resp.code == 200) {
				userRole = resp.data;
				$("#modifyRoleDialog").dialog({
					title: "Modify Role",
					buttons: [{
						text: "Save",
						handler: function() {
							if (JUDGE.isNull($("#modifyRoleNameValue").val())) {
								$.messager.alert("Warning", "Role Name is required.");
								return;
							}
							
							var entity = {
								"id": role,
								"name": $("#modifyRoleNameValue").val(),
								"userGroup": $("#updateUserGroup").combobox("getText"),
								"description": $("#modifyRoleDescValue").val()
							};
							$.ajax({
								type:"post",
								url:"../role/updateRole",
								dataType: "json",
								data: entity,
								success: function(resp) {
									if (resp.code == 001) {
										$.messager.alert("Error", "Param is null.");
									} else {
										$.messager.alert("Info", "Modify role Success.");
									}
									
									var dlg = $("#modifyRoleDialog").dialog("close");
									
									var queryParams = {};
									$("#roleTable").datagrid("options").queryParams = queryParams;
									$("#roleTable").datagrid("load");
								},
								error: function(event, request, settings) {
									$.messager.alert("Error","Bad request to modify role.","error");
								}
							});
						}
					},{
						text: "Cancel",
						handler: function() {
							$("#modifyRoleDialog").dialog("close");
						}
					}]
				});
				$("#modifyRoleNameValue").val(userRole.name);
				$("#modifyRoleDescValue").val(userRole.description);
				
				$("#updateUserGroup").combobox({
					valueField:'id',
				    textField:'text',
				    url: "../role/getAllUserGroups",
				    editable:false,
				    loadFilter: function(resp) {
				    	if (resp.data) {
				    		var response = [];
				    		for (var i = 0; i < resp.data.length; i++) {
				    			var item = new Object();
				    			item.id = resp.data[i].id;
				    			item.text = resp.data[i].name;
				    			response.push(item);
				    		}
				    		return response;
				    	} else {
				    		return data;
				    	}
				    }
				});
				$("#updateUserGroup").combobox("setValue", userRole.userGroup);
				
			} else {
				$.messager.alert("Error", "Failed to load Role.");
			}
		},
		error:function(event, request, settings)
		{
			$('body').hideLoading();
			$.messager.alert("Error","Bad request.","error");
		}
	});
}

function assign(roleId) {
	$("#assignRoleDialog").dialog({
		title: "Assign Role",
		buttons: [{
			text: "Assign",
			handler: function() {
				var nodes = $('#permissiontree').tree("getChecked");// 返回选中多行
				var ids = [];
				for (var i = 0; i < nodes.length; i++) {
					var id = nodes[i].id;
					ids.push(id);
				}

				$('body').showLoading();
				$.ajax({
					type : "post",
					url : "../role/assignPermission",
					traditional: true,
					data : {
						"menuIds" : ids,
						"roleId": roleId
					},
					error : function(event, request, settings) {
						$('body').hideLoading();
						$.messager.alert("Error","Bad request to assign menus.","error");
					},
					success : function(result) {
						$('body').hideLoading();
						if (result.code == 200) {
							$.messager.alert("Info", "Assign permission success.", "Info");
						} else {
							$.messager.alert("Info", "Assign permission success.", "Info");
						}
					}
				});
				$("#assignRoleDialog").dialog("close");
			}
		},{
			text: "Cancel",
			handler: function() {
				$("#assignRoleDialog").dialog("close");
			}
		}]
	});
	
	$('#permissiontree').tree({
		url : '../menu/load',
		formatter : function(node) {
			  return $('<span>').text( node.text ).html();
		},
		loadFilter : function(data) {
			if (data.data) {
				return data.data;
			} else {
				return data;
			}
		},
		onLoadSuccess: function(node, data) {
			$('body').showLoading();
			$.ajax({
				type : "post",
				url : "../role/getPermissionByRoleId",
				data : {
					"id" : roleId
				},
				error : function(event, request, settings) {
					$('body').hideLoading();
					$.messager.alert("Error","Bad request to get menus by role id.","error");
				},
				success : function(resp) {
					$('body').hideLoading();
					if (resp.code == 200) {
						if (resp.data.length > 0) {
							for (var i = 0; i < resp.data.length; i++) {
								debugger;
								 var n = $("#permissiontree").tree('find',resp.data[i]);
		                         if(n){
		                             $("#permissiontree").tree('check',n.target);
		                         }
							}
						}

					} else {
						$.messager.alert("Error","Bad request to get menus by role id.","error");
					}
				}
			});
		}
	});
}

function saveRole() {
	var roleName = $("#addRoleNameValue").val();
	var group = $("#userGroup").combobox("getText");
	debugger;
	if (JUDGE.isNull(roleName)) {
		$.messager.alert("Warning", "Role Name is required.");
		return;
	}
	var roleDesc = $("#addRoleDescValue").val();
	
	var userRoleEntity = {
		"name": roleName,
		"userGroup": group,
		"description": roleDesc
	};
	
	$('body').showLoading();
	$.ajax({
		type:"post",
		url:"../role/addRole",
		dataType: "json",
		data: userRoleEntity,
		success: function(resp) {
			$('body').hideLoading();
			if (resp.code == 001) {
				$.messager.alert("Error", "Param is null.");
			} else if (resp.code == 002) {
				$.messager.alert("Error", "The same role exists already.");
			} else {
				$.messager.alert("Info", "Add Role: " + roleName + " Success.");
			}
			
			var dlg = $("#addRoleDialog").dialog("close");
			
			var queryParams = {};
			$("#roleTable").datagrid("options").queryParams = queryParams;
			$("#roleTable").datagrid("load");
		},
		error:function(event, request, settings)
		{
			$('body').hideLoading();
			$.messager.alert("Error","Bad request.","error");
		}
	});
}

function queryByName() {
	var roleName = $("#queryName").val();
	var queryParams = $("#roleTable").datagrid("options").queryParams;
	queryParams.name = roleName;
	$("#roleTable").datagrid("options").queryParams = queryParams;
	$("#roleTable").datagrid("load");
}
