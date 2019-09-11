package com.gmcc.ssoserver.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

@RequestMapping("oauth")
@Controller
public class OauthController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OauthController.class);

	@GetMapping("error")
	public RedirectView error(@RequestParam Map<String, String> params, Model model) {
		Exception exception = (Exception) request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE);
//		RedirectMismatchException
//		ClientRegistrationException
		LOGGER.error("Authorization failed, please check.", exception);

		return new RedirectView("/login/error", true, false, false);
	}
}
