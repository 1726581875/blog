package com.smallchili.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.smallchili.blog.dataobject.Collection;

/**
* @author xmz
* 2020-03-28
* 
*/
public interface CollectionRepository extends JpaRepository<Collection, Integer>
   , JpaSpecificationExecutor<Collection>{
	
	public Collection findByUserIdAndArticleId(Integer userId,Integer articleId);

}
