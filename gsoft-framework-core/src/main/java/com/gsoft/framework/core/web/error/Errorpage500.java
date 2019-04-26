package com.gsoft.framework.core.web.error;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * TODO (描述该文件做什么)
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
@Component
public class Errorpage500 implements IErrorpageAdapter, Ordered {
	
	@Override
	public boolean supports(String errorCode) {
		return "500".equals(errorCode);
	}

	@Override
	public String buildErrorInfo(Throwable exception) {
		StringBuffer buf = new StringBuffer();
		buf.append("内部服务器错误");
		if (exception != null) {
			buf.append("</br>");
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			PrintStream sw = new PrintStream(byteStream);
			exception.printStackTrace(sw);
			buf.append(byteStream.toString());
		}
		return buf.toString();
	}

	@Override
	public int getOrder() {
		return 99;
	}
}