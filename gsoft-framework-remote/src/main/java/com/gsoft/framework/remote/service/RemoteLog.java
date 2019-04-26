package com.gsoft.framework.remote.service;

import com.gsoft.framework.core.log.LogInfo;
import com.gsoft.framework.remote.data.ReqContext;
import com.gsoft.framework.remote.data.ResContext;

/**
 * 远程调用日志
 * 
 * @author liupantao
 * 
 */
public interface RemoteLog {

	public void writeWebLog(String userName, String flowno, String serviceCaption, ReqContext<?> req,
			ResContext<?> res);

	public void writeLog(LogInfo logInfo, ReqContext<?> req, ResContext<?> res);

}
