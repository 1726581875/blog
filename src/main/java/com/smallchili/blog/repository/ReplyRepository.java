package com.smallchili.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.smallchili.blog.dataobject.CommentReply;

/**
* @author xmz
* 2020-03-12
* 
*/
public interface ReplyRepository extends JpaRepository<CommentReply, Integer>
            ,JpaSpecificationExecutor<CommentReply>{

}
