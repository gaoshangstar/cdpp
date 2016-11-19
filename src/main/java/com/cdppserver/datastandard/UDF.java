package com.cdppserver.datastandard;

/**
 * @author Zhang Wei  Email:hsdcloud@163.com
 *
 * @version 2016年10月21日  下午1:57:57
 */
public class UDF {

	private String id;
	private String functionName;
	private String className;
	private String  sourceId;
	private String purpose;
	private String commandType;
    private String parameterDes;
    private String projectId;
    private String isShare;
    private String sourceName;
    
	public String getSourceName() {
		return sourceName;
	}
	
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getCommandType() {
		return commandType;
	}
	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}
	public String getParameterDes() {
		return parameterDes;
	}
	public void setParameterDes(String parameterDes) {
		this.parameterDes = parameterDes;
	}

	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getIsShare() {
		return isShare;
	}
	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}
    
    
}
