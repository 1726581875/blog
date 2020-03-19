package com.smallchili.blog.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.smallchili.blog.dataobject.UserLogin;
import com.smallchili.blog.repository.UserLoginRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserLoginServiceTest {

	@Autowired
	UserLoginService userService;

	
	@Test
	public void testLoginByPhone(){
		String phone = "12345975691";
		UserLogin user = userService.loginByPhone(phone);
		System.out.println(user);
	}
	
	@Test
	public void testLoginByPassword(){
		String username = "111";
		String password = "123";
		UserLogin user = userService.loginByPassword(username, password);
		System.out.println(user);
	}
	
	@Test
	public void testUpdate(){
		
		String userPhone = "13922077569";
		String username = "111";
		String password = "123";
		UserLogin result = userService.updatePasswordByPhone(userPhone, username, password);
		System.out.println(result);
	}
	
	@Test
	public void testInsert(){
		UserLogin user = new UserLogin();
		  user.setUserPhone("12345975691");
		  user.setUsername("123");
		  user.setPassword("123");
		  user.setRole(1);
		UserLogin result = userService.register(user);
		System.out.println(result);
	}
	
	@Test
	public void testDelete(){
		Integer id = 7;
	    userService.delete(id);
		System.out.println(id);
	}
	
}
