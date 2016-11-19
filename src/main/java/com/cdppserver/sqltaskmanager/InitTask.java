package com.cdppserver.sqltaskmanager;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cdppserver.datastandard.ResponseResult;
import com.cdppserver.datastandard.SqlTask;
import com.cdppserver.jdbc.SqlTaskOperate;
import com.cdppserver.utils.JSONUtils;
import com.cdppserver.utils.UUIDUtils;

/**
 * 功能：初始化任务
 * @author ASh
 *
 *
 */
public class InitTask {
	private static Logger logger=Logger.getLogger(InitTask.class.getName());
			
	private static LinkedList<String> oneTimeTaskQueue = new LinkedList<String>();
	private static LinkedList<String> scheduleQueue = new LinkedList<String>();
		
	public static void initUnDealTask() throws InterruptedException{
		//加载所有的待运行的一次性任务
		SqlTaskOperate so=new SqlTaskOperate();
		SqlTask st=new SqlTask();
		st.setIsdel("0");
		List<SqlTask> tasks = so.query(st);
		//将任务放在队列中运行
		for(int i=0;i<tasks.size();i++){
			SqlTask sqlTask = tasks.get(i);
			//一次运行任务
			if(sqlTask.getFlag().equals("0")){
				logger.info("id=["+sqlTask.getId()+"]的任务正在加入任务队列任务详情:"+JSONUtils.toJSON(sqlTask));
				oneTimeTaskQueue.addLast(sqlTask.getId());
				logger.info("id=["+sqlTask.getId()+"]的任务成功任务队列");
				//定时任务	
				}else if(sqlTask.getFlag().equals("1")){
					logger.info("id=["+sqlTask.getId()+"]的任务正在加入任务队列任务详情:"+JSONUtils.toJSON(sqlTask));
				scheduleQueue.addLast(sqlTask.getId());
				logger.info("id=["+sqlTask.getId()+"]的任务成功任务队列");				
				}else{
				logger.warn("非法任务不处理......");
			}
			
		}
	}
	/**
	 * 功能：加入tasksql
	 * @param task
	 * @param rr 
	 * @throws InterruptedException 
	 */
	public static void addOneTimeTaskSQL2Queue(String task, List<ResponseResult> rr) throws InterruptedException{
		oneTimeTaskQueue.addLast(task);
		logger.info("id=["+task+"]的任务成功加入任务队列......");
		ResponseResult rLog=new ResponseResult();
		rLog.setId(UUIDUtils.uuid());
		rLog.setInfo("添加一次运行任务:"+task+"到任务队列......");
		rLog.setStatus(Boolean.TRUE);
		rr.add(rLog);
		
	}
	/**
	 * 功能：从队列中取出任务
	 * @return
	 * @throws InterruptedException
	 */
	public static String getOneTimeTaskSQLFromQueue() throws InterruptedException{
		if(oneTimeTaskQueue.size()==0){
			return "";
		}
		String task=oneTimeTaskQueue.getFirst();
		oneTimeTaskQueue.removeFirst();
		logger.info("获取一次运行任务"+task);
		return task;
	}
	
	/**
	 * 功能：加入tasksql
	 * @param task
	 * @param rr 
	 * @throws InterruptedException 
	 */
	public static void addScheduleTaskSQL2Queue(String task, List<ResponseResult> rr) throws InterruptedException{
		scheduleQueue.addFirst(task);
		logger.info("id=["+task+"]的任务成功加入任务队列......");
		ResponseResult rLog=new ResponseResult();
		rLog.setId(UUIDUtils.uuid());
		rLog.setInfo("添加定时调度任务:"+task+"到任务队列......");
		rLog.setStatus(Boolean.TRUE);
		rr.add(rLog);
		
	}
	/**
	 * 功能：从队列中取出任务
	 * @return
	 * @throws InterruptedException
	 */
	public static String getScheduleTaskSQLFromQueue() throws InterruptedException{
		if(scheduleQueue.size()==0){
			return "";
		}
		String task=scheduleQueue.getFirst();
		scheduleQueue.removeFirst();
		logger.info("获取定时调度任务"+task);
		return task;
	}
	
}
