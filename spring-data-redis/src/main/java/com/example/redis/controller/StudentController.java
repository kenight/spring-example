package com.example.redis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.redis.dao.StudentRepository;
import com.example.redis.domain.Student;
import com.example.redis.domain.Student.Gender;

@RestController
public class StudentController {

	@GetMapping("/add")
	public void add(String id, String name) {
		Student student = new Student(id, name, Gender.MALE, 20);
		repository.save(student);
	}

	@GetMapping("/update")
	public void update(String id) {
		Student student = repository.find(id);
		student.setGender(Gender.FEMALE);
		student.setAge(25);
		repository.update(student);
	}

	@GetMapping("/delete")
	public void delete(String id) {
		repository.delete(id);
	}

	@GetMapping("/list")
	public List<Student> list() {
		return repository.findAll();
	}

	@Autowired
	private StudentRepository repository;
}
