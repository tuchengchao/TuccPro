$(document).ready(function(){
	$(".cog-pull").hide();
	$(".cog-off .cog").off("click").on("click",function(){
		$(".cog-pull").toggle();
	});
	$(".cog-off .off").off("click").on("click",function(){
		tw.ui.confirm({message:"确认要注销吗？"},function(){
			logout();
		});
	});
});
function toIndex(){
	location.href= tw.ctx;
}
function logout(){
	$.post(tw.ctx + "logout", "r=" + Math.random(), function(data) {
		toIndex();
	});
}