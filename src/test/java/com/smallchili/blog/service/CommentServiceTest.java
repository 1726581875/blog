package com.smallchili.blog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
* @author xmz
* 2020-03-15
* 
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {

	@Autowired
	private CommentService commentService;
	
	@Test
	public void testFindAllComment(){
		Integer articleId = 1;
		Integer page = 1;
		commentService.findAllCommentByArticleId(articleId, page)
		.getCommentList().forEach(System.out::println);
	}
	
	
	
}
