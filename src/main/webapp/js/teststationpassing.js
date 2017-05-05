
function resize() {
	$("#resultTable").datagrid("resize", {
		width: function() {
			return document.documentElement.clientWidth * 0.96;
		},
		height: function() {
			return document.documentElement.clientHeight * 0.88;
		}
	});
}

$(function(){
	var width = document.documentElement.clientWidth * 0.96;
	var height = document.documentElement.clientHeight * 0.88;
	$("#resultTable").datagrid({
		title: "",
		width:width,
		height: height,
		singleSelect: true,
		rownumbers: true,
		queryParams: {},
		method:"post",
		loadFilter:function(data){
			if(data){
				var result = {
					rows: data.data,
					total: data.total
				};
				return result;
			}
		},
		pagination:true,
		striped: true,
		pageSize: 20,
		pageList: [20, 50, 100],
		fitColumns: true,
		columns: [[{
		        	   field:"sn",
		        	   title:"SN",
		        	   width:150
		           }, {
		        	   field:"nextWorkStep",
		        	   title:"Next Work Step",
		        	   width:150,
		        	   styler: function(value,row,index) {
		        		   debugger;
		        		   var v = $.trim(value);
		        		   if (v != 'J310CAP') {
		        			   return 'color:red;';
		        		   }
		        	   }
		           }, {
		        	   field: "description",
		        	   title: "Description",
		        	   width:150
		           }
		]]
	});
	
	var pager = $("#resultTable").datagrid("getPager"); 
	pager.pagination({
//		total:datas.length,
		onSelectPage:function (pageNo, pageSize) {
			var start = (pageNo - 1) * pageSize; 
			var end = start + pageSize; 
			
			var page = {
				total: datas.total,
				data: datas.data.slice(start, end)
			};
			
			$("#resultTable").datagrid("loadData", page);
			pager.pagination('refresh', {
				total:datas.total, 
				pageNumber:pageNo 
			}); 
		}
	});
});

function openDialog() {
	$("#searchDialog").dialog({
		title: "Pass Station",
		width: 400,
		height: 320,
		buttons: [{
			text: "Execute",
			handler: batchExecute
		},{
			text: "Cancel",
			handler: closeDialog
		}]
	});
	
	$("#searchText").val($("#sns").val());
	$("#searchText").focus();
}

var datas;

function batchExecute() {
	var sns = $("#searchText").val();
	if (!JUDGE.isNull(sns)) {
		$("#sns").val(sns);
	}
	execute();
}

function execute() {
	var sns = $("#sns").val();
	if (!JUDGE.isNull(sns)) {
		$("#searchText").val(sns);
	}
	
	if (JUDGE.isNull($("#sns").val())) {
		$.messager.alert("Warning", "Please input SN.");
		return;
	}
	
	var url = "../teststation/pass";
	
	var queryParams = $("#resultTable").datagrid("options").queryParams;
	queryParams.sns = sns;
	$("#resultTable").datagrid("options").queryParams = queryParams;
	$("#resultTable").datagrid("options").url = url;
	var rows = $("#resultTable").datagrid("getPager").data("pagination").options.pageSize;
	
	$('body').showLoading();
	$.ajax({
		type:"post",
		url:url,
		dataType: "json",
		data: {
			sns: $("#sns").val()
		},
		success: function(resp) {
			$('body').hideLoading();
			if (resp.code == '001') {
				$.messager.alert("warning", "Please input query condition.");
				return;
			}
			
			if (resp.total <= 0) {
				$("#resultTable").datagrid("loadData", {total: 0, data: []});
				$.messager.alert("warning", "No Data Found.");
				return;
			}
			
			datas = {
				total: resp.total,
				data: resp.data
			};
			
			var firstPage = {
				total: resp.total,
				data: resp.data.slice(0,rows)
			};
			$("#resultTable").datagrid("loadData", firstPage);
			
			var msg = resp.msg;
			if (msg) {
				$.messager.alert("warning", msg.replace(/\\n/g, "<br/>"));
			}
			closeDialog();
		},
		error: function(request, textStatus, errorThrown) {
			$('body').hideLoading();
			$.messager.alert("Error", "Failed to call Test Station Pass interface.");
		}
	});
}

function closeDialog() {
	var dlg = $("#searchDialog").dialog();
	if (dlg) {
		$("#searchDialog").dialog("close");
	}
	
}