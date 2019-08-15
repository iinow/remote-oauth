package com.ha;

import java.security.Principal;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ha.common.AuthDefine.OAuthResource;
import com.ha.config.AppConfig;
import com.ha.config.AppConfig.AppOAuthSiteConfigData;
import com.ha.entity.AuthClientModel;
import com.ha.service.ClientService;

@SpringBootApplication
@RestController
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}
	
	@Autowired
	private ClientService clientSerivce;
	
	@Autowired
	private AppConfig app;
	
	@PostConstruct
	public void postConstruct() {
		AppOAuthSiteConfigData github = app.getOauth().findOAuthSite(OAuthResource.GITHUB.type);
		
		AuthClientModel client = new AuthClientModel();
		client.setClientId(github.getClientId());
		client.setType(OAuthResource.GITHUB.type);
//		clientSerivce.insert(null);
	}
	
	@RequestMapping(path = "/user")
    public Principal user(Principal user) {
        System.out.println("AS /user has been called");
        System.out.println("user info: " + user.toString());
        return user;
    }
}
