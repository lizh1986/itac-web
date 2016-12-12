
function resize() {
	$("#ggyrTable").datagrid("resize", {
		width: function() {
			return document.documentElement.clientWidth * 0.96;
		},
		height: function() {
			return document.documentElement.clientHeight * 0.93;
		}
	});
}


$(function(){
	var width = document.documentElement.clientWidth * 0.96;
	var height = document.documentElement.clientHeight * 0.93;
	$("#ggyrTable").datagrid({
		title: "",
		width:width,
		height: height,
		singleSelect: true,
		rownumbers: true,
		pagination:true,
		fitColumns: true,
		columns: [[{
		        	   field:"idocNum",
		        	   title:"iDOC Number",
		        	   width:100
		           }, {
		        	   field:"mo",
		        	   title:"MO",
		        	   width:100
		           }, {
		        	   field:"ggyr",
		        	   title:"GGYR",
		        	   width:100
		           }, {
		        	   field:"pssd",
		        	   title:"PSSD",
		        	   width:100
		           }, {
		        	   field:"rpssd",
		        	   title:"RPSSD",
		        	   width:100
		           }, {
		        	   field:"stamp",
		        	   title:"Stamp",
		        	   width:100
		           }, {
		        	   field:"created",
		        	   title:"Created",
		        	   width:100
		           }]],
		toolbar: [{
			text: "Search",
			iconCls: "icon-search",
			plain: true,
			handler:openDialog
		}]
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
	
	$("#searchText").attr("value", "");
	$("#searchText").focus();
}

function doSearch() {
	var mos = $("#searchText").val();
	$.ajax({
		type:"post",
		url:"../ggyr/query",
		dataType: "json",
		data: mos,
		success: function(resp) {
			debugger;
			var data = resp.data;
			$("#ggyrTable").datagrid("loadData", data);
			closeDialog();
		},
		error: function() {
			$.messager.alert("Failed to query ggyr.");
		}
	});
	
}

function closeDialog() {
	$("#searchDialog").dialog("close");
}