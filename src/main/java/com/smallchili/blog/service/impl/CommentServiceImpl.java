package com.smallchili.blog.service.impl;

import java.util.ArrayList;
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

import com.smallchili.blog.dataobject.Article;
import com.smallchili.blog.dataobject.ArticleComment;
import com.smallchili.blog.dataobject.CommentReply;
import com.smallchili.blog.dataobject.UserStar;
import com.smallchili.blog.dto.CommentAndReplyDTO;
import com.smallchili.blog.dto.ReplyerDTO;
import com.smallchili.blog.error.EmUserError;
import com.smallchili.blog.error.UserException;
import com.smallchili.blog.repository.CommentRepository;
import com.smallchili.blog.service.ArticleService;
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
	
	private List<UserStar> commentStar;
    private List<UserStar> replyStar;
	
	@Autowired
    private CommentRepository commentRepository;	  
    @Autowired
    private ReplyService replyService;
    @Autowired
    private UserStarService userStarService;
    @Autowired 
    ArticleService articleService;
  //配置页数
  	@Value("${pageSize}")
  	private Integer pageSize;
    
	@Override
	public ArticleComment insertComment(ArticleComment comment) {
		Article article = articleService.findById(comment.getArticleId());
		if(article == null){
			throw new UserException(EmUserError.ARTICLE_NOT_EXISI);
		}
		article.setCommentCount(article.getCommentCount() + 1);
		articleService.updateArticle(article);
		comment.setCommentStar(0);
		comment.setCommentStatus(0);
		return commentRepository.save(comment);
	}


	@Override
	public CommentAndReplyVO findAllCommentByArticleId(Integer articleId, Integer page,Integer userId) {
		
		//构造查询的页，和每页大小
		Pageable pageable = PageRequest.of(page-1,pageSize);	
		
		//构造条件
		Specification<ArticleComment> spec = Specifications.where(e->{
			e.eq("articleId",articleId); 
		});
		//查询
		Page<ArticleComment> commentPage = commentRepository.findAll(spec, pageable);
		List<ArticleComment> commentList = commentPage.getContent();
		
		//userId != 0 表示要检查是否点赞
		if(userId != 0){
			//拿到评论Id数组
		    List<Integer> commentIds = commentList.stream().map(e -> e.getCommentId()).collect(Collectors.toList());
		    List<Integer> replyIds = new ArrayList<Integer>();
		    commentList.forEach(e -> replyIds.addAll(
		    		e.getReplyList().stream().map(e2 -> e2.getReplyId()).collect(Collectors.toList())
		    		));
		   //拿到回复Id数组
		     commentStar = null;
		     replyStar = null;
		    if(commentIds != null && !commentIds.isEmpty()){
		     commentStar = userStarService.findUserStar(userId,commentIds, CommonCode.COMMENT);		   
		    }
		    if(replyIds != null && !replyIds.isEmpty()){
		    replyStar = userStarService.findUserStar(userId,replyIds, CommonCode.REPLY);		 
		    }
		   
		};
		
		//转换,只拿要显示的属性
		List<CommentAndReplyDTO> commentAndReplyDTOList 
		  = commentList.stream().map(e -> {
			return myfunction.apply(e);
		}).collect(Collectors.toList());
		//构造显示页面的数据	
		CommentAndReplyVO commentAndReplyVO = new CommentAndReplyVO();
		commentAndReplyVO.setCommentList(commentAndReplyDTOList);
		commentAndReplyVO.setTotalPages(commentPage.getTotalPages());
		commentAndReplyVO.setNowPage(page);
		
		
		return commentAndReplyVO;
	}

	Function<CommentReply, ReplyerDTO> replyConversion = e -> {
		ReplyerDTO replyerDTO= new ReplyerDTO();			
		BeanUtils.copyProperties(e, replyerDTO);
		replyerDTO.setToUserId(e.getToUser().getUserId());
		replyerDTO.setToUserName(e.getToUser().getUserName());
		replyerDTO.setToUserImage(e.getToUser().getUserImage());
		
		replyerDTO.setReplyerId(e.getReplyer().getUserId());
		replyerDTO.setReplyerName(e.getReplyer().getUserName());
		replyerDTO.setReplyerImage(e.getReplyer().getUserImage());
		
		if(replyStar != null && !replyStar.isEmpty()){
			replyStar.forEach(e2 -> {
			if(e2.getObjId() == replyerDTO.getReplyId())
				replyerDTO.setStar(true);
			    commentStar.remove(replyerDTO.getReplyId());
			});
			
		}
		return replyerDTO;
	};
	
	
	Function<ArticleComment, CommentAndReplyDTO> myfunction = e -> {
		CommentAndReplyDTO commentAndReply= new CommentAndReplyDTO();			
		BeanUtils.copyProperties(e, commentAndReply);
		List<ReplyerDTO> replyerDTOList 
		  = e.getReplyList().stream().map(e1 -> {
			  return replyConversion.apply(e1);
		}).collect(Collectors.toList());
		commentAndReply.setReplyList(replyerDTOList);
		commentAndReply.setUserName(e.getReplyer().getUserName());
		commentAndReply.setUserImage(e.getReplyer().getUserImage());
		//如果点了赞设置为true
		if(commentStar != null && !commentStar.isEmpty()){
			commentStar.forEach(e2 -> {
			if(e2.getObjId() == commentAndReply.getCommentId())
				commentAndReply.setStar(true);	
			    commentStar.remove(commentAndReply.getCommentId());
			});
			 
			
		}
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
