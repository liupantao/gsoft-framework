package com.gsoft.framework.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsoft.framework.core.dataobj.tree.HtmlTreeNode;
import com.gsoft.framework.core.dataobj.tree.TreeNode;
import com.gsoft.framework.remote.annotation.ServiceMapping;
import com.gsoft.framework.security.AccountPrincipal;
import com.gsoft.framework.security.IUser;
import com.gsoft.framework.security.UserService;
import com.gsoft.framework.security.service.SecurityService;
import com.gsoft.framework.security.vo.MenuTreeVo;
import com.gsoft.framework.util.SecurityUtils;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private UserService userService;

	@Override
	@ServiceMapping(trancode = "", caption = "获取用户菜单", log = false)
	public List<MenuTreeVo> getUserMenus() {
		List<MenuTreeVo> menuTee = new ArrayList<MenuTreeVo>();
		AccountPrincipal account = SecurityUtils.getAccount();
		if (account != null && account instanceof IUser) {
			HtmlTreeNode menuTree = userService.getCachedMenuTree((IUser) account);
			if (menuTree != null) {
				List<TreeNode> children = menuTree.getChildren();
				for (TreeNode treeNode : children) {
					MenuTreeVo menuTreeVo = buildMenuTreeVo(treeNode);
					menuTee.add(menuTreeVo);
				}
			}
		}
		return menuTee;
	}

	private MenuTreeVo buildMenuTreeVo(TreeNode treeNode) {
		MenuTreeVo menuTreeVo = new MenuTreeVo(treeNode);
		if (treeNode.getChildren() != null && treeNode.getChildren().size() > 0) {
			List<MenuTreeVo> childrenMenus = new ArrayList<>();
			List<TreeNode> children = treeNode.getChildren();
			for (TreeNode c : children) {
				MenuTreeVo cMenuTreeVo = buildMenuTreeVo(c);
				childrenMenus.add(cMenuTreeVo);
			}
			menuTreeVo.setChildren(childrenMenus);
		}
		return menuTreeVo;
	}

}
