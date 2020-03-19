package com.smallchili.blog.utils;

import java.util.regex.Pattern;

public class CheckUtil {

	public static boolean checkPhone(String phone){		
		  String regex = "^1\\d{10}$";
		  boolean flag = Pattern.matches(regex, phone);
		  return flag;
	}
	
	
	
}
