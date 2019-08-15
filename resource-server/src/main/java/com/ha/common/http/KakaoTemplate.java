package com.ha.common.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

@Component
public class KakaoTemplate extends HttpServerTemplate {
	
	@Override
	public JsonObject getUser(String token) {
		HttpHeaders headers = new HttpHeaders();
		
		return getRestTemplate().exchange(
				"https://kapi.kakao.com/v1/api/talk/profile", 
				HttpMethod.GET, 
				new HttpEntity<Object>(headers), 
				JsonObject.class).getBody();
	}
}
