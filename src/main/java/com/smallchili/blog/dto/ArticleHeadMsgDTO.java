package com.smallchili.blog.dto;

import java.util.Date;

import lombok.Data;

/**
* @author xmz
* 2020-03-08
* 一行信息包括的内容
* 用于返回部分Artitle的信息,标题,id,用户名,点赞数
*/
@Data
public class ArticleHeadMsgDTO {
   
	private Integer articleId;

	private Integer userId;
	
	private String userName;
	
	private String userImage;
	
	private String articleTitle;	   
	
	private Integer articleStar;

	private Integer commentCount;
	
	private Integer articleView;
	
	private Date createTime;

	
}
