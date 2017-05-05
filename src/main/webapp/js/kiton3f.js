
function resize() {
	$("#kitInfoTable").datagrid("resize", {
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
	$("#fromDate").datebox({
		formatter: customFormat,
		parser: customParser,
		onSelect: function(date) {
			if (date > (new Date())) {
				$.messager.alert('warning', 'can not choose the day after today.');
				var toDate = customParser($('#toDate').datebox('getValue'));
				var fromDate = new Date();
				fromDate.setDate(toDate.getDate() - 6);
				$(this).datebox('setValue', customFormat(fromDate));
			}
		}
	});
	
	$("#toDate").datebox({
		formatter: customFormat,
		parser: customParser,
		onSelect: function(date) {
			if (date > (new Date())) {
				$.messager.alert('warning', 'can not choose the day after today.');
				$(this).datebox('setValue', customFormat(new Date()));
			}
		}
	});
	
	var current = new Date();
	$("#toDate").datebox('setValue', customFormat(current));
	var defaultFrom = new Date();
	defaultFrom.setDate(current.getDate() - 6);
	$("#fromDate").datebox('setValue', customFormat(defaultFrom));
	
	$("#kitInfoTable").datagrid({
		title: "",
		width:width,
		height: height,
		singleSelect: true,
		rownumbers: true,
		queryParams: {},
		loadFilter:function(data){
			if(data){
				var records = {
					rows: data.data,
					total: data.total
				};
				return records;
			}
		},
		pagination:true,
		striped: true,
		pageSize: 20,
		pageList: [20, 50, 100],
		fitColumns: true,
		columns: [[{
		        	   field:"serialNumber",
		        	   title:"Serial Number",
		        	   sortable: true,
		        	   width:150
		           }, {
		        	   field:"containerNumber",
		        	   title:"Container Number",
		        	   width:150
		           }, {
		        	   field:"partNumber",
		        	   title:"Part Number",
		        	   width:150
		           }
		]]
	});
	
	var pager = $("#kitInfoTable").datagrid("getPager"); 
	pager.pagination({
		onSelectPage:function (pageNo, pageSize) {
			var start = (pageNo - 1) * pageSize; 
			var end = start + pageSize; 
			debugger;
			var page = {
				total: datas.total,
				data: datas.data.slice(start, end)
			};
			
			$("#kitInfoTable").datagrid("loadData", page);
			pager.pagination('refresh', {
				total:datas.total, 
				pageNumber:pageNo 
			}); 
		}
	});
});

var datas;

function doSearch() {
	var from = $('#fromDate').datebox('getValue') + ' 00:00:00';
	var to = $('#toDate').datebox('getValue') + ' 23:59:59';

	$('body').showLoading();
	$.ajax({
		type:"get",
		url:"../kit3/query",
		dataType: "json",
		data: {
			'from': from,
			'to': to
		},
		success: function(resp) {
			$('body').hideLoading();
			if (resp.code == 001) {
				$.messager.alert("Error", "Params can not be null.");
				return;
			}
			
			if (resp.code == 007) {
				$.messager.alert("Error", "Start date can not be late than end date.");
				return;
			}
			
			if (resp.code == 008) {
				$.messager.alert("Error", "Data of no more than 10 days can be queried.");
				return;
			}
			
			datas = {
				total: resp.total,
				data: resp.data
			};
			
			var pageSize = $("#kitInfoTable").datagrid("getPager").data("pagination").options.pageSize;
			var firstPage = {
				total: resp.total,
				data: resp.data.slice(0,pageSize)
			};
			
			$("#kitInfoTable").datagrid("loadData", firstPage);
		},
		error: function(request, textStatus, errorThrown) {
			$('body').hideLoading();
			$.messager.alert("Error", "Failed to query Kit info on 3F.");
		}
	});
}

function doExport() {
	var from = $('#fromDate').datebox('getValue') + ' 00:00:00';
	var to = $('#toDate').datebox('getValue') + ' 23:59:59';
	
	$.ajax({
		type:"get",
		url:"../kit3/validate",
		dataType: "json",
		data: {
			'from': from,
			'to': to
		},
		success: function(resp) {
			if (resp.code == 001) {
				$.messager.alert("Error", "Params can not be null.");
				return;
			}
			
			if (resp.code == 007) {
				$.messager.alert("Error", "Start date can not be late than end date.");
				return;
			}
			
			if (resp.code == 008) {
				$.messager.alert("Error", "Data of no more than 10 days can be exported.");
				return;
			}
			
			if (resp.code == 200) {
				window.location.href = '../kit3/export?from=' + from + '&' + 'to=' + to;
			}
		},
		error: function(request, textStatus, errorThrown) {
			$.messager.alert("Error", "Failed to export Kit info on 3F.");
		}
	});
}

function customFormat(date){  
	var y = date.getFullYear();  
	var m = date.getMonth() + 1;  
	var d = date.getDate();  
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);  
}

function customParser(s) {
	if (!s) return new Date();
    
    var yStr = s.substr(0,4);
    var mStr = s.substr(5,2)
    var dStr = s.substr(8,2)

    var y = parseInt(yStr,10);
    var m = parseInt(mStr,10);
    var d = parseInt(dStr,10);
    
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}

function dateAfter(date, count) {
	if (date == null) {
		date = new Date();
	}
	
	var d = new Date();
	d.setDate(date.getDte() + count - 1);
	return d;
}