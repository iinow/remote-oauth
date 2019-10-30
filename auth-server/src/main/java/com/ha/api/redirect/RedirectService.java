package com.ha.api.redirect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ha.config.AuthorizationHelper;

@Service
public class RedirectService {

	@Autowired
	private AuthorizationHelper authHelper;
	
	public String getKakaoToken(String code) {
		String token = authHelper.postAuthorizationCode(code);
		return token;
	}
	
	public String getGithubToken(String code) {
		String token = authHelper.postGithubAuthorizationCode(code);
		return token;
	}
}
