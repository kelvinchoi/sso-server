package com.gmcc.ssoserver.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmcc.ssoserver.entity.UserDetailsEntity;
import com.gmcc.ssoserver.service.ILoginService;
import com.gmcc.ssoserver.service.IUserDetailsService;
import com.gmcc.ssoserver.utils.CommonUtils;

@Service
public class LoginServiceImpl implements ILoginService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	protected IUserDetailsService userDetailsService;

	// http://localhost:8080/UserLoginManager/sendSms.json?phoneNum=11的号码&idCode=验证码
	@Value("${login.sms.send.url}")
	private String sendSmsCodeUrl;

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
	public boolean sendSmsCode(String mobilePhoneNumber) {
		boolean isSuccessful = false;
		String result;
		HashMap<String, String> resultMap;

		Integer smsCode = CommonUtils.generateSmsCode();
		LOGGER.info("Generate SMS code {} for mobile phone number {}.", smsCode, mobilePhoneNumber);

		try {
			result = CommonUtils.httpGet(String.format(sendSmsCodeUrl, mobilePhoneNumber, smsCode));
			ObjectMapper objectMapper = new ObjectMapper();
			resultMap = objectMapper.readValue(result, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("Failed to send SMS code {} to {}.", smsCode, mobilePhoneNumber, e);
			resultMap = null;
		}

		// Check result, 0 for success, 1 for failure.
		if (resultMap != null && resultMap.get("status") != null && Integer.parseInt(resultMap.get("status")) == 0
				&& updateSmsCode(mobilePhoneNumber, smsCode)) {
			isSuccessful = true;
		}

		return isSuccessful;
	}

	@Override
	public boolean updateSmsCode(String mobilePhoneNumber, Integer smsCode) {
		return userDetailsService.lambdaUpdate().eq(UserDetailsEntity::getPhone, mobilePhoneNumber)
				.set(UserDetailsEntity::getCheckCode, smsCode)
				.set(UserDetailsEntity::getLastSmsCodeTime, LocalDateTime.now()).update();
	}

}
