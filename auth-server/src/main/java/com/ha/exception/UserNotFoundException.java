package com.ha.exception;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String msg) {
		super("user not found, username: "+msg);
	}
	
	public UserNotFoundException() {}
}