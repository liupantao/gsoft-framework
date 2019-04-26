package com.gsoft.framework.job.dao;

import com.gsoft.framework.job.entity.SchedulerJob;
import com.gsoft.framework.job.entity.SchedulerJobKey;
import com.gsoft.framework.jpa.dao.JpaDao;

/**
 * 
 * @author liupantao
 *
 */
public interface SchedulerJobDao extends JpaDao<SchedulerJob, SchedulerJobKey> {
	
}