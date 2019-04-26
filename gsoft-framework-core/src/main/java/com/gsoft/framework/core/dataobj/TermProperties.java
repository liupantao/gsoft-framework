/**
 * 
 */
package com.gsoft.framework.core.dataobj;

/**
 * 模糊term
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class TermProperties implements Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8856134487275173156L;
	
	/**
	 * @Fields codeProperty : 条件匹配为以输入的term开始
	 */
	private String codeProperty;
	
	/**
	 * @Fields textProperty : 条件匹配任意 
	 */
	private String textProperty;
	
	public TermProperties(String codeProperty, String textProperty) {
		super();
		this.codeProperty = codeProperty;
		this.textProperty = textProperty;
	}

	public String getCodeProperty() {
		return codeProperty;
	}

	public void setCodeProperty(String codeProperty) {
		this.codeProperty = codeProperty;
	}

	public String getTextProperty() {
		return textProperty;
	}

	public void setTextProperty(String textProperty) {
		this.textProperty = textProperty;
	}
	
	
}
