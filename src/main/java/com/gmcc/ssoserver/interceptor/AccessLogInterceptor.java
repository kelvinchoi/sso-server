package com.gmcc.ssoserver.interceptor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.gmcc.ssoserver.bo.SmsUserDetailsBo;

public class AccessLogInterceptor implements WebRequestInterceptor {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogInterceptor.class);

	@Override
	public void preHandle(WebRequest request) throws Exception {
	}

	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
	}

	@Override
	public void afterCompletion(WebRequest request, Exception ex) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			SmsUserDetailsBo smsUserDetails = (SmsUserDetailsBo) authentication.getPrincipal();
			LOGGER.info("User {} is accessing client service {} at {}.", smsUserDetails.getUsername(),
					request.getParameter("client_id"),
					LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		}
	}
}
