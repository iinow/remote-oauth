package com.ha.security;

import java.util.Date;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;

import com.ha.api.user.UserService;
import com.ha.config.AppConfig;
import com.ha.entity.AuthenticationToken;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class CustomTokenService implements AuthorizationServerTokenServices, ResourceServerTokenServices,
ConsumerTokenServices {
	
	private AppConfig appConfig;
	private CustomTokenStore tokenStore;
	private UserContext userContext;
	private UserService userService;
	
	public CustomTokenService(
			final CustomTokenStore tokenStore,
			final AppConfig appConfig,
			final UserContext userContext,
			final UserService userService) {
		this.tokenStore = tokenStore;
		this.appConfig = appConfig;
		this.userContext = userContext;
		this.userService = userService;
	}
	
	@Override
	public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
		OAuth2AccessToken existingAccessToken = tokenStore.getAccessToken(authentication);
		
		Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appConfig.getAuth().getTokenExpirationMsec());
        
        String email = authentication.getOAuth2Request().getRequestParameters().get("email");
        UserDetails userDetails = userService.loadUserByEmail(email);
        userContext.setCurrentUser(userDetails);
        
        UserPrincipal user = userContext.getCurrentUserPrincipal();
        	
        authentication.getCredentials();
        String value = Jwts.builder()
        		.setSubject(Long.toString(user.getId()))
        		.setIssuedAt(new Date())
        		.setExpiration(expiryDate)
        		.signWith(SignatureAlgorithm.HS512, appConfig.getAuth().getTokenSecret())
        		.compact();

        AuthenticationToken token = new AuthenticationToken();
        token.setExpiration(expiryDate);
        token.setToken(value);
		return token;
	}
	
	private OAuth2AccessToken createAccessToken() {
		return null;
	}
	
	@Override
	public boolean revokeToken(String tokenValue) {
		return false;
	}

	@Override
	public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
		return null;
	}

	@Override
	public OAuth2AccessToken readAccessToken(String accessToken) {
		return null;
	}

	@Override
	public OAuth2AccessToken refreshAccessToken(String refreshToken, TokenRequest tokenRequest) throws AuthenticationException {
		return null;
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		return null;
	}

}
