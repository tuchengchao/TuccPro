package com.tcc.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.plugins.Page;
import com.tcc.web.entity.User;
import com.tcc.web.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/main")
	public Object main(ModelMap map, Integer pageNo, Integer pageSize) {
		Page<User> page = new Page<User>(1, 10);
		page = userService.selectUserPage(page, "NORMAL");
		map.put("pageData", page);
		return "user/main";
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(User user) {
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> get(@PathVariable("id") String id) {
		User user = userService.selectById(id);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(user);
	}

	@PostMapping()
	public ResponseEntity<User> add(User user) {
		try {
			if (user != null) {
				userService.insert(user);
				return ResponseEntity.status(HttpStatus.CREATED).build();
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
