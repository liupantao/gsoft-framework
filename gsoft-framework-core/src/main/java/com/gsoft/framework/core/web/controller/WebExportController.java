package com.gsoft.framework.core.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gsoft.framework.core.orm.PagerRecords;
import com.gsoft.framework.core.web.export.WebExportService;

/**
 * 导出Controller
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
@Controller
@RequestMapping({ "/webexport" })
public class WebExportController {

	@Autowired
	private WebExportService webExportService;

	@RequestMapping({ "/{type}.html" })
	public ModelAndView export(HttpServletRequest request, HttpServletResponse response, DataIn<?> dataIn,
			@PathVariable("type") String type) throws Exception {
		Object pagerRecords = request.getAttribute("pagerRecords");

		if ((pagerRecords != null) && (pagerRecords instanceof PagerRecords)) {
			HttpHeaders headers = this.webExportService.writePagerRecords(response.getOutputStream(), type,
					(PagerRecords) pagerRecords);

			if (headers != null) {
				response.setContentType(headers.getContentType().toString());
				Map<String, String> headerMap = headers.toSingleValueMap();
				for (Map.Entry<String, String> entry : headerMap.entrySet()) {
					response.setHeader(entry.getKey(), entry.getValue());
				}
			}
		}
		return null;
	}
}