package com.ha.exception;

public class CheckTokenConnectException extends RuntimeException {
	public CheckTokenConnectException() {
		super("connection error");
	}
}
