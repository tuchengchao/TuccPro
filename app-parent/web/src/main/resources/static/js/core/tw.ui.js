(function($, window, undefined) {
	var tw = function() {
	};
	tw.init = function() {
		tw.ui.init();
	}
	/**
	 * UI组件
	 */
	tw.ui = function() {
		var _ui = function(){};
		_ui.width = $(window).width();
		_ui.height = $(window).height();
		/**
		 * 初始化方法
		 */
		_ui.init = function(){
			_ui.initToast();
		}
		/**
		 * 打开模态窗
		 * url
		 * width
		 * height
		 */
		_ui.modal = function(options) {
			options = $.extend({}, {
                url:"login",
                width: 200,
                height: 200
            }, options || {});
			var loader = $("#modalloader");
			if (loader.length == 0) {
				$(document.body).append("<div id=\"modalloader\"></div>");
			} else {
				loader.empty();
			}
			loader = $("#modalloader");
			loader.load(options.url);
			setTimeout(function() {
				var dialog = $("#modalloader .modal-dialog");
				dialog.css({"height":options.height,
					"width":options.width,
					"top": options.height < _ui.height ? _ui.height / 2 - options.height / 2 : "auto"
				});
				loader.find(".modal").modal('show');
			}, 100);
		};
		var alertHtml = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
	        '<div class="modal-dialog modal-sm">' +
	            '<div class="modal-content">' +
	                '<div class="modal-header">' +
	                    '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
	                    '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
	                '</div>' +
	                '<div class="modal-body">' +
	                	'<p>[Message]</p>' +
	                '</div>' +
	                '<div class="modal-footer">' +
						'<button type="button" class="btn btn-primary ok" data-dismiss="modal">[BtnOk]</button>' +
					'</div>' +
	            '</div>' +
	        '</div>' +
        '</div>';
		var confirmHtml = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
	        '<div class="modal-dialog modal-sm">' +
	            '<div class="modal-content">' +
	                '<div class="modal-header">' +
	                    '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
	                    '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
	                '</div>' +
	                '<div class="modal-body">' +
	                	'<p>[Message]</p>' +
	                '</div>' +
	                '<div class="modal-footer">' +
						'<button type="button" class="btn btn-default cancel" data-dismiss="modal">[BtnCancel]</button>' +
						'<button type="button" class="btn btn-success ok" data-dismiss="modal">[BtnOk]</button>' +
					'</div>' +
	            '</div>' +
	        '</div>' +
        '</div>';
		_ui.alert = function(options){
			options = $.extend({}, {
                title:"提示",
                message:"提示内容",
                btnok:"确认",
                backdrop:true
            }, options || {});
			var modalId = UUID.generate().replace(/-/g,'');
			var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
			var content = alertHtml.replace(reg, function (node, key) {
                return {
                    Id: modalId,
                    Title: options.title,
                    Message: options.message,
                    BtnOk: options.btnok,
                }[key];
            });
			$('body').append(content);
			var modal = $('#' + modalId);
			modal.modal({
                backdrop: options.backdrop
            });
			modal.on('hide.bs.modal', function (e) {
                $('body').find('#' + modalId).remove();
            });
		}
		_ui.confirm = function(options,callback){
			options = $.extend({}, {
                title:"确认",
                message:"确认内容",
                btnok:"确认",
                btncl:"取消",
                backdrop:true
            }, options || {});
			var modalId = UUID.generate().replace(/-/g,'');
			var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
			var content = confirmHtml.replace(reg, function (node, key) {
                return {
                    Id: modalId,
                    Title: options.title,
                    Message: options.message,
                    BtnOk: options.btnok,
                    BtnCancel: options.btncl
                }[key];
            });
			$('body').append(content);
			var modal = $('#' + modalId);
			modal.modal({
                backdrop: options.backdrop
            });
			modal.on('hide.bs.modal', function (e) {
                $('body').find('#' + modalId).remove();
            });
            modal.find('.ok').removeClass('btn-primary').addClass('btn-success');
            modal.find('.ok').on('click',callback);
		}
		/**
		 * 初始化toastr提示工具参数
		 */
		_ui.initToast = function() {
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
		return _ui;
	}();
	window.tw = tw;
	tw.init();
}(jQuery, window));