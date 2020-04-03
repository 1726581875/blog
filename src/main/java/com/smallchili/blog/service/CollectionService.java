package com.smallchili.blog.service;

import java.util.List;

import com.smallchili.blog.dataobject.Collection;
import com.smallchili.blog.vo.PageVO;

/**
* @author xmz
* 2020-03-28
* 
*/
public interface CollectionService {
	
	public PageVO<Collection> findUserCollection(Integer userId,Integer page);
	
	public void addInCollection(Integer userId,Integer articleId);
	
	public void removeFromCollection(List<Integer> collectionIds);
	
	public boolean isCollection(Integer userId , Integer articleId);

	public List<Collection> findUserCollection(Integer userId);
}
