/*
 * YOUI框架
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gsoft.framework.core.web.menu;

import com.gsoft.framework.core.dataobj.tree.TreeAttribute;

/**
 * 默认菜单类
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class DefaultMenu implements IMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6256436543642454970L;

	private String menuStyle;
	
	private String menuId;
	
	private String menuSrc;
	
	private String target;
	
	private String parentMenuId;
	
	private String menuCaption;

	/**
	 * @return the menuStyle
	 */
	@Override
	public String getMenuStyle() {
		return menuStyle;
	}

	/**
	 * @param menuIconStyle the menuStyle to set
	 */
	@Override
	public void setMenuStyle(String menuStyle) {
		this.menuStyle = menuStyle;
	}

	/**
	 * @return the menuId
	 */
	@Override
	@TreeAttribute(value = "id")
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	@Override
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the src
	 */
	@Override
	@TreeAttribute(value = "href")
	public String getMenuSrc() {
		return menuSrc;
	}

	/**
	 * @param src the src to set
	 */
	@Override
	public void setMenuSrc(String src) {
		this.menuSrc = src;
	}

	/**
	 * @return the target
	 */
	@Override
	@TreeAttribute(value = "target")
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	@Override
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the parentMenuId
	 */
	@Override
	@TreeAttribute(value = "parentId")
	public String getParentMenuId() {
		return parentMenuId;
	}

	/**
	 * @param parentMenuId the parentMenuId to set
	 */
	@Override
	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	/**
	 * @return the menuName
	 */
	@Override
	@TreeAttribute(value = "text")
	public String getMenuCaption() {
		return menuCaption;
	}

	/**
	 * @param menuName the menuName to set
	 */
	@Override
	public void setMenuCaption(String menuCaption) {
		this.menuCaption = menuCaption;
	}

	@Override
	public String toString(){
		return menuCaption+ " " +menuId;
	}
	

}
