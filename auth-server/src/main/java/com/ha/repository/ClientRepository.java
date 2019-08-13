package com.ha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ha.entity.AuthClientModel;

public interface ClientRepository extends JpaRepository<AuthClientModel, Long> {
	AuthClientModel findOneByClientId(String clientId);
}
