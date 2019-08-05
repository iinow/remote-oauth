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
	
	@Value(value = "app.resource.id")
	private String resource_id;
	
	@Autowired
	private CustomRemoteTokenService remoteTokenService;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//		service.setClientId("service-account-1");
//		service.setClientSecret("{noop}service-account-1-secret");
		remoteTokenService.loadHttpMethod(HttpMethod.POST);
		resources.tokenServices(remoteTokenService);
		resources.resourceId(resource_id);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
//		http.cors()
//		http.csrf()
//		http.requestMatchers().anyRequest().and().build();
		http.authorizeRequests()
			.antMatchers("/test").permitAll()
			.anyRequest().authenticated();
	}
}
