package com.gsoft.framework.security;

import com.gsoft.framework.core.dataobj.Domain;

/**
 * 用户接口
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public interface IUser extends AccountPrincipal,IdUser,Domain {
	
	/** 
	 * 获取密码
	 * @return 
	 */
	public String getPassword();
}