package com.ha.security;

import java.util.Collection;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import com.ha.api.client.ClientService;
import com.ha.api.token.RefreshTokenService;
import com.ha.api.token.TokenService;
import com.ha.api.user.UserService;
import com.ha.entity.AuthenticationRefreshToken;
import com.ha.entity.AuthenticationToken;
import com.ha.entity.Client;
import com.ha.entity.User;

/**
 * @author BISHOP
 * @since 2019.11.05
 * 
 * */
@Component
public class CustomTokenStore implements TokenStore {

	private TokenService tokenService;
	private RefreshTokenService refreshTokenService;
	private UserService userService;
	private ClientService clientService;
	
	public CustomTokenStore(
			final TokenService tokenService, 
			final RefreshTokenService refreshTokenService,
			final UserService userService,
			final ClientService clientService) {
		this.tokenService = tokenService;
		this.refreshTokenService = refreshTokenService;
		this.userService = userService;
		this.clientService = clientService;
	}
	
	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		return readAuthentication(token.getValue());
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		return readAccessToken(token).getAuthentication();
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		storeAuthenticationRefreshToken(token);
		
		if(readAccessToken(token.getValue()) != null) {
			this.removeAccessToken(token);
		}
		String email = authentication.getOAuth2Request().getRequestParameters().get("email");
		User user = userService.getUserByEmail(email);
		Client client = clientService.getClient(authentication.getOAuth2Request().getClientId());
	
		if(user != null)
			System.out.println(user.toString());
		if(client != null)
			client.toString();
//		token.getScope().iterator();
	}
	
	private void storeAuthenticationRefreshToken(OAuth2AccessToken token) {
		if(token.getRefreshToken() == null) {
			return;
		}
		
		AuthenticationRefreshToken refresh = new AuthenticationRefreshToken();
		refresh.setRefreshToken(token.getRefreshToken().getValue());
		refreshTokenService.add(refresh);
	}

	@Override
	public AuthenticationToken readAccessToken(String tokenValue) {
		return tokenService.getToken(tokenValue);
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		tokenService.removeAuthenticationToken(token.getValue());
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		return null;
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		return null;
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		return null;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		return null;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		return null;
	}

}
