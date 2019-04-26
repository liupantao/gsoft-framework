/*
 * YOUI框架
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gsoft.framework.core.log;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gsoft.framework.remote.data.ReqContext;
import com.gsoft.framework.remote.data.ResContext;

/**
 * 交易日志
 * @author liupantao
 *
 */
public interface TransLogService {

	/** 
	 * 写交易日记
	 * @param flowno
	 * @param trancode
	 * @param tranname
	 * @param serviceCaption
	 * @param rescode
	 * @param logMsg
	 * @param time
	 * @param ip
	 * @param req
	 * @param res 
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void writeLog(LogInfo logInfo, ReqContext<?> req, ResContext<?> res);
	
}
