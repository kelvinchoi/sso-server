package com.gmcc.ssoserver.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
	protected static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpServletResponse response;

	@Autowired
	protected HttpSession session;

	protected static final String REDIRECT_PREFIX = "redirect:";
	protected static final String FORWARD_PREFIX = "forward:";

	@ModelAttribute
	public void handle(Model model) {
//  	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		model.addAttribute("title", "系统单点登录");
		model.addAttribute("contextPath", request.getContextPath());
	}
}
