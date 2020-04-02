package com.smallchili.blog.service;

import java.util.List;

import com.smallchili.blog.dataobject.UserStar;

/**
* @author xmz
* 2020-03-26
* 点赞接口
* 
*/
public interface UserStarService {

	public void toStar(Integer userId,Integer objId,Integer starType);
	
	public boolean isStar(Integer userId,Integer objId,Integer starType);
	
	public void deleteStar(List<Integer> objIds,Integer starType);
	
	public List<UserStar> findUserStar(Integer userId ,List<Integer> objIds,Integer starType);
	
}
