package com.smallchili.blog.error;

public enum EmUserError implements CommonError{
	SUCCESS(0,"success"),
	PARAMETER_ERROR(1,"参数有误"),
	UNKONW_ERROR(-1, "未知错误"),
	
	//登录注册异常
	USER_NOT_EXIST(10001,"用户不存在"),
	PASSWORD_ERROR(10002,"密码错误"),
	PASSWORD_IS_NULL(10003,"密码不能为空"),
	USERNAME_IS_NULL(10004,"用户名不能为空"),
	USERNAME_REPEAT(10005,"用户名已存在"),
	PHONE_IS_NULL(10006,"手机号不能为空"),	
	USER_PHONE_REEOR(10007,"手机号错误"),	
	USER_PHONE_REPEAT(10008,"手机号已存在"),
	PHONE_NOT_EXIST(10018,"手机号不存在"),
	PHONE_OTP_ERROR(10009,"验证码错误"),
	REGISTER_FAIL(10010,"注册失败,未知错误"),
	USER_NOT_LOGIN(10011,"用户还没登录"),	
	
	//分类
	CATEGORY_REPEAT(10011,"分类重复"),
	CATEGORY_NOT_EXIST(10011,"分类不存在"),
	
	//评论
	COMMENT_NOT_EXIST(20000,"评论不存在"),
	
	//文章
	ARTICLE_NOT_EXISI(30000,"文章不存在"),
	
	COLLECTION_REPEAT(11111,"重复收藏")
	;
	

	private int errCode;
	
	private String errMsg;
	
	private EmUserError(int errCode , String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
	@Override
	public int getErrCode() {
		
		return this.errCode;
	}

	@Override
	public String getErrMsg() {
		
		return this.errMsg;
	}

	@Override
	public CommonError setErrMsg(String errMsg) {
		this.errMsg = errMsg;
		return this;
	}

	
	
}
