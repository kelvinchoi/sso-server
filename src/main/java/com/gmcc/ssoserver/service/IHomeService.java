package com.gmcc.ssoserver.service;

import com.gmcc.ssoserver.entity.OauthAccessTokenEntity;
import com.gmcc.ssoserver.entity.SystemServiceEntity;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface IHomeService extends IService<OauthAccessTokenEntity> {
	List<SystemServiceEntity> getAuthorizedSystemServiceList(String mobilePhoneNumber);
}
