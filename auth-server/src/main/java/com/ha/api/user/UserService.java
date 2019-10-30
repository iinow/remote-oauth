package com.ha.api.user;

import org.springframework.stereotype.Service;

import com.ha.entity.User;
import com.ha.exception.UserNotFoundException;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User getUser(String username) {
		return userRepository.findOneByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
	}
}
