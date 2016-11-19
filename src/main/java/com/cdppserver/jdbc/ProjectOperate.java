package com.cdppserver.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cdppserver.datastandard.Project;

/**
 * 
 * @author ASh
 *
 *
 */
public class ProjectOperate {
	
	/**
	 * 功能：查询项目相关信息
	 * @param project
	 * @return
	 * @throws SQLException 
	 */
	public Project query(Project project){
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet rs = null;
		try {
			 connection = MysqlJDBCDriver.getInstance().getConnection();
			 prepareStatement = connection.prepareStatement("select id, enname from project where id='"+project.getId()+"'");
			 rs = prepareStatement.executeQuery();
			while(rs.next()){
				project.setId(rs.getString("id"));
				project.setEnname(rs.getString("enname"));
			}
			 return project;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MysqlJDBCDriver.getInstance().free(connection, prepareStatement, rs);
		}
		return project;
		
	}
}
