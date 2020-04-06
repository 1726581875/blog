package com.smallchili.blog.utils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.smallchili.blog.dto.ArticleHeadMsgDTO;
import com.smallchili.blog.dto.ArticleUserDetail;

/**
* @author xmz
* 2020-04-06
* 
*/
public class ArticleUtil {

	public static List<ArticleHeadMsgDTO> conversionArticleList(List<ArticleUserDetail> articleAndUserList){
		
		return copyListfromArticleUD(articleAndUserList);
	}
	/**
	 * @param List<ArticleUserDetail> 连表查的list(属性太多)
	 * @return List<ArticleHeadMsgDTO> 目标list(只保留需要属性)
	 */
	public static List<ArticleHeadMsgDTO> copyListfromArticleUD(List<ArticleUserDetail> list){
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
    static Function<ArticleUserDetail,ArticleHeadMsgDTO> copyObject = (articleUserDetail) -> {
	    	ArticleHeadMsgDTO articlePageDTO = new ArticleHeadMsgDTO();
	    	 BeanUtils.copyProperties(articleUserDetail,articlePageDTO);
	    	 BeanUtils.copyProperties(articleUserDetail.getUserDetail(),articlePageDTO);
	    	 return articlePageDTO;
	    };
}
