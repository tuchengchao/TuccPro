package com.tcc.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tcc.web.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
	@Select("select * from sys_user where username = #{username}")
	User selectByUsername(String username);
}
