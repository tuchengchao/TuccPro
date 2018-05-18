package com.tcc.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.tcc.dubbo.api.IDubboDemo;

@Service
public class DubboDemo implements IDubboDemo{

	@Override
	public String sayHello(String name) {
		return "Hello," + name ;
	}

}
