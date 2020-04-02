package com.smallchili.blog.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smallchili.blog.dataobject.UserDetail;

import lombok.Data;

/**
* @author xmz
* 2020-03-10
* 说明：多表查询
* Article携带UserDetail
* 
*/
@Entity
@Table(name = "article")
@Data
public class ArticleUserDetail {

	   
		@Id
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

	    @OneToOne
	    @JoinColumn(name = "userId",insertable= false,updatable = false)
	    private UserDetail userDetail;

	    @Transient
	    private boolean isStar;
	    
	    @Transient
	    private boolean isCollection;
	
}
