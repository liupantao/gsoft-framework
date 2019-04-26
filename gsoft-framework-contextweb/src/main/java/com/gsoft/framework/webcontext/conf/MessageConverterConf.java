package com.gsoft.framework.webcontext.conf;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.gsoft.framework.core.log.ConfigRegisterLog;

/**
 * 使用fastjson
 * 
 * @author liupantao
 *
 */
@Configuration
public class MessageConverterConf extends WebMvcConfigurerAdapter {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);

		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter() {
			@Override
			public void write(Object o, Type type, MediaType contentType, HttpOutputMessage outputMessage)
					throws IOException, HttpMessageNotWritableException {
				// 处理ie上传变下载 将application/json 改为text/plain
				if (MediaType.APPLICATION_JSON.equals(contentType)
						|| MediaType.APPLICATION_JSON_UTF8.equals(contentType)) {
					contentType = MediaType.TEXT_PLAIN;
				}
				super.write(o, type, contentType, outputMessage);
			}
		};

		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);

		List<MediaType> fastMediaTypes = new ArrayList<MediaType>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON);
		fastMediaTypes.add(MediaType.TEXT_PLAIN);
		fastConverter.setSupportedMediaTypes(fastMediaTypes);
		fastConverter.setFastJsonConfig(fastJsonConfig);

		ConfigRegisterLog.registeBean(fastConverter, "FastJson MessageConverter", this);
		converters.add(fastConverter);
	}
}