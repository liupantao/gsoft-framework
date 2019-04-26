package com.gsoft.framework.core.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gsoft.framework.core.dataobj.Record;
import com.gsoft.framework.core.exception.BusException;
import com.gsoft.framework.remote.data.ExceptionResContext;
import com.gsoft.framework.remote.data.ResContext;
import com.gsoft.framework.security.AccountPrincipal;
import com.gsoft.framework.security.IUser;
import com.gsoft.framework.security.LogoutService;
import com.gsoft.framework.security.UserService;
import com.gsoft.framework.util.SecurityUtils;
import com.gsoft.framework.util.StringUtils;

/**
 * 
 * @author liupantao
 * 
 */
@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired(required = false)
	private LogoutService logoutService;

	@Autowired(required = false)
	private UserService userService;

	/**
	 * 默认首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/")
	public ModelAndView defaultIndex(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("index");
	}

	/**
	 * 首页，登录成功跳转页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index.html")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("index");
	}

	/**
	 * 登录页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/login.html")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		AccountPrincipal accountPrincipal = SecurityUtils.getAccount();
		if (accountPrincipal != null) {
			return new ModelAndView("redirect:/index.html");
		}
		
		if (request.getParameter("error") != null) {
			request.setAttribute("error", request.getParameter("error"));
		}
		return new ModelAndView("login");
	}

	/**
	 * 退出登录页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/logout.html")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		// 退出系统
		logoutService.logout(request, response);
		return null;
	}

	/**
	 * 欢迎页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/welcome.html")
	public ModelAndView welcome(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("welcome");
	}

	/**
	 * 修改用户登录密码交易
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/doModifyPassword.json")
	@ResponseBody
	public ResContext<?> doModifyPassword(HttpServletRequest request, HttpServletResponse response) {
		ResContext<Record> resContext = new ResContext<Record>();
		try {
			AccountPrincipal currentAccount = SecurityUtils.getAccount();
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirmPassword");
			String oldPassword = request.getParameter("oldPassword");
			if (StringUtils.isEmpty(oldPassword)) {
				throw new BusException("旧密码不能为空！");
			}
			if (StringUtils.isEmpty(password)) {
				throw new BusException("新密码不能为空！");
			}
			if (StringUtils.isEmpty(confirmPassword)) {
				throw new BusException("确认密码不能为空！");
			}

			if (!password.equals(confirmPassword)) {
				throw new BusException("两次输入的密码不一致！");
			}
			if (userService != null && currentAccount instanceof IUser) {
				userService.modifyPassword((IUser) currentAccount, password, confirmPassword, oldPassword);
			} else {
				throw new BusException("不支持密码修改！");
			}
		} catch (Exception e) {
			return new ExceptionResContext(e);
		}
		return resContext;
	}

}
