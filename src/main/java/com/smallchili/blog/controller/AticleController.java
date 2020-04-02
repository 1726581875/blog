package com.smallchili.blog.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smallchili.blog.dataobject.Article;
import com.smallchili.blog.dataobject.UserDetail;
import com.smallchili.blog.dto.ArticleUserDetail;
import com.smallchili.blog.error.EmUserError;
import com.smallchili.blog.error.UserException;
import com.smallchili.blog.service.ArticleService;
import com.smallchili.blog.service.CollectionService;
import com.smallchili.blog.service.UserStarService;
import com.smallchili.blog.utils.CheckUtil;
import com.smallchili.blog.utils.CommonCode;
import com.smallchili.blog.vo.ArticleHeadPageVO;
import com.smallchili.blog.vo.Result;

import top.springdatajpa.zujijpa.Specifications;

/**
* @author xmz
* 2020-03-08
* 
*/
@RestController
@CrossOrigin(allowCredentials = "true" ,allowedHeaders = "*")
@RequestMapping("/article")
public class AticleController extends BaseController{

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private UserStarService starService;
	
	@Autowired
	private CollectionService collectionService;
	
	@Value("${imagePath}")
	private String IMAGE_PATH;
	
	/**
	 * 2020/3/13
	 * 根据条件动态查询文章
	 * @param article 文章对象
	 * @param page 第几页
	 * @return
	 */
	@GetMapping("/condition/select")
	public Result<ArticleHeadPageVO> findArticlesByCondition(Article article,
			                 @RequestParam(value = "page",defaultValue="1") Integer page,
			                 HttpSession session){
		//若状态不传,默认是查正常状态的文章
	     if(article.getArticleStatus() == null)
	    	 article.setArticleStatus(CommonCode.ARTICLE_NORMAL);
	     
		//构造条件
	     Specification<ArticleUserDetail> spec = Specifications.where(e ->{
	    	 if(article.getBigType() != null){
	    	 e.eq("bigType", article.getBigType());
	    	 }
	    	 if(article.getArticleType()!= null){
	    		e.eq("articleType",article.getArticleType());
	    	 }
	    	 if(article.getUserId() != null){
	    	 e.eq("userId", article.getUserId());
	    	 }
	    	 if(article.getArticleTitle()!= null){
	    		e.like("articleTitle", "%"+article.getArticleTitle()+"%");
	    	}
             e.eq("articleStatus", article.getArticleStatus());
	     });
		
		//调用查找方法	
        ArticleHeadPageVO pageVO = articleService.findAll(spec, page);
	 
		return   new Result<ArticleHeadPageVO>(EmUserError.SUCCESS,pageVO);
	}
	
	
	
	/**
	 * 
	 * 查找文章信息
	 * @param articleId
	 * @return
	 */
	@GetMapping("/detail")
	public Object findArticleById(@RequestParam("articleId") Integer articleId ,HttpSession session){
		
		ArticleUserDetail articleUserDetail = articleService.findArticleById(articleId);
		//看用户是否登录
		UserDetail user = (UserDetail) session.getAttribute("user");
		if(user!=null){
		boolean isStar = starService.
				 isStar(user.getUserId(), articleId, CommonCode.ARTICLE);
		boolean isCollection = collectionService.isCollection(user.getUserId(), articleId);
		articleUserDetail.setCollection(isCollection);
		articleUserDetail.setStar(isStar);
		}	
		
		//阅读量加一
		articleService.addArticleViews(articleId, 1);
		return new Result<ArticleUserDetail>(EmUserError.SUCCESS,articleUserDetail);
	}
	
	
	/**
	 * 插入新文章信息的方法
	 * @param article
	 * @param session
	 * @return
	 */
	@PostMapping("/insert")
	public Result<Object> insertArticle(Article article,HttpSession session){	
		//简单校验参数
		if(!CheckUtil.checkArticle(article)){
			throw new UserException(EmUserError.PARAMETER_ERROR);
		}
		
		//调用service新增文章方法
		Article inserArticle = articleService.inserArticle(article);
		if(inserArticle == null){
			throw new UserException(EmUserError.UNKONW_ERROR);
		}
		
		return new Result<Object>(EmUserError.SUCCESS,inserArticle);
	}
	

