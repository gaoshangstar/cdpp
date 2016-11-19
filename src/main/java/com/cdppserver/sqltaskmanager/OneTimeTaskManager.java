package com.cdppserver.sqltaskmanager;

import java.util.List;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;

import com.cdppserver.datastandard.SqlStatus;
import com.cdppserver.datastandard.SqlTask;
import com.cdppserver.jdbc.SqlStatusManager;
import com.cdppserver.jdbc.SqlTaskOperate;

public class OneTimeTaskManager implements Runnable{
	private static 	Logger logger=Logger.getLogger(OneTimeTaskManager.class.getName());
	private int running;
	private long sleepTime;
	private ExecutorService executor;
	private SqlStatus ss;
	public OneTimeTaskManager(int running,long sleepTime,ExecutorService executor){
		this.running=running;
		this.sleepTime=sleepTime;
		this.executor=executor;
		 this.ss=new SqlStatus();
		 ss.setStatus("1");
	}
	@Override
	public void run() {
		while(true){
			try {
				//获取正在运行的任务数
				List<SqlStatus> runningTask = SqlStatusManager.query(ss);
				int count = runningTask.size();
				if(count>=running){
					logger.warn("当前正在运行任务数已经超出集群配置要求,将在"+sleepTime+"毫秒后继续接受新任务......");
					try {
						Thread.sleep(sleepTime);
					} catch (Exception e) {
						e.printStackTrace();
						}
				}else{
					//从队列中获取待处理任务id
					String taskId = InitTask.getOneTimeTaskSQLFromQueue();
					if(null==taskId || "".equals(taskId)){
						
						logger.warn("没有需要运行的任务,将在"+sleepTime+"毫秒后继续接受新任务......");
						try {
							Thread.sleep(sleepTime);
						} catch (Exception e) {
							e.printStackTrace();
							}
						
					}else{
						//从数据库中获取当前的配置信息
						//根据taskId获取任务信息，根据status=0及flag=0确定该任务是否需要运行
						SqlTaskOperate so=new SqlTaskOperate();
						SqlTask st=new SqlTask();
						st.setId(taskId);
						st.setStatus("0");
						st.setFlag("0");
						List<SqlTask> oneTimeTask = so.query(st);
						if(oneTimeTask.size()==1){
						//执行获取结果
							logger.info("id=["+oneTimeTask.get(0).getId()+"]的任务已经被取出开始调度执行......");
						 Runnable runner= new OneTimeTaskThread(oneTimeTask.get(0));
						 executor.execute(runner);
						}else{
							logger.info("任务已经被废弃......");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

}
