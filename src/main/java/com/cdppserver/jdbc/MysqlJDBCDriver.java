package com.cdppserver.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.cdppserver.utils.MysqlPropertiesUtils;

public class MysqlJDBCDriver {
	private static final String DRIVER = "com.mysql.jdbc.Driver";  
    public static MysqlJDBCDriver instance = null;  
    static Properties pro = new Properties(); 
    static { 
        try {  
            Class.forName(DRIVER);  
        } catch (ClassNotFoundException e) {  
            throw new ExceptionInInitializerError(e);  
        }  
    } 
    
    private MysqlJDBCDriver() {  
    }  
    public static MysqlJDBCDriver getInstance() {  
        synchronized (MysqlJDBCDriver.class) {  
            if (instance == null) {  
                instance = new MysqlJDBCDriver();  
            }  
        }  
        return instance;  
    }  
  
    public Connection getConnection() { 
		String url = MysqlPropertiesUtils.getValue("url");
		String username =MysqlPropertiesUtils.getValue("user");
		String passwd = MysqlPropertiesUtils.getValue("password");
        try {
			return DriverManager.getConnection(url, username, passwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;  
    }  
  
    public  void free(Connection conn, PreparedStatement st, ResultSet rs) {  
        try {  
            if (rs != null) {  
                rs.close();  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (st != null) {  
                    st.close();  
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
