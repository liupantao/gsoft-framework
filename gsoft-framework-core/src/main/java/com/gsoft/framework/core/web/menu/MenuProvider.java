/*

* @(#)MenuProvider.java  1.0.0 下午11:15:07

* Copyright 2013 gicom, Inc. All rights reserved.

*/
package com.gsoft.framework.core.web.menu;

import javax.servlet.jsp.PageContext;

import org.springframework.core.io.ResourceLoader;

/**
 * <p></p>
 * @author 
 * @version 1.0.0
 * @see    
 * @since 
 */
public interface MenuProvider {
	/**
	 * @Fields MENU_TYPE_V : 横向菜单
	 */
	public static final String MENU_TYPE_V = "v";
	
	/**
	 * @Fields MENU_TYPE_H : 纵向菜单
	 */
	public static final String MENU_TYPE_H = "h";
	
	public static final String NODE_BLANK="_blank";
	
	/** 
	 * 加载系统菜单
	 * @param resourceLoader 
	 */
	void loadSystemMenu(ResourceLoader resourceLoader);

	/**
	 * 生成系统菜单的html
	 * @param pageContext
	 * @param menuType 菜单类型：横向和纵向
	 * @return
	 */
	String buildMenuHtml(PageContext pageContext,String menuType);

}
