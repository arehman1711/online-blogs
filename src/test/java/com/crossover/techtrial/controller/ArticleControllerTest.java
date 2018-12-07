package com.crossover.techtrial.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crossover.techtrial.model.Article;
import com.crossover.techtrial.service.ArticleService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ArticleControllerTest {
	
	@InjectMocks 
	ArticleController articleController = new ArticleController();
	
	@Mock
	ArticleService articleService;

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setup() throws Exception {

	}

	@Test
	public void testArticleShouldBeCreated() throws Exception {
		HttpEntity<Object> article = getHttpEntity("{\"email\": \"user1@gmail.com\", \"title\": \"hello\" }");
		ResponseEntity<Article> resultAsset = template.postForEntity("/articles", article, Article.class);
		Assert.assertNotNull(resultAsset.getBody().getId());
		Assert.assertEquals(HttpStatus.CREATED, resultAsset.getStatusCode());
	}

	private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Object>(body, headers);
	}
	
	@Test
	public void testArticleFound() throws Exception {
		ResponseEntity<Article> resultAsset = template.getForEntity("/articles/1", Article.class);
		Assert.assertNotNull(resultAsset.getBody().getId());
		Assert.assertEquals(HttpStatus.OK, resultAsset.getStatusCode());
	}
	
	@Test
	public void testArticleNotFound() throws Exception {
		ResponseEntity<Article> resultAsset = template.getForEntity("/articles/0", Article.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND, resultAsset.getStatusCode());
	}
	
	@Test
	public void testArticleShouldBeUpdated() throws Exception {
		HttpEntity<Object> article = getHttpEntity("{\"email\": \"m.khushhal@gmail.com\", \"title\": \"hello\" }");
		ResponseEntity<Article> resultAsset =  template.exchange("/articles/1", HttpMethod.PUT, article,  Article.class);
		Assert.assertNotNull(resultAsset.getBody().getId());
		Assert.assertEquals(HttpStatus.OK, resultAsset.getStatusCode());
	}
	
	@Test
	public void testUpdateWhenArticleNotExist() throws Exception {
		HttpEntity<Object> article = getHttpEntity("{\"email\": \"m.khushhal@gmail.com\", \"title\": \"hello\" }");
		ResponseEntity<Article> resultAsset =  template.exchange("/articles/0", HttpMethod.PUT, article,  Article.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND, resultAsset.getStatusCode());
	}
	
	@Test
	public void testArticleShouldBeDeleted() throws Exception {
		Mockito.when(articleService.existsById((long)2)).thenReturn(true);
		Mockito.doNothing().when(articleService).delete((long)2);
		ResponseEntity<String> resultAsset = articleController.deleteArticleById((long)2);
		Assert.assertEquals(HttpStatus.OK, resultAsset.getStatusCode());
	}	

	@Test
	public void testDeleteWhenArticleNotExist() throws Exception {
		HttpEntity<Object> article = getHttpEntity(null);
		ResponseEntity<String> resultAsset =  template.exchange("/articles/0", HttpMethod.DELETE,  article, String.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND, resultAsset.getStatusCode());
	}
	
	@Test
	public void testSearchAticles() throws Exception {
		ResponseEntity<String>  resultAsset = template.exchange("/articles/search?text=hello",HttpMethod.GET, null,String.class);
		Assert.assertEquals(HttpStatus.OK, resultAsset.getStatusCode());
	}
	
	@Test
	public void testException() throws Exception {
		ResponseEntity<Article> resultAsset = template.getForEntity("/articles/urlNotFound", Article.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, resultAsset.getStatusCode());
	}
}
