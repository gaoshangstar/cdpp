package com.cdppserver.sqltaskmanager;

import java.sql.SQLException;
import java.util.LinkedList;

import com.cdppserver.datastandard.ParseFunction;
import com.cdppserver.datastandard.Project;
import com.cdppserver.datastandard.SqlStatus;
import com.cdppserver.datastandard.SqlTask;
import com.cdppserver.jdbc.ProjectOperate;
import com.cdppserver.jdbc.SqlStatusManager;
import com.cdppserver.jdbc.SqlTaskOperate;
import com.cdppserver.log.TaskLog;
import com.cdppserver.utils.DateConvert;
import com.cdppserver.utils.SqlManager;
import com.cdppserver.utils.UUIDUtils;
/**
 * 
 * @author ASh
 *
 *
 */
public class ScheduleTimeTaskThread implements Runnable {

	private SqlTask st;
	public ScheduleTimeTaskThread(SqlTask sqlTask) {
		this.st=sqlTask;
	}

	@Override
	public void run() {

		 //更新任务状态status由0变为1
		 //更新sqlstatus表
		 SqlTaskOperate sto=new SqlTaskOperate();
		 SqlTask s=new SqlTask();
		 s.setId(st.getId());
		 s.setStatus("1");
		 sto.update(s);
		 //更新sqlstatus状态表任务
		 String id=UUIDUtils.uuid();
		 TaskLog.addLog(id,"任务开始运行,id=["+id+"]的任务设置状态位status=1......");
		 long beginTime=System.currentTimeMillis();
		 String status="1";
		 String sqlTaskId=st.getId();
		 SqlStatus ss=new SqlStatus();
		 ss.setId(id);
		 ss.setBeginTime(DateConvert.convert2Date(beginTime));
		 ss.setSqlTaskId(sqlTaskId);
		 ss.setStatus(status);
		 SqlStatusManager sm=new SqlStatusManager();
		 sm.add(ss);
		//1、解析sql
		 TaskLog.addLog(id,"id=["+id+"]的任务设置SqlStatus日志表.任务开始执行时间:"+DateConvert.convert2Date(beginTime));
		 String sqltask = st.getSqltask();
		 LinkedList<ParseFunction> info = null;
		try {
			info = SqlManager.parse(sqltask);
			 TaskLog.addLog(id,"id=["+id+"]的任务解析sql完毕.完成表和函数的抽取"+info.toArray());
		} catch (Exception e1) {
			TaskLog.addLog(id,"id=["+id+"]的任务解析sql出现异常."+info.toArray());
			e1.printStackTrace();
		}
		//2、执行sql,获取对应的数据库信息
		 ProjectOperate po=new ProjectOperate();
		 Project pro=new Project();
		 pro.setId(st.getProjectid());
		 Project query = po.query(pro);
		 String projectName=query.getEnname();
		//3、设置任务状态
		 boolean execute = false;
		try {
			execute = SqlManager.execute(info,query,st,ss);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 if(execute){
			//设置任务状态
			 ss.setStatus("2");
		 }else{
			//设置任务状态
			 ss.setStatus("3");
		 }
		 ss.setFinishTime(DateConvert.convert2Date(System.currentTimeMillis()));
		 ss.setExecuteTime(((System.currentTimeMillis()-beginTime)/1000)+"");
		 SqlStatusManager.update(ss);
		 TaskLog.addLog(id,"id=["+id+"]的任务执行完毕.成功更新任务状态");
	}

}
