function refreshVerification() {
	$("img[name=verificationImg]")[0].src = tw.ctx + "verification?uri=" + tw.ws.uri + "&r=" + Math.random();
}
$(document).ready(function(){
	refreshVerification();
	var username = $("input[name=username]"),
	password = $("input[name=password]"),
	verification = $("input[name=verification]")
	loginBtn = $("button[name=login]");
	username.on("keyup",keyup2login);
	password.on("keyup",keyup2login);
	verification.on("keyup",keyup2login);
	function keyup2login(event){
		switch(event.keyCode) {
			case 13:
				loginBtn.click();
				break;
		}
	}
	loginBtn.on("click", function(){
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
		if(tw.ws.isConnected){
			vcode = "verification" + vcode.toLowerCase();
			if(b64_md5(vcode) != window.verification){
				toastr.warning("验证码不正确");
				verification.focus();
				return;
			}		
		}
		var data = $('form[name=loginForm]').serialize() + "&uri=" + tw.ws.uri;
		login(data, true);
	});
});
