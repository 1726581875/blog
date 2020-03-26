package com.smallchili.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import com.smallchili.blog.dataobject.CommentReply;
import com.smallchili.blog.repository.CommentRepository;
import com.smallchili.blog.repository.ReplyRepository;

import top.springdatajpa.zujijpa.Specifications;

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
	
	@Test
	public void deleteCommentByCommentId(){
        List<Integer> list =  new ArrayList<Integer>();
        list.add(1);
        list.add(5);      
        
		Specification<CommentReply> spec = Specifications.where(e->{
			e.in("commentId", list);
	      });

        List<CommentReply> all = replyRepository.findAll(spec);
        replyRepository.deleteInBatch(all);
		//commmentRepository.deleteInBatch(commentList);
	}
	
	
}
