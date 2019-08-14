package com.gmcc.ssoserver.service;

import org.springframework.ui.Model;

import com.gmcc.ssoserver.security.sms.SmsCodeEntity;

public interface ILoginService {
	boolean validate(String mobilePhoneNumber);

	SmsCodeEntity sendSmsCode(String mobilePhoneNumber);

	void checkException(Model model);
}
