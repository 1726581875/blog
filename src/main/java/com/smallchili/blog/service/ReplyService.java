package com.smallchili.blog.service;

import java.util.List;

import com.smallchili.blog.dataobject.CommentReply;
import com.smallchili.blog.dto.ReplyerDTO;

/**
* @author xmz
* 2020-03-12
*评论的回复业务接口 
*/
public interface ReplyService {
     /**
      * 查询某条评论的回复
      * */
	public List<ReplyerDTO> findAllReplyByComnentId(Integer commentId);
     
	public CommentReply inserCommentReply(CommentReply reply);
	
	public void deleteReply(Integer replyId);
	
	public void deleteReply(List<Integer> replyIds);
	
	public void deleteReplyByCommentId(Integer commontId);
}
