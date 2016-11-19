package com.cdppserver.tablemanager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cdppserver.datastandard.Project;
import com.cdppserver.datastandard.ResponseResult;
import com.cdppserver.datastandard.TtableDesc;
import com.cdppserver.jdbc.ProjectOperate;
import com.cdppserver.utils.CreateTable;
import com.cdppserver.utils.JDBCClient;
import com.cdppserver.utils.UUIDUtils;

/**
 * 
 * @author ASh
 *
 *
 */
public class TargetTableManager {
	/**
	 * 功能:创建表操作
	 * @param tabledesc
	 * @param rrs
	 * @throws SQLException 
	 */
	public static void createTable(TtableDesc tabledesc, List<ResponseResult> rrs) throws SQLException{
		//根据projectId或者project英文名称
		ProjectOperate pro=new ProjectOperate();
		Project project=new Project();
		project.setId(tabledesc.getProjectId());
		pro.query(project);
		String sql = CreateTable.generatet2SQL(tabledesc);
		//调用建表JDBCSQL创建表
		JDBCClient jc=new JDBCClient();
		List<String> sqls=new ArrayList<String>();
		sqls.add(sql);
		boolean createTable = jc.createTable(sqls, "use "+project.getEnname());
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
	 * @param ttableDesc
	 * @param rrs
	 * @throws SQLException
	 */
	public static void deleteTable(TtableDesc ttableDesc,List<ResponseResult> rrs) throws SQLException {
		//根据projectId或者project英文名称
		ProjectOperate pro=new ProjectOperate();
		Project project=new Project();
		project.setId(ttableDesc.getProjectId());
		pro.query(project);
		JDBCClient jc=new JDBCClient();
		boolean createTable = jc.delete(ttableDesc.getName(), project.getEnname());
		ResponseResult rr=new ResponseResult();
		rr.setId(UUIDUtils.uuid());
		if(createTable){
			rr.setInfo("创建表:"+ttableDesc.getName()+"成功......");
			rr.setStatus(Boolean.TRUE);
		}else{
			rr.setInfo("创建表:"+ttableDesc.getName()+"失败......");
			rr.setStatus(Boolean.FALSE);	
		}
		rrs.add(rr);
		
	}
}
