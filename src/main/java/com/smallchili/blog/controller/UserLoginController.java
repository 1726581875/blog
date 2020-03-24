package com.smallchili.blog.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smallchili.blog.dataobject.UserDetail;
import com.smallchili.blog.dataobject.UserLogin;
import com.smallchili.blog.error.EmUserError;
import com.smallchili.blog.error.UserException;
import com.smallchili.blog.service.UserDetailService;
import com.smallchili.blog.service.UserLoginService;
import com.smallchili.blog.utils.CheckUtil;
import com.smallchili.blog.vo.Result;


@RestController
@CrossOrigin(allowCredentials = "true" ,allowedHeaders = "*")
public class UserLoginController extends BaseController{

	@Autowired
	UserLoginService userLoginService;
	
	@Autowired
	UserDetailService userDetailService;
	
	@Autowired
	HttpServletRequest httpServletRequest;
	
	/**
	 * 获取手机验证码的接口
	 * @param userPhone
	 * @return
	 */
	@PostMapping("/phone/getotp")
	public Result<Map<String,String>> getOtp(@RequestParam("userPhone") String userPhone){
		
		if(StringUtils.isEmpty(userPhone) 
				|| !CheckUtil.checkPhone(userPhone)){
			throw new UserException(EmUserError.USER_PHONE_REEOR);
		}
		
		//随机获取四位数otp验证码
		Random random = new Random();
		String otpCode = "";
		for(int i = 0;i<4;i++){
			otpCode = otpCode + random.nextInt(9);
		}
	
		//session关联用户手机号,一般用redis做
		httpServletRequest.getSession().setAttribute(userPhone, otpCode);
		
		//不能发送到手机,简单返回给前端
		Map<String,String> map = new HashMap<String,String>();
		map.put("otpCode", otpCode);
		
		return new Result<Map<String,String>>(EmUserError.SUCCESS,map);
	}
	
	

	/**
	 * 手机登录
	 * @param userPhone 手机号
	 * @param otp 验证码
	 * @return
	 */
	@PostMapping(value = "/login/phone",produces = {"application/json;charset=UTF-8"})
    public Result<Object> loginByPhone(@RequestParam("userPhone") String userPhone,
    		@RequestParam("otp") String otp){
		
		//入参格式校验
		if(StringUtils.isEmpty(userPhone) 
				|| !CheckUtil.checkPhone(userPhone)
				|| StringUtils.isEmpty(otp)){
			throw new UserException(EmUserError.PARAMETER_ERROR);
		}
		
		//查询用户是否存在
	    UserLogin userLogin = userLoginService.loginByPhone(userPhone) ;   
	       if(userLogin == null){
	    	   throw new UserException(EmUserError.USER_PHONE_REEOR,null);
	         }
		
		//判断otp是否有效
		String userOtp = (String)httpServletRequest.getSession().getAttribute(userPhone);
		if(StringUtils.isEmpty(userOtp)){
			throw new UserException(EmUserError.PHONE_OTP_ERROR.setErrMsg("验证码不存在,或者已过期,请重新发送验证码"));
		}
		//判断otp是否正确
		if(!userOtp.equals(otp)){
			throw new UserException(EmUserError.PHONE_OTP_ERROR);
		}	
		
		//查出userDetail
		 UserDetail userDetail = userDetailService.findDetailById(userLogin.getUserId());
		//放入session
		httpServletRequest.getSession().setAttribute("user", userDetail);
         //返回成功
		
    	 return new Result<Object>(EmUserError.SUCCESS,userDetail.getUserId());
     }
	
	
	/**
	 *  密码登录
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 */
	
	@PostMapping(value = "/login",produces = {"application/json;charset=UTF-8"})
    public Result<Object> login(@RequestParam("username") String username, 
    		@RequestParam("password") String password){
		
		//入参校验
		if(StringUtils.isEmpty(username) 
				|| StringUtils.isEmpty(password)){
			throw new UserException(EmUserError.PARAMETER_ERROR);
		}
		
		//查询用户是否存在
    	 UserLogin userLogin = userLoginService.loginByPassword(username, password);
         if(userLogin == null){
        	 return new Result<Object>(EmUserError.USER_NOT_EXIST,null);
         }
         
       //查出userDetail
		 UserDetail userDetail = userDetailService.findDetailById(userLogin.getUserId());
		//放入session
		httpServletRequest.getSession().setAttribute("user", userDetail);
         
         //返回成功
    	 return new Result<Object>(EmUserError.SUCCESS,userDetail.getUserId());
     }
	
	
	/**
	 * 注册
	 * @param username 
	 * @param password
	 * @param userPhone
	 * @param otp
	 * @return
	 */
	@PostMapping(value = "/register",produces = {"application/json;charset=UTF-8"})
	public Result<UserDetail> register(@RequestParam("username") String username,
			                        @RequestParam("password") String password, 
			                       @RequestParam("userPhone") String userPhone,
			                       @RequestParam("otp") String otp){
		//入参校验
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)
		|| StringUtils.isEmpty(userPhone) || StringUtils.isEmpty(otp)){
			throw new UserException(EmUserError.PARAMETER_ERROR);
		}
		
		//判断otp是否有效
		String userOtp = (String)httpServletRequest.getSession().getAttribute(userPhone);
		if(StringUtils.isEmpty(userOtp)){
		   throw new UserException(EmUserError.PHONE_OTP_ERROR.setErrMsg("验证码不存在,或者已过期,请重新发送验证码"));
			}
		//判断otp是否正确
		if(!userOtp.equals(otp)){
			throw new UserException(EmUserError.PHONE_OTP_ERROR);
			}
		
		//调用Service的注册方法
		UserLogin userLogin = new UserLogin();
		userLogin.setUsername(username);
		userLogin.setPassword(password);
		userLogin.setUserPhone(userPhone);
		UserLogin user = userLoginService.register(userLogin);
		
		//再判断一遍,如果注册失败,抛出未知异常
		if(user == null){
			throw new UserException(EmUserError.REGISTER_FAIL);
		}
		//查出userDetail
		 UserDetail userDetail = userDetailService.findDetailById(user.getUserId());
		//放入session
		httpServletRequest.getSession().setAttribute("user", userDetail);
		
		//返回注册信息
		return new Result<UserDetail>(EmUserError.SUCCESS,userDetail);
	}
	
	
	/**
	 * 登出功能
	 * @return
	 */
	@GetMapping("/loginout")
	public Result<Object> loginOut(){
		
		httpServletRequest.getSession().removeAttribute("user");
		
		return new Result<Object>(EmUserError.SUCCESS,null);		
	}
	
	@GetMapping("/islogin")
	public Result<Object> whetherLogin(){
		UserDetail user = (UserDetail) httpServletRequest.getSession().getAttribute("user");
		if(user != null){
			return new Result<Object>(EmUserError.SUCCESS,user);
		}
		
		throw new UserException(EmUserError.USER_NOT_LOGIN);
		
	 }
	
	
	/**
	 * 判断用户名是否存在
	 * @param username
	 * @return
	 */
	@GetMapping("user/username")
	public Result<Object> findByUsername(@RequestParam("username") String username){
		
		UserLogin userLogin = userLoginService.findByUsername(username);
		
		if(userLogin != null){
			return new Result<Object>(EmUserError.SUCCESS,null);
		}
		
		throw new UserException(EmUserError.USER_NOT_EXIST);
		
	 }
	
	
	
}
