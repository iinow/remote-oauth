package com.ha.api.token;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ha.entity.AuthenticationRefreshToken;

public interface RefreshTokenRepository extends JpaRepository<AuthenticationRefreshToken, Long>{

}
