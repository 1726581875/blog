package com.smallchili.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import com.smallchili.blog.dataobject.Article;
import com.smallchili.blog.dto.ArticleUserDetail;
import com.smallchili.blog.vo.CommentAndReplyVO;
import com.smallchili.blog.vo.ArticleHeadPageVO;

/**
* @author xmz
* 2020-03-08
* 文章业务接口
*/

public interface ArticleService {

	/**
	 * 根据文章类型查找文章
	 * @param type 文章类型
	 * @param page 页数
	 * @return 包装过的文章信息  方便页面显示
	 */
	public ArticleHeadPageVO findAllAricleByType(Integer type ,Integer page);
	
	/**
	 * 查找全部文章
	 * @param page 页数
	 * @return 包装过的文章信息  方便页面显示
	 */
	public ArticleHeadPageVO findAll(Integer page);
	
	/**
	 * 根据用户Id查找文章
	 * @param type 文章类型
	 * @param page 页数
	 * @return 包装过的文章信息  方便页面显示
	 */
	public ArticleHeadPageVO findAllAricleByUserId(Integer userId ,Integer page);
	
	
	/**
	 * 2020/3/12
	 * 说明:
	 * 通用方法,是以上方法的抽取,用于满足多种条件查询,减少代码冗余
	 * 可以说有了这方法,前面的三个方法都作废了
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
	
	
}
