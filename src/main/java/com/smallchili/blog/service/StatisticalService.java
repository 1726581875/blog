package com.smallchili.blog.service;

import com.smallchili.blog.vo.ArticleHeadPageVO;

/**
* @author xmz
* 2020-04-06
* 
*/
public interface StatisticalService {

	public ArticleHeadPageVO statisticalRank(Integer page);
	
}
