package com.ha.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {
	private Resource resource;
	
	@Getter
	@Setter
	public static class Resource {
		private String id;
	}
}
