package com.gmcc.ssoserver.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.gmcc.ssoserver.controller.BaseController;

/**
 * <p>
 * Oauth approvals, due to JdbcApprovalStore configuration, field names are camel case. 前端控制器
 * </p>
 *
 * @author Kelvin@Inspur
 * @since 2019-07-25
 */
@Controller
@RequestMapping("/ssoserver/oauth-approvals-entity")
public class OauthApprovalsController extends BaseController {

}

