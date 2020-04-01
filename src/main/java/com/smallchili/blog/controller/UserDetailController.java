package com.smallchili.blog.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smallchili.blog.dataobject.UserDetail;
import com.smallchili.blog.dataobject.UserLogin;
import com.smallchili.blog.error.EmUserError;
import com.smallchili.blog.error.UserException;
import com.smallchili.blog.service.UserDetailService;
import com.smallchili.blog.service.UserLoginService;
import com.smallchili.blog.utils.CheckUtil;
import com.smallchili.blog.vo.Result;

/**
 * @author xmz 2020-03-09 用户详情
 */

@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("/user")
public class UserDetailController extends BaseController {

	@Autowired
	private UserDetailService userDetailService;


	@Autowired
	UserLoginService userLoginService;
	
	@Autowired
	HttpServletRequest httpServletRequest;
	
	@Value("${imagePath}")
	private String IMAGE_PATH;

	/**
	 * @param userId
	 * @return 获取自己的信息
	 */
	@GetMapping("/detail")
	public Result<UserDetail> findMyDetail(@RequestParam("userId") Integer userId, HttpSession session) {

		// 入参校验
		if (userId == 0 || StringUtils.isEmpty(userId)) {
			throw new UserException(EmUserError.PARAMETER_ERROR);
		}

		// session里取值
		UserDetail user = (UserDetail) session.getAttribute("user");
		// 判断是否是登录状态，或者是不是本人
		if (user == null || user.getUserId() != userId) {
			throw new UserException(EmUserError.USER_NOT_LOGIN);
		}

		// 调用查找方法
		UserDetail userDetail = userDetailService.findDetailById(userId);
		// 若不存在
		if (userDetail == null) {
			throw new UserException(EmUserError.USER_NOT_EXIST);
		}
		// 返回
		return new Result<UserDetail>(EmUserError.SUCCESS, userDetail);
	}


	/**
	 * 
	 * 修改用户信息
	 * 
	 * @param file
	 * @param userDetail
	 * @param session
	 * @return
	 */
	@PostMapping("/update/detail")
	public Result<UserDetail> updateUserMsg0(@RequestParam(value = "fileName", required = false) MultipartFile file,
			UserDetail userDetail, HttpSession session) {
		// 参数校验
		if (StringUtils.isEmpty(userDetail.getUserId()) || StringUtils.isEmpty(userDetail.getUserAge())
				|| StringUtils.isEmpty(userDetail.getUserName()) || StringUtils.isEmpty(userDetail.getUserSex())) {
			throw new UserException(EmUserError.PARAMETER_ERROR);

		}

		//判断是否是登录或者是否是本用户
		
		UserDetail user = (UserDetail) session.getAttribute("user");
		if (user == null || user.getUserId() != userDetail.getUserId()) {
			throw new UserException(EmUserError.USER_NOT_LOGIN);
		}
		

		// 如果没有文件(没有修改头像)
		if (file == null) {
			// UserDetail updataDetail = new UserDetail();
			// BeanUtils.copyProperties(userDetail,updataDetail);
			UserDetail detail = userDetailService.updateUserDetail(userDetail);
			if (detail == null) {
				throw new UserException(EmUserError.UNKONW_ERROR);
			}

			return new Result<UserDetail>(EmUserError.SUCCESS, detail);

		} else {// 否则,要修改头像

			// 获取现在的图形名称
			String nowImageName = user.getUserImage().substring(user.getUserImage().lastIndexOf("/") + 1);
			// 如果不是默认头像,要删除
			if (!nowImageName.equals("default.png")) {
				File oldFile = new File(IMAGE_PATH + nowImageName);
				// 判断文件是否存在
				if (oldFile.exists() == true) {
					System.out.println("图片存在，可执行删除操作");
					Boolean flag = false;
					flag = oldFile.delete();
					if (flag) {
						System.out.println("成功删除图片" + file.getName());
					} else {
						System.out.println("删除失败");
					}
				}
			}

			// 判断前端传来的文件是否为空
			if (file.isEmpty()) {
				System.out.println("文件为空");
			}
			String fileName = file.getOriginalFilename(); // 文件名
			String suffixName = fileName.substring(fileName.lastIndexOf(".")); // 后缀名

			String filePath = IMAGE_PATH;// 服务器上存图片的路径

			fileName = UUID.randomUUID() + suffixName; // 新文件名

			File dest = new File(filePath + fileName);
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();
			}
			try {
				System.out.println(dest.getPath());
				System.out.println("复制文件");
				file.transferTo(dest);
			} catch (IOException e) {
				System.out.println("异常");
				e.printStackTrace();
			}

			// 设置写到数据库的
			String filename = "/image/" + fileName;

			// 更新数据库头像路径
			UserDetail updateDetail = new UserDetail();
			updateDetail.setUserId(user.getUserId());
			updateDetail.setUserImage(filename);

			// 更新session里的值
			UserDetail detail = userDetailService.updateUserDetail(updateDetail);
			session.setAttribute("user", detail);

			return new Result<UserDetail>(EmUserError.SUCCESS, detail);

		}

	}
	
	
	/**
	 * 更改密码
	 * @param userPhone 手机号
	 * @param otp 验证码
	 * @param password 新密码
	 * @return 
	 */
	@PostMapping(value = "/update/password",produces = {"application/json;charset=UTF-8"})
    public Result<Object> updatePasswordByPhone(@RequestParam("userPhone") String userPhone,
    		                     @RequestParam("otp") String otp,
    		                     @RequestParam("password") String password){
		
		//入参格式校验
		if(StringUtils.isEmpty(userPhone) 
				|| !CheckUtil.checkPhone(userPhone)
				|| StringUtils.isEmpty(otp)
				||  StringUtils.isEmpty(password)){
			throw new UserException(EmUserError.PARAMETER_ERROR);
		}
		
		//查询用户是否存在
	    UserLogin userLogin = userLoginService.loginByPhone(userPhone);      
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
		
		//调用更新,并查出userDetail
		 UserLogin user = userLoginService.updatePasswordByPhone(userPhone, password);
		 if(user == null){
			 throw new UserException(EmUserError.UNKONW_ERROR);
		 }
         //返回成功
		
    	 return new Result<Object>(EmUserError.SUCCESS,null);
     }
   @PostMapping("/update/oldPassword")
	public Object updatePasswordByPassword(@RequestParam("userId") Integer userId,
			@RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword){
		
	   //入参格式校验
		if(StringUtils.isEmpty(userId)
		||StringUtils.isEmpty(oldPassword)
        ||StringUtils.isEmpty(newPassword)){
			throw new UserException(EmUserError.PARAMETER_ERROR);
		}
	  userLoginService.updatePassword(userId, oldPassword, newPassword);
	  
		return new Result<Object>(EmUserError.SUCCESS,null);
	}
	
	@GetMapping("/username")
	public Result<Object> findByUsername(@RequestParam("username") String username){
		
		UserLogin userLogin = userLoginService.findByUsername(username);
		
		if(userLogin != null){
			return new Result<Object>(EmUserError.SUCCESS,null);
		}		
		
		throw new UserException(EmUserError.USER_NOT_EXIST);
		
	 }
	
	
	@GetMapping("/getPhone")
	public Result<Object> getPhoneByUserId(@RequestParam("userId") Integer userId){
		
		UserLogin userLogin = userLoginService.findById(userId);
		    Map<String,String> map = new HashMap<String,String>();
		    map.put("userPhone", userLogin.getUserPhone());
			return new Result<Object>(EmUserError.SUCCESS,map);
		
	 }
	

}
