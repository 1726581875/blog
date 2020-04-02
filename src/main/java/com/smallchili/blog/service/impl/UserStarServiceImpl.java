package com.smallchili.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smallchili.blog.dataobject.UserStar;
import com.smallchili.blog.repository.UserStarRepository;
import com.smallchili.blog.service.ArticleService;
import com.smallchili.blog.service.CommentService;
import com.smallchili.blog.service.ReplyService;
import com.smallchili.blog.service.UserStarService;
import com.smallchili.blog.utils.CommonCode;

import top.springdatajpa.zujijpa.Specifications;

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
			toChangeStarSum(objId,starType,CommonCode.STAR_UP);
			
		}else{
			userStarRepository.deleteById(userStar.getStarId());
			toChangeStarSum(objId,starType,CommonCode.STAR_DOWN);
		}
	}

	
	public void toChangeStarSum(Integer objId,Integer starType,int star){
		if(starType == CommonCode.ARTICLE){
		articleService.articleStar(objId, star);
		}
		if(starType == CommonCode.COMMENT){
		commentService.commentStar(objId, star);
		}
		if(starType == CommonCode.REPLY){
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


	@Override
	public void deleteStar(List<Integer> objIds, Integer starType) {
		List<UserStar> list = userStarRepository.findAll(Specifications.where(e->{
			 if(starType == CommonCode.ARTICLE){e.in("objId", objIds);} 
			 if(starType == CommonCode.COMMENT){e.in("objId", objIds);}
			 if(starType == CommonCode.REPLY){e.in("objId", objIds);}
			}));
		
		if(!list.isEmpty() && list != null)
		userStarRepository.deleteInBatch(list);
	}


	@Override
	public List<UserStar> findUserStar(Integer userId,List<Integer> objIds, Integer starType) {
           	
		return userStarRepository.findAll(Specifications.where(e->{
			 if(starType == CommonCode.ARTICLE)e.in("objId", objIds);
			 if(starType == CommonCode.COMMENT)e.in("objId", objIds);
			 if(starType == CommonCode.REPLY)e.in("objId", objIds);
			 e.eq("userId", userId);
			 e.eq("starType", starType);
			}));
	}

}
