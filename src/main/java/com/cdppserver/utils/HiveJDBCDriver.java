package com.cdppserver.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 /**
 * Hive JDBC 连接 相关操作
 * @author ASh
 * */

public class HiveJDBCDriver {
	private static final String DRIVERNAME="org.apache.hive.jdbc.HiveDriver"; // org.apache.hive.jdbc.HiveDriver
    private static HiveJDBCDriver instance = new HiveJDBCDriver();  
    static {  
        try {  
            Class.forName(DRIVERNAME);  
        } catch (ClassNotFoundException e) {  
            throw new ExceptionInInitializerError(e);  
        }  
    }  
    private HiveJDBCDriver() {  
    }  
  
    public static HiveJDBCDriver getInstance() {  
        synchronized (HiveJDBCDriver.class) {  
            if (instance == null) {  
                instance = new HiveJDBCDriver();  
            }  
        }  
        return instance;  
    }  
  
    /**
     * 获取JDBC连接
     * 
     * @return  返回connection连接
     */
    public Connection getConnection() throws SQLException {  
     	String URL = HivePropertiesUtils.getValue("url");
		String USERNAME = HivePropertiesUtils.getValue("user");
		String PASSWD = HivePropertiesUtils.getValue("password");
   
    	Connection conn =  DriverManager.getConnection(URL, USERNAME, PASSWD);
        return conn; 
    }  
 
  /**
   *  释放连接
   * @param conn
   * @param rs
   * @param createStatement
   */
    public void free(Connection conn, ResultSet rs, Statement createStatement) {  
        try {  
            if (rs != null) {  
                rs.close();  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (createStatement != null) {  
                	createStatement.close();  
                }  
            } catch (SQLException e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    if (conn != null) {  
                        conn.close();  
                    }  
                } catch (SQLException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }
    
}
