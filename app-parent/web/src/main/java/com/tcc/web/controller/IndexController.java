package com.tcc.web.controller;

import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
	
	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "Greetings from Spring Boot!";
	}
	@RequestMapping("/json")
	@ResponseBody
	public Object json(){
		HashMap<String, String> map = new HashMap<>();
		map.put("1", "a");
		map.put("2", "b");
		return map;
	}
	@RequestMapping({"/","/index"})
	public String index(ModelMap map){
		map.addAttribute("words", "hello, it's me.");
		map.addAttribute("today", new Date());
		return "index";
	}
	
	@RequestMapping({"/login"})
	public String login(ModelMap map){
		return "login";
	}
	
	@RequestMapping("downloadZip")
	public String zip(){
		return "downloadZip";
	}
}
