package com.ha.common.http;

import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;

import lombok.Getter;

@Getter
public abstract class HttpServerTemplate {
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public abstract JsonObject getUser(String token);
}
