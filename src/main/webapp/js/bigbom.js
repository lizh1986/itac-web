
function resize() {
	$("#bigBomTable").datagrid("resize", {
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
	$("#bigBomTable").datagrid({
		title: "",
		width:width,
		height: height,
		singleSelect: true,
		rownumbers: true,
		loadMsg: 'Loading',
		url:'../bigbom/query',
		queryParams: {},
		method:"get",
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
		        	   field:"workOrder",
		        	   title:"MO",
		        	   width:150
		           }, {
		        	   field:"partNumber",
		        	   title:"Part Number",
		        	   width:150
		           }, {
		        	   field:"quantity",
		        	   title:"quantity",
		        	   width:150
		           }, {
		        	   field:"createTime",
		        	   title:"Creation Time",
		        	   width:150
		           }
		]]
	});
	
	var pager = $("#bigBomTable").datagrid("getPager"); 
	pager.pagination({
		onSelectPage:function (pageNo, pageSize) {
			var start = (pageNo - 1) * pageSize; 
			var end = start + pageSize; 
			
			var page = {
				total: datas.total,
				data: datas.data.slice(start, end)
			};
			
			$("#bigBomTable").datagrid("loadData", page);
			pager.pagination('refresh', {
				total:datas.total, 
				pageNumber:pageNo 
			}); 
		}
	});
});


function doSearch() {
	$("#bigBomTable").datagrid("reload");
}

