package com.cdppserver.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 功能：加载Mysql配置文件
 * @author ASh
 *
 */
public  class MysqlPropertiesUtils {

	private static Map<String,String> map=new HashMap<String,String>();
	private static Logger logger=Logger.getLogger(MysqlPropertiesUtils.class.getName());
	/**
	 * 功能:加载特定位置文件到map
	 * @param path
	 */
	public static void loadMySQLProperties2Map(){
		FileInputStream fis = null;
		try {
			Properties properties=new Properties();
			String res=ProjectPath.getProjectPath()+File.separator+"etc"+File.separator+"cdppserver"+File.separator+"mysql.properties";
			logger.debug("开始加载mysql配置文件:"+res);
			fis=new FileInputStream(res);
			properties.load(fis);
			logger.debug("成功加载mysql配置文件:"+res);
			String url = properties.getProperty("url");
			String user=properties.getProperty("user");
			String password=properties.getProperty("password");
			logger.debug("读取属性值: url:"+url+",user:"+user+",password:"+password);
			map.put("url", url);
			map.put("user", user);
			map.put("password", password);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MySQL配置文件加载异常......");
		}finally{
			if(null!=fis){
				try {
					fis.close();
					logger.debug("关闭文件流.......");
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("关闭文件流失败.......");
				}
			}
		}
	}

	/**
	 * 功能:根据key获取对应value
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		String value = map.get(key);
		return value;
	}
}
