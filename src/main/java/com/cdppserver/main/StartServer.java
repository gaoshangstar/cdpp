package com.cdppserver.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdppserver.constant.Constants;
import com.cdppserver.datastandard.Project;
import com.cdppserver.datastandard.ResponseResult;
import com.cdppserver.datastandard.SqlTask;
import com.cdppserver.datastandard.Tabledesc;
import com.cdppserver.datastandard.TtableDesc;
import com.cdppserver.projectmanager.ProjectManager;
import com.cdppserver.sqltaskmanager.InitTask;
import com.cdppserver.sqltaskmanager.SqlTaskDoor;
import com.cdppserver.tablemanager.TableManger;
import com.cdppserver.tablemanager.TargetTableManager;
import com.cdppserver.utils.ConfigProperties;
import com.cdppserver.utils.HivePropertiesUtils;
import com.cdppserver.utils.JSONUtils;
import com.cdppserver.utils.MysqlPropertiesUtils;
import com.cdppserver.utils.UUIDUtils;

@RestController
@SpringBootApplication
@EnableAutoConfiguration
@RequestMapping("/service")
public class StartServer  extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {
	//动态加载log4j.properties文件
	static{
		MysqlPropertiesUtils.loadMySQLProperties2Map();
		ConfigProperties.loadConfigProperties2Map();
		HivePropertiesUtils.loadHiveProperties2Map();
		System.setProperty("hadoop.home.dir",ConfigProperties.getValue("hadoop_home"));
	}
	private static Logger logger=Logger.getLogger(StartServer.class.getName());
	
    @RequestMapping(value="/{requestPath}" ,method = RequestMethod.POST)
    String info(@PathVariable("requestPath") String requestPath, @RequestParam String info) throws SQLException, InterruptedException {
    	List<ResponseResult> rr=new ArrayList<ResponseResult>();
    	if(requestPath.equals(Constants.PROJECTREQUEST)){
    		List<Project> projects = (List<Project>) JSONUtils.toObjectArray(info, Project.class);
    		
    		for(int i=0;i<projects.size();i++){
    			Project project = projects.get(i);
    			String type = project.getType();
    			logger.info("接收项目处理请求:"+info);
    			if(type.equals("1")){
    			logger.info("处理增加项目请求:"+info);
    			ProjectManager.create(project,rr);
    			logger.info("结束增加项目请求:"+info);
    			}else{
    				logger.info("处理删除项目请求:"+info);
    				ProjectManager.delete(project,rr);
    				logger.info("结束删除项目请求:"+info);
    			}
    		}
    		
    	}else if(requestPath.equals(Constants.TABLEREQUEST)){
    		List<Tabledesc> tables = (List<Tabledesc>) JSONUtils.toObjectArray(info, Tabledesc.class);
    		for(int i=0;i<tables.size();i++){
    			Tabledesc tabledesc = tables.get(i);
    			logger.info("接收表处理请求:"+info);
    			//判断表得操作
    			String type = tabledesc.getType();
    			//如果为1则为建表操作
    			if(type.equals("1")){
    				logger.info("处理增加表请求:"+info);
    				TableManger.createTable(tabledesc,rr);
    				logger.info("结束增加表请求:"+info);
    			}else if(type.equals("2")){
    				logger.info("开始处理删除表请求:"+info);
    				TableManger.deleteTable(tabledesc,rr);
    				logger.info("结束处理删除表请求:"+info);
    			}else{
    				ResponseResult rLog=new ResponseResult();
    	    		rLog.setId(UUIDUtils.uuid());
    	    		rLog.setInfo("非法表操作......");
    	    		rLog.setStatus(Boolean.FALSE);
    	    		rr.add(rLog);
    				}
    			}
    		
    		}else if(requestPath.equals(Constants.TASKREQUEST)){
    			//获取taskSQL的Info信息
    			List<SqlTask> sqlTasks = (List<SqlTask>) JSONUtils.toObjectArray(info, SqlTask.class);
    			//判断是单次任务还是定时任务，单次任务加入单次任务队列，定时任务加入定时任务队列
    			for(int i=0;i<sqlTasks.size();i++){
    				SqlTask sqlTask = sqlTasks.get(i);
    				String flag = sqlTask.getFlag();
    				if("1".equals(flag)){
    				//定时运行任务
    				logger.info("id=["+sqlTask.getId()+"]的任务正在加入任务队列任务详情:"+info);
    				InitTask.addScheduleTaskSQL2Queue(sqlTask.getId(),rr);
    				
    				}else if("0".equals(flag)){
    					//1次运行任务
    					logger.info("id=["+sqlTask.getId()+"]的任务正在加入任务队列任务详情:"+info);
    					InitTask.addOneTimeTaskSQL2Queue(sqlTask.getId(),rr);	
    				}else{
    					logger.info("id=["+sqlTask.getId()+"]的任务为非法任务,废弃处理.");
    		    		ResponseResult rLog=new ResponseResult();
    		    		rLog.setId(UUIDUtils.uuid());
    		    		rLog.setInfo("非法任务参数......");
    		    		rLog.setStatus(Boolean.FALSE);
    		    		rr.add(rLog);
    				}
    			}
    		}else if(requestPath.equals(Constants.TTABLEREQUEST)){
    			
    			List<TtableDesc> ttableDescs = (List<TtableDesc>) JSONUtils.toObjectArray(info,TtableDesc.class);
	    			for(int i=0;i<ttableDescs.size();i++){
	    			TtableDesc ttableDesc = ttableDescs.get(i);
	    			String type = ttableDesc.getType();
	    			if("1".equals(type)){
	    				TargetTableManager.createTable(ttableDesc, rr);
	    			}else if("2".equals(type)){
	    				TargetTableManager.deleteTable(ttableDesc, rr);
	    			}else{
	    				ResponseResult rLog=new ResponseResult();
	    	    		rLog.setId(UUIDUtils.uuid());
	    	    		rLog.setInfo("非法表操作......");
	    	    		rLog.setStatus(Boolean.FALSE);
	    	    		rr.add(rLog);
	    			}
	    		}
    			
    			
    		}else {
    		logger.info("非法访问路径,废弃处理.");
    		ResponseResult rLog=new ResponseResult();
    		rLog.setId(UUIDUtils.uuid());
    		rLog.setInfo("非法访问路径......");
    		rLog.setStatus(Boolean.FALSE);
    		rr.add(rLog);
    	}
    	return	JSONUtils.toJSON(rr);
    }
    
    @Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {  
        return builder.sources(StartServer.class);  
    }  
      
    public static void main(String[] args) throws InterruptedException {
    	logger.info("开始启动后端restful服务......");
        SpringApplication.run(StartServer.class, args);
        logger.info("成功开启restful服务,开始接受前端请求......");
    	logger.info("系统开始将任务加入队列......");
    	InitTask.initUnDealTask();
    	logger.info("系统将任务加入队列完成......");
    	logger.info("系统开始执行分析任务.......");
    	SqlTaskDoor.setup();
    }
    
    @Override   
    public void customize(ConfigurableEmbeddedServletContainer container) {  
        container.setPort(8081);  
    }  
}