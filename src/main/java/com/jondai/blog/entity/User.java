package com.jondai.blog.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**
 * @author JonDai
 * @since 2015年11月22日 下午4:51:35
 */
@Entity
@Table(name="user")
public class User extends IdEntity{
	@Expose
	private String username;
	private String password;
	@Expose
	private String email;
	@Expose
	private String ip;
	@Expose
	private String createtime;
	@Expose
	private String lastLoginTime;
	
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
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
