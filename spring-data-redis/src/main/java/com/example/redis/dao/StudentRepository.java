package com.example.redis.dao;

import java.util.List;

import com.example.redis.domain.Student;

public interface StudentRepository {

	void save(Student student);

	void update(Student student);

	void delete(String id);

	Student find(String id);

	List<Student> findAll();

}
