package com.smallchili.blog.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smallchili.blog.dataobject.Collection;
import com.smallchili.blog.dataobject.UserStar;
import com.smallchili.blog.dto.ArticleHeadMsgDTO;
import com.smallchili.blog.service.CollectionService;
import com.smallchili.blog.service.UserStarService;
import com.smallchili.blog.vo.ArticleHeadPageVO;

/**
* @author xmz
* 2020-04-03
* 
*/
@Service
public class CommonUtil {

    @Autowired
    private  UserStarService userStarService;
    
	@Autowired
	private CollectionService collectionService;
    
	public void toSetStar(ArticleHeadPageVO articleHeadPageVO,Integer userId){
		System.out.println(userStarService);
		List<ArticleHeadMsgDTO> articleList = articleHeadPageVO.getContent();
		List<Integer> articleIds = articleList.stream().map(e -> e.getArticleId()).collect(Collectors.toList());
		articleIds.forEach(System.out::println);
		List<UserStar> starList = userStarService.findUserStar(userId, articleIds, CommonCode.ARTICLE);
		List<Collection> collectionList = collectionService.findUserCollection(userId);
		articleList.forEach(e ->{
			starList.forEach(e2 -> {
				if(e.getArticleId() == e2.getObjId()){
					e.setStar(true);
					starList.remove(e.getArticleId());
				}				
			});
			collectionList.forEach(e2 -> {
				if(e.getArticleId() == e2.getArticleId()){
					e.setCollection(true);
					starList.remove(e.getArticleId());
				}	
			});
			
		});
	
	}
	
	
}
