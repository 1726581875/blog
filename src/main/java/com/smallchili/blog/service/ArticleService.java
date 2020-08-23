package com.smallchili.blog.service;

import java.util.List;

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
	
	public Article findById(Integer articleId);
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
	 * 2020/8/23
	 * 插入和更新方法
	 * @param article
	 * @return
	 */
	public Article save(Article article);

	/**
	 * 新增文章信息
	 * @param article
	 * @return
	 * @deprecated 2020/8/23 , 推荐使用save()方法代替
	 */
	@Deprecated
	public Article inserArticle(Article article);
	
	
	/**
	 * 修改文章信息
	 * @param article
	 * @return
	 * @deprecated 2020/8/23 , 推荐使用save()方法代替
	 */
	@Deprecated
	public Article updateArticle(Article article);
	
	/**
	 * 查找文章详情
	 * @param articleId
	 * @return
	 */
	public ArticleUserDetail findArticleById(Integer articleId);
	
	
	/**
	 * 文章点赞或取消
	 * @param articleId
	 * @param star -1 或 1
	 */
	public void articleStar(Integer articleId , Integer star);
	
	public void deleteArticle(Integer articleId);
	
	public void deleteArticle(List<Integer> ids ,Integer idsType);
	
	public void deleteOrRecoverArticle(List<Integer> articleIds,Integer flag);
	
	/**
	 * @param articleId 文章id
	 * @param amount 增加的加阅读量数量
	 */
	public void addArticleViews(Integer articleId, Integer amount);

	
}
