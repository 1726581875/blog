package com.smallchili.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smallchili.blog.dataobject.Category;

/**
*分类Repository
* @author xmz
* 2020年3月8日 
* 
*/
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	public Category findByCategoryNum(Integer categoryNum);
}
