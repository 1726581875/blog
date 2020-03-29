package com.smallchili.blog.dataobject;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

/**
 * 评论回复实体类
 *
 */
@Entity
@Data
public class CommentReply {
    
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer replyId;
	
	private Integer commentId;
	
	private Integer toUserId;
	
	private Integer replyerId;
	
	private String replyContent;
	
	private Integer replyStar;
	
	private Integer replyStatus;
	
	private Date createTime;
	
	@ManyToOne
	@JoinColumn(name="replyerId",insertable= false,updatable = false)
	private UserDetail replyer;
	
	@ManyToOne
	@JoinColumn(name="toUserId",insertable= false,updatable = false)
	private UserDetail toUser;


	
}
