package com.cdppserver.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.cdppserver.datastandard.SqlTask;

/**
 * 
 * @author ASh
 *
 *
 */
public class SqlTaskOperate {

	
	public List<SqlTask> query(SqlTask sqlTask) {
		List<SqlTask> sqlTasks=new ArrayList<SqlTask>();
		Connection connection = MysqlJDBCDriver.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT * FROM sqltask WHERE 1=1 ");
		if(null!=sqlTask.getId() && !"".equals(sqlTask.getId())){
			sb.append("AND id='").append(sqlTask.getId()).append("'");
		}
		if(null!=sqlTask.getStatus() && !"".equals(sqlTask.getStatus())){
			sb.append("AND status='").append(sqlTask.getStatus()).append("'");
		}
		if(null!=sqlTask.getFlag() && !"".equals(sqlTask.getFlag())){
			sb.append("AND flag='").append(sqlTask.getFlag()).append("'");
		}
		if(null!=sqlTask.getIsdel() && !"".equals(sqlTask.getIsdel())){
			sb.append("AND isdel='").append(sqlTask.getIsdel()).append("'");
		}
		try {
			 pstmt = connection.prepareStatement(sb.toString());
			 rs = pstmt.executeQuery();
			while(rs.next()){
				SqlTask st=new SqlTask();
				st.setId(rs.getString("id"));
				st.setSqltask(rs.getString("sqltask"));
				st.setTimerexpression(rs.getString("timerexpression")==null ? "" :rs.getString("timerexpression"));
				st.setTargettableexpression(rs.getString("targettableexpression")==null ? "" :rs.getString("targettableexpression"));
				st.setFlag(rs.getString("flag"));
				st.setOperate(rs.getString("operate"));
				st.setStatus(rs.getString("status"));
				st.setIsdel(rs.getString("isdel"));
				st.setCreateuser(rs.getString("createuser"));
				st.setCreatetime(rs.getString("createtime"));
				st.setUpdateuser(rs.getString("updateuser"));
				st.setUpdatetime(rs.getString("updatetime"));
				st.setProjectid(rs.getString("projectid"));
				st.setRemark(rs.getString("remark")==null ? "" :rs.getString("remark"));
				st.setTitle(rs.getString("title")==null ? "" :rs.getString("title"));
				sqlTasks.add(st);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MysqlJDBCDriver.getInstance().free(connection, pstmt, rs);
		}
		return sqlTasks;
	}
	
	public boolean update(SqlTask sqlTask){
		Connection connection = null;
		PreparedStatement pstmt = null;
		StringBuffer sb=new StringBuffer();
		sb.append("UPDATE sqltask set ");
		if(null!=sqlTask.getStatus() && !"".equals(sqlTask.getStatus())){
			sb.append("status ='").append(sqlTask.getStatus()).append("' ");
		}
		sb.append(" WHERE id='").append(sqlTask.getId()).append("'");
		try {
			 connection = MysqlJDBCDriver.getInstance().getConnection();
			 pstmt = connection.prepareStatement(sb.toString());
			 int executeUpdate = pstmt.executeUpdate();
			 if(executeUpdate==1){
				 return true;
			 }else{
				 return  false;
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MysqlJDBCDriver.getInstance().free(connection, pstmt, null);
		}
		return false;
	}
}
