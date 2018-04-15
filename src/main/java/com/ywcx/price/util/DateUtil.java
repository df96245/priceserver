package com.ywcx.price.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class DateUtil {
	private static Logger logger=LoggerFactory.getLogger(DateUtil.class);
	
	public static String getDate() {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return  simpleDateFormat.format(date);
	}
	public static String getDateChina() {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
		return  simpleDateFormat.format(date);
	}
	
	public static String getDate(long time) {
		Date date = new Date(time);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return  simpleDateFormat.format(date);
	}
	
	public static String getDate(String time) {
		Date date = new Date(Long.valueOf(time));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return  simpleDateFormat.format(date);
	}
	public static String getDate(Date time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return  simpleDateFormat.format(time);
	}
	
}
