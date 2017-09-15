package com.example.customize.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import com.xuanmo.framework.core.common.Captcha;

// 自定义过滤器，处理 Captcha
// 参考 UsernamePasswordAuthenticationFilter 类的写法
// 使用 RequestMatcher 判断拦截请求
// 使用 SimpleUrlAuthenticationFailureHandler 处理失败后的逻辑 (与 UsernamePasswordAuthenticationFilter 相同)
// 本来直接打算注入 UsernamePasswordAuthenticationFilter 直接使用 RequestMatcher 和 AuthenticationFailureHandler，共享路径等配置
// 结果发现 WebSecurityConfigurerAdapter 在配置时，以上类都是直接 new 出来后，并未放入 Spring 容器
public class CustomCaptchaAuthenticationFilter implements Filter {

	public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";

	private String failureUrl;
	private AuthenticationFailureHandler failureHandler;
	private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;
	private String loginProcessingUrl;
	private RequestMatcher requestMatcher;

	public void init(FilterConfig filterConfig) throws ServletException {
		if (failureUrl == null)
			failureUrl = "/login?error";
		this.failureHandler = new SimpleUrlAuthenticationFailureHandler(failureUrl);

		if (loginProcessingUrl == null)
			loginProcessingUrl = "/login";
		this.requestMatcher = new AntPathRequestMatcher(loginProcessingUrl, "POST");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (!requestMatcher.matches(request)) {
			chain.doFilter(request, response);
			return;
		}

		String captcha = obtainCaptcha(request);

		try {
			authentication(captcha, request);
		} catch (AuthenticationException e) {
			failureHandler.onAuthenticationFailure(request, response, e);
			return;
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	private boolean authentication(String captcha, HttpServletRequest q) {
		if (!StringUtils.hasText(captcha))
			throw new BadCaptchaException("NULL CAPTCHA");
		if (!Captcha.verifyAndClean("session_key_captcha", captcha, q))
			throw new BadCredentialsException("BAD CAPTCHA");
		return true;
	}

	private String obtainCaptcha(HttpServletRequest request) {
		return request.getParameter(captchaParameter);
	}

	public String getFailureUrl() {
		return failureUrl;
	}

	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}

	public String getCaptchaParameter() {
		return captchaParameter;
	}

	public void setCaptchaParameter(String captchaParameter) {
		this.captchaParameter = captchaParameter;
	}

	public String getLoginProcessingUrl() {
		return loginProcessingUrl;
	}

	public void setLoginProcessingUrl(String loginProcessingUrl) {
		this.loginProcessingUrl = loginProcessingUrl;
	}

}
