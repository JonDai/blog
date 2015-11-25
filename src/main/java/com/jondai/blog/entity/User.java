package com.jondai.blog.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author JonDai
 * @since 2015年11月22日 下午4:51:35
 */
@Entity
@Table(name="user")
public class User extends IdEntity{
	private String username;
	private String password;
	private String ip;
	private String createtime;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
}
