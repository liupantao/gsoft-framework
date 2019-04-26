package com.gsoft.framework.core.web.export;

import java.io.IOException;
import java.io.OutputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * TODO (描述该文件做什么)
 * 
 * @author liupantao
 * @date 2017年10月16日
 * 
 */
public class TxtWebExporter implements WebExporter {
	private static final String LINE_SPLIT = System.getProperty("line.separator");
	private OutputStream outputStream;

	public TxtWebExporter(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	@Override
	public void close() {
		try {
			if (this.outputStream != null) {
				this.outputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writeLine(int rowIndex, Object[] rowData) {
		StringBuilder builder = new StringBuilder();

		for (Object data : rowData) {
			builder.append(data).append(",");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append(LINE_SPLIT);
		try {
			this.outputStream.write(builder.toString().getBytes());
		} catch (IOException e) {
		}
	}

	@Override
	public HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.add("Content-disposition", "attachment; filename=\"grid.csv\"");
		return headers;
	}
	
}