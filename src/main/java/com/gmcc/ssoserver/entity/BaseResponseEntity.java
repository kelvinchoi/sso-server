package com.gmcc.ssoserver.entity;

public class BaseResponseEntity {
	String status;
	String message;

	public BaseResponseEntity() {
	}

	public BaseResponseEntity(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
