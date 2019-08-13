package com.ha.config.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import com.ha.entity.AuthClientModel;
import com.ha.service.ClientService;

@Service
public class AuthClientDetailService implements ClientDetailsService {

	private Map<String, AuthClientDetailsDecorator> map = new HashMap<>();

	@Autowired
	private ClientService clientService;
	
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		if(map.get(clientId) == null) {
			AuthClientModel client = clientService.findOneByClientId(clientId);
			map.put(clientId, new AuthClientDetailsDecorator(client));
		}
		return map.get(clientId);
	}
}
