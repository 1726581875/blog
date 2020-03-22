package com.smallchili.blog.service.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.smallchili.blog.dataobject.CommentReply;
import com.smallchili.blog.dto.ReplyerDTO;
import com.smallchili.blog.repository.ReplyRepository;
import com.smallchili.blog.service.ReplyService;

/**
* @author xmz
* 2020-03-12
* 
*/
@Service
public class ReplyServiceImpl implements ReplyService{

	@Autowired
	private ReplyRepository replyRepository;
	
    //配置页数
	@Value("${pageSize}")
	private Integer pageSize;
	
	@Override
	public List<ReplyerDTO> findAllReplyByComnentId(Integer commentId) {		
	   
		//构造条件
		Specification<CommentReply> spec = (root,query,cb) ->{		
			return cb.equal(root.get("commentId"), commentId);
		};
			
		List<CommentReply> replyList = replyRepository.findAll(spec);
		
		//转换为replyerDTOList
		List<ReplyerDTO> replyerDTOList = replyList.stream().map(e ->{
			return funtion.apply(e);
		}).collect(Collectors.toList());
		
		return replyerDTOList;
	}

 /**
 * 
 * 挑出需要的属性
 */
Function<CommentReply, ReplyerDTO> funtion = e -> {
		ReplyerDTO replyer = new ReplyerDTO();
		replyer.setReplyerId(e.getReplyerId());
		replyer.setReplyerName(e.getReplyer().getUserName());
		replyer.setReplyerImage(e.getReplyer().getUserImage());
		replyer.setToUserId(e.getToUserId());
		replyer.setToUserName(e.getToUser().getUserName());
		replyer.setToUserImage(e.getToUser().getUserImage());
		replyer.setReplyContent(e.getReplyContent());
		replyer.setReplyStar(e.getReplyStar());
		replyer.setCreateTime(e.getCreateTime());		
		return replyer;
	};
	
	@Override
	public CommentReply inserCommentReply(CommentReply reply) {
		
		
		reply.setReplyStar(0);
		reply.setReplyStatus(0);
		return replyRepository.save(reply);
	}

	@Override
	public void deleteReply(Integer replyId) {
		
		replyRepository.deleteById(replyId);
	}

	
	
	
}
