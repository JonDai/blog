package com.jondai.blog.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**
 * @author JonDai
 * @since 2015年12月1日 上午10:30:46
 * 分类列表
 */
@Entity
@Table(name="classify")
public class Classify extends IdEntity{
	/**
	 * 父级分类(导航栏中的分类)
	 * 1:dream
	 * 2:life
	 * 3:tech
	 */
	@Expose
	private int pclassify;
	@Expose
	private String name;
	/**
	 * 1:导航栏中的的分类
	 * 2:点击导航栏后，以下的分类
	 */
	@Expose
	private int level;
	
	/**
	 * 该分类下blog的数量
	 */
	@Expose
	private int count;
	
	/**
	 * 描述
	 */
	@Expose
	private String description;
	@Expose
	private String createtime;

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPclassify() {
		return pclassify;
	}

	public void setPclassify(int pclassify) {
		this.pclassify = pclassify;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
