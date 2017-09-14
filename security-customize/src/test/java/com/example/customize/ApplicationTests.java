package com.example.customize;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
	
	@Autowired
	private ApplicationContext ctx;

	@Test
	public void contextLoads() {
		String[] beanNames = ctx.getBeanDefinitionNames();
		System.out.println("bean count: " + beanNames.length);
		for (String name : beanNames) {
			System.out.println(name);
		}
	}

}
