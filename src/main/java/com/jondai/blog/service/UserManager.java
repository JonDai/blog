package com.jondai.blog.service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jondai.blog.dao.UserDao;
import com.jondai.blog.entity.User;
import com.jondai.blog.util.Constants;
import com.jondai.blog.util.Digest;
import com.jondai.blog.util.StringUtil;

/**
 * @author JonDai
 * @since 2015年11月26日 上午11:45:45
 */
@Service
public class UserManager {
	@Autowired 
	private UserDao uDao;
	
	/**
	 *验证用户名和密码
	 * @throws Exception 
	 */
	public User checkUser(String username, String password) throws Exception{
		List<User> users = uDao.findByUsername(username);
		if(users == null || users.size() < 1){
			throw new Exception("用户名或密码不正确");
		}else{
			User user = users.get(0);
			if(password.trim().equals(user.getPassword())){
				return user;
			}else{
				throw new Exception("用户名或密码不正确");
			}
		}
	}
	
	public User newRegist(User user) throws NoSuchAlgorithmException{
		String sha1 = Digest.SHA1(user.getPassword());
		user.setPassword(StringUtil.replace(sha1, ":", "").toLowerCase());
		user.setCreatetime(Constants.DF_yyyyMMddHHmmss.format(new Date()));
		uDao.save(user);
		return user;
	}
	
	/**
	 * 检验用户名和密码是否正确
	 */
	public boolean verifiUser(String username ,String password){
		List<User> users = uDao.findByUsername(username);
		if(users == null || users.size() < 1){
			return false;
		}else if(!users.get(0).getPassword().equals(password)){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean verifiUser(User user){
		return verifiUser(user.getUsername(), user.getPassword());
	}
	/**
	 * 检查是否已经存在该用户
	 * @throws Exception 
	 */
	public void hasUser(User user) throws Exception{
		List<User> user1 = uDao.findByUsername(user.getUsername());
		List<User> user2 = uDao.findByEmail(user.getEmail());
		if(user1 != null && user1.size() >= 1){
			throw new Exception("该用户名已经注册!");
		}else if(user2 != null && user2.size() >= 1){
			throw new Exception("该邮箱已经注册!");
		}
	}
}
