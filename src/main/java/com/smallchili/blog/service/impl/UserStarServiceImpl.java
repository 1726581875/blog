package com.smallchili.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smallchili.blog.dataobject.UserStar;
import com.smallchili.blog.repository.UserStarRepository;
import com.smallchili.blog.service.ArticleService;
import com.smallchili.blog.service.CommentService;
import com.smallchili.blog.service.ReplyService;
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
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private ReplyService replyService;
	
	
	@Override
	@Transactional
	public void toStar(Integer userId, Integer objId, Integer starType) {		
		
		UserStar userStar = userStarRepository.findByUserIdAndObjIdAndStarType(userId, objId, starType);
		if(userStar == null){
			UserStar starMsg = new UserStar();
			starMsg.setUserId(userId);
			starMsg.setObjId(objId);
			starMsg.setStarType(starType);
			userStarRepository.save(starMsg);
			toChangeStarSum(objId,starType,1);
			
		}else{
			userStarRepository.deleteById(userStar.getStarId());
			toChangeStarSum(objId,starType,-1);
		}
	}

	
	public void toChangeStarSum(Integer objId,Integer starType,int star){
		if(starType == 1){
		articleService.articleStar(objId, star);
		}
		if(starType == 2){
		commentService.commentStar(objId, star);
		}
		if(starType == 3){
		replyService.replyStar(objId, star);
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
