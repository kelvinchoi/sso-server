package com.gmcc.ssoserver.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gmcc.ssoserver.entity.SystemServiceEntity;
import com.gmcc.ssoserver.service.IHomeService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/")
public class HomeController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private IHomeService homeService;

	@GetMapping
	public String root(Principal principal, Model model) {
		return index(principal, model);
	}

	@GetMapping("index")
	public String index(Principal principal, Model model) {
		List<SystemServiceEntity> systemServiceEntityList = Collections.emptyList();

		if (principal != null && StringUtils.isNotBlank(principal.getName())) {
			systemServiceEntityList = homeService.getAuthorizedSystemServiceList(principal.getName());
			LOGGER.info("Get system service list for user {}, list size {}.", principal,
					systemServiceEntityList.size());
		}

		model.addAttribute("systemServiceEntityList", systemServiceEntityList);

		return "index";
	}

}
