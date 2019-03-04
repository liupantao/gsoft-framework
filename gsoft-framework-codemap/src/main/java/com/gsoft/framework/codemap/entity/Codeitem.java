/*
 * 
 */
package com.gsoft.framework.codemap.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import com.gsoft.framework.core.dataobj.Domain;

/**
 * 代码集-数据字典
 * @author liupantao
 *
 */
@Entity
@Table(name = "YOUI_CODEITEM")
public class Codeitem implements Domain{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8743600518640643407L;

	/**
	 * @Fields itemValue : 代码值 
	 */
	@Column(name = "ITEM_VALUE")
	@Length(max=512)
	private String itemValue;

	/**
	 * @Fields itemCaption : 代码描述
	 */
	@Column(name = "ITEM_CAPTION")
	@Length(max=80)
	private String itemCaption;
	/**
	 * @Fields itemId : 代码ID
	 */
	@Id 
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid.hex")
	@Column(name = "ITEM_ID")
	@Length(max=32)
	private String itemId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Codemap_ID")
	private Codemap codemap;
	
	/**
	 * @Fields itemGroup : 代码值
	 */
	@Column(name = "ITEM_GROUP")
	@Length(max=512)
	private String itemGroup;
	
	/**
	 * @Fields 排序
	 */
	@Column(name = "ITEM_INDEX")
	@Length(max=3)
	private String index;
	
	/**
	 * @return the codemap
	 */
	public Codemap getCodemap() {
		return codemap;
	}

	/**
	 * @param codemap the codemap to set
	 */
	public void setCodemap(Codemap codemap) {
		this.codemap = codemap;
	}

	public String getItemValue(){
		return this.itemValue;
	}
	
	public void setItemValue(String itemValue){
		this.itemValue = itemValue;
	}
	public String getItemCaption(){
		return this.itemCaption;
	}
	
	public void setItemCaption(String itemCaption){
		this.itemCaption = itemCaption;
	}
	public String getItemId(){
		return this.itemId;
	}
	
	public void setItemId(String itemId){
		this.itemId = itemId;
	}
	
	public String getItemGroup() {
		return itemGroup;
	}

	public void setItemGroup(String itemGroup) {
		this.itemGroup = itemGroup;
	}
	
	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
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
		result = prime * result + ((codemap == null) ? 0 : codemap.hashCode());
		result = prime * result + ((itemCaption == null) ? 0 : itemCaption.hashCode());
		result = prime * result + ((itemGroup == null) ? 0 : itemGroup.hashCode());
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + ((itemValue == null) ? 0 : itemValue.hashCode());
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
		Codeitem other = (Codeitem) obj;
		if (codemap == null) {
			if (other.codemap != null) {
				return false;
			}
		} else if (!codemap.equals(other.codemap)) {
			return false;
		}
		if (itemCaption == null) {
			if (other.itemCaption != null) {
				return false;
			}
		} else if (!itemCaption.equals(other.itemCaption)) {
			return false;
		}
		if (itemGroup == null) {
			if (other.itemGroup != null) {
				return false;
			}
		} else if (!itemGroup.equals(other.itemGroup)) {
			return false;
		}
		if (itemId == null) {
			if (other.itemId != null) {
				return false;
			}
		} else if (!itemId.equals(other.itemId)) {
			return false;
		}
		if (itemValue == null) {
			if (other.itemValue != null) {
				return false;
			}
		} else if (!itemValue.equals(other.itemValue)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString(){
		return super.toString();
	}
}