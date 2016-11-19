package com.cdppserver.utils;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.cdppserver.datastandard.ParseFunction;
import com.cdppserver.datastandard.Project;
import com.cdppserver.datastandard.SqlStatus;
import com.cdppserver.datastandard.SqlTask;
/**
 * 功能:sql处理逻辑
 * @author ASh
 *
 *
 */
public class SqlManager {
	
	public static LinkedList<ParseFunction> parse(String sql){
		LinkedList<ParseFunction> pfs=new LinkedList<ParseFunction>();
		String[] split = sql.split(";");
		List<String> tableName = null;
		for(int i=0;i<split.length;i++){
			ParseFunction pf=new ParseFunction();
			pf.setSql(split[i]);
			pf.setFunction("");//提取function函数
			pf.setTableName(tableName);
			pf.setType("1");//1代表select0代表其他
			//添加队列元素
			//pool移除队列元素
			pfs.offer(pf);
		}
		return pfs;
	}
	/**
	 * 功能:执行sql任务
	 * @param info
	 * @param projectName
	 * @param st
	 * @param ss 
	 * @return
	 * @throws SQLException 
	 */
	public static boolean execute(LinkedList<ParseFunction> info, Project project, SqlTask st, SqlStatus ss) throws SQLException{
		JDBCClient jc=new JDBCClient();
		return jc.executeTask(info, project, st,ss);
		
	}
}
