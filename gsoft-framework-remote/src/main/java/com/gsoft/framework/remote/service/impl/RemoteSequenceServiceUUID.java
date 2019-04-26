/**
 * 
 */
package com.gsoft.framework.remote.service.impl;

import java.util.UUID;

import com.gsoft.framework.remote.service.RemoteSequenceService;

/**
 * UUID流水号生成器
 * @author liupantao
 *
 */
public class RemoteSequenceServiceUUID implements RemoteSequenceService{

	public synchronized String generate(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
