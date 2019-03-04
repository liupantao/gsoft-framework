package com.gsoft.framework.core.convert;

import org.springframework.beans.factory.InitializingBean;

import com.gsoft.framework.core.convert.IConvert;
import com.gsoft.framework.core.dataobj.Record;

/**
 * convert代码集抽象类
 * @author liupantao
 * @date 2017年10月16日
 *
 */
public abstract class AbstractConvert implements IConvert<String>, InitializingBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2188043109962077968L;

	private String name;
	
	private Record record;

	public AbstractConvert() {
		this.record = new Record();
	}
	
	public AbstractConvert(Record record) {
		if(record==null){
			record = new Record();
		}
		this.record = record;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public Record getRecord() {
		return this.record;
	}

	@Override
	public String get(String value) {
		Object result = this.record.get(value);
		return (result == null) ? null : result.toString();
	}

	@Override
	public String toJson() {
		StringBuffer jsonBuf = new StringBuffer("{");

		for (String key : this.record.keySet()) {
			jsonBuf.append("\"").append(key).append("\":\"").append(get(key)).append("\",");
		}

		if (jsonBuf.length() > 1) {
			jsonBuf.deleteCharAt(jsonBuf.length() - 1);
		}
		jsonBuf.append("}");
		return jsonBuf.toString();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initRecord(this.record);
	}

	/** 
	 * 初始化代码集内容
	 * @param record 
	 */
	protected abstract void initRecord(Record record);
}