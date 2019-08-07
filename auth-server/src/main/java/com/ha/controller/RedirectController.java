package com.ha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ha.config.AppConfig;
import com.ha.config.AuthorizationHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "RedirectController")
@RestController
public class RedirectController {
	
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private AuthorizationHelper authHelper;

	@GetMapping("/oauth/redirect")
	public ResponseEntity<Object> kakaoRedirect(
			@RequestParam(name = "code", required = true) String code) {
		log.info("Authorization Code = "+code);
		
		String token = authHelper.postAuthorizationCode(code);
		log.info("Access Tokens = "+code);
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(
				"Location", 
				String.format(appConfig.getTsx1().getRedirectUrl(), token));
		return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
				.headers(responseHeaders)
				.build();
	}
}
