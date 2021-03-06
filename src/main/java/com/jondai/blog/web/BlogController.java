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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jondai.blog.entity.User;
import com.jondai.blog.service.BlogManager;
import com.jondai.blog.service.UserManager;

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
	@Autowired
	private UserManager userManager;
	
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
			blogManager.seveArticleReadCount(id);
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
	
	@RequestMapping(value="adduser" , method = RequestMethod.POST)
	@ResponseBody
	public String addUser(User user){
		try{
			userManager.hasUser(user);
			return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(userManager.newRegist(user));
		}catch(Exception e){
			Map<String,Object> result = new HashMap<String,Object>();
			logger.error("pageLoad",e);
			result.put("faild", e.getMessage());
			return new Gson().toJson(result);
		}
	}
	
	@RequestMapping(value="twoclassify" , method = RequestMethod.POST)
	@ResponseBody
	public String classifyByLevelone(@RequestParam(value="pclassify",required=true) Integer pclassify){
		try{
			return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(blogManager.getClassifyByPClassify(pclassify));
		}catch(Exception e){
			Map<String,Object> result = new HashMap<String,Object>();
			logger.error("getArticle",e);
			result.put("faild", e.getMessage());
			return new Gson().toJson(result);
		}
	}
	
	@RequestMapping(value="articlesbyclass" , method = RequestMethod.POST)
	@ResponseBody
	public String articlesByClass(@RequestParam(value="classifyid",required=true) int classifyid){
		try{
			return new Gson().toJson(blogManager.getAritcleByPid(classifyid));
		}catch(Exception e){
			Map<String,Object> result = new HashMap<String,Object>();
			logger.error("articlesByClass",e);
			result.put("faild", e.getMessage());
			return new Gson().toJson(result);
		}
	}
}
