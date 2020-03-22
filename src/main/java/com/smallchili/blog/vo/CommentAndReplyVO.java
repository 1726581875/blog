package com.smallchili.blog.vo;

import java.util.List;

import com.smallchili.blog.dto.CommentAndReplyDTO;

import lombok.Data;

/**
* @author xmz
* 2020-03-12
* 
* 文章详情展示页
*/
@Data
public class CommentAndReplyVO {
	
	private Integer totalPages;
	
	private List<CommentAndReplyDTO> commentList;
	
	
}
