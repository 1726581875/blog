package com.smallchili.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.smallchili.blog.dataobject.ArticleComment;

/**
* @author xmz
* 2020-03-12
* 
*/
public interface CommentRepository 
extends JpaRepository<ArticleComment, Integer>, JpaSpecificationExecutor<ArticleComment>{

}
