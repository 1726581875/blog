package com.smallchili.blog.error;

public class UserException extends RuntimeException implements CommonError{

	private CommonError commonError;
	
	
	
  public UserException(CommonError commonError) {
		super(commonError.getErrMsg());
		this.commonError = commonError;
	}

  public UserException(CommonError commonError,String errMsg) {
		super(errMsg);
		this.commonError = commonError;
		this.commonError.setErrMsg(errMsg);
	}
  
	@Override
	public int getErrCode() {
	
		return this.commonError.getErrCode();
	}

	@Override
	public String getErrMsg() {
		
		return this.commonError.getErrMsg();
	}

	@Override
	public CommonError setErrMsg(String errMsg) {
		this.commonError.setErrMsg(errMsg);
		return this;
	}

}
