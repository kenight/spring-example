package com.example.customize.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.customize.domain.RoleEntity;
import com.example.customize.domain.UserEntity;
import com.xuanmo.framework.core.common.Md5;

public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) {
		UserEntity user = loadUserBySql(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		// 放入角色到 GrantedAuthority 中，该用户如果对应多角色，这里需要循环
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

		return new User(user.getUsername(), user.getPassword(), authorities);
	}

	// 模拟服务层方法从数据库取出 user
	public UserEntity loadUserBySql(String username) {
		if (!username.equals("maiyo"))
			return null;

		UserEntity user = new UserEntity();
		user.setId(1);
		user.setUsername("maiyo");
		user.setPassword(Md5.MD5Encode("pass"));
		user.setIsDisable(false);
		user.setLastLoginTime(new Date());

		RoleEntity role = new RoleEntity();
		role.setId(1);
		role.setName("ROLE_USER");
		Set<String> resource = new HashSet<String>();
		resource.add("/my");
		resource.add("/my/other");
		role.setResource(resource);

		user.setRole(role);

		return user;
	}

}
