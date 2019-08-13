package com.ha.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ha.common.AuthDefine.OAuthResource;
import com.ha.config.AppConfig.AppOAuthSiteConfigData;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "AuthorizationHelper")
@Component
public class AuthorizationHelper {
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	private AppConfig appConfig;

	/**
	 * curl -d "grant_type=authorization_code& \
	 * 	client_id=524d446c73e56b0e2bd64962695d5259& \
	 * 	redirect_uri=http://211.215.173.198:8082/oauth/redirect& \
	 * 	code=88ImjMiCxCg2TgrBp4kKUFAn9EeFX-upRoAKsXpmzBK3XhHd8Cq7uY1yBwW_vnKnvX2kKgopyNkAAAFsasTSBg" 
	 * -H "Content-Type: application/x-www-form-urlencoded" 
	 * -X POST https://kauth.kakao.com/oauth/token
	 * */
	public String postAuthorizationCode(String code) {
		AppOAuthSiteConfigData site = appConfig.getOauth().findOAuthSite(OAuthResource.KAKAO.type);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("grant_type","authorization_code");
		map.add("client_id",site.getClientId());
		map.add("redirect_uri",site.getRedirectUrl());
		map.add("code",code);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		HttpEntity request = new HttpEntity(map, headers);
		
		log.info("보내기 전: "+map.toString());
		Map resMap = restTemplate.exchange(
				site.getTokenUrl(), 
				HttpMethod.POST,
				request,
				Map.class).getBody();
		
		log.info("receive data: "+resMap.toString());
		return resMap.get("access_token").toString();
	}
	
	public String postGithubAuthorizationCode(String code) {
		AppOAuthSiteConfigData site = appConfig.getOauth().findOAuthSite(OAuthResource.GITHUB.type);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("client_secret",site.getClientSecret());
		map.add("client_id",site.getClientId());
		map.add("redirect_uri",site.getRedirectUrl());
		map.add("code",code);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		HttpEntity request = new HttpEntity(map, headers);
		
		log.info("보내기 전: "+map.toString());
		Map resMap = restTemplate.exchange(
				site.getTokenUrl(), 
				HttpMethod.POST,
				request,
				Map.class).getBody();
		
		log.info("receive data: "+resMap.toString());
		return resMap.get("access_token").toString();
	}
}
