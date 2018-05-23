(function () {
	var tw = window.tw;
    if(!window.tw){
    	tw = window.tw = function(){};
    }
    tw.cv = function(){
    	var _cv = function(){};
    	/**
    	 * btn 触发截图的按钮对象
    	 * img 回显截图的img, 没有的话弹出一层显示
    	 * beforecall 点击前事件(最好把overflow设置成hidden，否则截取时会显示scroll隐藏的内容)
    	 * callback 截完图后的事件
    	 */
    	_cv.bind = function(btn, img, beforecall, callback){
    		var cv = {x:0,y:0,w:0,h:0};
			function preview(img, sel) {
				$('.cutview-temp').css({
					overflow : "hidden",
					width : sel.width,
					height : sel.height
				});
				$('.cutview-temp > img').css({
					marginLeft : '-' + sel.x1 + 'px',
					marginTop : '-' + sel.y1 + 'px'
				});
				$(".canvasBar").css({
					top: sel.height + sel.y1,
					left: sel.width + sel.x1
				});
				cv.x = sel.x1;
				cv.y = sel.y1;
				cv.w = sel.width;
				cv.h = sel.height;
				if(sel.height + sel.width > 0){
					$(".canvasBar").show();
				}
				else{
					$(".canvasBar").hide();
				}
			}
			function getCv(event){
				$(".canvas").remove();
				$(".canvasBar").remove();
				$(".imgareaselect-outer").remove();
				$(".imgareaselect-selection").parent().remove();
				if(event){//不是esc触发，button触发会自动传入event
					$('.cutview-temp > img').show();
					html2canvas($(".cutview-temp"), {
						onrendered : function(canvas) {
							dataURL = canvas.toDataURL("image/png");
							// $("body").append(canvas);
							if(img){
								img.src = dataURL;
							}
							else{
								//$("body").append("<img src='" + dataURL + "'></img>");
								showcutview(dataURL);
							}
							$('.cutview-temp').remove();
							if(callback){
								callback.call(this);
			    			}
						},
						width : cv.w,
						height : cv.h
					});
					if('.cutview-temp'){
						$('.cutview-temp').hide();
					}

				}
				else{
					$('.cutview-temp').remove();
					if(callback){
						callback.call(this);
	    			}
				}
				$(document).unbind("keyup", listenEsc);
			}
			function showcutview(dataURL){
				if($(".cutview-box").length > 0){
					$(".cutview-box .cutview")[0].src = dataURL;
				}
				else{
					$("body").append("<div class = 'cutview-box' >"+
				    "<img class = 'cutview' src='" + dataURL + "'>"+
				    "<a href='#' class='close-btn' onclick='$(\".cutview-box\").remove()'></a>"+
				    "</div>");
					$(".cutview-box").mousedown(function(e){ 
			            $(this).css("cursor","move");//改变鼠标指针的形状  
			            var offset = $(this).offset();//DIV在页面的位置  
			            var x = e.pageX - offset.left;//获得鼠标指针离DIV元素左边界的距离  
			            var y = e.pageY - offset.top;//获得鼠标指针离DIV元素上边界的距离  
			            $(document).bind("mousemove",function(ev){ //绑定鼠标的移动事件，因为光标在DIV元素外面也要有效果，所以要用doucment的事件，而不用DIV元素的事件  
			                $(".cutview-box").stop();//加上这个之后  
			                var _x = ev.pageX - x;//获得X轴方向移动的值  
			                var _y = ev.pageY - y;//获得Y轴方向移动的值  
			                $(".cutview-box").animate({left:_x+"px",top:_y+"px"},10);  
			            });  
			        });
					$(".cutview").mousedown(function(e){
						e.stopPropagation();
					});
					$(document).mouseup(function(e){ 
			            $(".cutview-box").css("cursor","default");//改变鼠标指针的形状  
			            $(document).unbind("mousemove");
			        });  
				}
				setTimeout(function(){
					$(".cutview-box").show();
					$(".cutview-box").css({left:(tw.UI.width-$(".cutview-box").width())/2, 
						top:(tw.UI.height-$(".cutview-box").height())/2});
				},50);
			}
			var listenEsc = function(event){
				switch(event.keyCode) {
					case 27:
						getCv();
						break;
				}
			}
    		btn.click(function(){
    			if(beforecall){
    				beforecall.call(this);
    			}
    			html2canvas($("body"), {
					onrendered : function(canvas) {
						dataURL = canvas.toDataURL("image/png");
						// $("body").append(canvas);
						$("body").append("<img class='canvas' src='" + dataURL + "'></img>");
						$("body").append("<div class='cutview-temp'><img style='display:none' src='" + dataURL + "'/><div>");
						$(".canvas").imgAreaSelect({
							//aspectRatio : '1:1',
							handles:true,
							onSelectChange : preview
						});
						$("body").append("<div class='canvasBar'><button class='btn'>确定</button></div>");
						$(".canvasBar .btn").click(getCv);
					},
					width : $("body").width(),
					height : $("body").height()
				});
    			
    			/**
    			 * esc退出截图
    			 */
				$(document).keyup(listenEsc);
    		});
    	}
    	return _cv;
    }();
})();