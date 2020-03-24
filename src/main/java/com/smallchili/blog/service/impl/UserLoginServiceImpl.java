package com.smallchili.blog.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.smallchili.blog.dataobject.UserLogin;
import com.smallchili.blog.error.EmUserError;
import com.smallchili.blog.error.UserException;
import com.smallchili.blog.repository.UserDetailRepository;
import com.smallchili.blog.repository.UserLoginRepository;
import com.smallchili.blog.service.UserDetailService;
import com.smallchili.blog.service.UserLoginService;

@Service
public class UserLoginServiceImpl implements UserLoginService{

	//盐值,用于混淆md5
   private final String slat = "s`hfw+7y984.wt7gt7*^874r&%&7s^usgi~!@#$G";
		
	@Autowired
	private UserLoginRepository userLoginRepository;
	@Autowired
	private UserDetailService userDetailService;
	@Autowired
	private UserDetailRepository userDetailRepository;
	
	@Override
	public UserLogin loginByPhone(String phone) {
		
		return userLoginRepository.findByUserPhone(phone);
	}
	
	@Override
	public UserLogin loginByPassword(String username, String password) throws UserException{
    
	 //获取用户对于md5
	 String passwordMD5 = getMD5(password);
	 //查该出用户
	 UserLogin user = userLoginRepository.findByUsername(username);
	 if(user == null) {//若不存在
		 throw new UserException(EmUserError.USER_NOT_EXIST);
    }else{
    	//存在,判断密码是否对应
		 if (!passwordMD5.equals(user.getPassword())){//不对应
			 throw new UserException(EmUserError.PASSWORD_ERROR);
		   }	
			 
		 }	
	 //返回用户
	 return user;
	}

	@Override
	public UserLogin register(UserLogin userLogin) throws UserException{		
		//判断用户名是否存在
		UserLogin userByname = userLoginRepository.findByUsername(userLogin.getUsername());
		if(userByname != null){
			throw new UserException(EmUserError.USERNAME_REPEAT);			    
		}
		//判断手机号是否存在
		UserLogin userByPhone = userLoginRepository.findByUserPhone(userLogin.getUserPhone());
		if(userByPhone != null){
			throw new UserException(EmUserError.USER_PHONE_REPEAT);
		}
		//密码以md5形式存储
		userLogin.setPassword(getMD5(userLogin.getPassword()));
		//权限设为普通用户
		userLogin.setRole(1);
		//插入用户登录表
		UserLogin user = userLoginRepository.save(userLogin);
		//插入用户详情表
	    userDetailService.insertUserDetail(user.getUserId());
		
		return user;
	}

	@Override
	public UserLogin updatePasswordByPhone(String userPhone,String password)throws UserException {
		
		//先查出用户
		UserLogin userLogin = 
				userLoginRepository.findByUserPhone(userPhone);
		if(userLogin == null){
			throw new UserException(EmUserError.USER_PHONE_REEOR);
		}
		
		//密码以md5加密方式存储	
		userLogin.setPassword(this.getMD5(password));
		//更新
		return userLoginRepository.save(userLogin);
	}

	@Override
	public void delete(Integer userId) {
		//删用户登录表
		userLoginRepository.deleteById(userId);	
		//删用户信息表
		userDetailRepository.deleteById(userId);
	}

	
 /**
  * 获取对应的md5密文
 * @param str
 * @return md5密文
 */
public String getMD5(String str){	   
	   String base = slat + str;
	   return DigestUtils.md5DigestAsHex(base.getBytes());
   }

@Override
public UserLogin findByUsername(String username) {
	
	return userLoginRepository.findByUsername(username);
}


   

}
