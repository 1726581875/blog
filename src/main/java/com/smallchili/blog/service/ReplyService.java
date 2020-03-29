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
	
	/**
	 * 根据id删除回复
	 * @param ids (Id数组:可以是commentId,也可以是reply数组)
	 * @param flag (1 表示传的是commentId数组 ,0表示传的是reply数组)
	 */
	public void deleteReply(List<Integer> ids, Integer flag);
	
	public void deleteReplyByCommentId(Integer commontId);
	
	public void replyStar(Integer replyId,Integer star);
}
