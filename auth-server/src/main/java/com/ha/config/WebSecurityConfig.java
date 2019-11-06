package com.ha.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.ha.api.user.UserService;
import com.ha.security.HttpCookieOAuth2AuthorizationRequestRepository;
import com.ha.security.filter.BeforeClientCheckFilter;
import com.ha.security.filter.TokenAuthenticationFilter;
import com.ha.security.handler.OAuth2AuthenticationFailureHandler;
import com.ha.security.handler.OAuth2AuthenticationSuccessHandler;

//@Order(1)
@Configuration
@Order(1000)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private UserService userService;
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private TokenAuthenticationFilter tokenFilter;
    private BeforeClientCheckFilter userFilter;
    
    public WebSecurityConfig(
    		final UserService userService,
    		final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
    		final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler,
    		final TokenAuthenticationFilter tokenFilter,
    		final BeforeClientCheckFilter userFilter) {
    	this.userService = userService;
    	this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
    	this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
    	this.tokenFilter = tokenFilter;
    	this.userFilter = userFilter;
	}
    
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
				.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.csrf()
				.disable()
			.httpBasic()
				.disable()
			.authorizeRequests()
				.antMatchers("/oauth/check_token").permitAll()
				.antMatchers(HttpMethod.GET, "/health/*").permitAll()
				.anyRequest().authenticated()
			.and()
			.oauth2Login()
            .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                .and()
            .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
            .userInfoEndpoint()
	            .userService(userService)
	            .and()
            .successHandler(oAuth2AuthenticationSuccessHandler)
            .failureHandler(oAuth2AuthenticationFailureHandler)
            	.and()
            .addFilterAfter(userFilter, BasicAuthenticationFilter.class)
            .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
	    return new HttpSessionOAuth2AuthorizationRequestRepository();
	}
	
	@Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }
}
