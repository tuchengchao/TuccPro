function opendl(){
	var loader = $("#modalloader");
	if(loader.length == 0){
		$(document.body).append("<div id=\"modalloader\"></div>");
	}
	else{
		loader.empty();
	}
	$("#modalloader").load("login.html");
	setTimeout(function(){
		$("#myModal").modal('show');
	}, 100);
}