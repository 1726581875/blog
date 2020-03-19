package com.smallchili.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smallchili.blog.dataobject.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Integer>{

	public UserDetail findByUserName(String UserName);
}
