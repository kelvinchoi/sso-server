package com.gmcc.ssoserver.security.sms;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.util.Assert;

import com.gmcc.ssoserver.entity.UserDetailsEntity;
import com.gmcc.ssoserver.service.IUserDetailsService;

/**
 * @author Kelvin@Inspur Refer to {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}.
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(SmsAuthenticationProvider.class);

	@Autowired
	private IUserDetailsService dbUserDetailsService;

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	private UserCache userCache = new NullUserCache();
	protected boolean hideUserNotFoundExceptions = true;
	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

	private UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.isInstanceOf(SmsAuthenticationToken.class, authentication, () -> messages
				.getMessage("SmsAuthenticationProvider.onlySupports", "Only SmsAuthenticationToken is supported"));

		// Determine username
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();

		boolean cacheWasUsed = true;
		UserDetails user = this.userCache.getUserFromCache(username);

		if (user == null) {
			cacheWasUsed = false;

			try {
				user = retrieveUser(username, (SmsAuthenticationToken) authentication);
			} catch (UsernameNotFoundException notFound) {
				LOGGER.debug("User '{}' not found", username);

				if (hideUserNotFoundExceptions) {
					throw new BadCredentialsException(messages
							.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
				} else {
					throw notFound;
				}
			}
		}

		Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");

		try {
//			preAuthenticationChecks.check(user);
			additionalAuthenticationChecks(user, (SmsAuthenticationToken) authentication);
		} catch (AuthenticationException exception) {
			if (cacheWasUsed) {
				// There was a problem, so try again after checking
				// we're using latest data (i.e. not from the cache)
				cacheWasUsed = false;
				user = retrieveUser(username, (SmsAuthenticationToken) authentication);
//				preAuthenticationChecks.check(user);
				additionalAuthenticationChecks(user, (SmsAuthenticationToken) authentication);
			} else {
				throw exception;
			}
		}

//		postAuthenticationChecks.check(user);

		if (!cacheWasUsed) {
			this.userCache.putUserInCache(user);
		}

		SmsAuthenticationToken result = new SmsAuthenticationToken(user, authentication.getCredentials(),
				authoritiesMapper.mapAuthorities(user.getAuthorities()));
		result.setDetails(authentication.getDetails());

		dbUserDetailsService.lambdaUpdate().eq(UserDetailsEntity::getPhone, user.getUsername())
				.set(UserDetailsEntity::getLoginTime, LocalDateTime.now()).update();

		return result;
	}

	protected void additionalAuthenticationChecks(UserDetails userDetails, SmsAuthenticationToken authentication)
			throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			LOGGER.debug("Authentication failed: no credentials provided");

			throw new BadCredentialsException(
					messages.getMessage("SmsAuthenticationProvider.badCredentials", "Bad credentials"));
		}

		String presentedPassword = authentication.getCredentials().toString();

		if (!StringUtils.equals(presentedPassword, userDetails.getPassword())
				|| !userDetails.isCredentialsNonExpired()) {
			LOGGER.debug("Authentication failed: password does not match stored value");

			throw new BadCredentialsException(
					messages.getMessage("SmsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (SmsAuthenticationToken.class.isAssignableFrom(authentication));
	}

	protected final UserDetails retrieveUser(String username, SmsAuthenticationToken authentication)
			throws AuthenticationException {
		try {
			UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
			if (loadedUser == null) {
				throw new InternalAuthenticationServiceException(
						"UserDetailsService returned null, which is an interface contract violation");
			}
			return loadedUser;
		} catch (UsernameNotFoundException ex) {
			throw ex;
		} catch (InternalAuthenticationServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
		}
	}

	public boolean isHideUserNotFoundExceptions() {
		return hideUserNotFoundExceptions;
	}

	public void setHideUserNotFoundExceptions(boolean hideUserNotFoundExceptions) {
		this.hideUserNotFoundExceptions = hideUserNotFoundExceptions;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}
}
