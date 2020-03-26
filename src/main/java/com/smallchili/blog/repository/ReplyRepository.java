package com.smallchili.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.smallchili.blog.dataobject.CommentReply;

/**
* @author xmz
* 2020-03-12
* 
*/
public interface ReplyRepository extends JpaRepository<CommentReply, Integer>
            ,JpaSpecificationExecutor<CommentReply>{

	 @Modifying
	 @Transactional
     @Query("delete from CommentReply where commentId=?1") 
	 public void deleteByCommentId(Integer commentId);
	 
	 public void deleteCommentReplyByReplyIdIn(List<CommentReply> replyIds);
	 
	 public void deleteCommentReplyByCommentIdIn(List<CommentReply> commentIds);
	 
	 
}
