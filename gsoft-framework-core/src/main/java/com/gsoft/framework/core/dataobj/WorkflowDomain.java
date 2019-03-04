package com.gsoft.framework.core.dataobj;

import com.gsoft.framework.core.dataobj.Domain;

/**
 * 工作流实体接口
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public interface WorkflowDomain extends Domain{

	/** 
	 * 获取业务ID
	 * @return 
	 */
	String getBusinessKey();

}
