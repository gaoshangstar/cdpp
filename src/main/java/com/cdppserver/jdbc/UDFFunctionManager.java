package com.cdppserver.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cdppserver.datastandard.UDF;

public class UDFFunctionManager {
	public List<String> getUDF(String projectId){
		List<String> udfs=new ArrayList<String>();
		Connection connection = MysqlJDBCDriver.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="SELECT u.functionName AS functionname ,u.className AS classname,s.path AS path  FROM userdefinedfunction u LEFT JOIN source s ON u.sourceId=s.id AND u.projectId='"+projectId+"'";
		try {
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String fn=rs.getString("functionname");
				String cn=rs.getString("classname");
				String path=rs.getString("path");
				udfs.add(fn+","+cn+","+path);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MysqlJDBCDriver.getInstance().free(connection, pstmt, rs);
		}
		return udfs;
		
	}

}
