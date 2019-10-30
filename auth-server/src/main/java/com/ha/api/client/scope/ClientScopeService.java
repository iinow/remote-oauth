package com.ha.api.client.scope;

import org.springframework.stereotype.Service;

@Service
public class ClientScopeService {

	private ClientScopeRepository clientScopeRepository;
	
	public ClientScopeService(ClientScopeRepository clientScopeRepository) {
		this.clientScopeRepository = clientScopeRepository;
	}
}
