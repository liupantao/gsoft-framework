package com.gsoft.framework.security.dev;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.gsoft.framework.core.dataobj.tree.TreeNode;
import com.gsoft.framework.core.web.menu.IMenu;
import com.gsoft.framework.core.web.menu.XmlMenuLoader;
import com.gsoft.framework.security.AbstractFormUserAdapter;
import com.gsoft.framework.security.IAgency;
import com.gsoft.framework.security.IRealmUserInfo;
import com.gsoft.framework.security.IRealmUserToken;
import com.gsoft.framework.security.IUser;
import com.gsoft.framework.util.PasswordUtils;

/**
 * 开发用户 服务
 * 
 * @author liupantao
 * @date 2017年10月16日
 * 
 */
public class DevUserAdapter extends AbstractFormUserAdapter<DevUser> implements ApplicationContextAware {
	private List<IMenu> providerMenus;
	private ApplicationContext applicationContext;
	private String menuResource;

	public final static String DEV_USER_PREFIX = "demo-";

	public DevUserAdapter() {
		this.menuResource = "classpath:configs/menu.xml";
	}

	@Override
	public boolean supports(IRealmUserToken token) {
		return (super.supports(token)) && (token.getUsername().startsWith(DEV_USER_PREFIX));
	}

	@Override
	public IRealmUserInfo getRealmUserInfo(IRealmUserToken token) {
		DevUser user = new DevUser();
		user.setLoginName(token.getUsername());

		DevUserInfo realmUserInfo = new DevUserInfo(user, PasswordUtils.md5Password("123456"));

		return realmUserInfo;
	}

	@Override
	public IRealmUserInfo getRealmUserInfo(DevUser user) {
		DevUserInfo realmUserInfo = new DevUserInfo(user, PasswordUtils.md5Password("123456"));

		return realmUserInfo;
	}

	@Override
	public List<IMenu> getProviderMenus(DevUser user) {
		XmlMenuLoader xmlMenuLoader = new XmlMenuLoader(this.applicationContext);
		return xmlMenuLoader.getMenus(this.menuResource);
	}

	@Override
	public List<String> getAccountMenus(DevUser user) {
		List<String> accountMenus = new ArrayList<String>();
		if (this.providerMenus == null) {
			this.providerMenus = getProviderMenus(user);
		}

//		String username = user.getLoginName();
//		String filterName = null;
//		if (username.startsWith(DEV_USER_PREFIX)) {
//			filterName = username.substring(DEV_USER_PREFIX.length());
//		}

		for (IMenu menu : this.providerMenus) {
//			if ((StringUtils.isNotEmpty(filterName)) && (StringUtils.isNotEmpty(menu.getTarget()))
//					&& ((" " + menu.getTarget()).indexOf(" " + filterName) == -1)) {
//				continue;
//			}

			accountMenus.add(menu.getMenuId());
		}
		return accountMenus;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public TreeNode getAgencyTree() {
		return null;
	}

	public List<IAgency> getAgencyByParent(String parentAgencyId) {
		return null;
	}

	@Override
	public boolean supports(IUser user) {
		return DevUser.class.isAssignableFrom(user.getClass());
	}

}