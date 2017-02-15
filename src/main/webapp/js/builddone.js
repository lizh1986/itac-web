
function resize() {
	$("#buildDoneTable").datagrid("resize", {
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
	$("#buildDoneTable").datagrid({
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
		        	   field:"mo",
		        	   title:"MO",
		        	   width:100
		           }, {
		        	   field:"sn",
		        	   title:"UWIP",
		        	   width:100
		           }, {
		        	   field:"status",
		        	   title:"STATUS",
		        	   width:100
		           }, {
		        	   field:"createdString",
		        	   title:"Last Work Step Time",
		        	   width:150
		           }
		]]
	});
	
	var pager = $("#buildDoneTable").datagrid("getPager"); 
	pager.pagination({
//		total:datas.length,
		onSelectPage:function (pageNo, pageSize) {
			var start = (pageNo - 1) * pageSize; 
			var end = start + pageSize; 
			
			var page = {
				total: datas.total,
				data: datas.data.slice(start, end)
			};
			
			$("#buildDoneTable").datagrid("loadData", page);
			pager.pagination('refresh', {
				total:datas.total, 
				pageNumber:pageNo 
			}); 
		}
	});
});

function openDialog() {
	$("#searchDialog").dialog({
		title: "Search",
		width: 400,
		height: 320,
		buttons: [{
			text: "Search",
			handler: doSearch
		},{
			text: "Cancel",
			handler: closeDialog
		}]
	});
	
	$("#searchText").val($("#mos").val());
	$("#searchText").focus();
}

var datas;

function doSearch() {
	var mos = $("#searchText").val();
	if (!JUDGE.isNull(mos)) {
		$("#mos").val(mos);
	}
	
	var url = "../builddone/query";
	
	var queryParams = $("#buildDoneTable").datagrid("options").queryParams;
	queryParams.mos = mos;
	$("#buildDoneTable").datagrid("options").queryParams = queryParams;
	$("#buildDoneTable").datagrid("options").url = url;
	var rows = $("#buildDoneTable").datagrid("getPager").data("pagination").options.pageSize;
	
	$.ajax({
		type:"post",
		url:url,
		dataType: "json",
		data: {
//			page: 1,
//			rows: rows,
			mos: $("#mos").val()
		},
		success: function(resp) {
			datas = {
				total: resp.total,
				data: resp.data
			};
			
			var firstPage = {
				total: resp.total,
				data: resp.data.slice(0,rows)
			};
			$("#buildDoneTable").datagrid("loadData", firstPage);
			
			var msg = resp.msg;
			if (msg) {
				$.messager.alert("warning", msg.replace(/\\n/g, "<br/>"));
			}
			closeDialog();
		},
		error: function() {
			$.messager.alert("Failed to query build done fail list.");
		}
	});
	
}

function closeDialog() {
	var dlg = $("#searchDialog").dialog();
	if (dlg) {
		$("#searchDialog").dialog("close");
	}
	
}