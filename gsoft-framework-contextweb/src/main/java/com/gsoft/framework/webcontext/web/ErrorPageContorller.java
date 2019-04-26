package com.gsoft.framework.webcontext.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * ErrorPageContorller
 * @author liupantao
 * @date 2018年1月5日
 *  
 */
@Controller
@RequestMapping({ "/errorpage" })
public class ErrorPageContorller {

	@RequestMapping({ "/{errorCode}.html" })
	public ModelAndView index(@PathVariable("errorCode") String errorCode, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.setViewName("errorpage/" + errorCode);
		return model;
	}
}