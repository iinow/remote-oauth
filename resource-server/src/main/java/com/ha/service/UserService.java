package com.ha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.ha.common.Define.Provider;
import com.ha.common.http.GithubTemplate;
import com.ha.common.http.KakaoTemplate;

@Service
public class UserService {

	@Autowired
	private GithubTemplate githubTemplate;
	
	@Autowired
	private KakaoTemplate kakaoTemplate;
	
	public JsonObject getUser(Provider type, String token) {
		switch(type) {
			case GITHUB:
				return githubTemplate.getUser(token);
			case GOOGLE:
				return kakaoTemplate.getUser(token);
			case KAKAO:
			default:
				return null;
		}
	}
}
