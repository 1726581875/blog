package com.smallchili.blog;

import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

	@Test
	public void contextLoads() {
		
		String regex = "^1\\d{10}$";
		boolean flag = Pattern.matches(regex, "14698569874");
		System.out.println(flag);
		
	}

}
