(function($, window, undefined) {
	var tw = function() {
	};
	tw.init = function() {
		tw.UI.init();
	}
	/**
	 * UI组件
	 */
	tw.UI = function() {
		this.width = $(window).width();
		this.height = $(window).height();
		/**
		 * 初始化方法
		 */
		this.init = function(){
			this.initToast();
		}
		/**
		 * 打开模态窗
		 * url
		 * width
		 * height
		 */
		this.modal = function(url,width,height) {
			var loader = $("#modalloader");
			if (loader.length == 0) {
				$(document.body).append("<div id=\"modalloader\"></div>");
			} else {
				loader.empty();
			}
			$("#modalloader").load(url);
			
			setTimeout(function() {
				$(".modal-dialog").css("height",height);
				$(".modal-dialog").css("width",width);
				$(".modal-dialog").css("top", height < this.height ? this.height / 2 - height / 2 : "auto");
				$(".modal").modal('show');
			}, 100);
		};
		
		/**
		 * 初始化toastr提示工具参数
		 */
		this.initToast = function() {
			// toast显示默认配置
			var messageOpts = {
				"closeButton" : true,// 是否显示关闭按钮
				"debug" : false,// 是否使用debug模式
				"positionClass" : "toast-top-center",// 弹出窗的位置
				"onclick" : null,
				"showDuration" : "300",// 显示的动画时间
				"hideDuration" : "1000",// 消失的动画时间
				"timeOut" : "3000",// 展现时间
				"extendedTimeOut" : "1000",// 加长展示时间
				"showEasing" : "swing",// 显示时的动画缓冲方式
				"hideEasing" : "linear",// 消失时的动画缓冲方式
				"showMethod" : "fadeIn",// 显示时的动画方式
				"hideMethod" : "fadeOut" // 消失时的动画方式
			};
			toastr.options = messageOpts;
		}
		return this;
	}();
	window.tw = tw;
	tw.init();
}(jQuery, window));