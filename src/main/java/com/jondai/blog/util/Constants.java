package com.jondai.blog.util;

import java.text.SimpleDateFormat;

/**
 * @author JonDai
 * @since 2015年11月25日 下午11:12:43
 */
public interface Constants {
	public final int PAGESIZE = 20;
	public final SimpleDateFormat DF_yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final SimpleDateFormat DF_DATE_SEQUENCE = new SimpleDateFormat("yyyyMMddHHmmss");
	public final SimpleDateFormat DF_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	public final SimpleDateFormat DF_YY = new SimpleDateFormat("yy");
	public final SimpleDateFormat DF_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	public final SimpleDateFormat DF_HHmmss = new SimpleDateFormat("HH:mm:ss");
	
	public final static String ADMIN = "admin";
	public final static String NOT_LOGON ="logon";
	public final static Integer DRAFT = 0;
	public final static Integer PUBLISH = 1; 
}
