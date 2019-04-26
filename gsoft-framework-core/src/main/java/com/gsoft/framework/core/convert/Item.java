package com.gsoft.framework.core.convert;

import com.gsoft.framework.core.dataobj.Domain;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 代码集Item
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
@Entity
public class Item implements Domain {
	private static final long serialVersionUID = -5549107218594937811L;

	@Id
	private String code;
	private String show;
	private String group;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getShow() {
		return this.show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	/* (非 Javadoc)  
	 * <p>Title: hashCode</p>  
	 * <p>Description: </p>  
	 * @return  
	 * @see java.lang.Object#hashCode()  
	 */  
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((show == null) ? 0 : show.hashCode());
		return result;
	}

	/* (非 Javadoc)  
	 * <p>Title: equals</p>  
	 * <p>Description: </p>  
	 * @param obj
	 * @return  
	 * @see java.lang.Object#equals(java.lang.Object)  
	 */  
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Item other = (Item) obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		if (group == null) {
			if (other.group != null) {
				return false;
			}
		} else if (!group.equals(other.group)) {
			return false;
		}
		if (show == null) {
			if (other.show != null) {
				return false;
			}
		} else if (!show.equals(other.show)) {
			return false;
		}
		return true;
	}

}