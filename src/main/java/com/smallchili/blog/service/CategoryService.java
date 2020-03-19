package com.smallchili.blog.service;

import java.util.List;

import com.smallchili.blog.dataobject.Category;

/**
* @author xmz
* 2020-03-08
* 
*/
public interface CategoryService {

	/**
	 * 查询所有分类
	 * @return
	 */
	public List<Category> findAllCategory();
	

	/**
	 * 根据bigType查询分类
	 * @return
	 */
	public List<Category> findAllCategory(Integer bigType);
	
	
	/**
	 * 增加一个类型
	 */
	public void addNewCategory(Category category);
	
	/**
	 * 删除一个类型
	 */
	public void deleteCategoryByNum(Integer categoryNum);
	
}
