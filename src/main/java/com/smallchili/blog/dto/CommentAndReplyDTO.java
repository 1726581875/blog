package com.smallchili.blog.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import lombok.Data;

/**
* @author xmz
* 2020-03-12
* 封装评论并携带该评论的回复
* 
*/
@Data
public class CommentAndReplyDTO {

	private Integer commentId;
	
	private Integer userId;
	
	private String userName;
	
	private String userImage;
	
	private String commentContent;
	
	private Integer commentStar;
	
	private Date createTime;
	
	private List<ReplyerDTO> replyList;

    private boolean isStar;
	
}
