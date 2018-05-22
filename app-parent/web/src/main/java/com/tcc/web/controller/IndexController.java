package com.tcc.web.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
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

	@RequestMapping({ "/"})
	public String index(ModelMap map,@Value("${frontVerification}")Boolean frontVerification) {
		map.addAttribute("words", "hello, it's me.");
		map.addAttribute("today", new Date());
		map.addAttribute("frontVerification", frontVerification);
		return "index";
	}

	@RequestMapping("/downloadZip")
	public String zip() {
		return "downloadZip";
	}

}
