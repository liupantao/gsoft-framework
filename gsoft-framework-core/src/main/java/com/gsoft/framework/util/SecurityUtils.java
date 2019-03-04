package com.gsoft.framework.util;

import com.gsoft.framework.security.AccountPrincipal;

/**
 * SecurityUtils
 * @author liupantao
 * @date 2017年10月17日
 *  
 */
public class SecurityUtils {

	
	/**
	 * @return
	 */
	public static Object getPrincipal(){
		return  org.apache.shiro.SecurityUtils.getSubject().getPrincipal();
	}
	
	/**
	 * @return
	 */
	public static AccountPrincipal getAccount(){
		Object principal;
		try {
			principal = getPrincipal();
		} catch (Exception e) {
			principal = null;
		}
		if(principal!=null&&AccountPrincipal.class.isAssignableFrom(principal.getClass())){
			return (AccountPrincipal)principal;
		}
		return null;
	}
}
