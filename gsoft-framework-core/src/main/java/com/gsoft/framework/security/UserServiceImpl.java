package com.gsoft.framework.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.util.StringUtils;

import com.gsoft.framework.core.dataobj.tree.HtmlTreeNode;
import com.gsoft.framework.core.dataobj.tree.TreeNode;
import com.gsoft.framework.core.dataobj.tree.TreeUtils;
import com.gsoft.framework.core.exception.BusException;
import com.gsoft.framework.core.web.menu.IMenu;

/**
 * 用户服务
 * 
 * @author liupantao
 * @date 2017年10月16日
 * 
 */
public class UserServiceImpl implements UserService, ApplicationContextAware {
	private static final Log logger = LogFactory.getLog(UserServiceImpl.class);
	private List<IUserAdapter<IUser>> userAdapters;
	@SuppressWarnings("rawtypes")
	private Map<String, IUserAdapter> userAdapterBeans;
	private CacheManager cacheManager;
	private static final String MENU_CACHE = "com.gsoft.framework.security.UserService_menuCache";

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	/**
	 * 获取用户信息
	 */
	@Override
	public IRealmUserInfo getRealmUserInfo(IUser user) {
		if (this.userAdapters != null) {
			for (IUserAdapter<IUser> adapter : this.userAdapters) {
				if (adapter.supports(user)) {
					IRealmUserInfo info = adapter.getRealmUserInfo(user);
					if (info != null) {
						return info;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 获取用户信息并加载菜单
	 */
	@Override
	public IRealmUserInfo getRealmUserInfo(IRealmUserToken token, boolean loadMenus) {
		if (this.userAdapters != null) {
			for (IUserAdapter<IUser> adapter : this.userAdapters) {
				if (adapter.supports(token)) {
					IRealmUserInfo userInfo = adapter.getRealmUserInfo(token);
					if (userInfo != null) {
						if (loadMenus) {
							loadMenuTreeToCache(adapter, userInfo.getUser());
						}
						return userInfo;
					}
				}
			}
		}
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		initRealmProviders(applicationContext);
	}

	/**
	 * 初始化适配用户
	 * 
	 * @param context
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initRealmProviders(ApplicationContext context) {
		if (this.userAdapters == null) {

			this.userAdapterBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, IUserAdapter.class, true,
					false);

			if (this.userAdapterBeans != null) {
				this.userAdapters = new ArrayList(this.userAdapterBeans.values());
				Collections.sort(this.userAdapters, new OrderComparator());
			}
		}
	}

	/**
	 * 加载缓存菜单树
	 * 
	 * @param userAdapter
	 * @param user
	 * @return
	 */
	private HtmlTreeNode loadMenuTreeToCache(IUserAdapter<IUser> userAdapter, IUser user) {
		Cache menuCache = getMenuCache();
		if (menuCache != null) {
			String menuKey = getMenuKey(user);
			HtmlTreeNode treeNode = menuCache.get(menuKey, HtmlTreeNode.class);
			if (treeNode == null) {
				treeNode = buildMenuTree(userAdapter, user);
				menuCache.put(menuKey, treeNode);
			}
			return treeNode;
		}
		return null;
	}

	/**
	 * 构造菜单树
	 * 
	 * @param userAdapter
	 * @param user
	 * @return
	 */
	private HtmlTreeNode buildMenuTree(IUserAdapter<IUser> userAdapter, IUser user) {
		List<IMenu> allMenus = userAdapter.getProviderMenus(user);
		List<String> menuIds = userAdapter.getAccountMenus(user);
		List<IMenu> userMenus = new ArrayList<IMenu>();
		if (allMenus != null) {
			for (IMenu menu : allMenus) {
				if (menuIds.contains(menu.getMenuId())) {
					userMenus.add(menu);
				}
			}
		}
		HtmlTreeNode tree = TreeUtils.listToHtmlTree(userMenus, null, "系统菜单");
		tree.setId("TREE_SYS_MENUID");
		return tree;
	}

	/**
	 * 获取菜单树html
	 */
	@Override
	public HtmlTreeNode getCachedMenuTree(IUser user) {
		Cache menuCache = getMenuCache();
		HtmlTreeNode treeNode = null;
		String menuKey;
		if (menuCache != null) {
			menuKey = getMenuKey(user);
			treeNode = menuCache.get(menuKey, HtmlTreeNode.class);
			if ((treeNode == null) && (this.userAdapters != null)) {
				for (IUserAdapter<IUser> adapter : this.userAdapters) {
					if (adapter.supports(user)) {
						IRealmUserInfo info = adapter.getRealmUserInfo(user);
						if (info != null) {
							treeNode = loadMenuTreeToCache(adapter, user);
							menuCache.put(menuKey, treeNode);
							break;
						}
					}
				}
			}
		}

		return treeNode;
	}

	/**
	 * 获取缓存
	 * 
	 * @return
	 */
	private Cache getMenuCache() {
		if (this.cacheManager == null) {
			return null;
		}
		return this.cacheManager.getCache(MENU_CACHE);
	}

	/**
	 * 获取用户menuKey
	 * 
	 * @param user
	 * @return
	 */
	private String getMenuKey(IUser user) {
		List<String> roles = new ArrayList<String>(user.roleIds());
		Collections.sort(roles);
		return StringUtils.collectionToCommaDelimitedString(roles);
	}

	@Override
	public void clearMenuCache() {
		Cache menuCache = getMenuCache();
		if (menuCache == null) {
			return;
		}
		menuCache.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public IUserAdapter<IUser> getUserAdapter(String beanName) {
		if (StringUtils.isEmpty(beanName)) {
			return null;
		}
		return this.userAdapterBeans.get(beanName);
	}

	/**
	 * 修改密码
	 */
	@Override
	public boolean modifyPassword(IUser account, String password, String confirmPassword, String oldPassword) {
		if (this.userAdapters != null) {
			for (IUserAdapter<IUser> adapter : this.userAdapters) {
				if (adapter.supports(account)) {
					if (adapter instanceof IPasswordService) {
						((IPasswordService) adapter).modifyPassword(account.getLoginName(), password, confirmPassword,
								oldPassword);
						break;
					}
					logger.warn(adapter.getClass() + "未实现IPasswordService接口.");
					throw new BusException("未实现IPasswordService接口,不提供修改密码服务");
				}
			}
		}
		return true;
	}

	@Override
	public TreeNode getAgencyTree(IUser user) {
		if (this.userAdapters != null) {
			for (IUserAdapter<IUser> adapter : this.userAdapters) {
				if (adapter.supports(user)) {
					// return adapter.getAgencyTree();
				}
			}
		}
		return null;
	}

	@Override
	public List<IAgency> getAgencyByParent(IUser user, String parentAgencyId) {
		if (this.userAdapters != null) {
			for (IUserAdapter<IUser> adapter : this.userAdapters) {
				if (adapter.supports(user)) {
					// return adapter.getAgencyByParent(parentAgencyId);
				}
			}
		}
		return null;
	}
}