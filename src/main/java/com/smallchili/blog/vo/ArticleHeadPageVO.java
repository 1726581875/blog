package com.smallchili.blog.vo;

import java.util.List;

import com.smallchili.blog.dto.ArticleHeadMsgDTO;

import lombok.Data;

/**
* @author xmz
* 2020-03-09
* 做分页
*/
@Data
public class ArticleHeadPageVO {

	private Integer totalPages;
	
	private List<ArticleHeadMsgDTO> content;

	
	
}
