package com.gsoft.framework.security;

import java.util.List;

import com.gsoft.framework.core.web.menu.IMenu;

/**
 * 
 * @author liupantao
 *
 * @param <T>
 */
/**
 * 用户适配器接口
 * @author liupantao
 * @date 2017年10月16日
 *
 * @param <T>  
 */
public interface IUserAdapter<T extends IUser> {
	
	/** 
	 * 是否支持 
	 * @param token
	 * @return 
	 */
	public boolean supports(IRealmUserToken token);

	/** 
	 * 是否支持 
	 * @param user
	 * @return 
	 */
	public boolean supports(IUser user);

	/** 
	 * 获取用户描述类
	 * @param token
	 * @return 
	 */
	public IRealmUserInfo getRealmUserInfo(IRealmUserToken token);

	/** 
	 * 获取用户描述类
	 * @param user
	 * @return 
	 */
	public IRealmUserInfo getRealmUserInfo(T user);

	/** 
	 * 获取菜单集合
	 * @param user
	 * @return 
	 */
	public List<IMenu> getProviderMenus(T user);

	/** 
	 * 获取用户授权菜单
	 * @param user
	 * @return 
	 */
	public List<String> getAccountMenus(T user);

}