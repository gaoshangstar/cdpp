package com.cdppserver.utils;

import com.alibaba.fastjson.JSONObject;

public class JSONUtils {
	/**
	 * object to json
	 * @param o
	 * @return
	 */
	public static String toJSON(Object o){
		return JSONObject.toJSONString(o);
	}

	/**
	 * json to object
	 * @param json
	 * @return
	 */
	public static Object toObjectArray(String json,Class<?> clazz){
		return JSONObject.parseArray(json, clazz); 
	}
}
