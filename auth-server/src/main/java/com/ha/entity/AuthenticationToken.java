package com.ha.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.ha.common.SerializableObjectConverter;

import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Setter
@Table(name = "auth_token")
@Entity
@EqualsAndHashCode(callSuper = false)
public class AuthenticationToken extends BaseEntity implements OAuth2AccessToken {
	private static final long serialVersionUID = 2155367796105217282L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 200)
	private String token;
	
	@ManyToOne
	@JoinColumn(name = "clientId", referencedColumnName = "id")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "refreshTokenId", referencedColumnName = "id")
	private AuthenticationRefreshToken refreshToken;
	
	@Lob
	@Column(length = 100000)
	private String authentication;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiration;

	private int expiresIn;
	
	private String tokenType;
	
	@Override
	public Map<String, Object> getAdditionalInformation() {
		return new HashMap<>();
	}

	@Override
	public Set<String> getScope() {
		return null;
	}

	@Override
	public String getTokenType() {
		return getTokenType();
	}

	@Override
	public boolean isExpired() {
		return expiration != null && expiration.before(new Date());
	}

	@Override
	public int getExpiresIn() {
		return getExpiresIn();
	}

	@Override
	public String getValue() {
		return getToken();
	}
	
	public void setAuthentication(OAuth2Authentication authentication) {
        this.authentication = SerializableObjectConverter.serialize(authentication);
    }
	
	public OAuth2Authentication getAuthentication() {
		return SerializableObjectConverter.deserialize(this.authentication);
	}
}
