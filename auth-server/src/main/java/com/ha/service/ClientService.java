package com.ha.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ha.entity.AuthClientModel;
import com.ha.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public AuthClientModel findOneByClientId(String clientId) {
		AuthClientModel client = repository.findOneByClientId(clientId);
		Hibernate.initialize(client);
		return client;
	}
	
	@Transactional
	public AuthClientModel insert(AuthClientModel client) {
		return repository.save(client);
	}
}
