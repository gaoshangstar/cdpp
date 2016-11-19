package com.cdppserver.tablemanager;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.cdppserver.datastandard.Project;
import com.cdppserver.datastandard.ResponseResult;
import com.cdppserver.datastandard.Tabledesc;
import com.cdppserver.jdbc.ProjectOperate;
import com.cdppserver.utils.CreateTable;
import com.cdppserver.utils.JDBCClient;
import com.cdppserver.utils.UUIDUtils;

/**
 * 
 * @author ASh
 * 
 * 功能:解析web端发送过来的表处理请求，处理表
 *
 */
public class TableManger {
	private static Logger logger =Logger.getLogger(TableManger.class.getName());
	/**
	 * 功能:创建表
	 * @param tabledesc
	 * @param rr
	 * @throws SQLException 
	 */
	public static void createTable(Tabledesc tabledesc, List<ResponseResult> rrs) throws SQLException {
		//根据projectId或者project英文名称
		ProjectOperate pro=new ProjectOperate();
		Project project=new Project();
		project.setId(tabledesc.getProjectId());
		pro.query(project);
		//拼装sql为最终的建表sql
		List<String> createTableSql=CreateTable.generate2SQL(tabledesc);
		//调用建表JDBCSQL创建表
		JDBCClient jc=new JDBCClient();
		boolean createTable = jc.createTable(createTableSql, "use "+project.getEnname());
		ResponseResult rr=new ResponseResult();
		rr.setId(UUIDUtils.uuid());
		if(createTable){
			rr.setInfo("创建表:"+tabledesc.getName()+"成功......");
			rr.setStatus(Boolean.TRUE);
		}else{
			rr.setInfo("创建表:"+tabledesc.getName()+"失败......");
			rr.setStatus(Boolean.FALSE);	
		}
		rrs.add(rr);
	}
	/**
	 * 功能:删除表
	 * @param tabledesc
	 * @param rr
	 * @throws SQLException 
	 */
	public static void deleteTable(Tabledesc tabledesc, List<ResponseResult> rrs) throws SQLException {
		//根据projectId或者project英文名称
		ProjectOperate pro=new ProjectOperate();
		Project project=new Project();
		project.setId(tabledesc.getProjectId());
		pro.query(project);
		ResponseResult rr=new ResponseResult();
		rr.setId(UUIDUtils.uuid());
		JDBCClient jc=new JDBCClient();
		boolean delete = jc.delete(tabledesc.getName(),project.getEnname());
		if(delete){
			rr.setInfo("删除表:"+tabledesc.getName()+"成功......");
			rr.setStatus(Boolean.TRUE);
		}else{
			rr.setInfo("删除表:"+tabledesc.getName()+"失败......");
			rr.setStatus(Boolean.FALSE);
		}
			rrs.add(rr);
	}
	

}
