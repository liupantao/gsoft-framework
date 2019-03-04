///**
// * 
// */
//package com.gsoft.framework.core.orm.hibernate;
//
//import java.io.Serializable;
//import java.util.Properties;
//
//import org.apache.commons.lang.StringUtils;
//import org.glassfish.jersey.internal.util.PropertiesHelper;
//import org.hibernate.dialect.Dialect;
//import org.hibernate.id.enhanced.TableGenerator;
//import org.hibernate.service.ServiceRegistry;
//import org.hibernate.type.IntegerType;
//import org.hibernate.type.Type;
//
///**
// * @author zhyi_12
// * 
// */
//public class SeqTableGenerator extends TableGenerator {
//
//	public static final String YOUI_SEQ_TABLE_NAME = "YOUI_SEQ_TABLE";
//
//	public static final String YOUI_SEQ_COLUMN_NAME = "SEQ_NAME";
//
//	public static final String YOUI_SEQ_COLUMN_VALUE = "SEQ_VALUE";
//
//	public static final String VALUE_MAX_LENGTTH = "MAX_LENGTH";
//
//	public static final String VALUE_MIN_LENGTTH = "MIN_LENGTH";
//
//	public static final String VALUE_PREFIX = "PERFIX";
//
//	private int minLength;
//	private int maxLength;
//	private String prefix;
//
//	private String tableName;
//	private String columnName;
//	private String query;
//	private String update;
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.hibernate.id.TableGenerator#configure(org.hibernate.type.Type,
//	 * java.util.Properties, org.hibernate.dialect.Dialect)
//	 */
//	public void configure(Type type, Properties params, Dialect dialect) {
//		params.put("table_name", PropertiesHelper.getString("table_name", params, "YOUI_TABLE_SEQ"));
//		params.put("segment_column_name", PropertiesHelper.getString("segment_column_name", params, "SEQ_ID"));
//		params.put("value_column_name", PropertiesHelper.getString("value_column_name", params, "SEQ_NO"));
//		params.put("increment_size", Integer.valueOf(PropertiesHelper.getInt("increment_size", params, 1000)));
//
//		this.minLength = PropertiesHelper.getInt("MIN_LENGTH", params, 0);
//		this.maxLength = PropertiesHelper.getInt("MAX_LENGTH", params, 2147483647);
//		this.prefix = PropertiesHelper.getString("PERFIX", params, "");
//
//		// 默认从parameters中配置的name属性中获取
//		String seqName = PropertiesHelper.getString("name", params, null);
//		if (StringUtils.isEmpty(seqName)) {
//			// 如果没有配置name属性，取当前实体对象对应的物理表名
//			seqName = PropertiesHelper.getString("target_table", params, "PUB_SEQ");
//		}
//
//		super.configure(new IntegerType(), params, (ServiceRegistry) dialect);
//	}
//
//	public synchronized Serializable generate(SessionImplementor session, Object obj) {
//		Serializable result;
//		try {
//			result = super.generate(session, obj);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//
//		if ((result != null) && (isNeedLeftPad(result.toString()))) {
//			result = StringUtils.leftPad(result.toString(), this.minLength - this.prefix.length(), "0");
//		}
//
//		return result;
//	}
//
//	private boolean isNeedLeftPad(String str) {
//		return (this.prefix + str).length() < this.minLength;
//	}
//
//	public int getMinLength() {
//		return this.minLength;
//	}
//
//	public void setMinLength(int minLength) {
//		this.minLength = minLength;
//	}
//
//	public int getMaxLength() {
//		return this.maxLength;
//	}
//
//	public void setMaxLength(int maxLength) {
//		this.maxLength = maxLength;
//	}
//
//	public String getPrefix() {
//		return this.prefix;
//	}
//
//	public void setPrefix(String prefix) {
//		this.prefix = prefix;
//	}
//
//	public void setTableName(String tableName) {
//		this.tableName = tableName;
//	}
//
//	public String getColumnName() {
//		return columnName;
//	}
//
//	public void setColumnName(String columnName) {
//		this.columnName = columnName;
//	}
//
//	public String getQuery() {
//		return query;
//	}
//
//	public void setQuery(String query) {
//		this.query = query;
//	}
//
//	public String getUpdate() {
//		return update;
//	}
//
//	public void setUpdate(String update) {
//		this.update = update;
//	}
//
//}