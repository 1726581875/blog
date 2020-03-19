package com.smallchili.blog.dataobject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;



/**
 * 用户登录表实体类
 *
 */
@Entity
@Data
public class UserLogin {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer userId;
	
	private String username;
	
	private String password;
	
	private String userPhone;
	
	private Integer role;


}
