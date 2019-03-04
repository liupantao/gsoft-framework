package com.gsoft.framework.security.service;

import java.util.List;

import com.gsoft.framework.security.vo.MenuTreeVo;

/**
 * @author liupantao
 *
 */
public interface SecurityService {
	
	/**
	 * 获取用户菜单
	 * @return
	 */
	public List<MenuTreeVo> getUserMenus();
	
}
