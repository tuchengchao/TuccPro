(function () {
    // Set URL of your WebSocketMain.swf here:
    WEB_SOCKET_SWF_LOCATION = "/plugins/websocket/WebSocketMain.swf";

    // Set this to dump debug message from Flash to console.log:
    WEB_SOCKET_DEBUG = false;

    var tw = window.tw;
    if(!window.tw){
    	tw = window.tw = function(){};
    }
    /**
     * websocket
     */
    tw.ws = function(){
    	this.socket = null;
    	this.isConnected = false;
    	this.uri = UUID.generate().replace(/-/g,'');
    	this.cfg = {addr: window.location.hostname, port: window.location.port, context: 'msWebSocket'};
    	this.params = "{}";
    	/**
    	 * 连接websocket
    	 * @parm params json格式参数
    	 * @parm cfg websocket配置 不传则为默认值
    	 */
    	this.connect = function(params, cfg){
    		if(!cfg){
    			cfg = this.cfg;
    		}
    		this.cfg = cfg;
    		if(!params){
    			params = this.params;
    		}
    		this.params = params;
    		
    		var protocol = window.location.protocol == 'http:' ? "ws://" : "wss://";
    		var url = protocol + cfg.addr + ":" + cfg.port + "/" + cfg.context + "/" + this.uri + "/" + params;
    		this.socket = new WebSocket(url);
    		this.isConnected = true;
    		window.onbeforeunload = function(){
    			tw.ws.close();
    		}
    		this.socket.onmessage = function(event){
        		tw.ws.recive(event);
        	}
    	};
    	/**
    	 * 发送信息
    	 */
    	this.send = function(message){
    		if(this.isConnected){
    			this.socket.send(message);
    		}
    	};
    	/**
    	 * 关闭连接
    	 */
    	this.close = function(){
    		if(this.socket && this.isConnected){
    			this.socket.close();
    			this.socket = null;
			}
    	};
    	/**
    	 * 重新创建websocket连接
    	 */
    	this.recreate = function(){
    		this.uri = UUID.generate().replace(/-/g,'');
    		this.close();
    		this.connect();
    	}
    	/**
    	 * 接收信息
    	 */
    	this.recive = function(event){
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
				case "logon":
					logon();
					setTimeout(function(){
						window.location.href = "logon";
					},2000);
					break;
				default:
					break;
			}
			;
    	};
    	return this;
    }();
}());
