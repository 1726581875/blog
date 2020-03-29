package com.smallchili.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smallchili.blog.dataobject.ArticleComment;
import com.smallchili.blog.dto.CommentAndReplyDTO;
import com.smallchili.blog.error.EmUserError;
import com.smallchili.blog.error.UserException;
import com.smallchili.blog.repository.CommentRepository;
import com.smallchili.blog.service.CommentService;
import com.smallchili.blog.service.ReplyService;
import com.smallchili.blog.service.UserStarService;
import com.smallchili.blog.utils.CommonCode;
import com.smallchili.blog.vo.CommentAndReplyVO;

import top.springdatajpa.zujijpa.Specifications;

/**
* @author xmz
* 2020-03-13
* 
*/
@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
    private CommentRepository commentRepository;	  
    @Autowired
    private ReplyService replyService;
    @Autowired
    private UserStarService userStarService;
  //配置页数
  	@Value("${pageSize}")
  	private Integer pageSize;
    
	@Override
	public ArticleComment insertComment(ArticleComment comment) {
		
		comment.setCommentStar(0);
		comment.setCommentStatus(0);
		return commentRepository.save(comment);
	}


	@Override
	@Transactional
	public CommentAndReplyVO findAllCommentByArticleId(Integer articleId, Integer page) {
		
		//构造查询的页，和每页大小
		Pageable pageable = PageRequest.of(page-1,pageSize);	
		
		//构造条件
		Specification<ArticleComment> spec = Specifications.where(e->{
			e.eq("articleId",articleId); 
		});
		
		Page<ArticleComment> commentPage = commentRepository.findAll(spec, pageable);
		List<ArticleComment> commentList = commentPage.getContent();
		List<CommentAndReplyDTO> commentAndReplyDTOList 
		  = commentList.stream().map(e -> {
			return myfunction.apply(e);
		}).collect(Collectors.toList());
			
		CommentAndReplyVO commentAndReplyVO = new CommentAndReplyVO();
		commentAndReplyVO.setCommentList(commentAndReplyDTOList);
		commentAndReplyVO.setTotalPages(commentPage.getTotalPages());
		commentAndReplyVO.setNowPage(page);
		return commentAndReplyVO;
	}

	Function<ArticleComment, CommentAndReplyDTO> myfunction = e -> {
		CommentAndReplyDTO commentAndReply= new CommentAndReplyDTO();			
		BeanUtils.copyProperties(e, commentAndReply);
		commentAndReply.setUserName(e.getReplyer().getUserName());
		commentAndReply.setUserImage(e.getReplyer().getUserImage());
		return commentAndReply;
	};

	
	@Override
	@Transactional
	public void deleteComment(Integer commentId) {
		
		Optional<ArticleComment> optional = commentRepository.findById(commentId);
		if(!optional.isPresent()){
			throw new UserException(EmUserError.COMMENT_NOT_EXIST);
		}
		//删除该条评论
		commentRepository.deleteById(commentId);		
		//删除该评论的回复
		replyService.deleteReplyByCommentId(commentId);
	}
	
	
	@Override
	@Transactional
	public void deleteComment(List<Integer> ids , Integer idType) {

		Specification<ArticleComment> spec = Specifications.where(e->{
				if(idType == CommonCode.COMMENT)e.in("commentId",ids); 
				if(idType == CommonCode.ARTICLE)e.in("articleId",ids);
			 });
		//查找出来评论List
		List<ArticleComment> commentList = commentRepository.findAll(spec);
		
		if(!commentList.isEmpty() && commentList != null){
		//1.删除评论
		commentRepository.deleteInBatch(commentList);		
		//拿到评论id的List
		List<Integer> commentIdList = commentList.stream().map(e ->e.getCommentId()
		).collect(Collectors.toList());		
		//2.删除评论的回复		
		replyService.deleteReply(commentIdList, CommonCode.COMMENT);
		//3.删除评论的点赞
		userStarService.deleteStar(commentIdList, CommonCode.COMMENT);
		}
	}


	@Override
	public void commentStar(Integer commentId, Integer star) {
		Optional<ArticleComment> optional = commentRepository.findById(commentId);
		if(!optional.isPresent()){
			throw new UserException(EmUserError.COMMENT_NOT_EXIST);
		}
		ArticleComment comment = optional.get();
		comment.setCommentStar(comment.getCommentStar() + star);
		commentRepository.save(comment);
		
	}
	
	
}
