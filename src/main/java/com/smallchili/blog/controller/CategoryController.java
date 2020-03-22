package com.smallchili.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smallchili.blog.dataobject.Category;
import com.smallchili.blog.error.EmUserError;
import com.smallchili.blog.error.UserException;
import com.smallchili.blog.service.CategoryService;
import com.smallchili.blog.vo.Result;

/**
* @author xmz
* 2020-03-08
* 
*/
@RestController
@CrossOrigin
public class CategoryController extends BaseController{

	@Autowired
	private CategoryService categoryService;
	
	
	@GetMapping("/categories")
	public Result<List<Category>> findAllCategory(@RequestParam(value = "bigType",defaultValue="-1")
	                                  Integer bigType) throws Exception{
		
		List<Category> allCategory;
		if(bigType == -1){//等于-1表示没有传参数,查全部分类
			 allCategory = categoryService.findAllCategory();		
		}else{
			 allCategory = categoryService.findAllCategory(bigType);	
		}
		
		return new Result<List<Category>>(EmUserError.SUCCESS,allCategory);
	}
	
	@PostMapping("/category/add")
	public Result<Object> addNewCategory(@RequestParam("categoryNum") Integer categoryNum,
			             @RequestParam("categoryName") String categoryName){
		//校验参数
		if(categoryNum == 0 || StringUtils.isEmpty(categoryName)){			
			throw new UserException(EmUserError.PARAMETER_ERROR);
		}
		
		 Category category = new Category();
		 category.setCategoryName(categoryName);
		 category.setCategoryNum(categoryNum);
		 //调用service的插入方法
		 categoryService.addNewCategory(category);
		 
		return new Result<Object>(EmUserError.SUCCESS,null);
	}
	
}
