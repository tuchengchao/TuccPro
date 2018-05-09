package com.tcc.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcc.web.entity.Dictionary;
import com.tcc.web.service.DictionaryService;

@Controller
@RequestMapping("/dictionary")
public class DictionaryController {
	@Resource
	DictionaryService dictionaryService;

	@RequestMapping(value = "/main")
	public String main(ModelMap map){
		return "dictionary/main";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Dictionary get(@PathVariable("id")String id){
		return dictionaryService.selectById(id);
	}
}
