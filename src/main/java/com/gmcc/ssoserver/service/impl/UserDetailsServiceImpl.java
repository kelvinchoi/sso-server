package com.gmcc.ssoserver.service.impl;

import java.time.ZonedDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmcc.ssoserver.bo.SmsUserDetailsBo;
import com.gmcc.ssoserver.dao.UserDetailsDao;
import com.gmcc.ssoserver.entity.UserDetailsEntity;
import com.gmcc.ssoserver.security.sms.SmsCodeEntity;
import com.gmcc.ssoserver.service.IUserDetailsService;
import com.gmcc.ssoserver.utils.Constants;

/**
 * <p>
 * User details 服务实现类
 * </p>
 *
 * @author Kelvin@Inspur
 * @since 2019-08-01
 */
@Service
public class UserDetailsServiceImpl extends ServiceImpl<UserDetailsDao, UserDetailsEntity>
		implements IUserDetailsService, UserDetailsService {
	@Autowired
	private EhCacheCacheManager ehcacheCacheManager;

	@Override
	public UserDetails loadUserByUsername(String mobilePhoneNumber) {
		UserDetails userDetails;

		// The username is mobile phone number here.
		UserDetailsEntity userDetailsEntity = getOne(
				Wrappers.<UserDetailsEntity>lambdaQuery().eq(UserDetailsEntity::getPhone, mobilePhoneNumber));
		if (userDetailsEntity == null
				|| StringUtils.isAnyBlank(userDetailsEntity.getPhone(), userDetailsEntity.getAuthority())) {
			throw new UsernameNotFoundException("Invalid mobile phone number.");
		} else {
			Cache cache = ehcacheCacheManager.getCache(Constants.SMS_CODE_CACHE_NAME);
			SmsCodeEntity smsCodeEntity = cache.get(mobilePhoneNumber, SmsCodeEntity.class);
			try {
				userDetails = new SmsUserDetailsBo(userDetailsEntity.getPhone(),
						smsCodeEntity == null ? null : smsCodeEntity.getCode(), userDetailsEntity.getAuthority(),
						smsCodeEntity != null && smsCodeEntity.getExpiredAt() > ZonedDateTime.now().toEpochSecond());
			} catch (IllegalArgumentException iae) {
				throw new UsernameNotFoundException("Invalid mobile phone number.", iae);
			}
		}

		return userDetails;
	}
}
