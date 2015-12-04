package com.jondai.blog.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jondai.blog.dao.ArticleDao;
import com.jondai.blog.dao.ClassifyDao;
import com.jondai.blog.entity.Article;
import com.jondai.blog.entity.Classify;
import com.jondai.blog.util.Constants;
import com.jondai.blog.util.HtmlUtil;

/**
 * @author JonDai
 * @since 2015年11月22日 下午5:31:03
 */
@Service
@Transactional(readOnly = true)
public class BlogManager extends CommonManager{
	@Autowired
	private ArticleDao aDao;
	@Autowired
	private ClassifyDao cDao;
	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void addArticle(Article article){
		aDao.save(article);
	}
	
	public List<Article> getAllArticle(){
		return (List<Article>) aDao.findAll();
	}
	
	public Article getArticleById(Long id){
		return aDao.findOne(id);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteArticleById(Long id){
		aDao.delete(id);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveArticle(Article article ){
		if(article.getId() == null || article.getId().toString().equals("")){
			article.setCreatetime(Constants.DF_yyyyMMddHHmmss.format(new Date()));
			article.setReadcount(0);
			aDao.save(article);
			//保存article的同时修改Classity中的数量
			Classify classify = cDao.findOne(new Long(article.getPid()));
			classify.setCount(classify.getCount() + 1);
		}else{
			Article oldArticle = aDao.findOne(article.getId());
			oldArticle.setTitle(article.getTitle());
			oldArticle.setContent(article.getContent());
			oldArticle.setUpdatetime(Constants.DF_yyyyMMddHHmmss.format(new Date()));
			aDao.save(oldArticle);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void seveArticleReadCount(Long id){
		Article art = aDao.findOne(id);
		art.setReadcount(art.getReadcount() + 1);
		aDao.save(art);
	}
	
	/**
	 * 加载分页的article
	 */
	public List<Map<String, Object>> getArticlesByPage(int currPage){
		int start = Constants.PAGE_SIZE * (currPage - 1);
		String sql = "select a.*,c.name from article a ,classify c where a.pid = c.id  order by a.createtime desc limit "+start+","+Constants.PAGE_SIZE+";";
		List<Map<String, Object>> articles = query(sql);
		for(Map<String, Object> article : articles){
			article.put("content", HtmlUtil.getTextFromHtml(article.get("content").toString()));
		}
		return articles;
	}
	
	public List<Classify> getAllClassify(int level){
		return cDao.findByLevel(level);
	}
	
	public List<Classify> getClassifyByPClassify(int pclassify){
		return cDao.findByPclassify(pclassify);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void addClassify(Classify classify){
		classify.setCreatetime(Constants.DF_yyyyMMddHHmmss.format(new Date()));
		//目前暂时只支持2级目录
		classify.setLevel(2);
		classify.setCount(0);
		cDao.save(classify);
	}
	
	public List<Article> getAritcleByPid(int classifyid){
		List<Article> articles =(List<Article>)aDao.findByPidOrderByCreatetimeDesc(classifyid);
		for(Article article : articles){
			String content = article.getContent();
			article.setContent(HtmlUtil.getTextFromHtml(content));
		}
		return articles;
	}
	
	/**
	 * 返回一级目录下的所有article
	 * @throws Exception 
	 */
	public List<Article> getArticleByPclassify(int pclassify) throws Exception{
		List<Classify> classifys = cDao.findByPclassify(pclassify);
		Set<Integer> articlepids = new HashSet<Integer>();
		if(classifys != null && classifys.size() >= 1){
			for(Classify classify : classifys){
				articlepids.add(Integer.parseInt(classify.getId().toString()));
			}
			return aDao.findByPidInOrderByCreatetimeDesc(articlepids);
		}else{
			throw new Exception("该分类什么也没有...");
		}
	}
}
