package com.ha.api.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ha.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findOneByUsername(String username);
}
