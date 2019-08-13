package com.ha.common;

import com.ha.config.AppConfig.AppOAuthSiteConfigData;
import com.ha.exception.GrantTypeNotFoundException;

public class AuthDefine {
	public enum OAuthResource {
		KAKAO(1),
		GOOGLE(2),
		GITHUB(3);
		
		public final int type;
		
		OAuthResource(int type) {
			this.type = type;
		}
		
		public static OAuthResource findOAuthResource(int type) {
			for(OAuthResource resource: values()) {
				if(resource.type == type) {
					return resource;
				}
			}
			return null;
		}
	}
	
	/**
	 * @author BISHOP
	 * @since 2019.08.05
	 * 
	 * 1. authorization code /oauth/authorize API 로 요청해서 코드를 받아야한다. 사전에 
	 * */
	public enum GrantType {
		NONE(0){
			@Override
			public String toTypeString() {
				return null;
			}
		},
		AUTHORIZATION_CODE(1) {
			@Override
			public String toTypeString() {
				return "authorization_code";
			}
		},
		IMPLICIT(2) {
			@Override
			public String toTypeString() {
				return "implicit";
			}
		},
		REFRESH_TOKEN(3) {
			@Override
			public String toTypeString() {
				return "refresh_token";
			}
		},
		PASSWORD(4) {//resource_owner_password_credentials
			@Override
			public String toTypeString() {
				return "password";
			}
		},											
		CLIENT_CREDENTIALS(5) {
			@Override
			public String toTypeString() {
				return "client_credentials";
			}
		},
		JWT_BEARER(6) {
			@Override
			public String toTypeString() {
				return "urn:ietf:params:oauth:grant-type:jwt-bearer";
			}
		},
		SAML2_BEARER(7) {
			@Override
			public String toTypeString() {
				return "urn:ietf:params:oauth:grant-type:saml2-bearer";
			}
		};
		
		private final int type;
		
		GrantType(int type) {
			this.type = type;
		}
		
		public GrantType findGrantType(int type) throws GrantTypeNotFoundException {
			for(GrantType grantType: values()) {
				if(grantType.type == type) {
					return grantType;
				}
			}
			throw new GrantTypeNotFoundException();
		}
		
		public abstract String toTypeString();
	}
}
