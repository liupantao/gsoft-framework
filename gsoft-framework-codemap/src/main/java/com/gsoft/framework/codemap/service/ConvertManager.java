package com.gsoft.framework.codemap.service;

import java.util.List;

import com.gsoft.framework.core.convert.IConvert;
import com.gsoft.framework.core.dataobj.Record;

/**
 * ConvertManager接口
 * @author liupantao
 * @date 2017年10月17日
 *  
 */
public interface ConvertManager {
	
	/** 
	 * 根据名称获取convert
	 * @param name
	 * @return 
	 */
	public IConvert<?> getConvert(String name);
	
	/** 
	 * 根据名称获取convert
	 * @param name
	 * @return 
	 */
	public Record getConverts(List<String> names);
	
	/** 
	 * 刷新convert缓存
	 * @param name 
	 */
	public void refreshCached(String name);
}
