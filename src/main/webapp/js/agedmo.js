
function resize() {
	$("#calendar").datagrid("resize", {
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
	$("#agedmoTable").datagrid({
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
		        	   width:150
		           }, {
		        	   field:"created",
		        	   title:"Creation Time",
		        	   width:150
		           }, {
		        	   field:"aged",
		        	   title:"Aged",
		        	   width:150,
		        	   styler: function(value,row,index) {
		        		   var v = $.trim(value.split('day')[0]);
		        		   if (v >= 2) {
		        			   return 'color:red;';
		        		   }
		        	   }
		           }, {
		        	   field:"id",
		        	   title:"Operation",
		        	   width:150,
		        	   formatter: function(value, row, index) {
		        		   return '<a href="javascript:void(0)" onclick="displayMoInfo(\'' + row.mo + '\')">detail</a>';
		        	   }
		           }
		]]
	});
	
	var pager = $("#agedmoTable").datagrid("getPager"); 
	pager.pagination({
//		total:datas.length,
		onSelectPage:function (pageNo, pageSize) {
			var start = (pageNo - 1) * pageSize; 
			var end = start + pageSize; 
			
			var page = {
				total: datas.total,
				data: datas.data.slice(start, end)
			};
			
			$("#agedmoTable").datagrid("loadData", page);
			pager.pagination('refresh', {
				total:datas.total, 
				pageNumber:pageNo 
			}); 
		}
	});
});

function displayMoInfo(mo) {
	$("#menuTree", window.parent.document).find("*").each(function() {
		if ($(this).text().indexOf('MO Info') != -1) {
			$(this).parent().attr("mo", mo);
			$(this).parent().click();
			var that = this;
			var t = setTimeout(function() {
				$(that).parent().removeAttr("mo");
				clearTimeout(t);
			}, 600);
		}
	});
	
//	addTab(title, url);
}

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
	
	var url = "../agedmo/query";
	
	var queryParams = $("#agedmoTable").datagrid("options").queryParams;
	queryParams.mos = mos;
	$("#agedmoTable").datagrid("options").queryParams = queryParams;
	$("#agedmoTable").datagrid("options").url = url;
	var rows = $("#agedmoTable").datagrid("getPager").data("pagination").options.pageSize;
	
	$.ajax({
		type:"post",
		url:url,
		dataType: "json",
		data: {
			mos: $("#mos").val()
		},
		success: function(resp) {
			if (resp.total <= 0) {
				$("#agedmoTable").datagrid("loadData", {total: 0, data: []});
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
			$("#agedmoTable").datagrid("loadData", firstPage);
			
			var msg = resp.msg;
			if (msg) {
				$.messager.alert("warning", msg.replace(/\\n/g, "<br/>"));
			}
			closeDialog();
		},
		error: function() {
			$.messager.alert("Failed to query aged MO information.");
		}
	});
	
}

function closeDialog() {
	var dlg = $("#searchDialog").dialog();
	if (dlg) {
		$("#searchDialog").dialog("close");
	}
	
}