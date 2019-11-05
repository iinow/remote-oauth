package com.ha.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "app")
@Data
public class AppConfig {
	private final OAuth2 oauth2 = new OAuth2();
	private final Auth auth = new Auth();
	
	@Getter
	@Setter
	public static class Auth {
        private String tokenSecret;
        private long tokenExpirationMsec;
        private long refreshTokenExpirationMsec;
	}
	
	@Getter
	@Setter
	public static final class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();
    }
}
