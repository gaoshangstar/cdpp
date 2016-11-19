package com.cdppserver.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cdppserver.datastandard.SqlStatus;

/**
 * 
 * @author ASh
 *
 *
 */
public class SqlStatusManager {

	public static List<SqlStatus> query(SqlStatus ss){
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SqlStatus> sss=new ArrayList<SqlStatus>();	
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT * FROM sqlstatus where 1=1 ");
		try {
		
		if(null!=ss.getStatus() && !"".equals(ss.getStatus())){
			sb.append("AND status='").append(ss.getStatus()).append("'");
		}
		
		connection = MysqlJDBCDriver.getInstance().getConnection();
		pstmt = connection.prepareStatement(sb.toString());
		rs = pstmt.executeQuery();
		while(rs.next()){
			SqlStatus sid=new SqlStatus();
			sid.setId(rs.getString("id"));
			sss.add(sid);
		}
			} catch (SQLException e) {
		e.printStackTrace();
		}finally{
			MysqlJDBCDriver.getInstance().free(connection, pstmt, rs);
		}
		return sss;
	}
	
	
	public boolean add(SqlStatus ss){
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
		    connection = MysqlJDBCDriver.getInstance().getConnection();
			pstmt = connection.prepareStatement("INSERT INTO sqlstatus(id,beginTime,status,sqltaskId) VALUES(?,?,?,?)");
		    pstmt.setString(1, ss.getId());
		    pstmt.setString(2, ss.getBeginTime());
		    pstmt.setString(3, ss.getStatus());
		    pstmt.setString(4, ss.getSqlTaskId());
		    int executeUpdate = pstmt.executeUpdate();
		    if(executeUpdate==1){
		    	return true;
		    }else{
		    	return false;
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MysqlJDBCDriver.getInstance().free(connection, pstmt, null);
		}
		return false;
		
	}
	
	public static boolean update(SqlStatus ss){
		StringBuffer sb=new StringBuffer();
		sb.append("UPDATE sqlstatus SET ");
		if(null!=ss.getStatus() && !"".equals(ss.getStatus())){
			sb.append("status='").append(ss.getStatus()).append("',");
		}
		if(null!=ss.getFinishTime() && !"".equals(ss.getFinishTime())){
			sb.append("finishTime='").append(ss.getFinishTime()).append("',");
		}
		if(null!=ss.getExecuteTime() && !"".equals(ss.getExecuteTime())){
			sb.append("executeTime='").append(ss.getExecuteTime()).append("',");
		}
		if(null!=ss.getPath() && !"".equals(ss.getPath())){
			sb.append("path='").append(ss.getPath()).append("',");
		}
		StringBuffer deleteCharAt = sb.deleteCharAt(sb.length()-1);
		String tmp=deleteCharAt.append(" WHERE id ='").append(ss.getId()).append("'").toString();
		Connection connection = null ;
		PreparedStatement pstmt = null;
		try {
			 connection = MysqlJDBCDriver.getInstance().getConnection();
			 pstmt = connection.prepareStatement(tmp);
			 int executeUpdate = pstmt.executeUpdate();
			 if(executeUpdate==1){
				 return true;
			 }else{
				 return false;
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MysqlJDBCDriver.getInstance().free(connection, pstmt, null);
		}
		
		return false;
		
	}
}
