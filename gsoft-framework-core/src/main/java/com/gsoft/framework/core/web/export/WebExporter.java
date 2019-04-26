package com.gsoft.framework.core.web.export;

import org.springframework.http.HttpHeaders;

/**
 * 导出类 接口
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public interface WebExporter {
	
	/** 
	 * 关闭
	 */
	public void close();

	/** 
	 * 写行记录
	 * @param paramInt
	 * @param paramArrayOfObject 
	 */
	public void writeLine(int paramInt, Object[] paramArrayOfObject);

	/** 
	 * 获取http头 
	 * @return 
	 */
	public HttpHeaders getHttpHeaders();
	
}