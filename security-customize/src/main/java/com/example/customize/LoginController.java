package com.example.customize;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.xuanmo.framework.core.common.Captcha;

@Controller
public class LoginController {

	// 自定义视图, 处理信息提示
	// @GetMapping("/login")
	public String login(String error, String logout, ModelMap model) {
		if (error != null)
			model.addAttribute("message", "用户或密码错误，请重试");
		if (logout != null)
			model.addAttribute("message", "已注销并退出系统");
		return "login";
	}

	// 获取详细错误信息
	@GetMapping("/login")
	public String login(String error, String logout, ModelMap model, HttpServletRequest q) {
		if (error != null) {
			// 一般是返回 BadCredentialsException , 因为 hideUserNotFoundExceptions
			AuthenticationException ex = (AuthenticationException) q.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			model.addAttribute("message", ex.getMessage());
		}
		if (logout != null)
			model.addAttribute("message", "已注销并退出系统");
		return "login";
	}

	@GetMapping("/captcha")
	public void captcha(HttpServletRequest q, HttpServletResponse r) throws IOException {
		Captcha.generateLite("session_key_captcha", q, r);
	}

}
