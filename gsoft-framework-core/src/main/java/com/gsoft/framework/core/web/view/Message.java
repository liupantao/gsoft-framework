package com.gsoft.framework.core.web.view;

import java.io.Serializable;

/**
 * 返回消息
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class Message implements Serializable {
	private static final long serialVersionUID = -694991823003131043L;
	/**
	 * @Fields info : 消息信息
	 */
	public String info;
	/**
	 * @Fields code : 消息代码 
	 */
	public String code;

	
	public Message(String info) {
		this.code = "000000";
		this.info = info;
	}
	
	public Message(String code, String info) {
		this.code = code;
		this.info = info;
	}

	public Message() {
		this.code = "000000";
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Message [info=" + this.info + ", code=" + this.code + "]";
	}
}