package com.smallchili.blog.dataobject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 *文章类别表的实体类
 *
 */
@Entity
@Data
public class Category {
   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer categoryId;
	
	private String categoryName;
	
	private Integer categoryNum;
	
	private Integer bigType;
	
}
