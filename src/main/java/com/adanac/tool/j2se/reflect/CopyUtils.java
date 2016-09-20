package com.adanac.tool.j2se.reflect;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 调用org.apache.commons.beanutils.BeanUtils.copyProperties(Object,
 * Object)方法实现对象属性的拷贝
 */
public class CopyUtils {

	/**
	 * 将源对象的属性拷贝到目标对象中
	 * 
	 * @param source
	 *            源对象
	 * @param target
	 *            目标对象
	 */
	public static void copyProperties(Object source, Object target) {
		try {
			BeanUtils.copyProperties(target, source);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
