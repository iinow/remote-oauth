package com.ha.config.client;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import com.ha.entity.Client;

public class ClientDetailsDecorator implements ClientDetails {
	private static final long serialVersionUID = -5769851545490163125L;

	private Client client;
	
	public ClientDetailsDecorator(Client client) {
		this.client = client;
	}
	
	@Override
	public String getClientId() {
		return client.getClientId();
	}

	@Override
	public Set<String> getResourceIds() {
		return Set.of();
	}

	@Override
	public boolean isSecretRequired() {
		return client.getSecret() != null && client.getSecret().isEmpty();
	}

	@Override
	public String getClientSecret() {
		return client.getSecret();
	}

	@Override
	public boolean isScoped() {
		return !client.getScopes().isEmpty();
	}

	@Override
	public Set<String> getScope() {
		return client.getScopes()
				.stream()
				.map(scope -> scope.getScope())
				.collect(Collectors.toSet());
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return client.getGranttypes()
				.stream()
				.map(grantType -> grantType.getGrantType())
				.collect(Collectors.toSet());
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return Set.of(client.getRedirectUri());
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return Arrays.asList(
				(GrantedAuthority) ()-> "ROLE_USER",
				(GrantedAuthority) ()-> "ROLE_ADMIN",
				(GrantedAuthority) ()-> "ROLE_ANONYMOUS");
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return null;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return null;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return false;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return null;
	}

}
