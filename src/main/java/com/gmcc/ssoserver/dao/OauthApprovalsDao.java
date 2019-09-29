package com.gmcc.ssoserver.dao;

import com.gmcc.ssoserver.entity.OauthApprovalsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Oauth approvals, due to JdbcApprovalStore configuration, field names are camel case. Mapper 接口
 * </p>
 *
 * @author Kelvin@Inspur
 * @since 2019-07-25
 */
public interface OauthApprovalsDao extends BaseMapper<OauthApprovalsEntity> {

}
