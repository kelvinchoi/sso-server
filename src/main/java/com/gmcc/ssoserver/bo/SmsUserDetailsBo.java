package com.gmcc.ssoserver.bo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * Refer to {@link org.springframework.security.core.userdetails.User}.
 * 
 * @author Kelvin
 */
public class SmsUserDetailsBo implements UserDetails {
	private static final long serialVersionUID = 2229801939912611748L;

	private final String mobilePhoneNumber;
	private final String smsCode;
	private final Set<GrantedAuthority> authorities;
	private final boolean credentialsNonExpired;

	public SmsUserDetailsBo(String mobilePhoneNumber, String smsCode, String authoritiesStr,
			boolean credentialsNonExpired) {
		if (StringUtils.isBlank(mobilePhoneNumber)) {
			throw new IllegalArgumentException("Invalid mobile phone number, cannot be null, empty or blank.");
		}

		this.mobilePhoneNumber = mobilePhoneNumber;
		this.smsCode = smsCode;
		this.authorities = Collections.unmodifiableSet(getAuthoritiesSet(authoritiesStr));
		this.credentialsNonExpired = credentialsNonExpired;
	}

	private static Set<GrantedAuthority> getAuthoritiesSet(String authoritiesStr) {
		if (StringUtils.isBlank(authoritiesStr)) {
			throw new IllegalArgumentException("Invalid authorities, cannot be null, empty or blank.");
		}

		List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesStr);

		Assert.notNull(authorityList, "Cannot pass a null GrantedAuthority collection");

		for (GrantedAuthority grantedAuthority : authorityList) {
			Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
		}

		return new HashSet<>(authorityList);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return smsCode;
	}

	@Override
	public String getUsername() {
		return mobilePhoneNumber;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
