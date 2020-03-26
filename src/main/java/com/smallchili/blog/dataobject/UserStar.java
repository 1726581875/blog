package com.smallchili.blog.dataobject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
* @author xmz
* 2020-03-26
* 
* 用户点赞的表对象实体
*/
@Entity
@Data
public class UserStar {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer starId;
	
	private Integer userId;
	
	private Integer objId;
	
	private Integer starType;
	
	
}
