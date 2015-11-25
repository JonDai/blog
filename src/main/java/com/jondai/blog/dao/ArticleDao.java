package com.jondai.blog.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jondai.blog.entity.Article;

/**
 * @author JonDai
 * @since 2015年11月22日 下午5:28:31
 */
@Repository
public interface ArticleDao extends CrudRepository<Article,Long>{

}
