package com.smallchili.blog.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.smallchili.blog.dataobject.UserDetail;
import com.smallchili.blog.dto.ArticleHeadMsgDTO;
import com.smallchili.blog.error.EmUserError;
import com.smallchili.blog.error.UserException;
import com.smallchili.blog.repository.UserDetailRepository;
import com.smallchili.blog.service.ArticleService;
import com.smallchili.blog.service.UserDetailService;
import com.smallchili.blog.utils.RandomUtil;
import com.smallchili.blog.vo.OtherUserDetailVO;
/**
 * 用户详情业务实现类
 *
 */
@Service
public class UserDetailServiceImpl implements UserDetailService{
	
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDetailRepository userDetailRepository;

	@Autowired
	private ArticleService articleService;

	@Override
	public UserDetail findDetailById(Integer userId) throws UserException{
		//查找
		Optional<UserDetail> optional = userDetailRepository.findById(userId);
		//若存在
		if(optional.isPresent()){
			return userDetailRepository.findById(userId).get();
		}
		//不存在
		throw new UserException(EmUserError.USER_NOT_EXIST);
			
	}

	@Override
	public OtherUserDetailVO findOtherDetailByName(Integer userId,Integer page) {
		//查找UserDetail
		Optional<UserDetail> optional = userDetailRepository.findById(userId);
		//若不存在
		if(!optional.isPresent()){
			throw new UserException(EmUserError.USER_NOT_EXIST);
		}
		//查出该用户的所有文章
	//	List<ArticleHeadMsgDTO>  articleList = articleService.findAllAricleByUserId(userId,page);
		
		//返回给前端页面的包装类
		OtherUserDetailVO otherUserDetailVO = new OtherUserDetailVO();
		//UserDetail copy to otherUserDetailVO
		BeanUtils.copyProperties(optional.get(), otherUserDetailVO);
		//把文章信息放进去
		//otherUserDetailVO.setContent(articleList);
		
		return otherUserDetailVO;
	}
	
	@Override
	public UserDetail updateUserDetail(UserDetail userDetail) {
	
		//先查用户是否存在
	Optional<UserDetail> UserOptional = userDetailRepository.findById(userDetail.getUserId());
		if(!UserOptional.isPresent()){
			throw new UserException(EmUserError.USER_NOT_EXIST);
		}
		
		//获取查到的updateDetail对象
		UserDetail updateDetail = UserOptional.get();
		
		//有选择性地更新
		if(userDetail.getUserAge() != null){
			updateDetail.setUserAge(userDetail.getUserAge());
		}
		if(userDetail.getUserImage() != null){
			updateDetail.setUserImage(userDetail.getUserImage());
		}
		if(userDetail.getUserName() != null){
			   //昵称是否已存在
			UserDetail userDet = userDetailRepository.findByUserName(userDetail.getUserName());
				if(userDet != null 
				 && userDet.getUserId() != userDetail.getUserId()){
					throw new UserException(EmUserError.USERNAME_REPEAT);
				}
			  updateDetail.setUserName(userDetail.getUserName());
		}
		if(userDetail.getUserSex() != null){
			if(userDetail.getUserSex().equals("男") || userDetail.getUserSex().equals("女"))
			updateDetail.setUserSex(userDetail.getUserSex());
		}
		if(userDetail.getUserMotto() != null){
			updateDetail.setUserMotto(userDetail.getUserMotto());
		}
		
	   return userDetailRepository.save(updateDetail);
		
	}

	@Override
	public UserDetail insertUserDetail(Integer userId) {
		UserDetail userDetail = new UserDetail();
          userDetail.setUserId(userId);
          userDetail.setUserImage("/image/default.png");
          userDetail.setUserName(RandomUtil.getRandomName());
          userDetail.setUserAge(0);
          userDetail.setUserSex("女");
          userDetail.setUserMotto("");
          
		return userDetailRepository.save(userDetail);
	}


	

}
