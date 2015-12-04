package com.jondai.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**
 * @author JonDai
 * @since 2015年11月22日 下午4:48:44
 */
@Entity
@Table(name = "article")
public class Article extends IdEntity{
	/**
	 * Classify分类id
	 */
	@Expose
	private int pid;
	@Expose
	private String title;
	@Expose
	@Column(length=100000)
	private String content;
	@Expose
	private int status;
	@Expose
	private String createtime;
	@Expose
	private String updatetime;
	@Expose
	private String tag;
	@Expose
	private int readcount;
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
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
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	
}
