package com.smallchili.blog.vo;

import java.util.List;

import lombok.Data;

/**
* @author xmz
* 2020-03-29
* 
*/
@Data
public class PageVO <T> {

	private Integer totalPages;
	
	private Integer nowPage;
	
	private List<T> content;
	
}
