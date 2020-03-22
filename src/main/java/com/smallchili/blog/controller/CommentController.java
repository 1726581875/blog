package com.smallchili.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smallchili.blog.dataobject.ArticleComment;
import com.smallchili.blog.dataobject.CommentReply;
import com.smallchili.blog.error.EmUserError;
import com.smallchili.blog.error.UserException;
import com.smallchili.blog.service.CommentService;
import com.smallchili.blog.service.ReplyService;
import com.smallchili.blog.vo.CommentAndReplyVO;
import com.smallchili.blog.vo.Result;

/**
* @author xmz
* 2020-03-15
* 评论controller
*/

@RestController
@CrossOrigin(allowCredentials = "true" ,allowedHeaders = "*")
public class CommentController extends BaseController{

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ReplyService replyService;
	
	/**
	 * 查找文章的评论并携带评论回复
	 * @param articleId
	 * @param page
	 * @return
	 */
	@GetMapping("/article/comments")
	private Object findAllCommentAndReply(@RequestParam("articleId") Integer articleId,
			                        @RequestParam(value = "page",defaultValue = "1") Integer page){
		
		CommentAndReplyVO commentAndReplyVO = commentService.findAllCommentByArticleId(articleId, page);
		
		return new Result<CommentAndReplyVO>(EmUserError.SUCCESS, commentAndReplyVO);
	}
	
	
	@PostMapping("insert/comment")
	public Object insertComment(@RequestParam("articleId") Integer articleId,
			             @RequestParam("commentId") Integer commentId,
			             @RequestParam("userId") Integer userId,
			             @RequestParam("toUserId") Integer toUserId,
			             @RequestParam("content") String content){
		
		
		if(StringUtils.isEmpty(content)
				|| StringUtils.isEmpty(userId)
				|| StringUtils.isEmpty(articleId)){
			throw new UserException(EmUserError.PARAMETER_ERROR);
		}
		
		//康康是不是要插入回复
		if(!StringUtils.isEmpty(commentId) 
				&& !StringUtils.isEmpty(toUserId)){
			 CommentReply reply = new CommentReply();
			 reply.setCommentId(commentId);
			 reply.setReplyerId(userId);
			 reply.setToUserId(toUserId);
			 reply.setReplyContent(content);
			 replyService.inserCommentReply(reply);
			 return new Result<Object>(EmUserError.SUCCESS,null);
		}
		
		//插入文章评论
		ArticleComment comment = new ArticleComment();
		 comment.setArticleId(articleId);
		 comment.setUserId(userId);
	     comment.setCommentContent(content);
	  ArticleComment articleComment = commentService.insertComment(comment);
	if(articleComment == null){
		throw new UserException(EmUserError.UNKONW_ERROR);
	}
	
	return new Result<Object>(EmUserError.SUCCESS,null);
	}
	
	
	
	@PostMapping("delete/comment")
	public Object deleteComment(@RequestParam("commentId") Integer commentId){	
     commentService.deleteComment(commentId);
	return new Result<Object>(EmUserError.SUCCESS,null);
	}
	
	@PostMapping("delete/reply")
	public Object deleteReply(@RequestParam("replyId") Integer replyId){	
		replyService.deleteReply(replyId);	
	return new Result<Object>(EmUserError.SUCCESS,null);
	}
}
