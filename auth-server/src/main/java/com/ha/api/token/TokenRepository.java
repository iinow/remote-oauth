package com.ha.api.token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ha.entity.AuthenticationToken;

public interface TokenRepository extends JpaRepository<AuthenticationToken, Long>{

	Optional<AuthenticationToken> findOneByToken(String token);
	void deleteOneByToken(String token);
	
}
