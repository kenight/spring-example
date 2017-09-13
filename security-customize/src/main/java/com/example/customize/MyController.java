package com.example.customize;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

// 需要认证后访问的视图
@Controller
public class MyController {

	// 通过 session 在页面获取也是可以的

	@GetMapping("/my")
	public String my(@AuthenticationPrincipal User user, ModelMap model) {
		model.addAttribute("username", user.getUsername());
		model.addAttribute("last", new CustomUserDetailsService().loadUserBySql(user.getUsername()).getLastLoginTime()); // 模拟服务层方法取出用户实体
		return "my/index";
	}

	@GetMapping("/my/other")
	public String other(ModelMap model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		model.addAttribute("username", user.getUsername());
		return "my/index";
	}

}
