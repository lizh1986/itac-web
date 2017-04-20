
function resize() {
	$("#ggyrTable").datagrid("resize", {
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
	$("#ggyrTable").datagrid({
		title: "",
		width:width,
		height: height,
		singleSelect: true,
		rownumbers: true,
		queryParams: {},
//		url:"../ggyr/query",
		loadFilter:function(data){
			if(data){
				var datas = {
					rows: data.data,
					total: data.total
				};
				return datas;
			}
		},
		onSortColumn: function(sort, order) {
			var rows = $("#ggyrTable").datagrid("getRows");
			if (rows == null || rows.length == 0) {
				return;
			}
			
			var queryParams = $("#ggyrTable").datagrid('options').queryParams;
			queryParams.sortName = sort;
			queryParams.sortOrder = order;
			doSearch(sort, order);
		},
		pagination:true,
		striped: true,
		pageSize: 20,
		pageList: [20, 50, 100],
		fitColumns: true,
		columns: [[{
		        	   field:"mo",
		        	   title:"MO",
		        	   sortable: true,
		        	   width:150
		           }, {
		        	   field:"ggyr",
		        	   title:"GGYR",
		        	   width:150
		           }, {
		        	   field:"pssd",
		        	   title:"PSSD",
		        	   width:150
		           }, {
		        	   field:"rpssd",
		        	   title:"RPSSD",
		        	   width:150
		           }
		]]
	});
	
	var pager = $("#ggyrTable").treegrid("getPager"); 
	pager.pagination({
		onSelectPage:function (pageNo, pageSize) {
			var queryParams = $("#ggyrTable").datagrid('options').queryParams;
			
			doSearch(queryParams.sortName, queryParams.sortOrder);
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
			handler: doBatchSearch
		},{
			text: "Cancel",
			handler: closeDialog
		}]
	});
	
	$("#searchText").val($("#mos").val());
	$("#searchText").focus();
}

function doBatchSearch() {
	var queryParams = $("#ggyrTable").datagrid('options').queryParams;
	doSearch(queryParams.sortName, queryParams.sortOrder);
}

function doSearch(sort, order) {
	var mos = $("#searchText").val();
	if (!JUDGE.isNull(mos)) {
		$("#mos").val(mos);
	}
	
	if (JUDGE.isNull($("#mos").val())) {
		$.messager.alert("Warning", "Please input Mo.");
		return;
	}
	
	var rows = $("#ggyrTable").datagrid("getPager").data("pagination").options.pageSize;
	var page = $("#ggyrTable").datagrid("getPager").data("pagination").options.pageNumber;
	if (page <= 0) {
		page = 1;
	}
	
	$.ajax({
		type:"post",
		url:"../ggyr/query",
		dataType: "json",
		data: {
			page: page,
			rows: rows,
			mos: $("#mos").val(),
			sort: sort,
			order: order
		},
		success: function(resp) {
			var datas = {
				total: resp.total,
				data: resp.data
			};
			$("#ggyrTable").datagrid("loadData", datas);
			
			var msg = resp.msg;
			if (msg) {
				$.messager.alert("warning", msg.replace(/\\n/g, "<br/>"));
			}
			closeDialog();
		},
		error: function() {
			$.messager.alert("Failed to query ggyr.");
		}
	});
}

function doExport() {
	var mos = $("#searchText").val();
	if (!JUDGE.isNull(mos)) {
		$("#mos").val(mos);
	} else {
		mos = $("#mos").val();
		$("#searchText").val(mos);
	}
	
	if (JUDGE.isNull(mos)) {
		$.messager.alert("Warning", "Please input Mo.");
		return;
	}
	
	mos = mos.replace(/\r\n/g, ',');
	mos = mos.replace(/\n/g, ',');
	window.location.href = '../ggyr/export?mos=' + mos;
}

function closeDialog() {
	var dlg = $("#searchDialog").dialog();
	if (dlg) {
		$("#searchDialog").dialog("close");
	}
}