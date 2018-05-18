package com.tcc.common.utils;

import java.io.FileInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.ho.yaml.Yaml;

public class YmlUtils {
	@SuppressWarnings("rawtypes")
	public static HashMap<String, Map> stores = new HashMap<>();
	public static HashMap<String, String> names = new HashMap<>();
	private static boolean isStore = false;
	/**
	 * 查看或者设置是否加载到内存
	 * @return
	 */
	public static boolean isStore(boolean... isStore){
		if(isStore.length > 0){
			YmlUtils.isStore = isStore[0];
		}
		return YmlUtils.isStore;
	}
	/**
	 * 将yml加载到内存
	 * @param yml 
	 * @param name  
	 */
	@SuppressWarnings("rawtypes")
	public static Map load(String yml, String name){
		try {
            URL url = YmlUtils.class.getClassLoader().getResource(yml);
            if (url != null) {
				Map map = (Map)Yaml.load(new FileInputStream(url.getFile()));
				if(isStore){
					stores.put(name, map);
				}
				names.put(name, yml);
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	/**
	 * 获取yml的内容
	 * @param key
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object getProperty(String key, String name){
		Map map = null;
		if(isStore){
			map = stores.get(name);
		}
		else{
			if(names.containsKey(name)){
				map = load(names.get(name), name);
			}
		}
		if(map != null){
			String[] keys = key.split("\\.");
			try{
				for(int i = 0;i < keys.length; i ++){
					if(i != keys.length - 1){
						map = (Map)map.get(keys[i]);
					}
					else{
						return map.get(keys[i]);
					}
				}
			}
			catch(Exception e){
				return null;
			}
			return null;
		}
		else{
			throw new IllegalArgumentException("没有加载对应name的yml文件");
		}
	}
	/**
	 * 查看有没有某个属性
	 * @param key
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean hasProperty(String key, String name){
		Map map = null;
		if(isStore){
			map = stores.get(name);
		}
		else{
			if(names.containsKey(name)){
				map = load(names.get(name), name);
			}
		}
		if(map != null){
			String[] keys = key.split("\\.");
			try{
				for(int i = 0;i < keys.length; i ++){
					if(i != keys.length - 1){
						map = (Map)map.get(keys[i]);
					}
					else{
						return map.containsKey(keys[i]);
					}
				}
			}
			catch(Exception e){
				return false;
			}
			return false;
		}
		else{
			throw new IllegalArgumentException("没有加载对应name的yml文件");
		}
	}
	/**
	 * 获取 application。yml的内容
	 * @param key
	 * @return
	 */
	public static Object getProperty(String key){
		loadDefault();
		return getProperty(key, "application");
	}
	
	public static boolean hasProperty(String key){
		loadDefault();
		return hasProperty(key, "application");
	}
	/**
	 * 加载appliaction.yml
	 * @param reload
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map loadDefault(boolean... reload){
		reload = reload.length == 0 ? new boolean[]{false} : reload;
		Map map = stores.get("application");
		if(map == null || reload[0]){
			map = load("application.yml", "application");
		}
		return map;
	}
	public static void main(String[] args) {
		System.out.println(getProperty("test.b.c"));
	}
}
