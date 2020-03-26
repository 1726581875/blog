package com.smallchili.blog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.smallchili.blog.repository.CommentRepository;
import com.smallchili.blog.repository.ReplyRepository;

/**
* @author xmz
* 2020-03-15
* 
*/

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReplyServiceTest {

	@Autowired
	private ReplyService replyService;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Test
	public void testFindAllReply(){
		replyRepository.findAll().forEach(System.out::println);
	}
	
	@Test
	public void testFindAllComment(){
		commentRepository.findAll().forEach(System.out::println);
	}
	
	@Test
	public void testFinAllReplyByCommentId(){
		Integer commentId = 1;
		replyService.findAllReplyByComnentId(commentId).forEach(System.out::println);
	}
	
	
	@Test
	public void deleteReplyByCommentId(){
		replyService.deleteReplyByCommentId(1);
	}
	
}
