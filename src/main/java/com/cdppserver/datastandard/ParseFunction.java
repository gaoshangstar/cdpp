package com.cdppserver.datastandard;

import java.util.List;

public class ParseFunction {
	private List<String> tableName;
	private String sql;
	private String function;
	private String type;//0:insert 1:select

	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getTableName() {
		return tableName;
	}
	public void setTableName(List<String> tableName) {
		this.tableName = tableName;
	}

}
