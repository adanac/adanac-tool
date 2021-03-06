package com.adanac.tool.entity;

import java.util.Date;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class OrderExcel {

	@Attribute
	private String id;

	@Element
	private String code;

	@Element
	private Date date;

	public OrderExcel() {
	}

	public OrderExcel(String id, String code, Date date) {
		super();
		this.id = id;
		this.code = code;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
