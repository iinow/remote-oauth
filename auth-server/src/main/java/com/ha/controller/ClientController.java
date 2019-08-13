package com.ha.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ha.entity.AuthClientModel;
import com.ha.service.ClientService;

@RestController
@RequestMapping("clients")
public class ClientController {
	
	@Autowired
	private ClientService service;
	
	@GetMapping(name = "", headers = {"version=1"})
	public ResponseEntity<?> getClientModel(
			@RequestParam(name = "clientid", required = true) String clientId,
			HttpServletRequest request, HttpServletResponse response){
		AuthClientModel client = service.findOneByClientId(clientId);
		return ResponseEntity.ok(client);
	}
}