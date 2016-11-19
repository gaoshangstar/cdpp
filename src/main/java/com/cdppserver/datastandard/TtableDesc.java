package com.cdppserver.datastandard;

import java.util.ArrayList;
import java.util.List;

/*
 * 外部表相关
 * @author Zhang Wei  Email:hsdcloud@163.com
 *
 * @version 2016年11月14日  下午2:23:15
 */
public class TtableDesc {

	private String id;
	private String name;
	private String 	remark;
	private String projectId;
	private String  storeFormat;
	private String properties;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private  List<TColumns> lt = new ArrayList<TColumns>();
	private  List<Properties> lp = new ArrayList<Properties>();
 	  
	public List<TColumns> getLt() {
		return lt;
	}
	public void setLt(List<TColumns> lt) {
		this.lt = lt;
	}
	public List<Properties> getLp() {
		return lp;
	}
	public void setLp(List<Properties> lp) {
		this.lp = lp;
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
	public String getStoreFormat() {
		return storeFormat;
	}
	public void setStoreFormat(String storeFormat) {
		this.storeFormat = storeFormat;
	}
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	
 }
