package com.cdppserver.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 加载Hive配置文件
 * @author ASh
 *
 */
public class HivePropertiesUtils {

	private static Map<String, String> map=new HashMap<String, String>();
	private static Logger logger=Logger.getLogger(HivePropertiesUtils.class.getName());
	/**
	 * 功能:加载hive配置文件到Map
	 * @param path
	 */
	public static  void loadHiveProperties2Map(){
		FileInputStream fis = null;
		Properties propertis=new Properties();
		String path=ProjectPath.getProjectPath()+File.separator+"etc"+File.separator+"cdppserver"+File.separator+"hive.properties";

       try {
			fis=new FileInputStream(path);
			propertis.load(fis);
		    logger.debug("成功加载配置文件...");
			String url = propertis.getProperty("url");
			String user=propertis.getProperty("user");
			String password=propertis.getProperty("password");
			map.put("url", url);
			map.put("user", user);
			map.put("password", password);
			logger.debug("读取配置文件:"+map.toString());
		} catch (Exception e) {
			e.printStackTrace();
		    logger.error("加载配置文件出现异常...");
		}finally{
			if(null!=fis){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("关闭文件流出现异常...");
				}
			}
		}
	}
	
	
	/**
	 * 功能:根据Map key值获取Map value
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		
		String value=map.get(key);
		return value;
	}
}
