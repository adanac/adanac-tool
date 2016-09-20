package com.adanac.tool.entity;

import java.io.Serializable;

public class PersonDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 906082526601087966L;
	private String name;
	private int num;
	private String sex;
	private int age;

	public PersonDto() {

	}

	public PersonDto(String name, int num, String sex, int age) {
		super();
		this.name = name;
		this.num = num;
		this.sex = sex;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", num=" + num + ", sex=" + sex + ", age=" + age + "]";
	}

}
