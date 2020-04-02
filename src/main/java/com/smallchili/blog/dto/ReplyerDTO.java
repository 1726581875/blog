package com.smallchili.blog.dto;

import java.util.Date;

import javax.persistence.Transient;

import lombok.Data;

/**
* @author xmz
* 2020-03-14
* 封装回复者的信息
* 
*/
@Data
public class ReplyerDTO {

	private Integer replyId;
	
	private Integer replyerId;
	
	private String replyerName;
	
	private String replyerImage;
	
	private Integer toUserId;
	
	private String toUserName;
	
	private String toUserImage;
	
	private String replyContent;
	
	private Integer replyStar;
	
	private Date createTime;
	
    private boolean isStar;
}
