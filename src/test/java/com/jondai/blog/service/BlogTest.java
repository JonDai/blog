package com.jondai.blog.service;

import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jondai.blog.entity.Article;

/**
 * @author JonDai
 * @since 2015年11月22日 下午5:33:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BlogTest {
	@Autowired 
	private BlogManager blogManager;
	
	@Test
	public void testaddArticle(){
		Article article = new Article();
		article.setTitle("测试Blog");
		article.setContent("测试中文还没结贴？今天我也遇到了这个问题，苦苦混战半天，最好发现mysql的表格整理应该改为gbk_chinese_ci，每个属性的整理也改为gbk_chinese_ci，然后就OK啦。。。 ");
		article.setCreatetime(new Date().toString());
		blogManager.addArticle(article);
	}
	
	@Test
	public void testgetAllArticle(){
		List<Article> articles = blogManager.getAllArticle();
		for (Article article : articles) {
			System.out.println("art:"+article.getContent());
		}
	}
	
	@Test
	public void testArticlesByPage(){
	}
}
