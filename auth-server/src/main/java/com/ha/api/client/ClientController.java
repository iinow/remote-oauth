package com.ha.api.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ha.entity.Client;

@RestController
@RequestMapping("clients")
public class ClientController {
	
	@Autowired
	private ClientService service;
	
	@GetMapping(name = "", headers = {"version=1"})
	public ResponseEntity<?> getClientModel(
			@RequestParam(name = "clientid", required = true) String clientId,
			HttpServletRequest request, HttpServletResponse response){
		Client client = service.getClient(clientId);
		return ResponseEntity.ok(client);
	}
}
