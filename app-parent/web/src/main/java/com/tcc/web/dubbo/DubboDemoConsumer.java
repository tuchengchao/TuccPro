package com.tcc.web.dubbo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tcc.dubbo.api.IDubboDemo;

@Component
public class DubboDemoConsumer implements CommandLineRunner {
	@Reference
	IDubboDemo dubboDemo;

	@Override
	public void run(String... arg0) throws Exception {
		if(dubboDemo == null){
			System.out.println("注入失败");
		}
		else {
			System.out.println(dubboDemo.sayHello("tucc"));
		}
	}
	
}
