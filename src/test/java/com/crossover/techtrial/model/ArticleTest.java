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
public class ArticleTest {
	/*
	 * This method will use EqualsTester class to test equals and hashCode method.
	 */
	
	@Test
	public void testEqualsAndHashCode() {
		
	  Article article1 = new Article();
	  article1.setTitle("Article#001");
	  article1.setEmail("m.khushhal@gmail.com");  
	  
	  Article article2 = article1;
	  
	  Article article3 = new Article();
	  article3.setContent("This is contecnt of article3.");
	  article3.setPublished(false);
	  
	  Article article4 = article3;
	  
	  Article article5 = new Article();
	  Article article6 = null;

	  
	  EqualsTester<Article> equalsTester = EqualsTester.newInstance(new Article());
	  
	  equalsTester.assertEqual( article1, article2);
	  equalsTester.assertEqual( article3, article4);
	  equalsTester.assertNotEqual( article1, article3);
	  equalsTester.assertNotEqual( article5, article6);
	}
}
