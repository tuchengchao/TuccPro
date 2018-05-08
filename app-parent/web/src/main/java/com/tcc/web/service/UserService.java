package com.tcc.web.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tcc.web.entity.User;
import com.tcc.web.mapper.UserMapper;

@Service
@EnableTransactionManagement(proxyTargetClass = true)
public class UserService extends ServiceImpl<UserMapper, User>{
	public Page<User> selectUserPage(Page<User> page, String state) {
        page.setRecords(baseMapper.selectUserList(page,"1"));
        return page;
    }
}
