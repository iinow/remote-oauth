package com.ha.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ha.config.client.ClientDetailService;
import com.ha.security.AuthAccessTokenConverter;
import com.ha.security.BeforeClientCheckFilter;
import com.ha.security.CustomTokenStore;

@Configuration
@EnableAuthorizationServer
@EnableOAuth2Client
//@EnableOAuth2Sso
public class AuthConfigServer extends AuthorizationServerConfigurerAdapter{
	
    private Environment environment;
    private ClientDetailService clientDetailService;
    private BeforeClientCheckFilter userFilter;
    private CustomTokenStore tokenStore;
    
    public AuthConfigServer(
    		final CustomTokenStore tokenStore,
    		final BeforeClientCheckFilter userFilter,
    		final ClientDetailService clientDetailService,
    		final Environment environment) {
    	this.environment = environment;
    	this.clientDetailService = clientDetailService;
    	this.userFilter = userFilter;
    	this.tokenStore = tokenStore;
	}
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	new ClientCredentialsAccessTokenProvider();
//    	JdbcTokenStore
//    	endpoints
//    		.tokenEnhancer(new TokenEnhancerChain())
////    		.accessTokenConverter(new JwtAuthenticationConverter())
//    		.tokenStore(tokenStore());
//    	DefaultTokenServices tt = new DefaultTokenServices();
//    	tt.setTokenStore(tokenStore);
    	endpoints
			.tokenEnhancer(new TokenEnhancerChain())
			.tokenStore(new InMemoryTokenStore());
	endpoints.accessTokenConverter(new AuthAccessTokenConverter());

    }

    
    /**
  		.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')")
		.checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
		.checkTokenAccess("isAuthenticated()");
		https://chanwookpark.github.io/spring/oauth/2016/01/26/oauth2-spring-dev-guide/
		/oauth/token_key (JWT ��ū�� ����ϴ� ��� ��ū ������ ���� ����Ű�� ����)�� �ִ�
		security.addTokenEndpointAuthenticationFilter((request, response, chain)->{
		request.getServletContext();
		});c
		isAuthenticated
     * */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    	security.tokenKeyAccess("permitAll()")
        		.checkTokenAccess("permitAll()");
    	security.addTokenEndpointAuthenticationFilter(userFilter);
    }

    /**
     * {@link https://tools.ietf.org/html/rfc6749#section-4.4.3}
     * Client_Credentials �� refresh_token ���� �޴� ���� RFC �� ���� �������� ����� ���� ����
     * 
     * */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailService);
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
    
    @Bean
	@Primary
	public DefaultTokenServices defaultTokenService() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
    }
    
    @Bean
	public WebMvcConfigurer webMvcConfigurer() {
	    return new WebMvcConfigurer() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/*")
	                    .allowedOrigins("*")
	                    .allowedMethods("*")
	                    .allowCredentials(false)
	                    .maxAge(3600);
	        }
	    };
	}
}
