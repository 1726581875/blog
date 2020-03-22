package com.smallchili.blog.service;

import org.springframework.data.jpa.domain.Specification;

import com.smallchili.blog.dataobject.Article;
import com.smallchili.blog.dto.ArticleUserDetail;
import com.smallchili.blog.vo.ArticleHeadPageVO;

/**
* @author xmz
* 2020-03-08
* 文章业务接口
*/

public interface ArticleService {
	
	
	/**
	 * 2020/3/12
	 * 说明:
	 * 通用方法,是以上方法的抽取,用于满足多种条件查询,减少代码冗余
	 * 
	 * @param spec 构造的条件
	 * @param page  页数
	 * @return 包装过的文章信息
	 */
	public ArticleHeadPageVO findAll(Specification<ArticleUserDetail> spec,Integer page);
	
	
	/**
	 * 新增文章信息
	 * @param article
	 * @return
	 */
	public Article inserArticle(Article article);
	
	
	/**
	 * 修改文章信息
	 * @param article
	 * @return
	 */
	public Article updateArticle(Article article);
	
	
	
	/**
	 * 查找文章详情
	 * @param articleId
	 * @return
	 */
	public ArticleUserDetail findArticleById(Integer articleId);
	
	
	/**
	 * 文章点赞
	 * @param articleId
	 */
	public void StarToArticle(Integer articleId);
	
}
