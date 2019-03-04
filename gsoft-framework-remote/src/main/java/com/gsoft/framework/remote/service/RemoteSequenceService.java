package com.gsoft.framework.remote.service;

import java.io.Serializable;

/**
 * 流水号生成器
 * @author liupantao
 *
 */
public interface RemoteSequenceService {

	/**
	 * 生成序号
	 * @return
	 */
	public Serializable generate();
}
