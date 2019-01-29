function openLogin() {
	tw.ui.modal({url:tw.ctx + "login", width:400, height:300});
}
function openRegister() {
	tw.ui.confirm({message:"确认要注册？"});
}
$(document).ready(function() {
	logout();
	if($("input[name=frontVerification]").val == "true"){
		tw.ws.connect();
	}
	tw.cv.bind($("button[name=cutScreen]"),null,function(){alert(1)}, function(){alert(2)});
	login("auto=true");
});
function logout(real){
	$(".login-register").show();
	$(".cog-off").hide();
	$(".login-register a")[0].href = "javascript:openLogin();";
	$(".login-register a")[1].href = "javascript:openRegister();";
	$(".cog-off .cog").off("click");
	$(".cog-off .off").off("click");
	if(real){
		$.post(tw.ctx + "logout", "r=" + Math.random(), function(data) {
			tw.ui.alert({message:data});
		});
	}
}
function login(data, showMsg){
	tw.ui.loading();
	$.post("login", data, function(data) {
		tw.ui.ready();
		switch(data.code){
			case 1: 
				if(showMsg){
					toastr.success(data.msg);
				}
				logon();
				break;
			default:
				if(showMsg){
					toastr.warning(data.msg);
				}
				break;
		}
	});
}
function logon(){
	$("#modalloader").find(".modal").modal('hide');
	$(".login-register a")[0].href = "#";
	$(".login-register a")[1].href = "#";
	$(".cog-off").show();
	$(".cog-pull").hide();
	$(".login-register").hide();
	$(".cog-off .cog").off("click").on("click",function(){
		$(".cog-pull").toggle();
	});
	$(".cog-off .off").off("click").on("click",function(){
		tw.ui.confirm({message:"确认要注销吗？"},function(){
			logout(true);
		});
	});
}