package com.cdppserver.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 功能:加载属性配置文件
 * @author ASh
 *
 */
public class ConfigProperties {

	private static Logger logger=Logger.getLogger(ConfigProperties.class.getName());
	private static Map<String,String> map=new HashMap<String,String>();
	/**
	 * 功能:加载配置文件到Map
	 */
	public static void loadConfigProperties2Map(){
		Properties pro=new Properties();
		FileInputStream fis = null;
		String res=ProjectPath.getProjectPath()+File.separator+"etc"+File.separator+"cdppserver"+File.separator+"config.properties";
		logger.debug("开始加载配置文件:"+res);
		try {
			fis=new FileInputStream(res);
			pro.load(fis);
			logger.debug("成功加载配置文件:"+res);
			String sourceType = pro.getProperty("sourceType");
			String oneTimeTasksleeptime=pro.getProperty("oneTimeTasksleeptime");
			String scheduleTasksleeptime=pro.getProperty("scheduleTasksleeptime");
			String oneTimeRunningTaskCount=pro.getProperty("taskCount");
			String hadoop_home=pro.getProperty("hadoop_home");
			String dfs_default_home=pro.getProperty("dfs_default_home");
			String bashpath=pro.getProperty("bashpath");
			String suffix=pro.getProperty("suffix");
			
			
			map.put("sourceType", sourceType);
			map.put("oneTimeTasksleeptime", oneTimeTasksleeptime);
			map.put("scheduleTasksleeptime", scheduleTasksleeptime);
			map.put("taskCount", oneTimeRunningTaskCount);
			map.put("hadoop_home", hadoop_home);
			map.put("dfs_default_home", dfs_default_home);
			map.put("bashpath", bashpath);
			map.put("suffix", suffix);
			logger.debug(map.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null!=fis){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("文件流:"+res+",关闭失败......");
				}
			}
		}
	}

	
	/**
	 * 功能:根据key获得value
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		String value = map.get(key);
		return value;
	}
}
