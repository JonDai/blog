package com.jondai.blog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author JonDai
 * @since 2015年11月25日 下午10:43:13
 */
@Controller
public class UserController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("signin")
	@ResponseBody
	public String userSignIn(@RequestParam(value="username",required=true) String username,
			@RequestParam(value="password" , required = true)String password){
		
		return null;
	}
}
