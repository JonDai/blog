package com.jondai.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jondai.blog.dao.ArticleDao;
import com.jondai.blog.entity.Article;

/**
 * @author JonDai
 * @since 2015年11月22日 下午5:31:03
 */
@Service
public class BlogManager {
	@Autowired
	private ArticleDao aDao;
	
	public void addArticle(Article article){
		aDao.save(article);
	}
	
	public List<Article> getAllArticle(){
		return (List<Article>) aDao.findAll();
	}
	
	public Article getArticleById(Long id){
		return aDao.findOne(id);
	}
}
