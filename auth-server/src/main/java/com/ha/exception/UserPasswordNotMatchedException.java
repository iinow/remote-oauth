package com.ha.exception;

public class UserPasswordNotMatchedException extends RuntimeException {
	public UserPasswordNotMatchedException(String username) {
		super("userpassword not matched, password: "+username);
	}
}
