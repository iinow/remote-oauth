package com.ha.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import lombok.Data;

@Data
@Entity
@Table(name = "auth_refresh_token")
public class AuthenticationRefreshToken implements Serializable, OAuth2RefreshToken {
	private static final long serialVersionUID = 439884597168625964L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String refreshToken;
	
	@Override
	public String getValue() {
		return getRefreshToken();
	}
}
