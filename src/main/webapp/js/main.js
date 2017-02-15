
(function($) {
	$(function(){
		var url = "nav/first";
		$.ajax({
			type: "post",
			url: url,
			dataType: "json",
			success: function(resp) {
				var data = resp.data;
				$("#topmenu").empty();
				$("#topmenu").append("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:addTab('Home','jsp/welcome.jsp')\" >Home</a>");
				if(data.length > 0){
					for(i in data) {
						$("#topmenu").append("<a id='" + data[i].id+"'>" + data[i].text + "</a>");	
					}
					//自动加载第一个一级菜单下面的二级菜单
					$("#topmenu  a").get(1).click();
				}
			},error: function(event, request, settings) {
				$.messager.alert("Warning", "Failed to load menus!", "info");
			}
		});
		
		//切换一级菜单 加载二级和三级菜单
		$("#topmenu").on('click','a',function(){
			//设置样式
			$("#topmenu > a").removeClass("active");
			$(this).addClass("active");
			
			//加载 二级菜单和三级菜单
			var parentId = $(this).attr("id");
			if(!JUDGE.isNull(parentId)){
				$('#menuTree').tree({
					url:"nav/second?id=" + parentId,
					onClick: function(node) {
						if(node.attributes){
							addTab(node.text,node.attributes.href);	
						}
					}
				});
			}
		});
	});
})(window.jQuery);

function logout() {
	var url = "logout";
	$.ajax({
		type: "post",
		url: url,
		dataType: "json",
		success: function(resp) {
			if (resp.code == 400) {
				$.messager.alert("Error", "Failed to logout at iTAC.", "Error");
			} else {
				window.location.href = "login.jsp";
			}
		},error: function(event, request, settings) {
			$.messager.alert("Warning", "Connection is Time out!", "info");
		}
	});
}