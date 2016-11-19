package com.cdppserver.datastandard;

import java.util.ArrayList;
import java.util.List;

/*
 * db table 'tabledesc'
 */
public class Tabledesc {

	private String id;
	private String name;
	private String remark;
	private String projectId;
	private String split;
	private String storeDir;
	private String partitions;
	private String type;
	private List<Columns> columnsList = new ArrayList<Columns>();

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	private String sql;
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getSplit() {
		return split;
	}
	public void setSplit(String split) {
		this.split = split;
	}
	public String getStoreDir() {
		return storeDir;
	}
	public void setStoreDir(String storeDir) {
		this.storeDir = storeDir;
	}
	public List<Columns> getColumnsList() {
		return columnsList;
	}
	public void setColumnsList(List<Columns> columnsList) {
		this.columnsList = columnsList;
	}
	public String getPartitions() {
		return partitions;
	}
	public void setPartitions(String partitions) {
		this.partitions = partitions;
	}

}
