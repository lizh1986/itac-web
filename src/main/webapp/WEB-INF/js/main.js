
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
				$("#topmenu").append("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:addTab('首页','html/welcome.html')\" >首页</a>");
				if(data.length > 0){
					for(i in data) {
						$("#topmenu").append("<a id='" + data[i].id+"'>" + data[i].text + "</a>");	
					}
					//自动加载第一个一级菜单下面的二级菜单
					$("#topmenu  a").get(1).click();	
				}
			},error: function(event, request, settings) {
				$.messager.alert("提示消息", "请求失败!", "info");
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

// 添加选项卡方法
function addTab(title, url) {
	//先判断是否存在标题为title的选项卡
	var tab = $('#first-tab').tabs('exists', title);
	if(tab) {
		//若存在则直接打开
		$('#first-tab').tabs('select', title);
	}else{
		//否则创建
		$('#first-tab').tabs('add', {
			title: title,
			content: "<iframe width='100%' height='100%'  id='iframe' frameborder='0' scrolling='no'  src='"+url+"'></iframe>",
			closable: true
		});
	}
}