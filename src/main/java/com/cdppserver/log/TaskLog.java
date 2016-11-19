package com.cdppserver.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.cdppserver.jdbc.MysqlJDBCDriver;


public class TaskLog {
	
	public static void addLog(String id,String info){
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			 connection = MysqlJDBCDriver.getInstance().getConnection();
			 prepareStatement = connection.prepareStatement("");
			 boolean execute = prepareStatement.execute("INSERT INTO loginfo(id,info) VALUES('"+id+"','"+info+"')");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MysqlJDBCDriver.getInstance().free(connection, prepareStatement, null);
		}
	}

}
