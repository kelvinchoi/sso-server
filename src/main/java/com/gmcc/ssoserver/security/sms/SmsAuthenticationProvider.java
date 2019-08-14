package com.gmcc.ssoserver.security.sms;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * Refer to {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}.
 * 
 * @author Kelvin@Inspur
 */
public class SmsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(SmsAuthenticationProvider.class);

	private UserDetailsService userDetailsService;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) {
		if (authentication.getCredentials() == null) {
			LOGGER.debug("Authentication failed: no credentials provided");

			throw new BadCredentialsException(
					messages.getMessage("SmsAuthenticationProvider.badCredentials", "Bad credentials"));
		}

		String presentSmsCode = authentication.getCredentials().toString();

		if (!StringUtils.equals(presentSmsCode, userDetails.getPassword())) {
			LOGGER.debug("Authentication failed: SMS code does not match stored value");

			throw new BadCredentialsException(
					messages.getMessage("SmsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}

	@Override
	protected void doAfterPropertiesSet() throws Exception {
		Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
	}

	@Override
	protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) {
		try {
			UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
			if (loadedUser == null) {
				throw new InternalAuthenticationServiceException(
						"UserDetailsService returned null, which is an interface contract violation");
			}
			return loadedUser;
		} catch (UsernameNotFoundException | InternalAuthenticationServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
		}
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}
}
