package com.gmcc.ssoserver.service.impl;

import com.gmcc.ssoserver.entity.OauthApprovalsEntity;
import com.gmcc.ssoserver.dao.OauthApprovalsDao;
import com.gmcc.ssoserver.service.IOauthApprovalsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Oauth approvals, due to JdbcApprovalStore configuration, field names are camel case. 服务实现类
 * </p>
 *
 * @author Kelvin@Inspur
 * @since 2019-07-25
 */
@Service
public class OauthApprovalsServiceImpl extends ServiceImpl<OauthApprovalsDao, OauthApprovalsEntity> implements IOauthApprovalsService {

}
