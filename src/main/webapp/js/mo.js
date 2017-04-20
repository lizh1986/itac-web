
function resize() {
	$("#moTable").treegrid("resize", {
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
	$("#moTable").treegrid({
		title: "",
		width:width,
		height: height,
		singleSelect: true,
		//rownumbers: true,
		queryParams: {},
		method:"post",
		loadFilter:function(data){
			var result;
			if(data){
				if (data.total == 0) {
					result = {
						total:0,
						rows:[]
					};
				} else {
					result = {
						rows: data.rows,
						total: data.total
					};
				}
				
				return result;
			}
		},
		onLoadSuccess: function () {
			$('#moTable').treegrid('collapseAll')
		},
		pagination:true,
		striped: true,
		pageSize: 20,
		pageList: [20, 50, 100],
		fitColumns: true,
		singleSelect: false,
		idField:'mo',
		treeField:'mo',
		columns: [[
		           { 
					   field:'ck',
					   checkbox:true 
				   },{
		        	   field:"mo",
		        	   title:"MO",
		        	   width:120,
		        	   formatter: function(data) {
		        		   if (data == null || data == undefined) {
		        			   return "";
		        		   } else {
		        			   return data;
		        		   }
		        	   }
		           }, {
		        	   field:"ws",
		        	   title:"WS",
		        	   width:80
		           }, {
		        	   field:"quantity",
		        	   title:"Quantity",
		        	   width:80
		           }, {
		        	   field:"mtm",
		        	   title:"MTM",
		        	   width:80
		           },{
		        	   field:"passed",
		        	   title:"Pass",
		        	   width:70
		           },{
		        	   field:"pending",
		        	   title:"Pending & Repair",
		        	   width:100
		           },{
		        	   field:"creationTime",
		        	   title:"Creation Time",
		        	   width:120
		           },{
		        	   field:"firstBooking",
		        	   title:"First Booking",
		        	   width:120
		           },{
		        	   field:"lastBooking",
		        	   title:"Last Booking",
		        	   width:120
		           }
		]]
	});
	
	var pager = $("#moTable").treegrid("getPager"); 
	pager.pagination({
//		total:datas.length,
		onSelectPage:function (pageNo, pageSize) {
			var start = (pageNo - 1) * pageSize; 
			var end = start + pageSize; 
			
			var page = {
				total: datas.total,
				rows: datas.data.slice(start, end)
			};
			
			$("#moTable").treegrid("loadData", page);
			pager.pagination('refresh', {
				total:datas.total, 
				pageNumber:pageNo 
			}); 
		}
	});
	var tt = $("#menuTree", window.parent.document).attr("mo");
	if (tt != null && tt != undefined) {
		$("#mos").val(tt);
		doSearch();
	}
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
	
	if (JUDGE.isNull($("#mos").val())) {
		$.messager.alert("Warning", "Please input Mo.");
		return;
	}
	
	var url = "../mo/querymo";
	
	var queryParams = $("#moTable").treegrid("options").queryParams;
	queryParams.mos = mos;
	$("#moTable").treegrid("options").queryParams = queryParams;
	$("#moTable").treegrid("options").url = url;
	var rows = $("#moTable").treegrid("getPager").data("pagination").options.pageSize;
	
//	$("#moTable").treegrid("loadData", moData);
	
	$.ajax({
		type:"post",
		url:url,
		dataType: "json",
		data: {
			mos: $("#mos").val()
		},
		success: function(resp) {
			datas = {
				total: resp.total,
				data: resp.data
			};
			
			var firstPage = {
				total: resp.total,
				rows: resp.data.slice(0,rows)
			};
//			$("#moTable").treegrid("loadData", {total: 0, rows: []});
			$("#moTable").treegrid("loadData", firstPage);
			
			var msg = resp.msg;
			if (msg) {
				$.messager.alert("Search", msg.replace(/\\n/g, "<br/>"), "warning");
			}
			closeDialog();
		},
		error: function() {
			$.messager.alert("Search", "Failed to query MO information.", "warning");
		}
	});
}

function doExportMo() {
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
	
	window.location.href = '../mo/export/mos?mos=' + mos;
}

function searchSns() {
	var mos = [];
	var selected = $("#moTable").treegrid('getSelections');
	for (var i = 0; i < selected.length; i++) {
		if (selected[i].mo != undefined) {
			mos.push(selected[i].mo);
		}
	}
	
	if (mos.length == 0) {
		$.messager.alert("Query Pending SN","Please Choose Mos to query pending SN.", "warning");
	}
	var queryString = mos.join("\n");
	
	$.ajax({
		type:"post",
		url:"../mo/querysn",
		dataType: "json",
		data: {
			mos: queryString
		},
		success: function(resp) {
			$("#snGrid").datagrid({
	            fitColumns: false,  
	            loadMsg: "数据加载中......",  
	            rownumbers: true,  
	            nowrap: false,  
	            showFooter: true,  
	            singleSelect: true
	        });  
	  
	        var snList = {
				total: resp.total,
				rows: resp.data
			};
	        
	        $("#snGrid").datagrid("loadData", snList);
	  
	        $('#snDialog').window('open');
		},
		error: function() {
			$.messager.alert("Query Pending SN", "Failed to query  pending SN.", "warning");
		}
	});
}

function doExportSns() {
	var mos = [];
	var selected = $("#moTable").treegrid('getSelections');
	for (var i = 0; i < selected.length; i++) {
		if (selected[i].mo != undefined) {
			mos.push(selected[i].mo);
		}
	}
	
	if (mos.length == 0) {
		$.messager.alert("Query Pending SN","Please Choose Mos to query pending SN.", "warning");
	}
	var queryString = mos.join(",");
	
	window.location.href = '../mo/export/sns?mos=' + queryString;
}

function closeDialog() {
	var dlg = $("#searchDialog").dialog();
	if (dlg) {
		$("#searchDialog").dialog("close");
	}
	
}