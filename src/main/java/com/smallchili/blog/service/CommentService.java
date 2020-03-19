package com.smallchili.blog.service;

import org.springframework.data.domain.Page;

import com.smallchili.blog.dataobject.ArticleComment;
import com.smallchili.blog.vo.CommentAndReplyVO;

/**
* @author xmz
* 2020-03-12
* 文章的评论service层接口
*/
public interface CommentService {

	public CommentAndReplyVO findAllCommentByArticleId(Integer articleId , Integer page);
	
	public ArticleComment insertComment(ArticleComment comment);
	
	public void deleteComment(Integer commentId);
	
}
