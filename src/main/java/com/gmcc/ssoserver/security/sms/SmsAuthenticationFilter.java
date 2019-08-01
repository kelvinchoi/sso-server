package com.gmcc.ssoserver.security.sms;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

/**
 * @author Kelvin@Inspur Refer to
 *         {@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter}.
 */
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_FORM_MOBILE_PHONE_NUMBER_KEY = "mobilePhoneNumber";
	public static final String SPRING_SECURITY_FORM_SMS_CODE_KEY = "smsCode";

	private String mobilePhoneNumberParameter = SPRING_SECURITY_FORM_MOBILE_PHONE_NUMBER_KEY;
	private String smsCodeParameter = SPRING_SECURITY_FORM_SMS_CODE_KEY;
	private boolean postOnly = true;

	public SmsAuthenticationFilter() {
		super(new AntPathRequestMatcher("/login/sms", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String mobilePhoneNumber = obtainMobilePhoneNumber(request);
		String smsCode = obtainSmsCode(request);

		if (mobilePhoneNumber == null) {
			mobilePhoneNumber = "";
		}

		if (smsCode == null) {
			smsCode = "";
		}

		mobilePhoneNumber = mobilePhoneNumber.trim();

		SmsAuthenticationToken authRequest = new SmsAuthenticationToken(mobilePhoneNumber, smsCode);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected String obtainMobilePhoneNumber(HttpServletRequest request) {
		return request.getParameter(mobilePhoneNumberParameter);
	}

	protected String obtainSmsCode(HttpServletRequest request) {
		return request.getParameter(smsCodeParameter);
	}

	protected void setDetails(HttpServletRequest request, SmsAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	public void setMobilePhoneNumberParameter(String mobilePhoneNumberParameter) {
		Assert.hasText(mobilePhoneNumberParameter, "Mobile phone number parameter must not be empty or null");
		this.mobilePhoneNumberParameter = mobilePhoneNumberParameter;
	}

	public void setSmsCodeParameter(String smsCodeParameter) {
		Assert.hasText(smsCodeParameter, "SMS code parameter must not be empty or null");
		this.smsCodeParameter = smsCodeParameter;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getMobilePhoneNumberParameter() {
		return mobilePhoneNumberParameter;
	}

	public final String getSmsCodeParameter() {
		return smsCodeParameter;
	}

}
