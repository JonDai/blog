package com.jondai.blog.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jondai.blog.service.UserManager;

/**
 * @author JonDai
 * @since 2015年11月25日 下午10:43:13
 */
@Controller
public class UserController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserManager userManager;
	
	@RequestMapping("signin")
	@ResponseBody
	public String userSignIn(@RequestParam(value="username",required=true) String username,
			@RequestParam(value="password" , required = true)String password){
		try{
			return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(userManager.checkUser(username, password));
		}catch(Exception e){
			Map<String,Object> result = new HashMap<String,Object>();
			logger.error("userSignIn name "+username+":"+e.getMessage());
			result.put("faild", e.getMessage());
			return new Gson().toJson(result);
		}
	}
}
