(function () {
	function TccWeb(){
		var noop = function(){};
		var initEvents = new Array();
		TccWeb.prototype.ui = noop;
		TccWeb.prototype.ws = noop;
		TccWeb.prototype.cv = noop;
	}
	window.tw = new TccWeb();
	tw.ctx = "/";
}
)();