package com.smallchili.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.smallchili.blog.dto.ArticleUserDetail;

/**
* @author xmz
* 2020-03-10
* Article和UserDetail 连表查询
*/
public interface ArticleUserDTORepository 
                     extends JpaRepository<ArticleUserDetail, Integer>,JpaSpecificationExecutor<ArticleUserDetail>{

}
