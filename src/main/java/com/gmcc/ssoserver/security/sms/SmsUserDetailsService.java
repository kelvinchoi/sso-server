package com.gmcc.ssoserver.security.sms;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gmcc.ssoserver.entity.UserDetailsEntity;
import com.gmcc.ssoserver.service.IUserDetailsService;

public class SmsUserDetailsService implements UserDetailsService {
	@Autowired
	protected IUserDetailsService userDetailsService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;

		// The username is mobile phone number here.
		UserDetailsEntity userDetailsEntity = userDetailsService.lambdaQuery().eq(UserDetailsEntity::getPhone, username)
				.one();
		if (userDetailsEntity == null
				|| StringUtils.isAnyBlank(userDetailsEntity.getPhone(), userDetailsEntity.getAuthority())) {
			throw new UsernameNotFoundException("Invalid mobile phone number.");
		} else {
			user = new User(userDetailsEntity.getPhone(), userDetailsEntity.getCheckCode(), Boolean.TRUE, Boolean.TRUE,
					userDetailsEntity.getLastSmsCodeTime().plusMinutes(30L).isAfter(LocalDateTime.now()), Boolean.TRUE,
					AuthorityUtils.commaSeparatedStringToAuthorityList(userDetailsEntity.getAuthority()));
		}

		return user;
	}

}
