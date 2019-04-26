/**
 * 
 */
package com.gsoft.framework.core.convert;

import com.gsoft.framework.core.dataobj.Domain;
import com.gsoft.framework.core.dataobj.Record;

/**
 * Convert接口
 * @author liupantao
 * @date 2017年10月16日
 *
 * @param <T>  
 */
public interface IConvert<T> extends Domain{

	/** 
	 * 根据key获取代码集值
	 * @param key
	 * @return 
	 */
	public T get(T key);
	
	/** 
	 * 获取该代码集集合
	 * @return 
	 */
	public Record getRecord();

	/** 
	 * 获取该代码集名称
	 * @return 
	 */
	public String getName();

	/** 
	 * 获取该代码集json串 
	 * @return 
	 */
	public String toJson();

}
