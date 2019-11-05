package com.ha.api.token;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ha.entity.AuthenticationToken;

/**
 * @author BISHOP
 * @since 2019.11.05
 * */
@Service
public class TokenService {

	private TokenRepository tokenRepository;
	
	public TokenService(TokenRepository tokenRepository) {
		this.tokenRepository = tokenRepository;
	}
	
	@Transactional
	public AuthenticationToken add(AuthenticationToken token) {
		return tokenRepository.save(token);
	}
	
	public AuthenticationToken getToken(String token) {
		return tokenRepository.findOneByToken(token).orElse(null);
	}
	
	@Transactional
	public void removeAuthenticationToken(String token) {
		tokenRepository.deleteOneByToken(token);
	}
}
