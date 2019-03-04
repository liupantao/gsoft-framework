package com.gsoft.framework.ui.url;

/**
 * TODO (描述该文件做什么)
 * @author liupantao
 * @date 2017年10月17日
 *  
 */
public interface UrlTransformerAdapter {
	/** 
	 * TODO(描述这个方法的作用) 
	 * @param paramString
	 * @return 
	 */
	public boolean supports(String paramString);

	/** 
	 * TODO(描述这个方法的作用) 
	 * @param paramString
	 * @return 
	 */
	public String transform(String paramString);
}