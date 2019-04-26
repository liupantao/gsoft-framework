package com.gsoft.framework.security;

/**
 * AbstractFormUserAdapter
 * @author liupantao
 * @date 2017年10月16日
 *
 * @param <T>  
 */
public abstract class AbstractFormUserAdapter<T extends IUser> implements IUserAdapter<T> {
	
	@Override
	public boolean supports(IRealmUserToken token) {
		return DefaultLoginFormToken.class.isAssignableFrom(token.getClass());
	}
	
}