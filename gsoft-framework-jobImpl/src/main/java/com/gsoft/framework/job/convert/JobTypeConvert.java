package com.gsoft.framework.job.convert;

import org.springframework.stereotype.Component;

import com.gsoft.framework.core.convert.AbstractConvert;
import com.gsoft.framework.core.dataobj.Record;

/**
 * 
 * @author liupantao
 *
 */
@Component
public class JobTypeConvert extends AbstractConvert {

	private static final long serialVersionUID = 9137918397468733197L;
	
	public static final String DBJOB = "DBJobTask";
	public static final String USERJOB = "UserJobTask";
	public static final String AUTOJOB = "AutoJobTask";
	
	public String getName() {
		return "GSOFT_JOB_TYPE";
	}

	protected void initRecord(Record record) {
		record.put(DBJOB, "通用调度");
		record.put(USERJOB, "自定义调度");
		record.put(AUTOJOB, "内置调度");
	}
}