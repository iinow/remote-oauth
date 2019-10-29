package com.ha.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ha.api.service.UserServiceImpl;
import com.ha.common.Define.Provider;

@RestController
@RequestMapping(name = "/users")
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;
	
	@GetMapping(name = "")
	public ResponseEntity<?> getUser(
			@RequestParam(name = "type", required = true) Integer resourceType,
			HttpServletRequest request, HttpServletResponse response){
		
		String token = request.getHeader("Authorization").replaceAll("Bearer", "").trim();
		Object res = userService.getUser(Provider.findProvider(resourceType), token);
		return ResponseEntity.ok(res);
	}
}
