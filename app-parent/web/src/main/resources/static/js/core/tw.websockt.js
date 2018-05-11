(function () {
    // Set URL of your WebSocketMain.swf here:
    WEB_SOCKET_SWF_LOCATION = "/plugins/websocket/WebSocketMain.swf";

    // Set this to dump debug message from Flash to console.log:
    WEB_SOCKET_DEBUG = false;

    // 默认服务配置
    // 注册并初始化消息对象
    var tw = window.tw;
    if(!window.tw){
    	tw = window.tw;
    }
    tw.ws = function(){
    	this.socket = null;
    	this.isConnected = false;
    	this.uri = UUID.generate();
    	this.cfg = {addr: window.location.hostname, port: window.location.port, context: 'msws'};
    	this.connect = function(params, cfg, callback){
    		if(!cfg){
    			cfg = this.cfg;
    		}
    		var protocol = window.location.protocol == 'https:' ? "ws://" : "wss://";
    		var url = protocol + cfg.addr + ":" + cfg.port + "/" + cfg.context + "/" + this.uri + "/" + params;
    		this.socket = new WebSocket();
    		if(callback){
    			callback.call(this);
    		}
    		window.onbeforeunload = function(){
    			tw.ws.close();
    		}
    	}
    	this.send = function(message){
    		if(this.isConnected){
    			this.socket.send(message);
    		}
    	}
    	this.close = function(){
    		if(s.socket && s.isConnected){
				s.socket.close();
			}
    	}
    	socket.onmessage = function(event){
    		this.recive(event);
    	}
    	
    }
    // 拓展事件

    s.prototype.connect = function (server, callback) {
        var t = this;
        t.socket = new WebSocket(server);
        t.isconnect = false;

        t.socket.onopen = function (message) {
            t.isconnect = true;
            if (callback) {
                callback.call(t);
            }
            t.onConnect(message);
        };

        t.socket.onclose = function () {
            t.isconnect = false;
            t.onClose();
        };

        t.socket.onerror = function (event) {
            t.onError(event);
        };

        t.socket.onmessage = function (message) {
        };
    };

    /**
     * websocket注册
     */
    s.prototype.register = function (params, config, callback) {
        // 检查是否存在自定义配置信息，如果存在自定义配置，则使用自定义配置信息
        if (config) {
            this.cfg = cfg = config;
        }
        var _ctx = $.trim(cfg.context);
        if (_ctx && _ctx != '/') {
            if (!(_ctx.indexOf("/") == 0)) {
                _ctx = "/" + _ctx;
            }
            if (_ctx.lastIndexOf("/") != (_ctx.length - 1)) {
                _ctx = _ctx + "/";
            }
        } else {
            _ctx = "/";
        }
        var SERVER_MESSAGE = cfg.addr + ":" + cfg.port + _ctx + '/' + uid + '/';
        if (window.location.protocol == 'http:') {
            this.connect('ws://' + SERVER_MESSAGE +params, callback);
        } else {
            this.connect('wss://' + SERVER_MESSAGE + params, callback);
        }
    };

    s.prototype.sendMessage = function (message) {
        var body = '';
        if (message instanceof String) {
            body = message;
        } else if (message instanceof Object) {
            body = JSON.stringify(message);
        }
        var result = false;
        if (this.isconnect) {
            result = this.socket.send(message);
        	
        }
        // websocket event doesn't feedback result, but the flash socket will tell you the truth
        if (result == undefined) {
            result = true;
        }
        
        return result;
    };
})();
