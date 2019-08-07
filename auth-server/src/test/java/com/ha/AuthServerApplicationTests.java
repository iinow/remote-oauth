package com.ha;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ha.config.AppConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServerApplicationTests {
	
	@Autowired
	private AppConfig app;

	@Test
	public void contextLoads() {
		System.out.println(app.toString());
	}
}
