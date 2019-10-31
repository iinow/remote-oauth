package com.ha.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ha.api.user.UserService;
import com.ha.config.AppConfig;
import com.ha.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * @since 2019.10.31
 * @author BISHOP
 * 
 * jwt token 발급, 확인
 * */
@Component
public class TokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private AppConfig appConfig;
    
    private UserService userService;
    
    public TokenProvider(AppConfig appProperties, UserService userService) {
        this.appConfig = appProperties;
        this.userService = userService;
    }
    
    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appConfig.getAuth().getTokenExpirationMsec());

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appConfig.getAuth().getTokenSecret())
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appConfig.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }
    
    public User getUserFromToken(String token) {
    	long id = getUserIdFromToken(token);
    	return userService.getUser(id);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(appConfig.getAuth().getTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature, token: "+authToken);
            throw ex;
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token, token: "+authToken);
            throw ex;
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token, token: "+authToken);
            throw ex;
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token, token: "+authToken);
            throw ex;
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty, token: "+authToken);
            throw ex;
        }
    }
}
