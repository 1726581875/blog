package com.smallchili.blog.dataobject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.smallchili.blog.dto.ArticleUserDetail;

import lombok.Data;

/**
* @author xmz
* 2020-03-28
* 收藏表对象
*/
@Entity
@Data
public class Collection {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer collectionId;
	
	private String articleTitle;
	
	private Integer userId;
	
	private Integer articleId;
	
	@OneToOne
	@JoinColumn(name="articleId",insertable= false,updatable = false)
	@NotFound(action=NotFoundAction.IGNORE)
	private ArticleUserDetail article;
	
	
}
