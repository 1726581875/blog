package com.smallchili.blog.service;

import com.smallchili.blog.dataobject.UserDetail;
import com.smallchili.blog.vo.OtherUserDetailVO;

/**
 * 用户详情Service接口
 *
 */
public interface UserDetailService {
   
	public UserDetail findDetailById(Integer userId);
	
	public OtherUserDetailVO findOtherDetailByName(Integer userId ,Integer page);
	
	public UserDetail updateUserDetail(UserDetail userDetail);
	
	public UserDetail insertUserDetail(Integer userId);
	
	
}
