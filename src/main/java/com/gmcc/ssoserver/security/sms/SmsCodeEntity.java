package com.gmcc.ssoserver.security.sms;

import java.io.Serializable;

public class SmsCodeEntity implements Serializable {
	private static final long serialVersionUID = -7374696330214662372L;

	private String code;
	private long expiredAt;

	public SmsCodeEntity(String code, long expiredAt) {
		this.code = code;
		this.expiredAt = expiredAt;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(long expiredAt) {
		this.expiredAt = expiredAt;
	}

}
