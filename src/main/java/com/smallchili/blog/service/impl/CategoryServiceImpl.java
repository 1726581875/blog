package com.smallchili.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.smallchili.blog.dataobject.Category;
import com.smallchili.blog.error.EmUserError;
import com.smallchili.blog.error.UserException;
import com.smallchili.blog.repository.CategoryRepository;
import com.smallchili.blog.service.CategoryService;

/**
* @author xmz
* 2020-03-08
* 分类业务实现类
*/

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<Category> findAllCategory() {
		
		return categoryRepository.findAll();
	}

	@Override
	public void addNewCategory(Category category) throws UserException{
	 //先查找是否存在	
	 Category categoryOld = categoryRepository.findByCategoryNum(category.getCategoryNum());
	  if(categoryOld != null){
		  throw new UserException(EmUserError.CATEGORY_REPEAT);
	  }	
	  //更新
	  categoryRepository.save(category);
	}

	@Override
	public void deleteCategoryByNum(Integer categoryNum) throws UserException{
		 //先查找是否存在	
		Category categoryOld = categoryRepository.findByCategoryNum(categoryNum);
		  if(categoryOld == null){
			  throw new UserException(EmUserError.CATEGORY_NOT_EXIST);
		  }	
		  
		  categoryRepository.deleteById(categoryOld.getCategoryId());
	}

	@Override
	public List<Category> findAllCategory(Integer bigType) {
		
		List<Category> list = findAllCategory().stream().filter(e -> 
		e.getBigType()==bigType).collect(Collectors.toList());
		
		return list;
	}


}
