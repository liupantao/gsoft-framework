package com.gsoft.framework.core.web.export;

import com.gsoft.framework.core.orm.PagerRecords;
import java.io.OutputStream;
import org.springframework.http.HttpHeaders;

/**
 * 导出服务
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public interface WebExportService {
	
	/** 
	 * 写记录
	 * @param paramOutputStream
	 * @param paramString
	 * @param paramPagerRecords
	 * @return 
	 */
	public HttpHeaders writePagerRecords(OutputStream paramOutputStream, String paramString, PagerRecords paramPagerRecords);

}