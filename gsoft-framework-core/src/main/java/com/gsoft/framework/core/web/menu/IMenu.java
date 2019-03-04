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

import com.gsoft.framework.core.dataobj.Domain;

/**
 * 菜单类接口
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public interface IMenu extends Domain{

	/** 
	 * 获取菜单ID
	 * @return 
	 */
	public String getMenuId();
	/** 
	 * 设置菜单ID
	 * @param menuId 
	 */
	public void setMenuId(String menuId);
	
	
	/** 
	 * 获取菜单src
	 * @return 
	 */
	public String getMenuSrc();
	/** 
	 * 设置菜单src
	 * @param menuSrc 
	 */
	public void setMenuSrc(String menuSrc);
	
	/** 
	 * 获取菜单样式
	 * @return 
	 */
	public String getMenuStyle();
	/** 
	 * 设置菜单样式
	 * @param iconStyle 
	 */
	public void setMenuStyle(String iconStyle);
	
	/** 
	 * 获取菜单描述
	 * @return 
	 */
	public String getMenuCaption();
	/** 
	 * 设置菜单描述 
	 * @param menuCaption 
	 */
	public void setMenuCaption(String menuCaption);

	/** 
	 * 获取菜单父节点ID
	 * @return 
	 */
	public String getParentMenuId();
	/** 
	 * 设置菜单父节点ID
	 * @param menuId 
	 */
	public void setParentMenuId(String menuId);
	
	/** 
	 * 获取target
	 * @return 
	 */
	public String getTarget();
	/** 
	 * 设置target
	 * @param target 
	 */
	public void setTarget(String target);
	
}
