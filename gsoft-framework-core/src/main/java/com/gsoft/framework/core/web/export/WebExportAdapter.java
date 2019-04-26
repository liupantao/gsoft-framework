package com.gsoft.framework.core.web.export;

import java.io.OutputStream;

/**
 * 导出适配器接口
 * @author liupantao
 * @date 2017年10月16日
 *
 * @param <T>  
 */
public interface WebExportAdapter<T extends WebExporter> {
	
	/** 
	 * 是否支持该类型 
	 * @param type
	 * @return 
	 */
	public boolean supports(String type);

	/** 
	 * 初始化导出类
	 * @param paramOutputStream
	 * @param type
	 * @return 
	 */
	public T openExporter(OutputStream paramOutputStream,String type);
	
}