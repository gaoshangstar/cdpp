package com.cdppserver.projectmanager;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.cdppserver.datastandard.Project;
import com.cdppserver.datastandard.ResponseResult;
import com.cdppserver.utils.JDBCClient;
import com.cdppserver.utils.UUIDUtils;

/**
 * 
 * @author ASh
 *
 *
 */
public class ProjectManager {

	private static Logger logger=Logger.getLogger(ProjectManager.class.getName());
	/**
	 * 
	 * @param project
	 * @param rr
	 * @return
	 * @throws SQLException 
	 */
	public static void create(Project project, List<ResponseResult> rrs) throws SQLException {
		ResponseResult rr=new ResponseResult();
		JDBCClient  jc = new JDBCClient();
		boolean createDataBase = jc.createDataBase(project.getEnname());
		rr.setId(UUIDUtils.uuid());
		if(createDataBase){
			rr.setInfo("项目:"+project.getEnname()+"创建成功......");
			rr.setStatus(Boolean.TRUE);
		}else{
			rr.setInfo("项目:"+project.getEnname()+"创建失败......");
			rr.setStatus(Boolean.FALSE);
		}
		rrs.add(rr);
	}

	public static void delete(Project project, List<ResponseResult> rr) {
		
	}
}
