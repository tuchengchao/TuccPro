function refreshVerification() {
	$("img[name=verificationImg]")[0].src = "verification?uri=" + tw.ws.uri + "&r=" + Math.random();
}
$(document).ready(function(){
	refreshVerification();
	$("button[name=login]").on("click", function(){
		var username = $("input[name=username]"),
		password = $("input[name=password]"),
		verification = $("input[name=verification]"),
		vcode = verification.val();
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
		vcode = vcode.toLowerCase();
		if(b64_md5(vcode + "verification") != window.verification){
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