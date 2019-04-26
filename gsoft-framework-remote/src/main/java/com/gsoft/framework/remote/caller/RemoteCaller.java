package com.gsoft.framework.remote.caller;

import com.gsoft.framework.remote.data.ReqContext;
import com.gsoft.framework.remote.data.ResContext;

/**
 * 调用远程服务
 * 
 * @author liupantao
 * 
 */
public interface RemoteCaller {

	/**
	 * @param channel
	 *            通道
	 * @param adapterChannel
	 *            适配器通道,可选参数
	 * @param servicesName
	 *            接口名
	 * @param tranName
	 *            交易名
	 * @param req
	 *            请求参数
	 * @return
	 */
	public ResContext<?> callRemoteService(String channel, String adapterChannel, String servicesName, String methodName,
			ReqContext<?> req);

	/**
	 * 
	 * @param channel
	 * @param servicesName
	 * @param tranName
	 * @param req
	 * @return
	 */
	public ResContext<?> callRemoteService(String channel,String servicesName, String methodName, ReqContext<?> req);
	
	/**
	 * @param tranName
	 * @param req
	 * @return
	 */
	public ResContext<?> callRemoteService(String servicesName, String methodName, ReqContext<?> req);

}
