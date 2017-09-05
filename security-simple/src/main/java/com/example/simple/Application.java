package com.example.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 最简单的示例 <br>
 * Spring boot 项目加入 Security 后, 默认所以请求都需要认证通过后才能访问
 * 
 * 
 * @author MaiYo
 *
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
