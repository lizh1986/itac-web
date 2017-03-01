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

var flag = true;

$(function(){
	$('#calendar').fullCalendar({
		header: {
			left: "prev, next",
			center: "title",
			right: "today"
		},
		editable : true,
		firstDay: 1,
		weekMode:'liquid',
		dayClick: function(date, allDay, jsEvent, view) {
			debugger;
			console.info(date);
			var dateString = $.fullCalendar.formatDate(date,"yyyy-MM-dd");
			var type = 1;
			
			var cell = $(this);
			if(cell.children().children().find('.redFont').text() != undefined 
					&& cell.children().children().find('.redFont').text() != "") {
				type = 0;
			}
			
			$.ajax({
				type: "post",
				url: "../workday/set",
				dataType: "json",
				data: {
					date: dateString,
					type: type
				},
				success: function(resp) {
					if (resp.code == "200") {
						if (type == 0) {
							cell.children().find('.fc-day-content').empty();
						} else {
							var state ='<font class="redFont" style="font-weight: bold;background: #FFAB3D;color: #fff;padding: 5px;font-size: 16px;border-radius: 5px;">OFF</font>';
							cell.children().find('.fc-day-content').append(state);
						}
					} else {
						$.messager.alert("Error Code: " + resp.code);
					}
				},
				error: function() {
					$.messager.alert("Failed to set off day.");
				}
			});
		},
		viewRender: function(view, element) {
			var start = $.fullCalendar.formatDate(view.start,"yyyy-MM-dd");
			var end = $.fullCalendar.formatDate(view.end,"yyyy-MM-dd");
			$.ajax({
				type: "post",
				url: "../workday/query",
				dataType: "json",
				data: {
					start: start,
					end: end
				},
				success: function(resp) {
					debugger;
					if (resp.code == "200") {
						$(".fc-day").each(function(){
							debugger;
							if (isExist(resp.data, $(this).attr("data-date"))) {
								debugger;
								var state ='<font class="redFont" style="font-weight: bold;background: #FFAB3D;color: #fff;padding: 5px;font-size: 16px;border-radius: 5px;">OFF</font>';
								$(this).children().find('.fc-day-content').append(state);
							}
						});
					} else {
						$.messager.alert("Failed to load off day.");
					}
				},
				error: function() {
					$.messager.alert("Failed to set off day.");
				}
			});
		}
	});
});

$(function(){
    $("#fc-dateSelect").delegate("select", "change", function(){
        var fcsYear = $("#fcs_date_year").val();
        var fcsMonth = $("#fcs_date_month").val();
        $("#calendar").fullCalendar('gotoDate', fcsYear, fcsMonth);
    });
    
});

function isExist(list, obj) {
	for (var i = 0; i < list.length; i++) {
		if (obj == list[i]) {
			return true;
		}
	}
	return false;
}

