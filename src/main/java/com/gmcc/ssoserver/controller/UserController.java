package com.gmcc.ssoserver.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@GetMapping(value = "user/me")
	public Principal user(Principal principal) {
		LOGGER.info("Principal: {}.", principal);
		return principal;
	}

}
