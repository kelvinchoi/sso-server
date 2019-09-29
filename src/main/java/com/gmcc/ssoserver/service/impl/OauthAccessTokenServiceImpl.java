package com.gmcc.ssoserver.service.impl;

import com.gmcc.ssoserver.entity.OauthAccessTokenEntity;
import com.gmcc.ssoserver.dao.OauthAccessTokenDao;
import com.gmcc.ssoserver.service.IOauthAccessTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Oauth access token 服务实现类
 * </p>
 *
 * @author Kelvin@Inspur
 * @since 2019-07-25
 */
@Service
public class OauthAccessTokenServiceImpl extends ServiceImpl<OauthAccessTokenDao, OauthAccessTokenEntity> implements IOauthAccessTokenService {

}
