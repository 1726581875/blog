package com.smallchili.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.smallchili.blog.dataobject.Article;

/**
* @author xmz
* 2020-03-08
* JpaRepository 常用增删查改
* JpaSpecificationExecutor 做分页
*/
public interface ArticleRepository 
      extends JpaRepository<Article, Integer>,JpaSpecificationExecutor<Article>{

	
	
}