	/**
	 * 从草稿箱上传文章
	 * @param articleIds
	 * @return
	 */
	@PostMapping("/insert/fromDraft")
	public Result<Object> insertArticleFromDraft(@RequestParam("articleIds") List<Integer> articleIds){	
		
		articleService.deleteOrRecoverArticle(articleIds, CommonCode.ARTICLE_NORMAL);		
		
		return new Result<Object>(EmUserError.SUCCESS,null);
	}
	
	/**
	 * 文章变草稿状态,下架
	 * @param articleIds
	 * @return
	 */
	@PostMapping("/toDraft")
	public Result<Object> articleToDraft(@RequestParam("articleIds") List<Integer> articleIds){	
		
		articleService.deleteOrRecoverArticle(articleIds, CommonCode.ARTICLE_DRAFT);		
		
		return new Result<Object>(EmUserError.SUCCESS,null);
	}
	
	
	
	/**
	 * 更新文章信息
	 * @param article
	 * @param session
	 * @return
	 */
	@PostMapping("/update")
	public Result<Object> updatetArticle(Article article ,HttpSession session){
				
		//简单校验参数
		if(!CheckUtil.checkArticle(article)){
			throw new UserException(EmUserError.PARAMETER_ERROR);
		}
		
		//调用service新增文章方法
		Article updateArticle = articleService.updateArticle(article);
		if(updateArticle == null){
			throw new UserException(EmUserError.UNKONW_ERROR);
		}
		
		return new Result<Object>(EmUserError.SUCCESS,updateArticle);
	}
	

	
	/**
	 * 删除文章,假删除
	 * @param articleId
	 * @return
	 */
	@PostMapping("/delete")
	public Object deleteAreticle(@RequestParam("articleIds") List<Integer> articleIds){		
	  articleService.deleteOrRecoverArticle(articleIds, CommonCode.ARTICLE_DELETED);
	  return new Result<Object>(EmUserError.SUCCESS,null);
	}
	
	/**
	 * 从回收站恢复
	 * @param articleIds id数组
	 * @return
	 */
	@PostMapping("/recover")
	public Object recoverAreticle(@RequestParam("articleIds[]") List<Integer> articleIds){		
		articleService.deleteOrRecoverArticle(articleIds, CommonCode.ARTICLE_NORMAL);		
		return new Result<Object>(EmUserError.SUCCESS,null);
	}
	
	
	/**
	 * 永久删除文章,真删
	 * @param articleId
	 * @return
	 */
	@PostMapping("/delete/forever")
	public Object foreverDeleteAreticle(@RequestParam("articleIds") List<Integer> articleIds ,
			@RequestParam("userId") Integer userId,HttpSession session){
		UserDetail user = (UserDetail) session.getAttribute("user");
		if(user==null || user.getUserId()!=userId){
			throw new UserException(EmUserError.USER_NOT_LOGIN);
		}
	  articleService.deleteArticle(articleIds, CommonCode.ARTICLE);
	  return new Result<ArticleUserDetail>(EmUserError.SUCCESS,null);
	}
	
	
	 /**
	  * 文章图片上传
	 * @param file
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/image")
 public Object fileUpload(@RequestParam(value = "fileName") MultipartFile file, HttpServletRequest request) {
	        if (file.isEmpty()) {
	            System.out.println("文件为空空");
	        }
	        String fileName = file.getOriginalFilename();  // 文件名
	        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
    
	        String filePath = IMAGE_PATH;
  
	        fileName = UUID.randomUUID() + suffixName; // 新文件名
	        File dest = new File(filePath + fileName);	   
	        
	        if (!dest.getParentFile().exists()) {
	            dest.getParentFile().mkdirs();
	        }
	        try {
	            file.transferTo(dest);
	        } catch (IOException e) {	        
	            e.printStackTrace();
	        }
	        String filename = "/image/" + fileName;
	        System.out.println(filename);
	        Map<String,String> hashmap =  new HashMap<String,String>();
	        hashmap.put("pic", filename);
	        
	        return new Result<Object>(EmUserError.SUCCESS, hashmap);
	    }
	
	
}
