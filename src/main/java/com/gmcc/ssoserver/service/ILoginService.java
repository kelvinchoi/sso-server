package com.gmcc.ssoserver.service;

public interface ILoginService {
	boolean validate(String mobilePhoneNumber);

	boolean sendSmsCode(String mobilePhoneNumber);

	boolean updateSmsCode(String mobilePhoneNumber, Integer smsCode);
}
