package com.ha.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64.Encoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Primary
@Service
@Slf4j(topic = "RemoteTokenService")
public class RemoteTokenService implements ResourceServerTokenServices {

	@Value(value = "${security.oauth2.resource.token-info-uri}")
	private String checkTokenHost;
	
	private RestOperations restTemplate;
	
	private String tokenName = "token";
	
	private boolean isAuthentication = false;
	
	private String clientId;
	
	private String clientSecret;
	
	private HttpMethod method = HttpMethod.POST;
	
	private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
	
	public RemoteTokenService() {
		restTemplate = new RestTemplate();
		((RestTemplate) restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			// Ignore 400
			public void handleError(ClientHttpResponse response) throws IOException {
				if (response.getRawStatusCode() != 400) {
					super.handleError(response);
				}
			}
		});
	}
	
	@Override
	public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
		formData.add(tokenName, accessToken);
		HttpHeaders headers = new HttpHeaders();
//		headers.set("Content-type", "application/x-www-form-urlencoded");
		if(isAuthentication) {
			headers.set("Authorization", getAuthorizationHeader(clientId, clientSecret));
		}
		Map<String, Object> map = postForMap(checkTokenHost, formData, headers, accessToken);

		if (map.containsKey("error")) {
			if (log.isDebugEnabled()) {
				log.debug("check_token returned error: " + map.get("error"));
			}
			throw new InvalidTokenException(accessToken);
		}

		// gh-838
		if (map.containsKey("active") && !"true".equals(String.valueOf(map.get("active")))) {
			log.debug("check_token returned active attribute: " + map.get("active"));
			throw new InvalidTokenException(accessToken);
		}

		return tokenConverter.extractAuthentication(map);
	}

	@Override
	public OAuth2AccessToken readAccessToken(String accessToken) {
		throw new UnsupportedOperationException("Not supported: read access token");
	}
	
	public void loadHttpMethod(HttpMethod method) {
		this.method = method;
	}

	private String getAuthorizationHeader(String clientId, String clientSecret) {

		if(clientId == null || clientSecret == null) {
			log.warn("Null Client ID or Client Secret detected. Endpoint that requires authentication will reject request with 401 error.");
		}

		String creds = String.format("%s:%s", clientId, clientSecret);
		try {
			Encoder encoder = java.util.Base64.getEncoder();
			String encodeStr = encoder.encodeToString(creds.getBytes("UTF-8"));
			return "Basic " + encodeStr;
		}
		catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Could not convert String");
		}
	}
	
	private Map<String, Object> postForMap(String path, MultiValueMap<String, String> formData, HttpHeaders headers, String accessToken) {
		if (headers.getContentType() == null) {
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		}
		@SuppressWarnings("rawtypes")
		Map map = restTemplate.exchange(path, this.method,
				new HttpEntity<MultiValueMap<String, String>>(formData, headers), Map.class).getBody();
		@SuppressWarnings("unchecked")
		Map<String, Object> result = map;
		return result;
	}
}
