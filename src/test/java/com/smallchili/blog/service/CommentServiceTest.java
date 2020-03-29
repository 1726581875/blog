package com.smallchili.blog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.fabric.xmlrpc.base.Array;
import com.smallchili.blog.dataobject.ArticleComment;
import com.smallchili.blog.dataobject.CommentReply;
import com.smallchili.blog.repository.CommentRepository;
import com.smallchili.blog.repository.ReplyRepository;
import com.smallchili.blog.utils.CommonCode;

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
	@Transactional
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
		commentService.deleteComment(commentIds,CommonCode.COMMENT);
	}
	
	@Test
	@Transactional
   public void testfind(){
	   Optional<ArticleComment> optional = commmentRepository.findById(7);
	   if(optional.isPresent()){
	   ArticleComment comment = optional.get();
	   System.out.println(comment);
	   System.out.println(comment.getReplyList() == null);
	   System.out.println(comment.getReplyList().isEmpty());
	   comment.getReplyList().forEach(System.out::println);
	   }
}
	
}
