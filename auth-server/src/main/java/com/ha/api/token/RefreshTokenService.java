package com.ha.api.token;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ha.entity.AuthenticationRefreshToken;

@Service
public class RefreshTokenService {

	private RefreshTokenRepository repository;
	
	public RefreshTokenService(RefreshTokenRepository repository) {
		this.repository = repository;
	}
	
	@Transactional
	public AuthenticationRefreshToken add(AuthenticationRefreshToken refreshToken) {
		return repository.save(refreshToken);
	}
}
