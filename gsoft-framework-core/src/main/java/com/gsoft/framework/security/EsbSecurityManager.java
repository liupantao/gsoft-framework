package com.gsoft.framework.security;

import com.gsoft.framework.remote.data.SecurityRecord;

/**
 * TODO (描述该文件做什么)
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public interface EsbSecurityManager {
	/** 
	 * TODO(描述这个方法的作用) 
	 * @param paramString
	 * @return 
	 */
	public SecurityRecord decryptSecurityInfo(String paramString);

	/** 
	 * TODO(描述这个方法的作用) 
	 * @param paramSecurityRecord
	 * @return 
	 */
	public String encryptSecurityInfo(SecurityRecord paramSecurityRecord);

	/** 
	 * TODO(描述这个方法的作用) 
	 * @return 
	 */
	public String getSecurityKey();
}