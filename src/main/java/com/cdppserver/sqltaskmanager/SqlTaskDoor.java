package com.cdppserver.sqltaskmanager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.log4j.Logger;

import com.cdppserver.utils.ConfigProperties;


/**
 * 功能:运行任务
 * @author ASh
 * ntpdate cn.pool.ntp.org
 * clock -w
 *
 */
public class SqlTaskDoor{
	
	private static Logger logger=Logger.getLogger(SqlTaskDoor.class.getName());
	
	private static ExecutorService oneTimeTask = Executors.newFixedThreadPool(Integer.parseInt(ConfigProperties.getValue("taskCount")));
	private static  ScheduledExecutorService scheduleTask = Executors.newScheduledThreadPool(Integer.parseInt(ConfigProperties.getValue("taskCount")));
	public static  void setup(){
		logger.info("开启一次运行调度任务.......");
		new Thread(new OneTimeTaskManager(Integer.parseInt(ConfigProperties.getValue("taskCount")), Long.parseLong(ConfigProperties.getValue("oneTimeTasksleeptime")), oneTimeTask)).start();
		logger.info("开启定时运行调度任务.......");
		new Thread(new ScheduleTaskManager(Integer.parseInt(ConfigProperties.getValue("taskCount")), Long.parseLong(ConfigProperties.getValue("scheduleTasksleeptime")), scheduleTask)).start();
	}
}
