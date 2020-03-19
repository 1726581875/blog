package com.smallchili.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smallchili.blog.dataobject.UserLogin;

public interface UserLoginRepository extends JpaRepository<UserLogin, Integer>{
    
	UserLogin findByUsernameAndPassword(String username,String password);
	
	UserLogin findByUsername(String username);
	
	UserLogin findByUserPhoneAndUsername(String userPhone ,String username);
	
	UserLogin findByUserPhone(String userPhone);
	
}
