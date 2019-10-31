package com.ha.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ha.api.user.UserService;
import com.ha.common.Utils;
import com.ha.entity.User;
import com.ha.exception.UserPasswordNotMatchedException;

/**
 * @author BISHOP
 * @since 2019.10.30
 * 
 * BasicAuthenticationFilter에서 grant_type을 확인하기 전에 해당 필터를 탄다.
 * 
 * MIME = x-www-form-urlencoded
 * 
 * @param email
 * @param password
 * */

@Component
public class BeforeClientCheckFilter extends OncePerRequestFilter {

	private final static String path = "/oauth/token";
	
	private UserService userService;
	
	public BeforeClientCheckFilter(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			if(path.equals(request.getRequestURI())) {
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				
				User user = userService.getUserByEmail(email);
				if(!Utils.passwordMatch(password, user.getPassword())) {
					throw new UserPasswordNotMatchedException(email);
				}
			}
		} catch (Exception e) {
			response.getWriter().write(e.getMessage());
			return;
		}
		filterChain.doFilter(request, response);
	}
}
