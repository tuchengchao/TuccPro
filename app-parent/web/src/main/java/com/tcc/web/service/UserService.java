package com.tcc.web.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tcc.web.entity.User;
import com.tcc.web.mapper.UserMapper;

@Service
@EnableTransactionManagement(proxyTargetClass = true)
public class UserService extends ServiceImpl<UserMapper, User>{
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	public User selectByUsername(String username){
		return baseMapper.selectByUsername(username);
	}
}
