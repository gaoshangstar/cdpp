package com.cdppserver.datastandard;

/**
 * @author Zhang Wei  Email:hsdcloud@163.com
 *
 * @version 2016年11月14日  下午2:26:54
 */
public class TColumns {

	private String id;
	private String field;
	private String  dataType;
	private String  remark;
	private String  tableId;
	//对字段进行排序
	private int sortNum;
	
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	
}
