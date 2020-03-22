 package com.smallchili.blog.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smallchili.blog.dataobject.UserDetail;
import com.smallchili.blog.error.EmUserError;
import com.smallchili.blog.error.UserException;
import com.smallchili.blog.service.UserDetailService;
import com.smallchili.blog.vo.Result;

/**
* @author xmz
* 2020-03-09
* 用户详情
*/

@RestController
@CrossOrigin(allowCredentials = "true" ,allowedHeaders = "*")
public class UserDetailController extends BaseController{

	@Autowired
	private UserDetailService userDetailService;
	
	@Value("${imagePath}")
	private String IMAGE_PATH;
	
	/**
	 * @param userId
	 * @return
	 * 获取自己的信息
	 */
	@GetMapping("/user/detail")
	public Result<UserDetail> findMyDetail(@RequestParam("userId") Integer userId ,HttpSession session){
		
		//入参校验
		if(userId == 0){
			throw new UserException(EmUserError.PARAMETER_ERROR);
		}
		
       //session里取值
		UserDetail user = (UserDetail) session.getAttribute("user");
	   //判断是否是登录状态，或者是不是本人
		if(user == null || user.getUserId() != userId){			
			throw new UserException(EmUserError.USER_NOT_LOGIN);
		}
		
		//调用查找方法
		UserDetail userDetail = userDetailService.findDetailById(userId);
		//若不存在
		if(userDetail == null){
			throw new UserException(EmUserError.USER_NOT_EXIST);
		}			
		//返回
		return new Result<UserDetail>(EmUserError.SUCCESS,userDetail);
	}
	
	
	/**
	 * @param userId
	 * @return
	 * 获取其他用户的信息
	 */
	@GetMapping("/otheruser/detail")
	public Result<UserDetail> findOtherUserDetail(@RequestParam("userId") Integer userId ,HttpSession session){
		
		//入参校验
		if(userId == 0){
			throw new UserException(EmUserError.PARAMETER_ERROR);
		}		
	
		//调用查找方法
		UserDetail userDetail = userDetailService.findDetailById(userId);
		//若不存在
		if(userDetail == null){
			throw new UserException(EmUserError.USER_NOT_EXIST);
		}			
		//返回
		return new Result<UserDetail>(EmUserError.SUCCESS,userDetail);
	}



    @PostMapping(value = "/user/update/image")
    public Result<Map<String,String>> updateUserImage(@RequestParam(value = "fileName") MultipartFile file, HttpSession session) {
       
    	//session里取值,查看是否登录
    	UserDetail userDetail = (UserDetail)session.getAttribute("user");
    	if(userDetail == null){
    		throw new UserException(EmUserError.USER_NOT_LOGIN);
    	}
    	
    	//获取现在的图形名称
    	String nowImageName = userDetail.getUserImage().substring(userDetail.getUserImage().lastIndexOf("/")+1);
       System.out.println(nowImageName);
    	//如果不是默认头像,要删除   	
    	if(!nowImageName.equals("default.png")){
    	File oldFile = new File(IMAGE_PATH+nowImageName);
        //判断文件是否存在
        if (oldFile.exists() == true){
            System.out.println("图片存在，可执行删除操作");
            Boolean flag = false;
            flag = oldFile.delete();
            if (flag){
                System.out.println("成功删除图片"+file.getName());
            }else {
                System.out.println("删除失败");
            }
          }
    	}
    	
       //判断传来的文件是否为空
    	if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        
        System.out.println("输出文件名:"+fileName);
        String filePath = IMAGE_PATH;
        System.out.println("输出文件要保留的服务器地址:"+filePath);
        
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        System.out.println("输出新文件名:"+fileName);
        File dest = new File(filePath + fileName);
        System.out.println("生成新文件:"+dest.getName());
        
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
        
        //设置返回值
        String filename = "/image/" + fileName;
        System.out.println(filename);
        Map<String,String> hashmap =  new HashMap<String,String>();
        hashmap.put("pic", filename);
        //更新数据库头像路径
        UserDetail updateDetail = new UserDetail();
        updateDetail.setUserId(userDetail.getUserId());
        updateDetail.setUserImage(filename);
        
        UserDetail detail = userDetailService.updateUserDetail(updateDetail);
        session.setAttribute("user", detail);
        
        return new Result<Map<String,String>>(EmUserError.SUCCESS,hashmap);
    }
	
    
    
   /**
 
 * 修改用户信息
 * @param file
 * @param userDetail
 * @param session
 * @return
 */
@PostMapping("user/update/detail")
   public Result<UserDetail> updateUserMsg0(@RequestParam(value = "fileName",required=false) MultipartFile file, 
		                             UserDetail userDetail,
		                             HttpSession session){
    	 //参数校验
	       if( StringUtils.isEmpty(userDetail.getUserId())
               || StringUtils.isEmpty(userDetail.getUserAge())
               || StringUtils.isEmpty(userDetail.getUserName())
               || StringUtils.isEmpty(userDetail.getUserSex())){
	    	   throw new UserException(EmUserError.PARAMETER_ERROR); 
	    	  
	       }
	   	    	   
	       UserDetail user = (UserDetail) session.getAttribute("user");
	       if(user == null || user.getUserId()!=userDetail.getUserId()){
	    	   throw new UserException(EmUserError.USER_NOT_LOGIN);
	       }
	   
	  //如果没有文件(没有修改头像) 
   if(file == null){
		  // UserDetail updataDetail = new UserDetail();
		  // BeanUtils.copyProperties(userDetail,updataDetail);
		   UserDetail detail = userDetailService.updateUserDetail(userDetail);
		   if(detail == null){
			   throw new UserException(EmUserError.UNKONW_ERROR);
		   }
		   
		   return new Result<UserDetail>(EmUserError.SUCCESS,detail);
		   
   }else{//否则,要修改头像
		   		   
	    	
	    	//获取现在的图形名称
	    	String nowImageName = user.getUserImage().substring(user.getUserImage().lastIndexOf("/")+1);	      
	    	//如果不是默认头像,要删除   	
	    	if(!nowImageName.equals("default.png")){
	    	File oldFile = new File(IMAGE_PATH+nowImageName);
	        //判断文件是否存在
	        if (oldFile.exists() == true){
	            System.out.println("图片存在，可执行删除操作");
	            Boolean flag = false;
	            flag = oldFile.delete();
	            if (flag){
	                System.out.println("成功删除图片"+file.getName());
	            }else {
	                System.out.println("删除失败");
	            }
	          }
	    	}
	    	
	       //判断前端传来的文件是否为空
	    	if (file.isEmpty()) {
	            System.out.println("文件为空");
	        }
	        String fileName = file.getOriginalFilename();  // 文件名
	        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
	        	   
	        String filePath = IMAGE_PATH;//服务器上存图片的路径	     
	        
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
	        
	        //设置写到数据库的
	        String filename = "/image/" + fileName;
	        
	        //更新数据库头像路径
	        UserDetail updateDetail = new UserDetail();
	        updateDetail.setUserId(user.getUserId());
	        updateDetail.setUserImage(filename);
	        
	        //更新session里的值
	        UserDetail detail = userDetailService.updateUserDetail(updateDetail);
	        session.setAttribute("user", detail);
	        
	        return  new Result<UserDetail>(EmUserError.SUCCESS,detail);
		   
		   
	   }
	   
    	  	
    	
    }
    
    
}
