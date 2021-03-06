package com.gmcc.ssoserver.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmcc.ssoserver.dao.OauthAccessTokenDao;
import com.gmcc.ssoserver.entity.OauthAccessTokenEntity;
import com.gmcc.ssoserver.entity.OauthClientDetailsEntity;
import com.gmcc.ssoserver.entity.SystemServiceEntity;
import com.gmcc.ssoserver.entity.UserDetailsEntity;
import com.gmcc.ssoserver.service.IHomeService;
import com.gmcc.ssoserver.service.IOauthClientDetailsService;
import com.gmcc.ssoserver.service.IUserDetailsService;

@Service
public class HomeServiceImpl extends ServiceImpl<OauthAccessTokenDao, OauthAccessTokenEntity> implements IHomeService {
	@Autowired
	private IUserDetailsService userDetailsService;

	@Autowired
	private IOauthClientDetailsService oauthClientDetailsService;

	@Override
	public List<SystemServiceEntity> getAuthorizedSystemServiceList(String mobilePhoneNumber) {
		List<SystemServiceEntity> systemServiceEntityList = Collections.emptyList();

		UserDetailsEntity userDetailsEntity = userDetailsService
				.getOne(Wrappers.<UserDetailsEntity>lambdaQuery().eq(UserDetailsEntity::getPhone, mobilePhoneNumber));
		if (userDetailsEntity != null && StringUtils.isNotBlank(userDetailsEntity.getAuthority())) {
			List<String> systemServiceNameList = Arrays.asList(userDetailsEntity.getAuthority().split(",", -1));
			List<OauthClientDetailsEntity> authorizedClientDetailsEntityList = oauthClientDetailsService
					.list(Wrappers.<OauthClientDetailsEntity>lambdaQuery().in(OauthClientDetailsEntity::getClientId,
							systemServiceNameList));

			systemServiceEntityList = authorizedClientDetailsEntityList.stream().map(authorizedClientDetailsEntity -> {
				SystemServiceEntity systemServiceEntity = new SystemServiceEntity(
						authorizedClientDetailsEntity.getServiceName(),
						authorizedClientDetailsEntity.getWebServerRedirectUri());
				if (StringUtils.isNotBlank(authorizedClientDetailsEntity.getAdditionalInformation())) {
					ObjectMapper objectMapper = new ObjectMapper();
					try {
						String description = (String) objectMapper
								.readValue(authorizedClientDetailsEntity.getAdditionalInformation(), Map.class)
								.get("description");
						systemServiceEntity.setDescription(description);
					} catch (IOException e) {
					}
				}
				return systemServiceEntity;
			}).collect(Collectors.toList());
		}

		return systemServiceEntityList;
	}
}
