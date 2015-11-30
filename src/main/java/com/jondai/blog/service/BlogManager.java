package com.jondai.blog.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jondai.blog.dao.ArticleDao;
import com.jondai.blog.entity.Article;
import com.jondai.blog.util.Constants;

/**
 * @author JonDai
 * @since 2015年11月22日 下午5:31:03
 */
@Service
public class BlogManager {
	@Autowired
	private ArticleDao aDao;
	@PersistenceContext
	private EntityManager em;
	
	public void addArticle(Article article){
		aDao.save(article);
	}
	
	public List<Article> getAllArticle(){
		return (List<Article>) aDao.findAll();
	}
	
	public Article getArticleById(Long id){
		return aDao.findOne(id);
	}
	
	public void deleteArticleById(Long id){
		aDao.delete(id);
	}
	
	public void saveArticle(Article article ){
		article.setCreatetime(Constants.DF_yyyyMMddHHmmss.format(new Date()));
		aDao.save(article);
	}
	
	/**
	 * 加载分页的article
	 */
	@SuppressWarnings("unchecked")
	public List<Article> getArticlesByPage(int currPage){
		int start = Constants.PAGE_SIZE * (currPage - 1);
		String sql = "select * from article limit "+start+","+Constants.PAGE_SIZE+";";
		return (ArrayList<Article>)em.createNativeQuery(sql).getResultList();
	}
}
