package com.ha.api.client;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ha.entity.Client;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public Client findOneByClientId(String clientId) {
		Client client = repository.findOneByClientId(clientId);
		Hibernate.initialize(client);
		return client;
	}
	
	@Transactional
	public Client insert(Client client) {
		return repository.save(client);
	}
}
