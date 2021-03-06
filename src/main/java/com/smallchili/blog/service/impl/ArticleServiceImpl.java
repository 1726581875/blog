package com.smallchili.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.smallchili.blog.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smallchili.blog.dataobject.Article;
import com.smallchili.blog.dataobject.UserDetail;
import com.smallchili.blog.dataobject.UserStar;
import com.smallchili.blog.dto.ArticleHeadMsgDTO;
import com.smallchili.blog.dto.ArticleUserDetail;
import com.smallchili.blog.error.EmUserError;
import com.smallchili.blog.error.UserException;
import com.smallchili.blog.repository.ArticleRepository;
import com.smallchili.blog.repository.ArticleUserDTORepository;
import com.smallchili.blog.repository.UserDetailRepository;
import com.smallchili.blog.service.ArticleService;
import com.smallchili.blog.service.CommentService;
import com.smallchili.blog.service.ReplyService;
import com.smallchili.blog.service.UserStarService;
import com.smallchili.blog.vo.ArticleHeadPageVO;

import top.springdatajpa.zujijpa.Specifications;

/**
* @author xmz
* 2020-03-08
* 博客文章业务实现类
*/

@Service
public class ArticleServiceImpl implements ArticleService{
	
	@Autowired
	private ArticleRepository articleRepository;	
	@Autowired
	private ArticleUserDTORepository articleUserDTORepository;
	@Autowired
	private UserDetailRepository userDetailRepository;
	@Autowired
	private CommentService commentService;
	@Autowired
	private ReplyService replyService;	
    
    private List<UserStar> userArticleStar;

    //配置页数
	@Value("${pageSize}")
	private Integer pageSize;
	
	@Override
	public Article findById(Integer articleId) {
		Optional<Article> optional = articleRepository.findById(articleId);
		if(!optional.isPresent()){
			throw new UserException(EmUserError.ARTICLE_NOT_EXISI);
		}		
		return optional.get();
	}
	
	@Override
	public ArticleHeadPageVO findAll(Specification<ArticleUserDetail> spec, Integer page) {
	      
		   //构造页面条件 ,第几页,每页大小
		    Pageable pageable = PageRequest.of(page-1, pageSize, Sort.Direction.DESC ,"createTime");
	 	
		   //调用查找方法,返回Page对象
	 		Page<ArticleUserDetail> articleAndUserPage = articleUserDTORepository.findAll(spec, pageable);
	 		//获取page对象的list
	 		List<ArticleUserDetail> articleAndUserList = articleAndUserPage.getContent();	 		
            //转换为目标list,只保留需要的部分属性
	        List<ArticleHeadMsgDTO> articleHeadList = ArticleUtil.conversionArticleList(articleAndUserList);
		   //构造返回参数对象
	        ArticleHeadPageVO articlePageVO = new ArticleHeadPageVO();
	        articlePageVO.setContent(articleHeadList);
	        articlePageVO.setTotalPages(articleAndUserPage.getTotalPages());
	        articlePageVO.setNowPage(page);
	        
	 		return articlePageVO;
	}


	/**
	 * 2020/8/23 新增
	 * @param article
	 * @return
	 */
	@Override
	public Article save(Article article) {

		if(article.getArticleId() != null){ //如果id不为空，表示是更新
			Article saveArticle = findById(article.getArticleId());
			CopyUtil.notNullCopy(article,saveArticle);
			return  articleRepository.save(saveArticle);
		}
		//把点赞数和评论数为0
		article.setArticleStar(0);
		article.setCommentCount(0);
		article.setArticleView(0);
		return articleRepository.save(article);
	}


	/**
	 *
	 * @param article
	 * @return
	 * @deprecated
	 * 2020/8/23 ,推荐使用save方法代替()
	 */
	@Override
	@Deprecated
	public Article inserArticle(Article article){
		
	 //判断一下用户是否存在
	 Optional<UserDetail> userOptional = userDetailRepository.findById(article.getUserId());
     if(!userOptional.isPresent()){
    	 throw new UserException(EmUserError.USER_NOT_EXIST);
     }	 
		//把点赞数和评论数为0
		article.setArticleStar(0);
		article.setCommentCount(0);
		article.setArticleView(0);
		article.setArticleStatus(article.getArticleStatus());
		return articleRepository.save(article);
	}


