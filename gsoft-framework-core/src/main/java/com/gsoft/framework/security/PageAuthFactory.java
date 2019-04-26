package com.gsoft.framework.security;

/**
 * PageAuthFactory
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public interface PageAuthFactory {
	
	/**
	 * hasPermission
	 * @param pageId 页面唯一标志
	 * @param authCode 页面元素权限校验码
	 * @return
	 */
	public boolean hasPermission(String pageId,String authCode);
}
