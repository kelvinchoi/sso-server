package com.gmcc.ssoserver.service.impl;

import com.gmcc.ssoserver.entity.OauthClientDetailsEntity;
import com.gmcc.ssoserver.dao.OauthClientDetailsDao;
import com.gmcc.ssoserver.service.IOauthClientDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Oauth client details 服务实现类
 * </p>
 *
 * @author Kelvin@Inspur
 * @since 2019-07-24
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsDao, OauthClientDetailsEntity> implements IOauthClientDetailsService {

}
