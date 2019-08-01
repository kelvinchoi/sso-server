package com.gmcc.ssoserver.security.sms;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class SmsAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(SmsAuthenticationFailureHandler.class);

	public static final String ERROR_MESSAGE_KEY = "errorMessage";
	private static final String INVALID_MOBILE_PHONE_NUMBER_ERROR_MESSAGE = "手机号码不存在或未授权！";
	private static final String INVALID_AUTHENTICATION_INFO_ERROR_MESSAGE = "手机号码或验证码错误！";
	private static final String UNKNOWN_AUTHENTICATION_FAILURE_ERROR_MESSAGE = "未知错误，请联系管理员！";

	public SmsAuthenticationFailureHandler() {
	}

	public SmsAuthenticationFailureHandler(String defaultFailureUrl) {
		super.setDefaultFailureUrl(defaultFailureUrl);
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String errorMessage;
		if (exception instanceof UsernameNotFoundException) {
			errorMessage = INVALID_MOBILE_PHONE_NUMBER_ERROR_MESSAGE;
		} else if (exception instanceof InternalAuthenticationServiceException
				|| exception instanceof BadCredentialsException) {
			errorMessage = INVALID_AUTHENTICATION_INFO_ERROR_MESSAGE;
		} else {
			errorMessage = UNKNOWN_AUTHENTICATION_FAILURE_ERROR_MESSAGE;
		}

		LOGGER.error("Authentication failed, set error message {}.", errorMessage, exception);

		if (isUseForward()) {
			request.setAttribute(ERROR_MESSAGE_KEY, errorMessage);
		} else {
			HttpSession session = request.getSession(false);

			if (session != null || isAllowSessionCreation()) {
				request.getSession().setAttribute(ERROR_MESSAGE_KEY, errorMessage);
			}
		}

		super.onAuthenticationFailure(request, response, exception);
	}
}
