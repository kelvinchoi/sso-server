package com.gmcc.ssoserver.service;

import com.gmcc.ssoserver.entity.OauthApprovalsEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * Oauth approvals, due to JdbcApprovalStore configuration, field names are camel case. 服务类
 * </p>
 *
 * @author Kelvin@Inspur
 * @since 2019-07-25
 */
public interface IOauthApprovalsService extends IService<OauthApprovalsEntity> {

}
