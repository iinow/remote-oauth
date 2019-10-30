package com.ha.api.client.granttype;

import org.springframework.stereotype.Service;

@Service
public class ClientGrantTypeService {

	private ClientGrantTypeRepository clientGrantTypeRepository;
	
	public ClientGrantTypeService(ClientGrantTypeRepository clientGrantTypeRepository) {
		this.clientGrantTypeRepository = clientGrantTypeRepository;
	}
}
