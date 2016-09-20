/**
 * 上海蓝鸟集团
 * 上海蓝鸟科技股份有限公司
 * 华东工程中心（无锡）
 * 2015版权所有
 */
package com.adanac.tool.j2se.reflect;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Java常用数据类型枚举
 */
public enum Types {

	INT(Integer.class), SHORT(Short.class), LONG(Long.class), CHAR(Character.class), STRING(String.class), BYTE(
			Byte.class), FLOAT(Float.class), DOUBLE(Double.class), DATE(Date.class), BIGDECIMAL(BigDecimal.class);

	private Class<?> clazz;

	Types(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getValue() {
		return clazz;
	}

}
