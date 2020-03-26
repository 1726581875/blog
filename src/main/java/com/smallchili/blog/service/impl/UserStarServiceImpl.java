package com.smallchili.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smallchili.blog.dataobject.UserStar;
import com.smallchili.blog.repository.UserStarRepository;
import com.smallchili.blog.service.UserStarService;

/**
* @author xmz
* 2020-03-26
* 
*/
@Service
public class UserStarServiceImpl implements UserStarService{

	@Autowired
	private UserStarRepository userStarRepository;
	
	@Override
	public void toStar(Integer userId, Integer objId, Integer starType) {		
		
		UserStar userStar = userStarRepository.findByUserIdAndObjIdAndStarType(userId, objId, starType);
		if(userStar == null){
			UserStar starMsg = new UserStar();
			starMsg.setUserId(userId);
			starMsg.setObjId(objId);
			starMsg.setStarType(starType);
			userStarRepository.save(starMsg);
		}else{
			userStarRepository.deleteById(userStar.getStarId());		
		}
	}

	@Override
	public boolean isStar(Integer userId, Integer objId, Integer starType) {
		UserStar userStar = userStarRepository.findByUserIdAndObjIdAndStarType(userId, objId, starType);
		if(userStar == null){
			return false;
		}
		return true;
	}

}
