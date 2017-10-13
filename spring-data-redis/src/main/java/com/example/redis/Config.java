package com.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Config {

	// 自定义 RedisTemplate 配置 （非必须）

	@Autowired
	private RedisTemplate redisTemplate;

	@Bean
	public RedisTemplate customizeRedisTemplate() {
		// 默认的 KeySerializer 使用 JdkSerializationRedisSerializer 会出现乱码
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		return redisTemplate;
	}

}
