package com.smallchili.blog.dto;

import com.smallchili.blog.error.EmUserError;

/**
 * @author xmz
 *封装所有的返回结果
 */
public class Result<T> {

	private Integer status;
	
	private String msg;
	
	private T data;
	
	
	public Result(EmUserError emUserError, T data) {
		this.status = emUserError.getErrCode();
		this.msg = emUserError.getErrMsg();
		this.data = data;
	}

	public Result(Integer status, String msg, T data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public boolean isSuccess(){
		
	if(this.status == 0) return true;
		
		return false;		
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
	
}
