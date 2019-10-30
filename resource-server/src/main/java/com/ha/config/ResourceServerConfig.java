package com.ha.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import com.ha.security.entry.AuthExceptionEntryPoint;
import com.ha.security.handler.AuthAccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	private AppConfig appConfig;
	private RemoteTokenService remoteTokenService;
	private AuthAccessDeniedHandler deniedHandler;
	
	public ResourceServerConfig(AppConfig appConfig, RemoteTokenService remoteTokenService, AuthAccessDeniedHandler deniedHandler) {
		this.appConfig = appConfig;
		this.remoteTokenService = remoteTokenService;
		this.deniedHandler = deniedHandler;
	}
	
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		remoteTokenService.loadHttpMethod(HttpMethod.POST);
		resources.accessDeniedHandler(null);
		resources.tokenServices(remoteTokenService);
		resources.resourceId(appConfig.getResource().getId());
		resources.authenticationEntryPoint(new AuthExceptionEntryPoint())
			.accessDeniedHandler(deniedHandler);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/health").permitAll()
			.anyRequest().authenticated()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and();
	}
}
