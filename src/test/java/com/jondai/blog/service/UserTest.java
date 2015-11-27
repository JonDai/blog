package com.jondai.blog.service;

import java.security.NoSuchAlgorithmException;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jondai.blog.entity.User;

/**
 * @author JonDai
 * @since 2015年11月26日 下午12:06:01
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserTest {

	@Autowired UserManager userManger;
	
	@Test
	public void testnewRegist() throws NoSuchAlgorithmException{
		User user = new User();
		user.setUsername("test");
		user.setPassword("111111");
		User u = userManger.newRegist(user);
		System.out.println(u.getPassword());
	}
	
	@Test
	public void testcheckUser(){
	}
	

}
