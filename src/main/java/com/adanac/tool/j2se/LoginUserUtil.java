package com.adanac.tool.j2se;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.adanac.tool.entity.User;

public class LoginUserUtil {
	public static User loginUser() {
		// 项目根目录
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		User user = (User) request.getSession().getAttribute("loginuser");

		return user;
	}

	public static void main(String[] args) {
		System.out.println(loginUser().getName());
	}

}
