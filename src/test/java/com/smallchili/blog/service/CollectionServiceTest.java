package com.smallchili.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
* @author xmz
* 2020-03-28
* 
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectionServiceTest {

	@Autowired
	private CollectionService collectionService;
	
	@Test
	public void findAll(){
		collectionService.findUserCollection(1, 1).getContent().forEach(System.out::println);
		
	}
	
	@Test
	public void testAdd(){
		collectionService.addInCollection(1, 4);
		
	}
	
	@Test
	public void testRemove(){
		List<Integer> collectionIds = new ArrayList<Integer>();
		collectionIds.add(1);
		collectionIds.add(4);
		collectionService.removeFromCollection(collectionIds);
		
	}
	
}
