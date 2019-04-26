package com.gsoft.framework.security;

import java.util.List;

import com.gsoft.framework.core.dataobj.tree.HtmlTreeNode;
import com.gsoft.framework.core.dataobj.tree.TreeNode;

public interface UserService {
	public IRealmUserInfo getRealmUserInfo(IUser paramIUser);

	public IRealmUserInfo getRealmUserInfo(IRealmUserToken paramIRealmUserToken, boolean paramBoolean);

	public HtmlTreeNode getCachedMenuTree(IUser paramIUser);

	public IUserAdapter<IUser> getUserAdapter(String paramString);

	public boolean modifyPassword(IUser user, String password, String confirmPassword, String oldPassword);

	public TreeNode getAgencyTree(IUser user);

	public List<IAgency> getAgencyByParent(IUser paramIUser, String paramString);

	public void clearMenuCache();
}