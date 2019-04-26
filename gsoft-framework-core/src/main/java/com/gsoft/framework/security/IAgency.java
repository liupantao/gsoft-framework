package com.gsoft.framework.security;

/**
 * TODO (描述该文件做什么)
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public interface IAgency {
	/** 
	 * TODO(描述这个方法的作用) 
	 * @return 
	 */
	public String getAgencyId();

	/** 
	 * TODO(描述这个方法的作用) 
	 * @return 
	 */
	public String getParentAgencyId();

	/** 
	 * TODO(描述这个方法的作用) 
	 * @param paramString 
	 */
	public void setAgencyCaption(String paramString);
}