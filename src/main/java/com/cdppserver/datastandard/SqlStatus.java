package com.cdppserver.datastandard;

public class SqlStatus {

	private String id;
	private String beginTime;
	private String finishTime;
	private String status;
	private String sqlTaskId;
	private String path;
	private String executeTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSqlTaskId() {
		return sqlTaskId;
	}
	public void setSqlTaskId(String sqlTaskId) {
		this.sqlTaskId = sqlTaskId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getExecuteTime() {
		return executeTime;
	}
	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}
	
}
