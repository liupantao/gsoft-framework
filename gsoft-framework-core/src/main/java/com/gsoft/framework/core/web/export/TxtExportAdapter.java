package com.gsoft.framework.core.web.export;

import java.io.OutputStream;
import org.springframework.stereotype.Component;

/**
 * 导出txt适配器
 * 
 * @author liupantao
 * 
 */
@Component
public class TxtExportAdapter implements WebExportAdapter<TxtWebExporter> {
	
	@Override
	public boolean supports(String type) {
		return "txt".equals(type);
	}

	@Override
	public TxtWebExporter openExporter(OutputStream outputStream, String type) {
		return new TxtWebExporter(outputStream);
	}
}