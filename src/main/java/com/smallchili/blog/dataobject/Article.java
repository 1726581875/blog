package com.smallchili.blog.dataobject;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章表实体类
 *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer articleId;
	
	private Integer userId;
	
	private Integer articleType;
	
	private Integer bigType;
	
	private String articleTitle;
	
	private String articleContent;
	
	private Integer articleStar;
	
	private Integer commentCount;
	
	private Integer articleView;
	
	private Integer articleStatus;
	
	private Date createTime;


}
