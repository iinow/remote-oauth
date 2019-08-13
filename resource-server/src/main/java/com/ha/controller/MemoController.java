package com.ha.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("memos")
public class MemoController {

	@GetMapping(name = "")
	public ResponseEntity<?> getMemo(
			HttpServletResponse response, HttpServletRequest request) {
		return ResponseEntity.ok(null);
	}
}
