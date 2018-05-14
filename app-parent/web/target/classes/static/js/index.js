function openLogin(){
	tw.UI.modal("login", 300, 300);
}
function openRegister(){
	tw.UI.modal("register", 300, 300);
}
$(document).ready(function(){
	$(".navbar .right a")[0].href="javascript:openLogin();";
	$(".navbar .right a")[1].href="javascript:openRegister();";
	tw.ws.connect();
});