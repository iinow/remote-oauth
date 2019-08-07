package com.ha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ResourceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceServerApplication.class, args);
	}

	@GetMapping("/test")
	public String get() {
		return "Hello world";
	}
	
	@GetMapping("/test2")
	public String get2() {
		return "Hello world2";
	}
	
	@GetMapping("/oauth/redirect")
	public String kakaoRedirect(
			@RequestParam(name = "code") String code) {
		return code;
	}
}
