package com.adanac.tool.entity;

import java.io.Serializable;
import java.util.Arrays;

public class Student implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4316329687734557972L;
	private Integer id;
	private String name;
	private String sex;
	private Integer age;
	private String[] hobby;

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String[] getHobby() {
		return hobby;
	}

	public void setHobby(String[] hobby) {
		this.hobby = hobby;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", sex=" + sex + ", age=" + age + ", hobby="
				+ Arrays.toString(hobby) + "]";
	}

}
