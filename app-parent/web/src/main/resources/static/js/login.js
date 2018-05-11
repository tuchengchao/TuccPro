function refresh_yzm() {
	$("img[name=yzmImg]")[0].src = "verification?r=" + Math.random();
}
$(document).ready(function(){
	refresh_yzm();
});