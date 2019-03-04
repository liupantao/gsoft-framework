package com.gsoft.framework.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.MutablePrincipalCollection;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * AbstractFormUserInfo
 * 
 * @author liupantao
 * @date 2017年10月16日
 * 
 */
public abstract class AbstractFormUserInfo implements IRealmUserInfo {
	private static final long serialVersionUID = 8664644598081045414L;
	protected IUser user;
	private PrincipalCollection principals;
	private Object credentials;
	private ByteSource credentialsSalt;
	private String tokenCode;
	private String loginType;

	public String getTokenCode() {
		return tokenCode;
	}

	public void setTokenCode(String tokenCode) {
		this.tokenCode = tokenCode;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public AbstractFormUserInfo() {
	}

	public AbstractFormUserInfo(IUser user, Object credentials) {
		this.principals = new SimplePrincipalCollection(user, FormLoginRealm.class.getName());
		this.credentials = credentials;
		this.user = user;
	}

	public AbstractFormUserInfo(IUser user, Object hashedCredentials, ByteSource credentialsSalt, String realmName) {
		this.principals = new SimplePrincipalCollection(user, realmName);
		this.credentials = hashedCredentials;
		this.credentialsSalt = credentialsSalt;
		this.user = user;
	}

	@Override
	public PrincipalCollection getPrincipals() {
		return this.principals;
	}

	public void setPrincipals(PrincipalCollection principals) {
		this.principals = principals;
	}

	@Override
	public Object getCredentials() {
		return this.credentials;
	}

	public void setCredentials(Object credentials) {
		this.credentials = credentials;
	}

	@Override
	public ByteSource getCredentialsSalt() {
		return this.credentialsSalt;
	}

	public void setCredentialsSalt(ByteSource salt) {
		this.credentialsSalt = salt;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void merge(AuthenticationInfo info) {
		if ((info == null) || (info.getPrincipals() == null) || (info.getPrincipals().isEmpty())) {
			return;
		}

		if (this.principals == null) {
			this.principals = info.getPrincipals();
		} else {
			if (!(this.principals instanceof MutablePrincipalCollection)) {
				this.principals = new SimplePrincipalCollection(this.principals);
			}
			((MutablePrincipalCollection) this.principals).addAll(info.getPrincipals());
		}

		if ((this.credentialsSalt == null) && (info instanceof SaltedAuthenticationInfo)) {
			this.credentialsSalt = ((SaltedAuthenticationInfo) info).getCredentialsSalt();
		}

		Object thisCredentials = getCredentials();
		Object otherCredentials = info.getCredentials();

		if (otherCredentials == null) {
			return;
		}

		if (thisCredentials == null) {
			this.credentials = otherCredentials;
			return;
		}

		if (!(thisCredentials instanceof Collection)) {
			Set<Object> newSet = new HashSet<Object>();
			newSet.add(thisCredentials);
			setCredentials(newSet);
		}

		Collection credentialCollection = (Collection) getCredentials();
		if (otherCredentials instanceof Collection) {
			credentialCollection.addAll((Collection) otherCredentials);
		} else {
			credentialCollection.add(otherCredentials);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AbstractFormUserInfo)) {
			return false;
		}

		AbstractFormUserInfo that = (AbstractFormUserInfo) o;

		if (this.principals != null) {
			if (this.principals.equals(that.principals)) {
				return true;
			} else if (that.principals == null) {
				return false;
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		return (this.principals != null) ? this.principals.hashCode() : 0;
	}

	@Override
	public String toString() {
		return this.principals.toString();
	}

	@Override
	public Collection<String> getRoles() {
		return this.user.roleIds();
	}

	@Override
	public Collection<String> getStringPermissions() {
		return null;
	}

	@Override
	public Collection<Permission> getObjectPermissions() {
		return null;
	}

	@Override
	public IUser getUser() {
		return this.user;
	}

	public void setUser(IUser user) {
		this.user = user;
	}
}