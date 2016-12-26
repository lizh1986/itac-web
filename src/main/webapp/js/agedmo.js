
function resize() {
	$("#agedmoTable").datagrid("resize", {
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
			debugger;
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
		        	   field:"firstBooking",
		        	   title:"First Booking Time",
		        	   width:150
		           }, {
		        	   field:"aged",
		        	   title:"Aged",
		        	   width:150,
		        	   styler: function(value,row,index) {
		        		   var v = $.trim(value.split('day')[0]);
		        		   if (v >=2) {
		        			   return 'background-color:red;';
		        		   }
		        	   }
		           }, {
		        	   field:"id",
		        	   title:"Operation",
		        	   width:150,
		        	   formatter: function() {
		        		   return "<a href='http://www.baidu.com'>detail</a>";
		        	   }
		           }
		]]
	});
	
	var pager = $("#agedmoTable").datagrid("getPager"); 
	pager.pagination({
//		total:datas.length,
		onSelectPage:function (pageNo, pageSize) {
			debugger;
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

function agedMoLoader(param, success, error) {
	debugger;
	var that = $(this);
    var opts = that.datagrid("options");
    if (!opts.url) {
        return false;
    }
    
    var cache = that.data().datagrid.cache;
    if (!cache) {
        $.ajax({
            type: opts.method,
            url: opts.url,
            data: param,
            dataType: "json",
            success: function (data) {
            	debugger;
            	var result = {
            			rows: data.data,
    					total: data.total
            	};
                that.data().datagrid['cache'] = result;
                buildData(result);
            },
            error: function () {
                error.apply(this, arguments);
            }
        });
    } else {
    	buildData(cache);
    }

    function buildData(data) {
        debugger;
        var temp = $.extend({}, data);
        var tempRows = [];
        var start = (param.page - 1) * parseInt(param.rows);
        var end = start + parseInt(param.rows);
        var rows = data.rows;
        for (var i = start; i < end; i++) {
            if (rows[i]) {
                tempRows.push(rows[i]);
            } else {
                break;
            }
        }
  
        temp.rows = tempRows;
        return temp;
    }
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
//	var rows = $("#agedmoTable").datagrid("getPager").data("pagination").options.pageSize;
	
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
			debugger;
			datas = {
				total: resp.total,
				data: resp.data
			};
			
			var firstPage = {
				total: resp.total,
				data: resp.data.slice(0,2)
			};
			$("#agedmoTable").datagrid("loadData", firstPage);
			
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
	debugger;
	var dlg = $("searchDialog").dialog();
	if (dlg) {
		$("#searchDialog").dialog("close");
	}
	
}