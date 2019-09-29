package com.gmcc.ssoserver.service.impl;

import com.gmcc.ssoserver.entity.OauthClientTokenEntity;
import com.gmcc.ssoserver.dao.OauthClientTokenDao;
import com.gmcc.ssoserver.service.IOauthClientTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Oauth client token 服务实现类
 * </p>
 *
 * @author Kelvin@Inspur
 * @since 2019-07-31
 */
@Service
public class OauthClientTokenServiceImpl extends ServiceImpl<OauthClientTokenDao, OauthClientTokenEntity> implements IOauthClientTokenService {

}
