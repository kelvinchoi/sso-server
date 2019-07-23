package com.gmcc.ssoserver.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gmcc.ssoserver.common.BaseTest;

public class BCryptPasswordEncoderTest extends BaseTest {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Test
	public void testEncode() {
		System.out.println(passwordEncoder.encode("admin"));
		assertTrue(passwordEncoder.matches("admin", "$2a$10$x.acThg18gxx2YGBr/1Hq.DMXnY.CSJD8AGHB9.UNPcA1qtD.ClKO"));
	}

}
