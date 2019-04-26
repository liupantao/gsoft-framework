package com.gsoft.framework.job;

/**
 * 
 * @author liupantao
 * 
 */
public abstract class JobTask {

	public final String getName() {
		return this.getClass().getName();
	}

	public abstract String getCronExpression();

	public boolean getDurability() {
		return true;
	}

	public boolean getRequestsRecovery() {
		return true;
	}

	public abstract void execute();

	public abstract String getCronDescription();

	public abstract String getJobDescription();

}
