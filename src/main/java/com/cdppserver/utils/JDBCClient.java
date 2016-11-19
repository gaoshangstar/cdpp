package com.cdppserver.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cdppserver.datastandard.ParseFunction;
import com.cdppserver.datastandard.Project;
import com.cdppserver.datastandard.SqlStatus;
import com.cdppserver.datastandard.SqlTask;
import com.cdppserver.log.TaskLog;

/**
 *Hive JDBC操作接口
 *@author Zhang Wei
 * 
 * */

public class JDBCClient {

	private static Logger log  = Logger.getLogger(JDBCClient.class.getName());
 
    /**
     * 功能:创建表   
     * @param tableSql
     * @param databaseSql
     */
    public boolean createTable(List<String> tableSqls,String databaseSql) throws SQLException{
    	HiveJDBCDriver instance = HiveJDBCDriver.getInstance();
    	Connection connection = instance.getConnection();
    	Statement createStatement = connection.createStatement();
    	boolean create=false;
    	try {
    		createStatement.execute(databaseSql);
    		if(tableSqls.size()==1){
    			createStatement.execute(tableSqls.get(0));
    		}else{
    			createStatement.execute(tableSqls.get(0));
        		createStatement.execute(tableSqls.get(1));
    		}
        		
    		log.info(String.format("创建表成功!!!建表语句为%s，所属数据库:%s", tableSqls.toArray(),databaseSql));
			create=true;
		} catch (SQLException e) {
		    log.error(String.format("创建表失败!!!建表语句为%s，所属数据库:%s", tableSqls.toArray(),databaseSql));
			e.printStackTrace();
		}finally{
			instance.free(connection, null, createStatement);
		}
		return create;
    }
    
    /**
     * 建数据库：创建数据库
     * @param databaseName 数据库名称
     * @throws SQLException 
     * */
    public boolean createDataBase(String databaseName) throws SQLException{
    	HiveJDBCDriver instance = HiveJDBCDriver.getInstance();
    	Connection connection = instance.getConnection();
    	Statement createStatement = connection.createStatement();
    	try {
    		createStatement.execute("create database "+databaseName);
		    log.info(String.format("创建%s数据库成功...", databaseName));
		    return true;
		} catch (SQLException e) {
			log.error(String.format("创建%s数据库失败!!!", databaseName));
			e.printStackTrace();
			return false;
		}finally{
			instance.free(connection, null, createStatement);
		}
    }
    /**
     * 功能:sql处理器
     * @param info
     * @param projectName
     * @param st
     * @param ss 
     * @return
     * @throws SQLException
     */
    public boolean executeTask(LinkedList<ParseFunction> info, Project project, SqlTask st, SqlStatus ss) throws SQLException{
    	Connection connection = null;
    	Statement createStatement = null;
    	ResultSet rs = null;
    	try {
			HiveJDBCDriver instance = HiveJDBCDriver.getInstance();
			 connection = instance.getConnection();
			 createStatement = connection.createStatement();
			 createStatement.execute("use "+project.getEnname());
		    	//加载每个项目下面的UDF函数
		    	log.info("开始加载"+project.getEnname()+",下面的UDF函数......");
		    	LoadUDFFunction.loadFunction(project.getId(),createStatement);
		    	log.info(project.getEnname()+",下面的UDF函数加载完毕......");
			
				for(int i=0;i<info.size()-1;i++){
				createStatement.execute(info.get(i).getSql());
				TaskLog.addLog(ss.getId(),"id=["+ss.getId()+"]的任务sql"+info.get(i).getSql()+"被成功执行");
			}
			StringBuffer sb=new StringBuffer();
			ParseFunction parseFunction = info.get(info.size()-1);
			String sql = parseFunction.getSql();
				if(st.getOperate().equals("1")){
				 rs = createStatement.executeQuery(sql);
				ResultSetMetaData rsmd = rs.getMetaData();
				int count=rsmd.getColumnCount();
				String[] name=new String[count];
				
				for(int i=0;i<count;i++){
				name[i]=rsmd.getColumnName(i+1);
				sb.append(name[i]).append(" ");
				if(i<count-1){
					sb.append(",");
					}
				}
				sb.append("\n");
				while(rs.next()){
				for(int i=0;i<name.length;i++){
					sb.append(rs.getString(name[i])).append(" ");
					if(i<count-1){
						sb.append(",");
					}
				}
				sb.append("\n");
				}
				TaskLog.addLog(ss.getId(),"id=["+ss.getId()+"]的下载任务成功执行完毕");
				Store.writer2File(sb.toString(),st,ss);
			}else if(st.getOperate().equals("0")){
				createStatement.execute(sql);
				TaskLog.addLog(ss.getId(),"id=["+ss.getId()+"]的etl任务成功执行完毕");
			}else if(st.getOperate().equals("2")){
				createStatement.execute(sql);
				TaskLog.addLog(ss.getId(),"id=["+ss.getId()+"]的导出任务成功执行完毕");
			}
		} catch (Exception e) {
			TaskLog.addLog(ss.getId(),"id=["+ss.getId()+"]的JDBC连接出现异常"+e.getMessage());
			e.printStackTrace();
			return false;
		}finally{
			HiveJDBCDriver.getInstance().free(connection, rs, createStatement);
		}
    	return true;
    	
    }
    /**
     * 功能:删除某个项目下的表
     * @param tableName
     * @param projectName
     * @return
     * @throws SQLException 
     */
	public boolean delete(String tableName, String projectName) throws SQLException {
    	HiveJDBCDriver instance = HiveJDBCDriver.getInstance();
    	Connection connection = instance.getConnection();
    	Statement createStatement = connection.createStatement();
    	boolean delete=false;
    	try {
    		createStatement.execute("use "+projectName);
    		createStatement.execute("drop table if exists "+tableName);
    		createStatement.execute("drop table if exists "+tableName+"_tmp");
			log.info(String.format("删除表成功!!!删除表为%s，所属数据库:%s", tableName,projectName));
			delete=true;
		} catch (SQLException e) {
		    log.error(String.format("删除表失败!!!删除表为%s，所属数据库:%s", tableName,projectName));
			e.printStackTrace();
		}finally{
			instance.free(connection, null, createStatement);
		}
		return delete;
    
		
	}
}
