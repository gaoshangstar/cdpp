package com.cdppserver.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.cdppserver.datastandard.SqlStatus;
import com.cdppserver.datastandard.SqlTask;
import com.cdppserver.log.TaskLog;
/**
 * 
 * @author ASh
 *
 *
 */
public class Store {
	
	private static Logger logger=Logger.getLogger(Store.class.getName());
	/**
	 * 功能：写入文件到本地磁盘
	 * @param ss 
	 * @param lists
	 */
	public static void writer2File(String content ,SqlTask st, SqlStatus ss) {
		 BufferedWriter writer = null;
		try{
			String path=ConfigProperties.getValue("bashpath")+st.getId()+System.currentTimeMillis()+ConfigProperties.getValue("suffix");
			File file=new File(path);
			if(file.exists()){
			   file.delete();
			   logger.warn("文件:"+path+",存在,将被删除......");
			}
		     writer = new BufferedWriter(new FileWriter(file));
		     writer.write(content);
		     //设置文件存储的路径
		     ss.setPath(path);
		     TaskLog.addLog(st.getId(),"id=["+ss.getId()+"]的任务成功写入本地文件......");
		}catch(Exception e){
			TaskLog.addLog(st.getId(),"id=["+ss.getId()+"]的任务写入本地文件失败......");
			e.printStackTrace();
		     }finally{
		    	if(null!=writer){
		    		try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
		    	}
		     }
	}
}
