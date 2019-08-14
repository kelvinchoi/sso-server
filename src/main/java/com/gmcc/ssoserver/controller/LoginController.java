package com.gmcc.ssoserver.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gmcc.ssoserver.entity.BaseResponseEntity;
import com.gmcc.ssoserver.security.sms.SmsCodeEntity;
import com.gmcc.ssoserver.service.ILoginService;
import com.gmcc.ssoserver.utils.Constants;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	private static final String LOGIN_PAGE = "login";
	private static final String INDEX_PAGE = "index";

	@Autowired
	private ILoginService loginService;

	@Autowired
	private EhCacheCacheManager ehcacheCacheManager;

	@GetMapping
	public String login(Model model) {
		// Redirect the logged in user to index page
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// For principle name, it equals to anonymousUser
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			return FORWARD_PREFIX + INDEX_PAGE;
		}

		loginService.checkException(model);
		return LOGIN_PAGE;
	}

	@PostMapping
	public String postLogin(Model model) {
		loginService.checkException(model);
		return LOGIN_PAGE;
	}

	@PostMapping("sendSmsCode")
	public ResponseEntity<BaseResponseEntity> sendSmsCode(@RequestParam String mobilePhoneNumber) {
		String status = "1";
		String message;
		if (!loginService.validate(mobilePhoneNumber)) {
			message = "手机号码不存在或未授权，请重试";
		} else if (loginService.sendSmsCode(mobilePhoneNumber) != null) {
			status = "0";
			message = "发送验证码成功，请查看";
		} else {
			message = "发送验证码失败，请重试";
		}

		return ResponseEntity.ok(new BaseResponseEntity(status, message));
	}

	@GetMapping("UserLoginManager/sendSms")
	public ResponseEntity<Map<String, Object>> mockSendSms(@RequestParam String phoneNum, @RequestParam String idCode) {
		Map<String, Object> resultMap = new HashMap<>();
		LOGGER.info("==================================================={}: {}", phoneNum, idCode);
		resultMap.put("status", "0");
		return ResponseEntity.ok(resultMap);
	}

	@GetMapping("checkSms")
	public ResponseEntity<Map<String, Object>> checkSms(@RequestParam String mobilePhoneNumber) {
		Map<String, Object> resultMap = new HashMap<>();
		Cache cache = ehcacheCacheManager.getCache(Constants.SMS_CODE_CACHE_NAME);
		if (cache != null) {
			SmsCodeEntity smsCodeEntity = cache.get(mobilePhoneNumber, SmsCodeEntity.class);
			if (smsCodeEntity != null) {
				resultMap.put("code", smsCodeEntity.getCode());
				resultMap.put("expired", smsCodeEntity.getExpiredAt());
			}
		}
		return ResponseEntity.ok(resultMap);
	}
}
