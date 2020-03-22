package com.smallchili.blog.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.smallchili.blog.error.EmUserError;
import com.smallchili.blog.error.UserException;
import com.smallchili.blog.vo.Result;

public class BaseController {

private final static Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Object handlerException(HttpServletRequest request , Exception e){
		
	 if(e instanceof UserException){
		UserException userException = (UserException) e;
		int errCode = userException.getErrCode();
		String errMsg = userException.getErrMsg();
		return new Result<Object>(errCode,errMsg,null);
		
		}else{
			logger.error("[系统异常]:{}",e);
		  return new Result<Object>(EmUserError.UNKONW_ERROR, null);			
		}
	
	}
	
}
