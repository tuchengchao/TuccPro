package com.tcc.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.tcc.web.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

	@Select("selectUserList")
	List<User> selectUserList(Pagination page, String state);
}
