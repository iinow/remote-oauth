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
 * @param username
 * @param password
 * */

@Component
public class BeforeClientCheckFilter extends OncePerRequestFilter {

	private UserService userService;
	
	public BeforeClientCheckFilter(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			User user = userService.getUser(username);
			if(!Utils.passwordMatch(password, user.getPassword())) {
				throw new UserPasswordNotMatchedException(username);
			}
			
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			response.getWriter().write(e.getMessage());
		}
	}
}
