$(document).ready(
		function() {
			$('.form_datetime').datetimepicker({
				format : 'yyyy-mm',
				autoclose : true,
				todayBtn : true,
				startView : 'year',
				minView : 'year',
				maxView : 'decade',
				language : 'zh-CN'
			});
			var now = new Date();
			var date = now.getFullYear() + "-"
					+ (now.getMonth() < 9 ? "0" : "") + (now.getMonth() + 1);
			$("input[name=stdate]").val(date);
			$("input[name=eddate]").val(date);
		});
function check() {
	var stdate = $("input[name=stdate]").val();
	var eddate = $("input[name=eddate]").val();
	var dclj = $("input[name=dclj]").val();
	var kz = $("select[name=kz]").val();
	if (stdate == "") {
		alert("请选择备份的起始年月");
		return false;
	}
	if (eddate == "") {
		alert("请选择备份的终止年月");
		return false;
	}
	if (eddate < stdate) {
		alert("时间范围不对");
		return false;
	}
	$("button[name=sub]").attr("disabled", true);
	$.ajax({
		url : 'ZskExp.do',
		type : 'POST', // GET
		async : true, // 或false,是否异步
		data : {
			kz : kz,
			stdate : stdate,
			eddate : eddate,
			dclj : dclj
		},
		dataType : 'text',
		beforeSend : function(xhr) {
		},
		success : function(data, textStatus, jqXHR) {
			var parm = $("#zskExpForm").serialize();
			window.location = "ZskExp.do?method=dowmload";
		},
		error : function(xhr, textStatus) {
			alert("程序异常");
		},
		complete : function() {
			$("button[name=sub]").attr("disabled", false);
		}
	});
	return false;
}