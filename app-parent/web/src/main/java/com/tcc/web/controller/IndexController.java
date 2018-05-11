package com.tcc.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcc.common.utils.VerificationUtil;

@Controller
public class IndexController {

	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "Greetings from Spring Boot!";
	}

	@RequestMapping("/json")
	@ResponseBody
	public Object json() {
		HashMap<String, String> map = new HashMap<>();
		map.put("1", "a");
		map.put("2", "b");
		return map;
	}

	@RequestMapping({ "/", "/index" })
	public String index(ModelMap map) {
		map.addAttribute("words", "hello, it's me.");
		map.addAttribute("today", new Date());
		return "index";
	}

	@RequestMapping({ "/login" })
	public String login(ModelMap map) {
		return "login";
	}

	@RequestMapping({ "/logon" })
	public String logon() {
		return "manager";
	}

	@RequestMapping("/downloadZip")
	public String zip() {
		return "downloadZip";
	}

	@RequestMapping("/register")
	public String register() {
		return "register";
	}

	@RequestMapping({ "/verification" })
	public void verification(HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
		com.tcc.common.utils.VerificationUtil.Result result = VerificationUtil.create();
		ImageIO.write(result.getImage(), "JPEG", response.getOutputStream());
	}
}
