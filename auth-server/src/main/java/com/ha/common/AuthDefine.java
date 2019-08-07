package com.ha.common;

import com.ha.config.AppConfig.AppOAuthSiteConfigData;

public class AuthDefine {
	public enum OAuthResource {
		KAKAO(1),
		GOOGLE(2),
		GITHUB(3);
		
		public final int type;
		
		OAuthResource(int type) {
			this.type = type;
		}
		
		public OAuthResource findOAuthResource(int type) {
			for(OAuthResource resource: values()) {
				if(resource.type == type) {
					return resource;
				}
			}
			return null;
		}
	}
}
