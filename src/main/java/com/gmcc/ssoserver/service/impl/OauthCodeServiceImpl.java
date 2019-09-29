package com.gmcc.ssoserver.service.impl;

import com.gmcc.ssoserver.entity.OauthCodeEntity;
import com.gmcc.ssoserver.dao.OauthCodeDao;
import com.gmcc.ssoserver.service.IOauthCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Oauth code 服务实现类
 * </p>
 *
 * @author Kelvin@Inspur
 * @since 2019-07-25
 */
@Service
public class OauthCodeServiceImpl extends ServiceImpl<OauthCodeDao, OauthCodeEntity> implements IOauthCodeService {

}
