/**
 * 
 */
package com.gsoft.framework.core.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 浏览器支持
 * 
 * @author liupantao
 * 
 */
@Controller
@RequestMapping("/browser")
public class BrowserController {
	
	public final static String USER_AGENT = "User-Agent";
	public final static String FIRE_FOX = "firefox";

	@Value("${browser.path:/browser}")
	private String root;

	@RequestMapping("/not-support.html")
	public ModelAndView modifyPassword(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("not-support");
	}

	@RequestMapping("/downloadChrome.html")
	public ModelAndView downloadChrome(HttpServletRequest request, HttpServletResponse response) {
		try {
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			String path = root + File.separator + "Chrome.exe";
			File file = new File(path);
			if (!file.exists()) {
				return null;
			}
			InputStream inputstream = new FileInputStream(file);
			String filenName = file.getName();
			String filename = URLEncoder.encode(filenName, "utf-8");
			if (request.getHeader(USER_AGENT).toLowerCase().indexOf(FIRE_FOX) > 0) {
				filename = new String(filenName.getBytes("utf-8"), "ISO8859-1");
			}
			response.setContentType("application/msexcel");
			response.setHeader("Content-disposition", "attachment;filename=" + filename);
			FileCopyUtils.copy(inputstream, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/downloadFirefox.html")
	public ModelAndView downloadFirefox(HttpServletRequest request, HttpServletResponse response) {
		try {
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			String path = root + File.separator + "Firefox.exe";
			File file = new File(path);
			if (!file.exists()) {
				return null;
			}
			InputStream inputstream = new FileInputStream(file);
			String filenName = file.getName();
			String filename = URLEncoder.encode(filenName, "utf-8");
			if (request.getHeader(USER_AGENT).toLowerCase().indexOf(FIRE_FOX) > 0) {
				filename = new String(filenName.getBytes("utf-8"), "ISO8859-1");
			}
			response.setContentType("application/msexcel");
			response.setHeader("Content-disposition", "attachment;filename=" + filename);
			FileCopyUtils.copy(inputstream, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
