package com.ha.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.stereotype.Component;

import com.ha.api.user.UserService;
import com.ha.entity.User;

/**
 * @author BISHOP
 * @since 2019.11.05
 * */
@Component
public class UserContext {
	
	private UserService userService;
	
	public UserContext(
			final UserService userService) {
		this.userService = userService;
	}

	public void setCurrentUser(HttpServletRequest request, UserDetails userDetails) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		if(request != null) {
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	public void setCurrentUser(UserDetails userDetails) {
		this.setCurrentUser(null, userDetails);
	}
	
	public UserPrincipal getCurrentUserPrincipal() {
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return null;
        }
        return (UserPrincipal) authentication.getPrincipal();
	}
	
	public User getCurrentUser() {
//		org.springframework.security.core.userdetails.User
		UserPrincipal prin = getCurrentUserPrincipal();
		if(prin == null) {
			throw new RuntimeException();
		}
		return userService.getUser(prin.getId());
	}
}
