package com.smallchili.blog.utils;

import java.util.Random;

public class RandomUtil {

	/**
	 * 随机获取一个昵称: 时间戳+随机数
	 */
	public static String getRandomName(){		
		String name =  "用户" +System.currentTimeMillis()+new Random().nextInt(10000);
		return name;
	}
	
}
