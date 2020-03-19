package com.smallchili.blog.vo;

import java.util.List;

import com.smallchili.blog.dto.ArticleHeadMsgDTO;

import lombok.Data;

/**
* @author xmz
* 2020-03-11
* 其他用户的详情页展示的数据
*/
@Data
public class OtherUserDetailVO {

	private String userImage;
	
	private String userName;
	
	private String userSex;
	
	private Integer userAge;
	
	private String userMotto;
	
	/**页数*/
	private Integer totalPages;
	
	/**发表的文章*/
	private List<ArticleHeadMsgDTO> content;
	
	
}
