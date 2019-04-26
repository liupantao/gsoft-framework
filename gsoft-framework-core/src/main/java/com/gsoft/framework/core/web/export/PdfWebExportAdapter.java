package com.gsoft.framework.core.web.export;

import java.io.OutputStream;
import org.springframework.stereotype.Component;

/**
 * 导出pdf适配器
 * @author liupantao
 *
 */
@Component
public class PdfWebExportAdapter implements WebExportAdapter<PdfWebExporter> {
	
	@Override
	public boolean supports(String type) {
		return "pdf".equals(type);
	}

	@Override
	public PdfWebExporter openExporter(OutputStream outputStream, String type) {
		return new PdfWebExporter(outputStream);
	}
}