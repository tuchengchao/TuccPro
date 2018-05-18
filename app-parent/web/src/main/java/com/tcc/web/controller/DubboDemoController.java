package com.tcc.web.controller;

import org.springframework.data.annotation.Reference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.dubbo.api.IDubboDemo;

@RestController
public class DubboDemoController {
	
	@Reference
	IDubboDemo dubboDemo;
	
	@RequestMapping("/dubbo/{name}")
	public String sayHello(@PathVariable("name")String name){
		return dubboDemo.sayHello(name);
	}
}
