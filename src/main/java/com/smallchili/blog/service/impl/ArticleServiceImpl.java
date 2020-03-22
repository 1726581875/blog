package com.smallchili.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.smallchili.blog.dataobject.Article;
import com.smallchili.blog.dataobject.UserDetail;
import com.smallchili.blog.dto.ArticleHeadMsgDTO;
import com.smallchili.blog.dto.ArticleUserDetail;
import com.smallchili.blog.error.EmUserError;
import com.smallchili.blog.error.UserException;
import com.smallchili.blog.repository.ArticleRepository;
import com.smallchili.blog.repository.ArticleUserDTORepository;
import com.smallchili.blog.repository.UserDetailRepository;
import com.smallchili.blog.service.ArticleService;
import com.smallchili.blog.vo.ArticleHeadPageVO;

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
	
	
    //配置页数
	@Value("${pageSize}")
	private Integer pageSize;
	
	@Override
	public ArticleHeadPageVO findAll(Specification<ArticleUserDetail> spec, Integer page) {
	      
		 //构造页面条件 ,第几页,每页大小
		    Pageable pageable = PageRequest.of(page-1,pageSize);	
	 	
		   //调用查找方法,返回Page对象
	 		Page<ArticleUserDetail> articleAndUserPage = articleUserDTORepository.findAll(spec, pageable);
	 		//获取page对象的list
	 		List<ArticleUserDetail> articleAndUserList = articleAndUserPage.getContent();
            //转换为目标list,只保留需要的部分属性
	        List<ArticleHeadMsgDTO> articleHeadList = copyListfromArticleUD(articleAndUserList);          
	        //构造返回参数对象
	        ArticleHeadPageVO ArticlePageVO = new ArticleHeadPageVO();
	        ArticlePageVO.setContent(articleHeadList);
	        ArticlePageVO.setTotalPages(articleAndUserPage.getTotalPages());
	        
	 		return ArticlePageVO;
	}
	
	/**
	 * @param List<ArticleUserDetail> 连表查的list(属性太多)
	 * @return List<ArticleHeadMsgDTO> 目标list(只保留需要属性)
	 */
	public List<ArticleHeadMsgDTO> copyListfromArticleUD(List<ArticleUserDetail> list){
		//java8的lambda表达式妙用，转换List<ArticleUserDetail> --> List<ArticlePageDTO>
		List<ArticleHeadMsgDTO> articleDtoList = list.stream().map(e -> 			
		   copyObject.apply(e)//调用下面的111行处的lambda函数
		).collect(Collectors.toList()); 
		
		return articleDtoList;
	}
	
	/**
	 * copy单个对象
	 * 作用：ArticleUserDetail给ArticlePageDTO的相同属性值赋
	 * 使用 BeanUtils.copyProperties(source,target)
	 */
    Function<ArticleUserDetail,ArticleHeadMsgDTO> copyObject = (articleUserDetail) -> {
	    	ArticleHeadMsgDTO articlePageDTO = new ArticleHeadMsgDTO();
	    	 BeanUtils.copyProperties(articleUserDetail,articlePageDTO);
	    	 BeanUtils.copyProperties(articleUserDetail.getUserDetail(),articlePageDTO);
	    	 return articlePageDTO;
	    };
	    	

	@Override
	public Article inserArticle(Article article) {
		
	 //判断一下用户是否存在
	 Optional<UserDetail> userOptional = userDetailRepository.findById(article.getUserId());
     if(!userOptional.isPresent()){
    	 throw new UserException(EmUserError.USER_NOT_EXIST);
     }
	 
		//把点赞数和评论数为0
		article.setArticleStar(0);
		article.setCommentCount(0);	
		return articleRepository.save(article);
	}


	@Override
	public Article updateArticle(Article article) {
		//先查
		Optional<Article> optional = articleRepository.findById(article.getArticleId());
		if(!optional.isPresent()){
			throw new UserException(EmUserError.ARTICLE_NOT_EXISI);
		}
		
		//选择性更新
		Article updateArticle = optional.get(); 
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
	public void StarToArticle(Integer articleId) {		
		Optional<Article> optional = articleRepository.findById(articleId);
		if(optional.isPresent()){
			throw new UserException(EmUserError.ARTICLE_NOT_EXISI);
		}
		
		Article article = optional.get();
		article.setArticleStar(article.getArticleStar() + 1);
		
		articleRepository.save(article);
		
	}

}
