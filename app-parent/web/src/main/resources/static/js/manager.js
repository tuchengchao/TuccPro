var lpw,rpw;
$(document).ready(function(t){
	var lp = $(".left-panel"), rp = $(".right-panel"), lph = $(".left-panel-hidden"), rph = $(".right-panel-hidden");
	var ppt = 400;
	lph.on("click", function(){
		if(lp.css('display') == "none"){
			lph.animate({ 
				left:parseInt(lph.css('left'), 10) == lp.width() ? lph.outerWidth() : lp.width() 
			});
			lph.html("&lt;");
			lp.show(ppt);
		}
		else{
			lph.animate({ 
				left:parseInt(lph.css('left'), 10) == 0 ? - lph.outerWidth() : 0 
			});
			lph.html("&gt;");
			lp.hide(ppt);
		}
	});
	rph.on("click", function(){
		if(rp.css('display') == "none"){
			rph.animate({ 
				right:parseInt(rph.css('right'), 10) == rp.width() ? rph.outerWidth() : rp.width() 
			});
			rph.html("&nbsp;&gt;");
			rp.show(ppt);
		}
		else{
			rph.animate({ 
				right:parseInt(rph.css('right'), 10) == 0 ? - rph.outerWidth() : 0 
			});
			rph.html("&nbsp;&lt;");
			rp.hide(ppt);
		}
	});
	
	$(".menu-tab-row .btn").on("click", function(){
		$('.menu-active').removeClass('menu-active');
		this.classList.add("menu-active");
	});
});