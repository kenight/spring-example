package com.example.customize.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.example.customize.domain.UserEntity;

public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	// 不改变默认逻辑，只演示增加一些额外操作，对 User 信息的修改
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		UserEntity user = new CustomUserDetailsService().loadUserBySql("maiyo");
		user.setLastLoginTime(new Date());
		
		// 接下来的逻辑交给父类，不作改动
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
