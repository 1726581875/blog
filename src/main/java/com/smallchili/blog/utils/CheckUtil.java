package com.smallchili.blog.utils;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.smallchili.blog.dataobject.Article;

public class CheckUtil {

	public static boolean checkPhone(String phone){		
		  String regex = "^1\\d{10}$";
		  boolean flag = Pattern.matches(regex, phone);
		  return flag;
	}
	
	public static boolean checkArticle(Article article){		
		if(StringUtils.isEmpty(article.getArticleTitle())
				|| StringUtils.isEmpty(article.getArticleContent())
				|| StringUtils.isEmpty(article.getBigType())
			    || StringUtils.isEmpty(article.getArticleType())
                || StringUtils.isEmpty(article.getUserId())
                || StringUtils.isEmpty(article.getArticleStatus())){
			
			  return false;
		}
		int status = article.getArticleStatus();
		if(!(status == 0 || status == 1)) return false;
		
		return true;
	}
	
}
