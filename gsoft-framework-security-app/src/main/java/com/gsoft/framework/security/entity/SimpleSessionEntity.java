package com.gsoft.framework.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gsoft.framework.core.dataobj.Domain;

/**
 * session
 * 
 * @author liupantao
 * @date 2018年4月18日
 * 
 */
@Entity
@Table(name = "YOUI_SESSION")
public class SimpleSessionEntity implements Domain {

	private static final long serialVersionUID = 1065501290608635427L;
	/**
	 * @Fields ID
	 */
	@Id
	@Column(name = "SESSION_ID")
	private String id;

	@Column(name = "SESSION_CONTENT")
    private String session;  
    
	@Column(name = "USER_NAME")
	private String username;

	@Column(name = "CREATE_TIME")
	private String createTime;

	@Column(name = "UPDATE_TIME")
	private String updateTime;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the session
	 */
	public String getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(String session) {
		this.session = session;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}