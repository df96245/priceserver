package com.ywcx.price.common;

public class Constants {
	public static final String SELF_CAR="SELF";
	public static final String MANY_CAR="MANY";
	
	
	//BAIDU URL
	public static final String TRACK_ADD_POINT="http://yingyan.baidu.com/api/v3/track/addpoint";
	public static final String TRACK_GET_DISTANCE="http://yingyan.baidu.com/api/v3/track/getdistance";
	public static final String TRACK_ROUTE_MATRIX="http://api.map.baidu.com/routematrix/v2/driving";
	
	public static final String GEO_CODER="http://api.map.baidu.com/geocoder/v2/";

	
	public static final String OUT_TIME_RANGE="起始和结束时间必须在24小时之内";
	public static final String OVER_RETRY_TIMES="重试次数超过限制，请重新尝试。";
	
	public static final Double ILLEGALE_AGUMENT=-2.0;
	public static final Double INCORRECT_STATUS=-1.0;
	
}
