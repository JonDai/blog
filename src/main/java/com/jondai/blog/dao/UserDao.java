package com.jondai.blog.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jondai.blog.entity.User;

/**
 * @author JonDai
 * @since 2015年11月26日 上午11:46:16
 */
@Repository
public interface UserDao extends CrudRepository<User, Long>{
	
	public List<User> findByUsername(String username);
	
	public List<User> findByEmail(String email);
}
