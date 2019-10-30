package com.ha.controller;

import static org.junit.Assert.assertNotNull;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ha.api.client.ClientService;
import com.ha.entity.Client;
import com.ha.entity.ClientGrantType;
import com.ha.entity.ClientScope;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientControllerTests {
	
	@Autowired
	private ClientService clientService;

	@Test
	public void getClient() {
		Client c = clientService.getClient("client");
		assertNotNull(c);
	}
	
	@Test
	public void addClient() {
		Client c = new Client();
		c.setClientId("client");
		c.setSecret("{noop}secret");
		
		ClientScope scope = new ClientScope();
		scope.setScope("read");
		scope.setClient(c);
		
		ClientGrantType grant = new ClientGrantType();
		grant.setGrantType("read");
		grant.setClient(c);
		
		c.getScopes().add(scope);
		c.getGranttypes().add(grant);
		
		c = clientService.insert(c);
		assertNotNull(c.getId());
	}
}
