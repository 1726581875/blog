package com.smallchili.blog.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.smallchili.blog.dataobject.Category;

/**
* @author xmz
* 2020-03-08
* 
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 测试查询所有类目
	 */
	@Test
	public void TestFindAll(){
		List<Category> allCategory = categoryService.findAllCategory();
	    allCategory.forEach(System.out::println);
	}
	
	/**
	 * 测试增加类目
	 */
	@Test
	public void TestAddNewCategory(){
		Category category = new Category();
		category.setCategoryName("我是c++");
		category.setCategoryNum(10);
		categoryService.addNewCategory(category);
	}
	
	/**
	 * 测试删除类目
	 */
	@Test
	public void TestdeleteCategoryByNum(){
		categoryService.deleteCategoryByNum(10);
	}
	
	/**
	 * 测试根据bigType查找分类
	 */
	@Test
	public void findCategoryBybigType(){
		Integer bigType = 1;
		categoryService.findAllCategory(bigType).forEach(System.out::println);
	}
}
