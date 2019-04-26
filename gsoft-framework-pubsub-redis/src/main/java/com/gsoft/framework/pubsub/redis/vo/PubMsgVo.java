/*
 * Gsoft开发框架
 * Copyright 2015-2020 the original author or authors.
 */

package com.gsoft.framework.pubsub.redis.vo;

import java.io.Serializable;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.gsoft.framework.core.dataobj.Domain;
import com.gsoft.framework.core.exception.BusException;

/**
 * 广播消息映射对象
 * 
 * @author liupantao
 * @date 2018年7月13日
 * 
 */
public class PubMsgVo implements Serializable {

	private static final long serialVersionUID = -7588207506737114854L;

	/**
	 * @Fields 消息id
	 */
	private String msgId;

	/**
	 * @Fields 消息数据
	 */
	private String data;

	/**
	 * 创建一个新的实例 PubMsgVo.
	 * 
	 */
	public PubMsgVo() {
	}

	/**
	 * 创建一个新的实例 PubMsgVo.
	 * 
	 */
	public PubMsgVo(Domain data) {
		if (data == null) {
			throw new BusException("广播数据不能为空");
		}
		this.msgId = UUID.randomUUID().toString();
		this.data = JSON.toJSONString(data);
	}

	/**
	 * @return the msgId
	 */
	public String getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId
	 *            the msgId to set
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

}