	/**
	 *
	 * @param article
	 * @return
	 *
	 * @deprecated 2020/8/23 , 推荐使用save()方法代替
	 */
	@Override
	@Deprecated
	public Article updateArticle(Article article) {
		//先查
		Optional<Article> optional = articleRepository.findById(article.getArticleId());
		if(!optional.isPresent()){
			throw new UserException(EmUserError.ARTICLE_NOT_EXISI);
		}

		//选择性更新
		Article updateArticle = optional.get();
		CopyUtil.notNullCopy(article,updateArticle);

		/*
		if(article.getArticleTitle() != null){
			updateArticle.setArticleTitle(article.getArticleTitle());
		}
		if(article.getArticleContent() != null){
			updateArticle.setArticleContent(article.getArticleContent());
		}
		if(article.getBigType() != null){
			updateArticle.setBigType(article.getBigType());
		}
		if(article.getArticleType() != null){
			updateArticle.setArticleType(article.getArticleType());
		}
		if(article.getArticleStatus() != null){
			updateArticle.setArticleStatus(article.getArticleStatus());
		}
		if(article.getCommentCount() != null){
			updateArticle.setCommentCount(article.getCommentCount());
		}*/
		
		return articleRepository.save(updateArticle);
	}


	
	@Override
	public ArticleUserDetail findArticleById(Integer articleId) {
		Optional<ArticleUserDetail> optional = articleUserDTORepository.findById(articleId);		
		if(!optional.isPresent()){
			throw new UserException(EmUserError.ARTICLE_NOT_EXISI);
		}					
		return optional.get();
	}

	
	@Override
	public void articleStar(Integer articleId ,Integer star) {		
		Optional<Article> optional = articleRepository.findById(articleId);
		if(!optional.isPresent()){
			throw new UserException(EmUserError.ARTICLE_NOT_EXISI);
		}		
		Article article = optional.get();
		article.setArticleStar(article.getArticleStar() + star);	
		articleRepository.save(article);		
	}

	@Override
	@Transactional
	public void deleteArticle(Integer articleId) {
			
		articleRepository.deleteById(articleId);
			
	}

	@Override
	@Transactional
	public void deleteArticle(List<Integer> ids, Integer idType) {
				
		//先查出来
		List<Article> articleList = articleRepository.findAll(Specifications.where(e->{
			if(idType == CommonCode.USER)e.in("userId",ids); 
			if(idType == CommonCode.ARTICLE){
			e.in("articleId",ids);
			e.or(e2 -> e2.eq("articleStatus", CommonCode.ARTICLE_DELETED)
					     .eq("articleStatus", CommonCode.ARTICLE_DRAFT));
			}
		 }));
		
	if(!articleList.isEmpty() && articleList != null){		
		//删除文章
		articleRepository.deleteInBatch(articleList); 
		//取出id的List
		List<Integer> articleIdList = articleList.stream()
				  .map(e -> e.getArticleId()).collect(Collectors.toList());
		//删除所有文章评论
		commentService.deleteComment(articleIdList,CommonCode.ARTICLE);
		//删除文章的点赞记录
		replyService.deleteReply(articleIdList, CommonCode.ARTICLE);
		}
	}
	
	@Override
	public void deleteOrRecoverArticle(List<Integer> ids ,Integer flag) {
		//查出来
		List<Article> articleList = articleRepository.findAll(Specifications.where(e->{
		    e.in("articleId",ids);
			if(flag==CommonCode.ARTICLE_DELETED
			  || flag==CommonCode.ARTICLE_DRAFT)e.eq("articleStatus", CommonCode.ARTICLE_NORMAL);
			if(flag==CommonCode.ARTICLE_NORMAL){
				e.or(e2 -> e2.eq("articleStatus", CommonCode.ARTICLE_DELETED)
				             .eq("articleStatus", CommonCode.ARTICLE_DRAFT));						
			}		
		 }));
		
		articleList.stream().forEach(e -> e.setArticleStatus(flag));
		articleRepository.saveAll(articleList);
	}
	

	@Override
	public void addArticleViews(Integer articleId, Integer amount) {
         Article article = findById(articleId);
         article.setArticleView(article.getArticleView() + amount);
		 articleRepository.save(article);
	}


}
