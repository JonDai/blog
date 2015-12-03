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
import com.jondai.blog.entity.Article;
import com.jondai.blog.entity.Classify;
import com.jondai.blog.entity.User;
import com.jondai.blog.service.BlogManager;
import com.jondai.blog.service.UserManager;
import com.jondai.blog.util.Constants;

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
	@Autowired
	private UserManager userManager;
	
	@RequestMapping(value = "articlesbyclassify",method = RequestMethod.POST)
	@ResponseBody
	public String articles(User user ,Classify classify){
		/*验证用户是否登陆*/
		Map<String,String> result = new HashMap<String,String>();
		if(!userManager.verifiUser(user)){
			result.put("faild", "logon");
			return new Gson().toJson(result);
		}
		try{
			return new Gson().toJson(blogManager.getArticleByPclassify(classify.getPclassify()));
		}catch(Exception e){
			logger.error("articles:"+e.getMessage());
			result.put("faild", e.getMessage());
			return new Gson().toJson(result);
		}
	}
	
	@RequestMapping(value="article/{id}",method = RequestMethod.POST)
	@ResponseBody
	public String getArticle(@PathVariable("id") Long id,User user){
		/*验证用户是否登陆*/
		Map<String,String> result = new HashMap<String,String>();
		if(!userManager.verifiUser(user)){
			result.put("faild", "logon");
			return new Gson().toJson(result);
		}
		try{
			return new Gson().toJson(blogManager.getArticleById(id));
		}catch(Exception e){
			logger.error("getArticle",e);
			result.put("faild", e.getMessage());
			return new Gson().toJson(result);
		}
	}
	
	@RequestMapping(value="savearticle" , method = RequestMethod.POST)
	@ResponseBody
	public String saveArticle(Article article , User user){
		/*验证用户是否登陆*/
		Map<String,String> result = new HashMap<String,String>();
		if(!userManager.verifiUser(user)){
			result.put("faild", "logon");
			return new Gson().toJson(result);
		}else{
			try{
				blogManager.saveArticle(article);
				result.put("success", article.getTitle());
			}catch(Exception e){
				logger.error("getArticle",e);
				result.put("faild", e.getMessage());
			}
			return new Gson().toJson(result);
		}
	}
	
	/**
	 * 删除文章
	 */
	@RequestMapping(value="deletearticle/{id}",method = RequestMethod.POST)
	@ResponseBody
	public String deleteArticle(@PathVariable("id") Long id,User user){
		/*验证用户是否登陆*/
		Map<String,Object> result = new HashMap<String,Object>();
		if(!userManager.verifiUser(user)){
			result.put("faild", "logon");
			return new Gson().toJson(result);
		}
		
		try{
			blogManager.deleteArticleById(id);
			result.put("success", id);
		}catch(Exception e){
			logger.error("getArticle",e);
			result.put("faild", e.getMessage());
		}
		return new Gson().toJson(result);
	}
	
	
	/**
	 * 加载后台页面时，验证是否已经登陆
	 */
	@RequestMapping(value = "vervifi",method = RequestMethod.POST)
	@ResponseBody
	public String verifiLogon(User user){
		Map<String,String> result = new HashMap<String,String>();
		try{
			if(userManager.verifiUser(user)){
				result.put("success", user.getUsername());
			}else{
				result.put("faild", Constants.NOT_LOGON);
			}
		}catch(Exception e){
			logger.error("getArticle",e);
			result.put("faild", e.getMessage());
		}
		return new Gson().toJson(result);
	}
	
	@RequestMapping(value = "classifies",method = RequestMethod.POST)
	@ResponseBody
	public String classifies(User user,Classify classify){
		/*验证用户是否登陆*/
		Map<String,Object> result = new HashMap<String,Object>();
		if(!userManager.verifiUser(user)){
			result.put("faild", "logon");
			return new Gson().toJson(result);
		}
		try{
			return new Gson().toJson(blogManager.getClassifyByPClassify(classify.getPclassify()));
		}catch(Exception e){
			logger.error("classifies",e);
			result.put("faild", e.getMessage());
			return new Gson().toJson(result);
		}
	}
	
	@RequestMapping(value = "addclassify",method = RequestMethod.POST)
	@ResponseBody
	public String addClassify(User user ,Classify classify){
		/*验证用户是否登陆*/
		Map<String,Object> result = new HashMap<String,Object>();
		if(!userManager.verifiUser(user)){
			result.put("faild", "logon");
			return new Gson().toJson(result);
		}
		try{
			blogManager.addClassify(classify);
		}catch(Exception e){
			logger.error("getArticle",e);
			result.put("faild", e.getMessage());
		}
		return new Gson().toJson(result);
	}
}
