package com.ha.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.ha.common.AuthDefine.OAuthResource;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2(topic = "AppYmlConfig")
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
@Data
public class AppConfig {
	private AppOAuthConfigData oauth;
	private AppOAuthTsxConfigData tsx1;

	@Data
	public static class AppOAuthConfigData {
		private List<AppOAuthSiteConfigData> sites;
		
		public AppOAuthSiteConfigData findOAuthSite(int resourceType) {
			for(AppOAuthSiteConfigData site: sites) {
				if(site.getResourceName() == resourceType) {
					return site;
				}
			}
			return null;
		}
	}
	
	@Data
	public static class AppOAuthSiteConfigData {
		private int resourceName;
		private String redirectUrl;
		private String clientId;
		private String clientSecret;
		private String tokenUrl;
	}
	
	@Data
	public static class AppOAuthTsxConfigData {
		private String redirectUrl;
	}
}
