package com.example.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/")		// 匹配请求路径 '/'
					.permitAll()		// 允许未认证用户访问
				.anyRequest()			// 所有的请求(排除掉上面放行的)
					.authenticated()	// 通过认证的任意角色用户可以访问
				.and()					// 返回 HttpSecurity
			.formLogin()				// 通过登录进行认证，默认映射为 /login, Spring boot 也会提供一个默认页面
				.permitAll()			// 允许未认证用户访问 /login
				.and()					// 返回 HttpSecurity
			.logout()					// 配置注销的支持，默认映射为 /logout
				.permitAll();			// 允许未认证用户访问 /logout
	}
	
	@Autowired // 注入全局的 AuthenticationManagerBuilder, 如果使用 @Override 则会新建一个 AuthenticationManager 实例
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication() // 使用基于内存的 AuthenticationProvider
			.withUser("user").password("123321").roles("USER"); // 构建一个 User 给 userDetailsManager, 而该类继承于 UserDetailsService
	}

}
