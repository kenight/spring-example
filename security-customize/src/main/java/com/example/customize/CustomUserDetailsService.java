package com.example.customize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.xuanmo.framework.core.common.Md5;

public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) {
		UserEntity user = loadUserBySql();

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : user.getRoles()) {
			// Spring security GrantedAuthority 默认以 ROLE_ 开头
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		}

		return new User(user.getUsername(), user.getPassword(), authorities);
	}

	// 模拟从数据库取出 user
	private UserEntity loadUserBySql() {
		UserEntity user = new UserEntity();
		user.setId(1);
		user.setUsername("maiyo");
		user.setPassword(Md5.MD5Encode("123321"));
		user.setIsDisable(false);

		Set<String> roles = new HashSet<String>();
		roles.add("USER");

		user.setRoles(roles);
		return user;
	}

}
