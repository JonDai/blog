package com.jondai.blog.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.jondai.blog.service.BlogManager;

/**
 * @author JonDai
 * @since 2015年11月26日 下午11:03:01
 */
@RequestMapping("/admin")
@Controller
public class AdminController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BlogManager blogManager;
	
	@RequestMapping("articles")
	@ResponseBody
	public String articles(){
		try{
			return new Gson().toJson(blogManager.getAllArticle());
		}catch(Exception e){
			logger.error("articles:"+e.getMessage());
			Map<String,String> result = new HashMap<String,String>();
			result.put("faild", e.getMessage());
			return new Gson().toJson(result);
		}
	}
	
	@RequestMapping(value="article/{id}")
	@ResponseBody
	public String getArticle(@PathVariable("id") Long id){
		try{
			return new Gson().toJson(blogManager.getArticleById(id));
		}catch(Exception e){
			Map<String,Object> result = new HashMap<String,Object>();
			logger.error("getArticle",e);
			result.put("faild", e.getMessage());
			return new Gson().toJson(result);
		}
	}
}
