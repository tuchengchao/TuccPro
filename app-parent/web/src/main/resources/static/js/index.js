function openLogin() {
	tw.UI.modal("login", 300, 300);
}
function openRegister() {
	tw.UI.modal("register", 300, 300);
}
$(document).ready(function() {
	$(".navbar .right a")[0].href = "javascript:openLogin();";
	$(".navbar .right a")[1].href = "javascript:openRegister();";
	if($("input[name=frontVerification]").val == "true"){
		tw.ws.connect();
	}
	tw.cv.bind($("button[name=cutScreen]"),null,function(){alert(1)}, function(){alert(2)});
	}
);
function checkLogin(){
	
}