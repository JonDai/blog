package com.jondai.blog.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author JonDai
 * @since 2015年12月2日 下午9:42:24
 */
@Service
@Transactional
public abstract class CommonManager {
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> query(String sql) {
		Session session = em.unwrap(org.hibernate.Session.class);
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> result = query.list();
		return result;
	}

	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
	public int update(String sql) {
		Session session = em.unwrap(org.hibernate.Session.class);
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		int rows = query.executeUpdate();
		return rows;
	}
}
