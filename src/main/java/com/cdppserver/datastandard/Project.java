package com.cdppserver.datastandard;

/**
 * @author Zhang Wei  Email:hsdcloud@163.com
 *
 * @version 2016年9月27日  上午10:14:53
 */
public class Project {

	private String id;
	private String name;
	private String enname;
	private String remark;
	private String roleId;
	
	private String type;//1表示增加.2删除.
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getEnname() {
		return enname;
	}
	public void setEnname(String enname) {
		this.enname = enname;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	
}
