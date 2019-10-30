package com.ha.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "health")
@RestController
public class HealthController {

	@GetMapping
	public ResponseEntity<?> getHealth(
			HttpServletRequest request, HttpServletResponse response){
		return ResponseEntity.ok(true);
	}
}
