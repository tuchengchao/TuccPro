(function () {
    // Set URL of your WebSocketMain.swf here:
    WEB_SOCKET_SWF_LOCATION = "/plugins/websocket/WebSocketMain.swf";

    // Set _ws to dump debug message from Flash to console.log:
    WEB_SOCKET_DEBUG = false;

    var tw = window.tw;
    if(!window.tw){
    	tw = window.tw = function(){};
    }
    /**
     * websocket
     */
    tw.ws = function(){
    	var _ws = function(){};
    	_ws.socket = null;
    	_ws.isConnected = false;
    	_ws.uri = UUID.generate().replace(/-/g,'');
    	_ws.cfg = {addr: window.location.hostname, port: window.location.port, context: 'msWebSocket'};
    	_ws.params = "{}";
    	/**
    	 * 连接websocket
    	 * @parm params json格式参数
    	 * @parm cfg websocket配置 不传则为默认值
    	 */
    	_ws.connect = function(params, cfg){
    		if(!_ws.isConnected){
	    		if(!cfg){
	    			cfg = _ws.cfg;
	    		}
	    		_ws.cfg = cfg;
	    		if(!params){
	    			params = _ws.params;
	    		}
	    		_ws.params = params;
	    		
	    		var protocol = window.location.protocol == 'http:' ? "ws://" : "wss://";
	    		var url = protocol + cfg.addr + ":" + cfg.port + "/" + cfg.context + "/" + _ws.uri + "/" + params;
	    		_ws.socket = new WebSocket(url);
	    		_ws.isConnected = true;
	    		window.onbeforeunload = function(){
	    			tw.ws.close();
	    		}
	    		_ws.socket.onmessage = function(event){
	        		tw.ws.recive(event);
	        	}
    		}
    	};
    	/**
    	 * 发送信息
    	 */
    	_ws.send = function(message){
    		if(_ws.isConnected){
    			_ws.socket.send(message);
    		}
    	};
    	/**
    	 * 关闭连接
    	 */
    	_ws.close = function(){
    		if(_ws.socket && _ws.isConnected){
    			_ws.socket.close();
    			_ws.socket = null;
			}
    	};
    	/**
    	 * 重新创建websocket连接
    	 */
    	_ws.recreate = function(){
    		_ws.uri = UUID.generate().replace(/-/g,'');
    		_ws.close();
    		_ws.connect();
    	}
    	/**
    	 * 接收信息
    	 */
    	_ws.recive = function(event){
    		var data = event.data;
    		data= JSON.parse(data);
    		switch(data.category){
	    		case "verification":
	    			window.verification = data.msg;
	    			break;
	    		default:
	    			break;
    		};
			switch (data.operation) {
				default:
	    			break;
			}
			;
    	};
    	return _ws;
    }();
}());
