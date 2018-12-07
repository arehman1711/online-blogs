/**
 * 
 */
package com.crossover.techtrial.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CommentTest {
	/*
	 * This method will use EqualsTester class to test equals and hashCode method.
	 */
	
	@Test
	public void testEqualsAndHashCode() {
		
	  Comment comment1 = new Comment();
	  comment1.setEmail("m.khushhal@gmail.com");  
	  
	  Comment comment2 = comment1;
	  
	  Comment comment3 = new Comment();
	  comment3.setMessage("This is comment.");
	  Comment comment4 = comment3;
	  
	  Comment comment5 = new Comment();
	  Comment comment6 = null;

	  
	  EqualsTester<Comment> equalsTester = EqualsTester.newInstance(new Comment());
	  
	  equalsTester.assertEqual( comment1, comment2);
	  equalsTester.assertEqual( comment3, comment4);
	  equalsTester.assertNotEqual( comment1, comment3);
	  equalsTester.assertNotEqual( comment5, comment6);
	}
}
