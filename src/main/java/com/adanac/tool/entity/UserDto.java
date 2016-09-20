package com.adanac.tool.entity;

import java.io.Serializable;

public class UserDto implements Serializable {

	private static final long serialVersionUID = -2392811205898246815L;

	private Integer id;
	private String name;
	private String password;
	private int age;
	private int sex;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", password=" + password + ", age=" + age + ", sex=" + sex
				+ "]";
	}

}
