package com.smallchili.blog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.smallchili.blog.dataobject.UserStar;
import com.smallchili.blog.repository.UserStarRepository;

/**
* @author xmz
* 2020-03-26
* 
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class StarTest {
	
	@Autowired
	private UserStarRepository starRepository;
	@Autowired
	private UserStarService starService;
	
	@Test
	public void testFindStar(){
		Integer userId = 1;
		Integer objId = 1;
		Integer starType = 1;
		UserStar userStar = starRepository.findByUserIdAndObjIdAndStarType(userId, objId, starType);
	   System.out.println(userStar);
	   
	   starService.toStar(userId, objId, starType);
	}

}
