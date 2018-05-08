function open_dl(){
	tw.UI.modal("login.html", 300, 300);
}
function open_zc(){
	tw.UI.modal("register.html", 300, 300);
}
$(document).ready(function(){
	$(".navbar .right a")[0].href="javascript:open_dl();";
	$(".navbar .right a")[1].href="javascript:open_zc();";
});