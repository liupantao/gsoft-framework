package com.gsoft.framework.security;

import com.gsoft.framework.core.web.menu.MenuProvider;

public interface IRealmMenuProvider extends MenuProvider {
	public void loadMenu(IRealmUserInfo paramIRealmUserInfo, IUserAdapter<IUser> paramIUserAdapter);
}