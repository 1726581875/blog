package com.smallchili.blog.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
		
		private Date createTime;

	    @OneToOne
	    @JoinColumn(name = "userId",insertable= false,updatable = false)
	    private UserDetail userDetail;

		public Integer getArticleId() {
			return articleId;
		}

		public void setArticleId(Integer articleId) {
			this.articleId = articleId;
		}

		

		public Integer getArticleType() {
			return articleType;
		}

		public void setArticleType(Integer articleType) {
			this.articleType = articleType;
		}

		public String getArticleTitle() {
			return articleTitle;
		}

		public void setArticleTitle(String articleTitle) {
			this.articleTitle = articleTitle;
		}

		public String getArticleContent() {
			return articleContent;
		}

		public void setArticleContent(String articleContent) {
			this.articleContent = articleContent;
		}

		public Integer getArticleStar() {
			return articleStar;
		}

		public void setArticleStar(Integer articleStar) {
			this.articleStar = articleStar;
		}

		public Integer getCommentCount() {
			return commentCount;
		}

		public void setCommentCount(Integer commentCount) {
			this.commentCount = commentCount;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public UserDetail getUserDetail() {
			return userDetail;
		}

		public void setUserDetail(UserDetail userDetail) {
			this.userDetail = userDetail;
		}

		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		@Override
		public String toString() {
			return "ArticleUserDetail [articleId=" + articleId + ", userId=" + userId + ", articleType=" + articleType
					+ ", articleTitle=" + articleTitle + ", articleContent=" + articleContent + ", articleStar="
					+ articleStar + ", commentCount=" + commentCount + ", createTime=" + createTime + ", userDetail="
					+ userDetail + "]";
		}

		


	
}
