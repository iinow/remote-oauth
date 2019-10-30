package com.ha.config.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import com.ha.api.client.ClientService;
import com.ha.entity.Client;

@Service
public class ClientDetailService implements ClientDetailsService {

	private Map<String, ClientDetailsDecorator> map = new HashMap<>();

	@Autowired
	private ClientService clientService;
	
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		if(map.get(clientId) == null) {
			Client client = clientService.getClient(clientId);
			map.put(clientId, new ClientDetailsDecorator(client));
		}
		return map.get(clientId);
	}
}
