package com.ha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	private AppConfig appConfig;
	
	public ResourceServerConfig(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
	
	@Autowired
	private RemoteTokenService remoteTokenService;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//		service.setClientId("service-account-1");
//		service.setClientSecret("{noop}service-account-1-secret");
		remoteTokenService.loadHttpMethod(HttpMethod.POST);
		resources.tokenServices(remoteTokenService);
		resources.resourceId(appConfig.getResource().getId());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/test", "/oauth/redirect").permitAll()
			.anyRequest().authenticated();
	}
}
