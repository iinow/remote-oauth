package com.ha.exception;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String msg) {
		super("user not found, email: "+msg);
	}
	
	public UserNotFoundException(Long id) {
		super("user not found, id: "+id);
	}
	
	public UserNotFoundException() {}
}
