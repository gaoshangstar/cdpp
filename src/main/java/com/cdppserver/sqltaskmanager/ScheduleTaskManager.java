package com.cdppserver.sqltaskmanager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.cdppserver.constant.Constants;
import com.cdppserver.datastandard.SelfTimer;
import com.cdppserver.datastandard.SqlStatus;
import com.cdppserver.datastandard.SqlTask;
import com.cdppserver.jdbc.SqlStatusManager;
import com.cdppserver.jdbc.SqlTaskOperate;

public class ScheduleTaskManager implements Runnable{
	private static Logger logger=Logger.getLogger(ScheduleTaskManager.class.getName());
	
	private int running;
	private long sleepTime;
	private  ScheduledExecutorService executor;
	private SqlStatus ss;;
	public ScheduleTaskManager(int running,long sleepTime,ScheduledExecutorService executor){
		this.running=running;
		this.sleepTime=sleepTime;
		this.executor=executor;
		 this.ss=new SqlStatus();
		ss.setStatus("1");
		 }
	
	public static void main(String[] args) {
		long initialDelay = getTimeMillis("2016-11-18 23:55") - System.currentTimeMillis();
		System.out.println(initialDelay/(1000*60));
	}
	@Override
	public void run() {
		
		while(true){
			try {
			//获取正在运行的任务数
			List<SqlStatus> runningTask = SqlStatusManager.query(ss);
			int count = runningTask.size();
			
			if(count>=running){
				Thread.sleep(sleepTime);
				logger.warn("当前正在运行任务数已经超出集群配置要求,将在"+sleepTime+"毫秒后继续接受新任务......");
			}else{
				//从队列中获取待处理任务id
				String scheduleTaskSQL = InitTask.getScheduleTaskSQLFromQueue();
				if(null==scheduleTaskSQL || "".equals(scheduleTaskSQL)){
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
					st.setId(scheduleTaskSQL);
					st.setFlag("1");
					st.setIsdel("0");
					List<SqlTask> scheduleTimeTask = so.query(st);
					if(scheduleTimeTask.size()==1){
					//执行获取结果
						this.executeFixedRate(scheduleTimeTask.get(0));
						logger.info("开启定时任务.......");
					}else{
						logger.warn("任务已经被废弃......");
						}
					}
						}
				} catch (Exception e) {
					e.printStackTrace();
					}
			}
	}
	 
	public  void executeFixedRate(SqlTask sqltask) { 
		//根据开始时间延时时间及定时时间设置最终的时间
		String timerexpression = sqltask.getTimerexpression();
        String tmp=timerexpression.substring(1,timerexpression.length()-1);
        String[] split = tmp.split(";");
//        for(int i=0;i<split.length;i++){
//        	String pro = split[i];
//        	String[] p = pro.split(":");
//        	selfTimer.setTimerBeginTime(p[1]);
//        	selfTimer.setTimerIntervalTime(timerIntervalTime);
//        }
        String beginTimetmp=split[0].split(",")[1];
        String intervalTimetmp=split[1].split(",")[1];
        String unittmp=split[2].split(",")[1];
        SelfTimer selfTimer=new SelfTimer();
        selfTimer.setTimerBeginTime(beginTimetmp);
        selfTimer.setTimerIntervalTime(intervalTimetmp);
        selfTimer.setTimerUnit(unittmp);
		logger.info("定时任务：根据开始时间,延时时间及定时时间设置最终的时间...");
		logger.info("定时任务：开始时间 :"+selfTimer.getTimerBeginTime());
		logger.info("定时任务：间隔时间 :"+selfTimer.getTimerIntervalTime());
		logger.info("定时任务：时间单位 :"+selfTimer.getTimerUnit());
		TimeUnit unit = TimeUnit.MINUTES;
		Long period=0l;
		Long initialDelay = 0l;
		if("0".equals(sqltask.getStatus())){ //没运行过
			initialDelay = getTimeMillis(selfTimer.getTimerBeginTime()) - System.currentTimeMillis();
		}else if("1".equals(sqltask.getStatus())){ //运行过
			initialDelay = getTimeMillis1(selfTimer.getTimerBeginTime()) - System.currentTimeMillis();
		}
		if("0".equals(selfTimer.getTimerUnit())){ //分
			period= Long.parseLong(selfTimer.getTimerIntervalTime());
			initialDelay = (initialDelay > 0 ? initialDelay : (period*60*1000 + initialDelay))/(1000*60);
		} else if("1".equals(selfTimer.getTimerUnit())){ //时
			initialDelay = (initialDelay > 0 ? initialDelay : (period*60*60*1000 + initialDelay))/(1000*60);
			period= Long.parseLong(selfTimer.getTimerIntervalTime())*60;
		} else if("2".equals(selfTimer.getTimerUnit())){ //天
			period= Long.parseLong(selfTimer.getTimerIntervalTime())*60*24;
			initialDelay = (initialDelay > 0 ? initialDelay : (period*60*60*1000*24 + initialDelay))/(1000*60);
		}
		logger.info("任务类型:"+sqltask.getTitle()+"执行时间:延迟时间:"+initialDelay+",间隔时间:"+period+",类型:"+unit);
		executor.scheduleAtFixedRate(new ScheduleTimeTaskThread(sqltask),initialDelay,period,unit);    
	}   
	
	private static long getTimeMillis(String time) {  
	    try {  
	        DateFormat dateFormat = new SimpleDateFormat(Constants.DATETIME_FORMAT);  
	        Date curDate = dateFormat.parse(time+":00");  
	        return curDate.getTime();  
	    } catch (ParseException e) {
	    	logger.error("ParseException:"+e.getMessage());
	    }  
	    return 0;
	}
	
	private static long getTimeMillis1(String time) {
		String temp = time.split(" ")[1];
	    try {  
	        DateFormat dateFormat = new SimpleDateFormat(Constants.DATETIME_FORMAT);  
	        DateFormat dayFormat = new SimpleDateFormat(Constants.DATE_FORMAT);  
	        Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + temp+":00");  
	        return curDate.getTime();  
	    } catch (ParseException e) {  
	    	logger.error("ParseException:"+e.getMessage()); 
	    }  
	    return 0;
	} 
}
