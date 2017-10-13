package com.example.customize.domain;

import java.util.Set;

// 角色实体
public class RoleEntity {

	private Integer id;
	private String name;
	private Set<String> resource; // 模拟资源 url

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getResource() {
		return resource;
	}

	public void setResource(Set<String> resource) {
		this.resource = resource;
	}

}
