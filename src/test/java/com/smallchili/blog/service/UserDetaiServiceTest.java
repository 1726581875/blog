package com.smallchili.blog.service;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.smallchili.blog.dataobject.UserDetail;
import com.smallchili.blog.repository.UserDetailRepository;
import com.smallchili.blog.service.impl.UserDetailServiceImpl;
import com.smallchili.blog.utils.RandomUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDetaiServiceTest {

	@Autowired
	UserDetailServiceImpl userService;
	
	@Autowired
	UserDetailRepository repository;
	
	@Test
	public void testFindDetailById(){
		UserDetail userDetail = userService.findDetailById(1);
		System.out.println(userDetail);
	}
	
	@Test
	public void testUpdateUserDetail(){
		UserDetail userDetail = new UserDetail();
        userDetail.setUserId(10);
        userDetail.setUserName(RandomUtil.getRandomName());
        userDetail.setUserAge(0);
        userDetail.setUserSex("女");
        userDetail.setUserMotto("1111");
        
		userService.updateUserDetail(userDetail);
	}
	
	@Test
	public void testInsertDetail(){
		Integer userId = 10;
		userService.insertUserDetail(userId);
	}
	
	@Test
	public void testFindByName(){
		String name = "oneGe";
		UserDetail findByUserN1ume = repository.findByUserName(name);
		System.out.println(findByUserN1ume);
	}
	
}
