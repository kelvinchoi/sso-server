package com.gmcc.ssoserver.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gmcc.ssoserver.security.sms.SmsAuthenticationFailureHandler;
import com.gmcc.ssoserver.service.ILoginService;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private ILoginService loginService;

	@GetMapping("custom")
	public String login(Model model) {
		request.getSession().removeAttribute(SmsAuthenticationFailureHandler.ERROR_MESSAGE_KEY);
		return "login";
	}

	@GetMapping("error")
	public String loginError(@RequestParam String code, Model model) {
		if (StringUtils.isNotBlank(code)) {
			switch (code) {
			case "401":
				request.setAttribute("errorMessage", "登录失败！");
				break;
			case "403":
				request.setAttribute("errorMessage", "您没有权限访问该服务！");
				break;
			default:
				break;
			}
		}
		return "login";
	}

	@PostMapping("sendSmsCode")
	public ResponseEntity<Map<String, Object>> sendSmsCode(@RequestParam String mobilePhoneNumber) {
		Map<String, Object> resultMap = new HashMap<>();
		if (!loginService.validate(mobilePhoneNumber)) {
			resultMap.put("status", "1");
			resultMap.put("message", "手机号码不存在或未授权，请重试");
		} else if (loginService.sendSmsCode(mobilePhoneNumber)) {
			resultMap.put("status", "0");
		} else {
			resultMap.put("status", "1");
			resultMap.put("message", "发送验证码失败，请重试");
		}

		return ResponseEntity.ok(resultMap);
	}

	@GetMapping("UserLoginManager/sendSms")
	public ResponseEntity<Map<String, Object>> mockSendSms(@RequestParam String phoneNum, @RequestParam String idCode) {
		Map<String, Object> resultMap = new HashMap<>();
		LOGGER.info("==================================================={}: {}", phoneNum, idCode);
		resultMap.put("status", "0");
		return ResponseEntity.ok(resultMap);
	}
}
