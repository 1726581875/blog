package com.smallchili.blog.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
* @author xmz
* 2020-03-09
* cookie工具类
*/
public class CookieUtil {

	public static void set(HttpServletResponse response,
			String name,String value ,int maxAge){
		
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);		
	} 
	
	
	
}
