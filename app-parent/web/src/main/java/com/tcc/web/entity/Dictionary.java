package com.tcc.web.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("sys_dictionary")
public class Dictionary extends Model<Dictionary>{

	private static final long serialVersionUID = 445372944756566642L;
	
	@TableId(value="id", type=IdType.AUTO)
	private Integer id;
	private String name;
	private String value;
	private Integer sort;
	private Integer pid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
