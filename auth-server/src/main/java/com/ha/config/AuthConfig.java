package com.ha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.ha.common.GrantType;

@Configuration
@EnableAuthorizationServer
public class AuthConfig extends AuthorizationServerConfigurerAdapter{
	
    @Autowired
    private Environment environment;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	new ClientCredentialsAccessTokenProvider();
    	endpoints
    		.tokenEnhancer(new TokenEnhancerChain())
    		.tokenStore(new InMemoryTokenStore());
//    		.tokenServices(new DefaultTokenServices())
//    		.tokenGranter(new TokenGranter() {
//    		.tokenGranter(new AuthorizationCodeGrant);
    		
    		
//        endpoints.tokenStore(new InMemoryTokenStore())
//                .tokenEnhancer(jwtTokenEnhancer());
//                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//    	.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')")
//        .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
//                .checkTokenAccess("isAuthenticated()");
    	
    	//https://chanwookpark.github.io/spring/oauth/2016/01/26/oauth2-spring-dev-guide/
    	// /oauth/token_key (JWT 토큰을 사용하는 경우 토큰 검증을 위한 공개키를 노출)가 있다
    	security.tokenKeyAccess("permitAll()")
        		.checkTokenAccess("permitAll()");
    }

    /**
     * {@link https://tools.ietf.org/html/rfc6749#section-4.4.3}
     * Client_Credentials 로 refresh_token 까지 받는 것은 RFC 명세 보면 포함하지 말라고 적혀 있음
     * 
     * */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("service-account-1")
                .secret("{noop}service-account-1-secret")
                .refreshTokenValiditySeconds(1500)
                .authorities("ADMIN", "CLIENT", "ANOYMOUS")
                .authorizedGrantTypes(
                		GrantType.CLIENT_CREDENTIALS.toTypeString()) 
//                		GrantType.AUTHORIZATION_CODE.toTypeString(), 
//                		GrantType.IMPLICIT.toTypeString(), 
//                		GrantType.PASSWORD.toTypeString(),
//                		GrantType.REFRESH_TOKEN.toTypeString())
                .scopes("resource-server-read", "resource-server-write")
                .accessTokenValiditySeconds(300).autoApprove(true);
    }
    
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        String pwd = environment.getProperty("keystore.password");
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource("jwt.jks"),
                pwd.toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
        return converter;
    }
}
