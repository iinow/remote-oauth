package com.ha.common.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

@Component
public class GithubTemplate extends HttpServerTemplate {

	@Override
	public JsonObject getUser(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
		JsonObject obj = getRestTemplate().exchange(
				"https://api.github.com/users", 
				HttpMethod.GET, 
				httpEntity, 
				JsonObject.class).getBody();
		return obj;
	}

}
