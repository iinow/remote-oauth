package com.ha.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ha.api.user.UserService;
import com.ha.security.TokenProvider;
import com.ha.security.UserContext;
import com.ha.security.handler.FilterExceptionHandler;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
	
    private TokenProvider tokenProvider;
    private UserService userService;
    private FilterExceptionHandler exceptionHandler;
    private UserContext userContext;
    
    public TokenAuthenticationFilter(
    		final TokenProvider tokenProvider,
    		final UserService userService,
    		final FilterExceptionHandler exceptionHandler,
    		final UserContext userContext) {
    	this.tokenProvider = tokenProvider;
    	this.userService = userService;
    	this.exceptionHandler = exceptionHandler;
    	this.userContext = userContext;
	}
    
    private ObjectMapper mapper = new ObjectMapper();
    
    /**
     * stateless 를 사용할 경우 SecurityContextHolder.getContext().setAuthentication 을 삭제해줘야한다. 
     * */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Long userId = tokenProvider.getUserIdFromToken(jwt);

                UserDetails userDetails = userService.loadUserById(userId);
                userContext.setCurrentUser(request, userDetails);
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
        	//로그는 StandardWrapperValve 여기서 찍기 때문에 내용을 찍을 필요 없음..
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        	response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(mapper.writeValueAsString(exceptionHandler.handleJwtException(request, ex).getBody()));
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
