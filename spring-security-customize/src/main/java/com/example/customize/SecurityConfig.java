package com.example.customize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.customize.security.CustomCaptchaAuthenticationFilter;
import com.example.customize.security.CustomSuccessHandler;
import com.example.customize.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() // 关闭 csrf token 验证
				.addFilterBefore(customCaptchaAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests().antMatchers("/my/**").hasRole("USER").and()
				.formLogin().loginPage("/login").successHandler(customSuccessHandler()).and() // 使用自定义页面必须定义 loginPage
				.logout();
	}

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService()).passwordEncoder(new Md5PasswordEncoder());
	}

	/** 下面 Bean 的注入也可以使用 @Autowired 结合 @Component 进行注入 */

	@Bean
	public CustomUserDetailsService customUserDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public CustomSuccessHandler customSuccessHandler() {
		return new CustomSuccessHandler();
	}

	@Bean
	public CustomCaptchaAuthenticationFilter customCaptchaAuthenticationFilter() throws Exception {
		return new CustomCaptchaAuthenticationFilter();
	}

}
