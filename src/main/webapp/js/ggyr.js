
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
		url:"../ggyr/query",
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
		columns: [[{
		        	   field:"mo",
		        	   title:"MO",
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

function doSearch() {
	var mos = $("#searchText").val();
	if (!JUDGE.isNull(mos)) {
		$("#mos").val(mos);
	}
	
	var queryParams = $("#ggyrTable").datagrid("options").queryParams;
	queryParams.mos = mos;
	$("#ggyrTable").datagrid("options").queryParams = queryParams;
	var rows = $("#ggyrTable").datagrid("getPager").data("pagination").options.pageSize;
	
	$.ajax({
		type:"post",
		url:"../ggyr/query",
		dataType: "json",
		data: {
			page: 1,
			rows: rows,
			mos: $("#mos").val()
		},
		success: function(resp) {
			debugger;
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

function closeDialog() {
	var dlg = $("#searchDialog").dialog();
	if (dlg) {
		$("#searchDialog").dialog("close");
	}
}