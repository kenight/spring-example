package com.example.redis.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.redis.domain.Student;

@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class StudentRepositoryImpl implements StudentRepository {

	/**
	 * 通过 Redis Hash 模拟数据库表存储 Student 对象, 存储结构:表名-主键-对象
	 */

	private static final String KEY = "Student";

	@PostConstruct
	private void init() {
		this.hashOps = customizeRedisTemplate.opsForHash();
	}

	public void save(Student student) {
		this.hashOps.put(KEY, student.getId(), student);
	}

	public void update(Student student) {
		this.hashOps.put(KEY, student.getId(), student);
	}

	public void delete(String id) {
		this.hashOps.delete(KEY, id);
	}

	public Student find(String id) {
		return (Student) this.hashOps.get(KEY, id);
	}

	public List<Student> findAll() {
		Map<String, Student> map = this.hashOps.entries(KEY);
		List<Student> list = new ArrayList<Student>();
		for (String id : map.keySet()) {
			list.add(map.get(id));
		}
		return list;
	}

	public Set<String> keys(Object pattern) {
		return customizeRedisTemplate.keys(pattern);
	}

	private HashOperations<String, String, Student> hashOps;

	// 注意:注入的属性名称，如果名称为 redisTemplate 则是原 Spring boot 自动配置的 redisTemplate
	@Autowired
	private RedisTemplate customizeRedisTemplate;

}
