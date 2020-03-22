package com.smallchili.blog.service;

import java.util.List;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import com.smallchili.blog.dataobject.Article;
import com.smallchili.blog.dto.ArticleHeadMsgDTO;
import com.smallchili.blog.dto.ArticleUserDetail;
import com.smallchili.blog.vo.ArticleHeadPageVO;

import top.springdatajpa.zujijpa.Specifications;

/**
* @author xmz
* 2020-03-08
* 
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTest {

	@Autowired
	private ArticleService articleService;
	
	
	/**
	 * 测试插入
	 */
	@Test
	public void testInsert(){
		Article article = new Article();
		article.setUserId(1);
		article.setArticleTitle("我是傻子");
		article.setArticleContent("我也许是傻子,但是我很努力,我的爱好是吃饭睡觉,爱吃辣条...");
		article.setBigType(1);
		article.setArticleType(1);
		
		Article article2 = articleService.inserArticle(article);
		System.out.println(article2);
	}
	
	/**
	 * 测试更新
	 */
	@Test
	public void testUpdate(){
		Article article = new Article();
		article.setArticleId(6);
		article.setArticleTitle("我是chili");
		Article article2 = articleService.updateArticle(article);
		System.out.println(article2);
		
	}
	
	
	
	//测试通用分页条件查询
	@Test
	public void testFindAllAricleBySpec(){
		Integer userId = 1;
		Integer articleType = 2;
		Integer page = 1;
		
		//根据用户Id和类型查找
/*	      Specification<ArticleUserDetail> spec = (root,query,cb) ->{ 
	    	  
	    	 Path id = root.get("userId");
	    	 Path type = root.get("articleType");
	    	 
	    	 Predicate predicate = cb.equal(id, userId);
	    	 Predicate predicate2 = cb.equal(type, articleType);
	    	  
	 	    return cb.and(predicate,predicate2);
	 	  };*/

		String articleTitle = "第一天";
	     Specification<ArticleUserDetail> spec = Specifications.where(e ->{
	    	 e.eq("bigType", 1);
	    	 e.eq("userId", 1);
	     });
	 	  
		ArticleHeadPageVO articlePageVO = articleService.findAll(spec, page);
		articlePageVO.getContent().forEach(System.out::println);
	}
	
	
}
