package com.gsoft.framework.core.convert;

import java.util.Locale;

/**
 * 代码集提供工厂接口
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public interface IConvertProviderFactory {

	/** 
	 * 根据代码集名称及国际化参数获取代码集
	 * @param name
	 * @param locale
	 * @return 
	 */
	public IConvert<?> getConvert(String name,Locale locale);
	
}
