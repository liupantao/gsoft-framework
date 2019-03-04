/**
 * 
 */
package com.gsoft.framework.security;

/**
 * 角色接口
 * @author zhyi_12
 */
public interface IRole {
	/** 
	 * 获取角色id
	 * @return 
	 */
	public String getRoleId();

	/**
	 * @param roleId the roleId to set
	 */
	/** 
	 * 设置角色id
	 * @param roleId 
	 */
	public void setRoleId(String roleId);

	/** 
	 * 获取角色描述
	 * @return 
	 */
	public String getRoleCaption();

	/**
	 * @param roleCaption the roleCaption to set
	 */
	/** 
	 * 设置角色描述
	 * @param roleCaption 
	 */
	public void setRoleCaption(String roleCaption);
}
