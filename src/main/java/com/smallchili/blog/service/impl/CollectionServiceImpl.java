package com.smallchili.blog.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.smallchili.blog.dataobject.Article;
import com.smallchili.blog.dataobject.Collection;
import com.smallchili.blog.error.EmUserError;
import com.smallchili.blog.error.UserException;
import com.smallchili.blog.repository.CollectionRepository;
import com.smallchili.blog.service.ArticleService;
import com.smallchili.blog.service.CollectionService;
import com.smallchili.blog.vo.PageVO;

import top.springdatajpa.zujijpa.Specifications;

/**
* @author xmz
* 2020-03-28
* 
*/
@Service
public class CollectionServiceImpl implements CollectionService{

	@Autowired
	private CollectionRepository collectionRepository;
	@Autowired
	private ArticleService articleService;
	
    //配置页数
	@Value("${pageSize}")
	private Integer pageSize;
	
	@Override
	public PageVO<Collection> findUserCollection(Integer userId, Integer page) {
		
		Page<Collection> collectionPage = collectionRepository.findAll(Specifications.where(e ->{
			e.eq("userId", userId);
		}),PageRequest.of(page-1,pageSize));
		
		PageVO<Collection> pageVO = new PageVO<Collection>();
		pageVO.setNowPage(page);
		pageVO.setTotalPages(collectionPage.getTotalPages());
		pageVO.setContent(collectionPage.getContent());
		return pageVO;
	}

	@Override
	public void addInCollection(Integer userId, Integer articleId) {
		Article article = articleService.findById(articleId);
		if(article == null){
			throw new UserException(EmUserError.ARTICLE_NOT_EXISI);
		}
	//是否是已收藏
	Collection collection = collectionRepository.findByUserIdAndArticleId(userId, articleId);
		if(collection != null){
			throw new UserException(EmUserError.COLLECTION_REPEAT);
		}
	
		Collection newCollection = new Collection();
		newCollection.setUserId(userId);
		newCollection.setArticleId(articleId);
		newCollection.setArticleTitle(article.getArticleTitle());
		collectionRepository.save(newCollection);
		
	}

	@Override
	public void removeFromCollection(List<Integer> collectionIds) {
		List<Collection> list = collectionRepository.findAll(Specifications.where(e ->{
			e.in("collectionId", collectionIds);
		}));
		if(!list.isEmpty() && list != null){
			collectionRepository.deleteInBatch(list);
		}else{
			throw new UserException(EmUserError.ARTICLE_NOT_EXISI);
		}
		
	}

	@Override
	public boolean isCollection(Integer userId, Integer articleId) {
		
		Optional<Collection> optional = collectionRepository.findOne(Specifications.where(e ->{
			e.eq("userId", userId);
			e.eq("articleId", articleId);
		}));
		return optional.isPresent() ? true : false;
	}

}
