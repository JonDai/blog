package com.jondai.blog.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author JonDai
 * @since 2015年11月22日 下午4:48:44
 */
@Entity
@Table(name = "article")
public class Article extends IdEntity{
	private String title;
	private String content;
	private int status;
	private String createtime;
	private String updatetime;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public int getStatus() {
		return status;
	}
	/**
	 * 0:草稿
	 * 1:发布
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
}
