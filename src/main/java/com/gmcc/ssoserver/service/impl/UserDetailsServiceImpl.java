package com.gmcc.ssoserver.service.impl;

import com.gmcc.ssoserver.entity.UserDetailsEntity;
import com.gmcc.ssoserver.dao.UserDetailsDao;
import com.gmcc.ssoserver.service.IUserDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * User details 服务实现类
 * </p>
 *
 * @author Kelvin@Inspur
 * @since 2019-08-01
 */
@Service
public class UserDetailsServiceImpl extends ServiceImpl<UserDetailsDao, UserDetailsEntity> implements IUserDetailsService {

}
