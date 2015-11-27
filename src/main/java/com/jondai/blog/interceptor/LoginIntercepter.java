package com.jondai.blog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jondai.blog.entity.User;
import com.jondai.blog.service.UserManager;
import com.jondai.blog.util.Constants;

/**
 * @author JonDai
 * @since 2015年11月27日 上午9:58:57
 */
public class LoginIntercepter extends HandlerInterceptorAdapter{
	
	@Autowired
	private UserManager userManager;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURI();
		if(url.contains(Constants.ADMIN)){
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			if(userManager.verifiUser(username, password)){
				return true;
			}else{
				//转到首页
				response.sendRedirect("/index.html");
				return false;
			}
		}else{
			//转到首页
			response.sendRedirect("/index.html");
			return false;
		}
	}
	
}
