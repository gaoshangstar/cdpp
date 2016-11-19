package com.cdppserver.utils;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.cdppserver.jdbc.UDFFunctionManager;

public class LoadUDFFunction {

	private static Logger logger=Logger.getLogger(LoadUDFFunction.class.getName());
	public static void loadFunction(String projectId, Statement createStatement) throws SQLException{
		Set<String> sets=new HashSet<String>();
		List<String> register=new ArrayList<String>();
		//根据projectName获取ProjectID
		UDFFunctionManager udfs=new UDFFunctionManager();
		List<String> udffs = udfs.getUDF(projectId);
		for(String udf :udffs){
			String[] split = udf.split(",");
			register.add("create temporary function "+split[0]+" as '"+split[1]+"'");
			sets.add("add jar "+split[2]);
		}

		for(String set:sets){
			logger.info("开始加载jar文件:"+set+"......");
			createStatement.execute(set);
			logger.info("成功加载jar文件:"+set+"......");
		}
		for(String udf :register){
			logger.info("开始加载udf函数:"+udf+"......");
			createStatement.execute(udf);
			logger.info("udf函数:"+udf+"加载成功......");
		}
	}
}
