package com.smallchili.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smallchili.blog.dataobject.Collection;
import com.smallchili.blog.error.EmUserError;
import com.smallchili.blog.service.CollectionService;
import com.smallchili.blog.vo.PageVO;
import com.smallchili.blog.vo.Result;

/**
* @author xmz
* 2020-03-29
* 
*/
@RestController
@CrossOrigin(allowCredentials = "true" ,allowedHeaders = "*")
@RequestMapping("/collection")
public class CollectionController extends BaseController{
	
	@Autowired
	private CollectionService collectionService;
	
	@GetMapping("/select")
	public Object findCollection(@RequestParam("userId") Integer userId,
			                @RequestParam(value ="page",defaultValue="1") Integer page){
		 PageVO<Collection> pageVO = collectionService.findUserCollection(userId, page);
		return new Result<Object>(EmUserError.SUCCESS,pageVO);
	}
	
	@PostMapping("/add")
	public Object addInCollection(@RequestParam("userId") Integer userId,
			                @RequestParam("articleId") Integer articleId){
		collectionService.addInCollection(userId, articleId);
		return new Result<Object>(EmUserError.SUCCESS,null);
	}
	
	@PostMapping("/remove")
	public Object removeFromCollection(@RequestParam("collectionIds[]") List<Integer> collectionIds){
		collectionService.removeFromCollection(collectionIds);
		return new Result<Object>(EmUserError.SUCCESS,null);
	}

}
