package com.ha.controller;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ha.api.user.UserService;
import com.ha.common.AuthDefine.PROVIDER;
import com.ha.common.Utils;
import com.ha.entity.User;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTests {
	private Logger log = LoggerFactory.getLogger(UserControllerTests.class);
	
	@Autowired
	private UserService userService;

	@Test
	public void passwordEncode() {
		String password = "1234";
		String encode = Utils.passwordEncoded(password);
		log.info(password + ", encode: "+encode);
		assertNotEquals(encode, password);
	}
	
	@Test
	public void getUser() {
		User u = new User();
		u.setEmail("haha@haha.com");
		u.setName("Joon-Ha");
		u.setProvider(PROVIDER.LOCAL);
		u.setPassword(Utils.passwordEncoded("1234"));
		assertNotNull(userService.addUser(u).getId());
	}
}
