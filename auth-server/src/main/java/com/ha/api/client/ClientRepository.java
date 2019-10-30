package com.ha.api.client;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ha.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	Client findOneByClientId(String clientId);
}
