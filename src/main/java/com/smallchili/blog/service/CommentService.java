package com.smallchili.blog.service;

import java.util.List;

import com.smallchili.blog.dataobject.ArticleComment;
import com.smallchili.blog.vo.CommentAndReplyVO;

/**
* @author xmz
* 2020-03-12
* 文章的评论service层接口
*/
public interface CommentService {

	public CommentAndReplyVO findAllCommentByArticleId(Integer articleId , Integer page);
	
	//public findCommentByUserId();
	
	public ArticleComment insertComment(ArticleComment comment);
	
	public void deleteComment(Integer commentId);
	
	public void deleteComment(List<Integer> ids , Integer idType);
	
	public void commentStar(Integer commentId,Integer star);
	
}
