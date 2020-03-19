package com.smallchili.blog.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.smallchili.blog.dataobject.Article;
import com.smallchili.blog.dto.ArticleUserDetail;
import com.smallchili.blog.repository.ArticleUserDTORepository;

/**
* @author xmz
* 2020-03-10
* 
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleUserDTOTest {

	@Autowired
	private ArticleUserDTORepository ArticleRepository;
	
	@Test
	public void ArticleUserDTORepositoryTest(){
		
		List<ArticleUserDetail> findAll = ArticleRepository.findAll();
		findAll.forEach(System.out::println);
	}
	
	
	
}
