package com.gmcc.ssoserver.service.impl;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmcc.ssoserver.entity.UserDetailsEntity;
import com.gmcc.ssoserver.security.sms.SmsCodeEntity;
import com.gmcc.ssoserver.service.ILoginService;
import com.gmcc.ssoserver.service.IUserDetailsService;
import com.gmcc.ssoserver.utils.CommonUtils;
import com.gmcc.ssoserver.utils.Constants;

@Service
public class LoginServiceImpl implements ILoginService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	private HttpServletRequest request;

	@Autowired
	protected IUserDetailsService userDetailsService;

	// http://localhost:8080/UserLoginManager/sendSms.json?phoneNum=11的号码&idCode=验证码
	@Value("${login.sms.send.url}")
	private String sendSmsCodeUrl;

	@Value("${login.sms.code.validity.in.second}")
	private String smsCodeValidityInSecond;

	@Override
	public boolean validate(String mobilePhoneNumber) {
		boolean isValid = false;
		UserDetailsEntity userDetailsEntity = userDetailsService.lambdaQuery()
				.eq(UserDetailsEntity::getPhone, mobilePhoneNumber).one();
		if (userDetailsEntity != null
				&& StringUtils.isNoneBlank(userDetailsEntity.getPhone(), userDetailsEntity.getAuthority())) {
			isValid = true;
		}

		return isValid;
	}

	@Override
	@CachePut(value = Constants.SMS_CODE_CACHE_NAME, key = "#mobilePhoneNumber")
	public SmsCodeEntity sendSmsCode(String mobilePhoneNumber) {
		SmsCodeEntity smsCodeEntity = null;
		String result;
		Map<String, String> resultMap;

		Integer smsCode = new Random().nextInt(899999) + 100000;
		LOGGER.info("Generate SMS code {} for mobile phone number {}.", smsCode, mobilePhoneNumber);

		try {
			result = CommonUtils.httpGet(String.format(sendSmsCodeUrl, mobilePhoneNumber, smsCode));
			ObjectMapper objectMapper = new ObjectMapper();
			resultMap = objectMapper.readValue(result, new TypeReference<Map<String, String>>() {
			});
		} catch (Exception e) {
			LOGGER.error("Failed to send SMS code {} to {}.", smsCode, mobilePhoneNumber, e);
			resultMap = null;
		}

		if (resultMap != null && resultMap.get("status") != null && Integer.parseInt(resultMap.get("status")) == 0) {
			smsCodeEntity = new SmsCodeEntity(String.valueOf(smsCode),
					ZonedDateTime.now().plusSeconds(Long.parseLong(smsCodeValidityInSecond)).toEpochSecond());
		}

		return smsCodeEntity;
	}

	@Override
	public void checkException(Model model) {
		String errorMessage;

		AuthenticationException exception = (AuthenticationException) request
				.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		HttpSession session = request.getSession(false);
		if (exception == null && session != null) {
			exception = (AuthenticationException) request.getSession(false)
					.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			request.getSession(false).removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		} else {
			request.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}

		if (exception != null) {
			model.addAttribute("mobilePhoneNumber", request.getParameter("mobilePhoneNumber"));

			if (exception instanceof UsernameNotFoundException
					|| exception instanceof InternalAuthenticationServiceException) {
				errorMessage = "手机号码不存在或未授权，请重试";
			} else if (exception instanceof BadCredentialsException) {
				errorMessage = "验证码错误";
			} else if (exception instanceof CredentialsExpiredException) {
				errorMessage = "验证码已过期";
			} else {
				errorMessage = "登陆失败，请联系管理员";
			}
			model.addAttribute(Constants.MODEL_ERROR_MESSAGE_KEY, errorMessage);
		}
	}
}
