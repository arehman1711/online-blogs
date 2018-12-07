package com.crossover.techtrial.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.crossover.techtrial.model.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CommentControllerTest {

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setup() throws Exception {

	}

	@Test
	public void testCommentShouldBeCreated() throws Exception {
		HttpEntity<Object> article = getHttpEntity("{\"email\": \"user1@gmail.com\", \"message\": \"hello\" }");
		ResponseEntity<Comment> resultAsset = template.postForEntity("/articles/1/comments", article, Comment.class);
		Assert.assertNotNull(resultAsset.getBody().getId());
		Assert.assertEquals(HttpStatus.CREATED, resultAsset.getStatusCode());
	}

	@Test
	public void testCreateCommentWhenArticleNotExist() throws Exception {
		HttpEntity<Object> article = getHttpEntity("{\"email\": \"user1@gmail.com\", \"message\": \"hello\" }");
		ResponseEntity<Comment> resultAsset = template.postForEntity("/articles/0/comments", article, Comment.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND, resultAsset.getStatusCode());
	}
	
	private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Object>(body, headers);
	}
	
	@Test
	public void testGetComments() throws Exception {
		ResponseEntity<String>  resultAsset = template.exchange("/articles/1/comments",HttpMethod.GET, null,String.class);
		Assert.assertEquals(HttpStatus.OK, resultAsset.getStatusCode());
	}
}
