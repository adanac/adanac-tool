package com.adanac.tool.thirdparty.redis;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 回掉抽象类
 * @author zhangs
 *
 * @param <T>
 */
public abstract class Cacheable<T> {

	/**
	 * 泛型类的class
	 */
	protected Class<T> clazz;

	public Cacheable() {
		Class clazz = getClass();  
        while (clazz != Object.class) {  
            Type t = clazz.getGenericSuperclass();  
            if (t instanceof ParameterizedType) {  
                Type[] args = ((ParameterizedType) t).getActualTypeArguments();  
                if (args[0] instanceof Class) {  
                    this.clazz = (Class<T>) args[0];  
                    break;  
                }  
            }  
            clazz = clazz.getSuperclass();  
        }  
	}
	/**
	 * 会掉方法
	 * @return
	 */
	public abstract T call();
	
}
