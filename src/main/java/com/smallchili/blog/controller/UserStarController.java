package com.smallchili.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smallchili.blog.dto.ArticleUserDetail;
import com.smallchili.blog.error.EmUserError;
import com.smallchili.blog.error.UserException;
import com.smallchili.blog.service.UserStarService;
import com.smallchili.blog.utils.CommonCode;
import com.smallchili.blog.vo.Result;

/**
* @author xmz
* 2020-03-26
* 
*/
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserStarController {

	@Autowired
	private UserStarService starService;
	
	/**
	 *    点赞
	 * @param userId 必传
	 * 以下三个选其一传
	 * @param articleId 
	 * @param commentId
	 * @param replyId
	 * @return
	 */
	@PostMapping("/star")
	public Object userStar(@RequestParam("userId") Integer userId,
			 @RequestParam(value = "articleId" ,defaultValue="0") Integer articleId,
             @RequestParam(value = "commentId" ,defaultValue="0") Integer commentId,
             @RequestParam(value = "replyId" ,defaultValue="0") Integer replyId){
		
		if(StringUtils.isEmpty(userId)){
			throw new UserException(EmUserError.PARAMETER_ERROR);
		}
		if(articleId != 0){
			starService.toStar(userId, articleId, CommonCode.ARTICLE);
			
		}
		if(commentId != 0){
			starService.toStar(userId, commentId, CommonCode.COMMENT);
		}
		if(replyId != 0){
			starService.toStar(userId, replyId, CommonCode.REPLY);
		}
		
		return new Result<ArticleUserDetail>(EmUserError.SUCCESS,null);
	}

	
	
	/**
	 * 判断是否点赞
	 * @param userId
	 * @param articleId
	 * @param commentId
	 * @param replyId
	 * @return
	 */
	@GetMapping("/isStar")
	public Object isStar(@RequestParam("userId") Integer userId,
			               @RequestParam(value = "articleId" ,defaultValue="0") Integer articleId,
			               @RequestParam(value = "commentId" ,defaultValue="0") Integer commentId,
			               @RequestParam(value = "replyId" ,defaultValue="0") Integer replyId){
		
		if(StringUtils.isEmpty(userId)){
			throw new UserException(EmUserError.PARAMETER_ERROR);
		}
		
		boolean flag = false;
		
		if(articleId != 0){
			flag = starService.isStar(userId, articleId, 1);
		}
		if(commentId != 0){
			flag = starService.isStar(userId, commentId, 2);
		}
		if(replyId != 0){
			flag = starService.isStar(userId, replyId, 3);
		}
		
		return new Result<Object>(EmUserError.SUCCESS,flag);
	}
	
	
}
