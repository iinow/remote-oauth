package com.ha.security;

import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;

public class JwtAuthenticationConverter implements AccessTokenConverter {

	@Override
	public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		System.out.println("convertAccessToken");
		return null;
	}

	@Override
	public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
		System.out.println("extractAccessToken");
		return null;
	}

	@Override
	public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
		System.out.println("extractAuthentication");
		return null;
	}
}
