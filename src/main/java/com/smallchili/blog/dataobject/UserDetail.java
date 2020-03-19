package com.smallchili.blog.dataobject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * 用户详情表实体类
 *
 */

@Entity
@Data
public class UserDetail {
    
	@Id
	private Integer userId;
	
	private String userImage;
	
	private String userName;
	
	private String userSex;
	
	private Integer userAge;
	
	private String userMotto;

	
}
