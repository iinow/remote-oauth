package com.ha.security;

import java.util.Date;
import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

public class AuthAccessTokenConverter extends DefaultAccessTokenConverter {

	@Override
	public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		Map<String, Object> map = (Map<String, Object>) super.convertAccessToken(token, authentication);
		map.put("active", token.getExpiration().after(new Date()));
		return map;
	}
}
