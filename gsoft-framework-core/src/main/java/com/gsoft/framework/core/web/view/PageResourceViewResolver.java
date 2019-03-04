/**
 * 
 */
package com.gsoft.framework.core.web.view;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * PageResourceViewResolver
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class PageResourceViewResolver  extends InternalResourceViewResolver{
	
	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		InternalResourceView view = (InternalResourceView) super.buildView(viewName);
//		if(viewName.startsWith(PAGE_URL_JAR)){
//			view.setUrl("/WEB-INF/pages/" + viewName.substring(PAGE_URL_JAR.length()) + getSuffix());
//		}
//		 
//		if(isTest()){
////			view.setUrl("/WEB-INF/pages/" + viewName + getSuffix());
//		}
//		 
//		ServletContext sc = this.getServletContext();
//		InputStream bIn = sc.getResourceAsStream(view.getUrl());
//		
//		System.out.println(new String(FileCopyUtils.copyToByteArray(bIn)));
		
		return view;
	}

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		return super.resolveViewName(viewName, locale);
	}
	
	

}
