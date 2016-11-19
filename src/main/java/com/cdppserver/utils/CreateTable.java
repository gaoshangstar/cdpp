package com.cdppserver.utils;

import java.util.ArrayList;
import java.util.List;

import com.cdppserver.datastandard.Columns;
import com.cdppserver.datastandard.TColumns;
import com.cdppserver.datastandard.Tabledesc;
import com.cdppserver.datastandard.TtableDesc;
/**
 * 
 * @author ASh
 *
 *
 */
public class CreateTable {

	public static List<String> generate2SQL(Tabledesc tabledesc) {
		List<String> sqls=new ArrayList<String>();
		StringBuffer sb=new StringBuffer();
		List<Columns> columnsList = tabledesc.getColumnsList();
		for(int i=0;i<columnsList.size();i++){
			Columns c = columnsList.get(i);
			String name = c.getField();
			String type=c.getDataType();
			String remark=c.getRemark();
			sb.append(name).append(" ").append(type).append(" ").append("comment '").append(remark).append("'");
		if(i<columnsList.size()-1){
			sb.append(",");
			}
		}
		String pn = tabledesc.getPartitions();
		String sql_txt;
		String sql_orc;
		if(null!=pn &&!"".equals(pn)){
			sql_txt=String.format("create table %s (%s) comment '%s' partitioned by(%s) row format delimited fields terminated by '%s' stored as textfile", tabledesc.getName()+"_tmp",sb.toString(),tabledesc.getRemark(),pn,tabledesc.getSplit());
			sql_orc=String.format("create table %s (%s) comment '%s' partitioned by(%s) row format delimited fields terminated by '%s' stored as orc", tabledesc.getName(),sb.toString(),tabledesc.getRemark(),pn,tabledesc.getSplit());
		
		}else{
			sql_txt=String.format("create table %s (%s) comment '%s' row format delimited fields terminated by '%s' stored as textfile", tabledesc.getName()+"_tmp",sb.toString(),tabledesc.getRemark(),tabledesc.getSplit());
			sql_orc=String.format("create table %s (%s) comment '%s' row format delimited fields terminated by '%s' stored as orc", tabledesc.getName(),sb.toString(),tabledesc.getRemark(),tabledesc.getSplit());
		}
		sqls.add(sql_txt);
		sqls.add(sql_orc);
		return sqls;
	}
	/**
	 * 新建外部表
	 * @param tabledesc
	 * @return
	 */
	public static String generatet2SQL(TtableDesc tabledesc) {
		StringBuffer sb=new StringBuffer();
		List<TColumns> lts = tabledesc.getLt();
		for(int i=0;i<lts.size();i++){
			TColumns c = lts.get(i);
			String name = c.getField();
			String type=c.getDataType();
			String remark=c.getRemark();
			sb.append(name).append(" ").append(type).append(" ").append("comment '").append(remark).append("'");
		if(i<lts.size()-1){
			sb.append(",");
			}
		}
		String tablesql=String.format("create EXTERNAL table %s (%s) comment '%s' STORED BY '%s' %s", tabledesc.getName(),sb.toString(),tabledesc.getRemark(),tabledesc.getStoreFormat(),tabledesc.getProperties());
		return tablesql;
	}
}
