package com.tcc.web.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tcc.web.entity.Dictionary;

@Mapper
@CacheConfig(cacheNames = "dictionary")
public interface DictionaryMapper extends BaseMapper<Dictionary> {
	
	@Override
	@Cacheable(key = "#p0")
	Dictionary selectById(Serializable id);
	
	@Override
	@CachePut(key = "#p0.id")
	Integer updateById(Dictionary dictionary);

	@Override
	@CacheEvict(key = "#p0",allEntries = true)
	Integer deleteById(Serializable id);
	
}
