package com.smallchili.blog.service;

import com.smallchili.blog.dataobject.UserDetail;


/**
 * 用户详情Service接口
 *
 */
public interface UserDetailService {
   
	public UserDetail findDetailById(Integer userId);
	
	public UserDetail updateUserDetail(UserDetail userDetail);
	
	public UserDetail insertUserDetail(Integer userId);
	
	
}
