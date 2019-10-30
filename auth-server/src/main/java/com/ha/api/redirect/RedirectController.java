package com.ha.api.redirect;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ha.api.redirect.RedirectService;
import com.ha.config.AppConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "RedirectController")
@RestController
public class RedirectController {
	
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private RedirectService redirectService;

	/**
	 * īī�� ���� ��� ������ �̸��� �����ͼ� UserName �� �־� �شٵ���...
	 * ���⼭ ȸ������ ó�� ������
	 * */
	@GetMapping("/oauth/redirect")
	public ResponseEntity<Object> kakaoRedirect(
			@RequestParam(name = "code", required = true) String code,
			HttpServletRequest request) {
		
		String token = redirectService.getGithubToken(code);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(
				"Location", 
				String.format(appConfig.getTsx1().getRedirectUrl(), token));
		return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
				.headers(responseHeaders)
				.build();
	}
	
	@GetMapping("/github/oauth/redirect")
	public ResponseEntity<Object> githubRedirect(
			@RequestParam(name = "code", required = true) String code,
			HttpServletRequest request) {
		
		String token = redirectService.getGithubToken(code);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(
				"Location", 
				String.format(appConfig.getTsx1().getRedirectUrl(), token));
		return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
				.headers(responseHeaders)
				.build();
	}
	
	//access_token=f5e940d825cef0e32ea5bc672da142a0dd047dbe&scope=&token_type=bearer
	@PostMapping(name = "/form", 
			headers = {"version=1"}, 
			produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, 
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public void dd(
			@RequestParam MultiValueMap<String, String> map) {
		
		System.out.println(map.toString());
	}
}
