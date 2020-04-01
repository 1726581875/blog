package com.smallchili.blog.service;

import com.smallchili.blog.dataobject.UserLogin;
import com.smallchili.blog.error.UserException;

public interface UserLoginService {

	UserLogin findById(Integer userId);
	
	UserLogin findByUsername(String username);
	
	UserLogin loginByPassword(String username,String password) throws UserException;
	
	UserLogin loginByPhone(String phone);
	
	UserLogin register(UserLogin userLogin);
	
	UserLogin updatePasswordByPhone(String userPhone,String password);
	
	UserLogin updatePassword(Integer userId,String oldPassword,String newPassword);
	
	public void delete(Integer userId);
	
	
}
