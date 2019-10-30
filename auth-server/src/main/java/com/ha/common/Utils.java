package com.ha.common;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Utils {
	public static String passwordEncoded(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
	
	public static boolean passwordMatch(String password, String encodedPassword) {
		return BCrypt.checkpw(password, encodedPassword);
	}
}
