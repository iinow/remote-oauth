package com.ha.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ha.AuthServerApplication;
import com.ha.config.AppConfig;
import com.ha.security.filter.BeforeClientCheckFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServerApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccessTokenTests {

	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private WebApplicationContext context;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}
	
	private String createToken(Long id) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appConfig.getAuth().getTokenExpirationMsec());

        return Jwts.builder()
                .setSubject(Long.toString(id))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appConfig.getAuth().getTokenSecret())
                .compact();
    }
	
	@Test
	public void login() throws Exception {
		String key = new String(Base64.getEncoder().encode("client:secret".getBytes(Charset.forName("utf-8"))), Charset.forName("utf-8"));
		
		this.mockMvc.perform(post("/oauth/token")
				.param("grant_type", "client_credentials")
				.param("email", "jh@haha.com")
				.param("password", "1234")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.header("Authorization", "Basic " + key))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	public static void main(String[] args) {
		String key = new String(Base64.getEncoder().encode("client:secret".getBytes(Charset.forName("utf-8"))), Charset.forName("utf-8"));
		System.out.println(key);
	}
}
