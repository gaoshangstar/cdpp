package com.cdppserver.datastandard;

public class ImportConfig {

	private String id;
	private String connect;
	private String tableName;
	private String targetDir;
	private String isAppend;
	
	private String storeType;
	private String extractColumns;
	private String fetchSize;

	private String mapNum;
	private String querySql;
	private String splitBy;
	private String whereCondition;
	
	private String type;
	private String projectId;
	private String dbaccount;
	private String dbpasswd;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getConnect() {
		return connect;
	}
	public void setConnect(String connect) {
		this.connect = connect;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTargetDir() {
		return targetDir;
	}
	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}
	public String getIsAppend() {
		return isAppend;
	}
	public void setIsAppend(String isAppend) {
		this.isAppend = isAppend;
	}
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	public String getExtractColumns() {
		return extractColumns;
	}
	public void setExtractColumns(String extractColumns) {
		this.extractColumns = extractColumns;
	}
	public String getFetchSize() {
		return fetchSize;
	}
	public void setFetchSize(String fetchSize) {
		this.fetchSize = fetchSize;
	}
	public String getMapNum() {
		return mapNum;
	}
	public void setMapNum(String mapNum) {
		this.mapNum = mapNum;
	}
	public String getQuerySql() {
		return querySql;
	}
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	public String getSplitBy() {
		return splitBy;
	}
	public void setSplitBy(String splitBy) {
		this.splitBy = splitBy;
	}
	public String getWhereCondition() {
		return whereCondition;
	}
	public void setWhereCondition(String whereCondition) {
		this.whereCondition = whereCondition;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getDbaccount() {
		return dbaccount;
	}
	public void setDbaccount(String dbaccount) {
		this.dbaccount = dbaccount;
	}
	public String getDbpasswd() {
		return dbpasswd;
	}
	public void setDbpasswd(String dbpasswd) {
		this.dbpasswd = dbpasswd;
	}
	
}
