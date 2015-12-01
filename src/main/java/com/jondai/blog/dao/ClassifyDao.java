package com.jondai.blog.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jondai.blog.entity.Classify;

/**
 * @author JonDai
 * @since 2015年12月1日 上午10:45:11
 */
@Repository
public interface ClassifyDao extends CrudRepository<Classify, Long>{
	public List<Classify> findByLevel(int level);
	public List<Classify> findByPclassify(int pclassify);
}
