/*
 * 
 */
package com.gsoft.framework.codemap.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import com.gsoft.framework.core.dataobj.Domain;

/**
 * 实体 代码集
 * @author liupantao
 * @date 2017年10月17日
 *  
 */
@Entity
@Table(name = "YOUI_CODEMAP")
public class Codemap implements Domain{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4412783077750845000L;
	
	
	/**
	 * @Fields codemapId : 代码集ID
	 */
	@Id 
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid.hex")
	@Column(name = "CODEMAP_ID")
	@Length(max=32)
	private String codemapId;

	/**
	 * @Fields type : 代码集类型
	 */
	@Column(name = "TYPE")
	@Length(max=20)
	private String type;

	/**
	 * @Fields code : 代码集编码 
	 */
	@Column(name = "CODE")
	@Length(max=40)
	private String code;

	/**
	 * @Fields caption : 代码集描述
	 */
	@Column(name = "CAPTION")
	@Length(max=80)
	private String caption;
	
	/**
	 * @Fields expression : 值表达式
	 */
	@Column(name = "EXPRESSION")
	@Length(max=1000)
	private String expression;
	
	public String getExpression(){
		return this.expression;
	}
	
	public void setExpression(String expression){
		this.expression = expression;
	}
	public String getCodemapId(){
		return this.codemapId;
	}
	
	public void setCodemapId(String codemapId){
		this.codemapId = codemapId;
	}
	public String getType(){
		return this.type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	public String getCode(){
		return this.code;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	public String getCaption(){
		return this.caption;
	}
	
	public void setCaption(String caption){
		this.caption = caption;
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
		result = prime * result + ((caption == null) ? 0 : caption.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((codemapId == null) ? 0 : codemapId.hashCode());
		result = prime * result + ((expression == null) ? 0 : expression.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Codemap other = (Codemap) obj;
		if (caption == null) {
			if (other.caption != null) {
				return false;
			}
		} else if (!caption.equals(other.caption)) {
			return false;
		}
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		if (codemapId == null) {
			if (other.codemapId != null) {
				return false;
			}
		} else if (!codemapId.equals(other.codemapId)) {
			return false;
		}
		if (expression == null) {
			if (other.expression != null) {
				return false;
			}
		} else if (!expression.equals(other.expression)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString(){
		return super.toString();
	}
}