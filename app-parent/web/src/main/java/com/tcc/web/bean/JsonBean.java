package com.tcc.web.bean;

import com.google.gson.GsonBuilder;

public class JsonBean {
	public String toJson() {
		return new GsonBuilder().serializeNulls().create().toJson(this);
	}
}
