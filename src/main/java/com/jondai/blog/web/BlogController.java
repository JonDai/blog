package com.jondai.blog.web;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jondai.blog.service.BlogManager;

/**
 * @author JonDai
 * @since 2015年11月22日 下午10:16:50
 */
@Controller
//@RequestMapping(value = "/post")
public class BlogController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private BlogManager blogManager;
	
	@RequestMapping(value="postlately")
	@ResponseBody
	public String postlately(){
		try{
			return new Gson().toJson(blogManager.getAllArticle());
		}catch(Exception e){
			Map<String,Object> result = new HashMap<String,Object>();
			logger.error("postlately",e);
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
	
	@RequestMapping(value="loadpage/{currpage}" , method = RequestMethod.POST)
	@ResponseBody
	public String pageLoad(@PathVariable("currpage") Integer currpage){
		try{
			return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(blogManager.getArticlesByPage(currpage));
		}catch(Exception e){
			Map<String,Object> result = new HashMap<String,Object>();
			logger.error("pageLoad",e);
			result.put("faild", e.getMessage());
			return new Gson().toJson(result);
		}
	}
}
