package com.smallchili.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import com.mysql.fabric.xmlrpc.base.Array;
import com.smallchili.blog.dataobject.ArticleComment;
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
public class CommentServiceTest {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private CommentRepository commmentRepository;
	@Autowired
	private ReplyRepository replyRepository;
	
	@Test
	public void testFindAllComment(){
		Integer articleId = 1;
		Integer page = 1;
		commentService.findAllCommentByArticleId(articleId, page)
		.getCommentList().forEach(System.out::println);
	}
	
	@Test
	public void deleteAllCommentByArticleId(){
		List<Integer> commentIds = new ArrayList<Integer>();
		commentIds.add(1);
		commentIds.add(2);
		commentIds.add(3);
		commentService.deleteComment(commentIds);
	}
	
	@Test
	public void deleteCommentByArticleId(){
        List<Integer> list =  new ArrayList<Integer>();
        list.add(4);
        list.add(5);      
		//List<ArticleComment> commentList = commmentRepository.findAllById(list);
		
		Specification<CommentReply> spec = Specifications.where(e->{
			e.eq("commentId",list); 
	      });

        replyRepository.findAll(spec).forEach(System.out::println);
		
		//commmentRepository.deleteInBatch(commentList);
	}
	
}
