package com.smallchili.blog.utils;
/**
* @author xmz
* 2020-03-28
* 
*/
public class CommonCode {

	//代表类型是文章
	public static final int ARTICLE = 1;
	//代表类型是评论
	public static final int COMMENT = 2;
	//代表类型是回复
	public static final int REPLY = 3;
	//类型是用户
	public static final int USER = 4;
		
	//点赞加一
	public static final int STAR_UP = 1;
	//点赞减一
	public static final int STAR_DOWN = -1;
	
	//文章状态是草稿
	public static final int ARTICLE_DRAFT = 0;
	//正常
	public static final int ARTICLE_NORMAL = 1;
	//已删除
	public static final int ARTICLE_DELETED = 2;
}
