package com.smallchili.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.smallchili.blog.dataobject.UserStar;

/**
* @author xmz
* 2020-03-26
* 
*/
public interface UserStarRepository extends JpaRepository<UserStar, Integer>
                                  ,JpaSpecificationExecutor<UserStar>{

	public UserStar findByUserIdAndObjIdAndStarType(Integer userId,Integer objId,Integer starType);
	
}
