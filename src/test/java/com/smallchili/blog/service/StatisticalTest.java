package com.smallchili.blog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.smallchili.blog.vo.ArticleHeadPageVO;

/**
* @author xmz
* 2020-04-06
* 
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticalTest {

	@Autowired
	private  StatisticalService  statisticalService;
	
	@Test
	public void testRank(){
		ArticleHeadPageVO headPageVO = statisticalService.statisticalRank(1);
		headPageVO.getContent().forEach(System.out::println);
	}
}
