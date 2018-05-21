function refreshVerification() {
	$("img[name=verificationImg]")[0].src = "verification?uri=" + tw.ws.uri + "&r=" + Math.random();
}
$(document).ready(function(){
	refreshVerification();
	var username = $("input[name=username]"),
	password = $("input[name=password]"),
	verification = $("input[name=verification]")
	login = $("button[name=login]");
	username.on("keyup",function(event){
		switch(event.keyCode) {
		case 13:
			login.click();
			break;
		}
	});
	login.on("click", function(){
		var vcode = verification.val();
		if(!username.val()){
			username.focus();
			return;
		}
		if(!password.val()){
			password.focus();
			return;
		}
		if(!vcode){
			verification.focus();
			return;
		}
		vcode = "verification" + vcode.toLowerCase();
		if(b64_md5(vcode) != window.verification){
			toastr.warning("验证码不正确");
			verification.focus();
			return;
		}		
		var data = $('form[name=loginForm]').serialize() + "&uri=" + tw.ws.uri;
		$.post("login", data, function(data) {
			  toastr.warning(data);
		});
	});
});
function logon(){
	
}