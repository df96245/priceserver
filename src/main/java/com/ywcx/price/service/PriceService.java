package com.ywcx.price.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ywcx.price.common.Constants;
import com.ywcx.price.common.ResponseCode;
import com.ywcx.price.common.ServerResponse;
import com.ywcx.price.entity.json.Result;
import com.ywcx.price.entity.json.BaiduResponse;
import com.ywcx.price.pricecounter.RealTimePriceCounter;
import com.ywcx.price.util.JsonUtil;

@Service
public class PriceService {
	private Logger logger = LoggerFactory.getLogger(PriceService.class);
	
	private ThreadLocal<String> ori = new ThreadLocal<String>();
	private ThreadLocal<String> dest = new ThreadLocal<String>();
	
	@Autowired
	private TrackService tkService;

	@Autowired
	private RealTimePriceCounter rtpCounter;

	public ServerResponse getRealTimePrice(String type, String city, String entityName, String start_time, String end_time)
			throws Exception {
		String json = tkService.getDistance(entityName, start_time, end_time);
		Double distance  = JsonUtil.parseDistanceByJson(json);
		if (distance<0) {
			if (distance==Constants.INCORRECT_STATUS) {
				String message= "获取距离失败。";
				return ServerResponse.createByErrorCodeMessage(ResponseCode.CALL_BAIDU_FAILD.getCode(), message);
			}else {
				String message= "起始和结束时间必须在24小时之内";
				return ServerResponse.createByErrorCodeMessage(ResponseCode.CALL_BAIDU_FAILD.getCode(), message);
			}
		}
		if (distance==0) {
			String message= "最近时间没位置没有变化.";
			return ServerResponse.createByErrorCodeMessage(ResponseCode.CALL_BAIDU_FAILD.getCode(), message);
		}
		Double realTimePrice = rtpCounter.calPrice("driverId", type, distance, city);
		return ServerResponse.createBySuccess("获取实时价钱成功",realTimePrice);
	}
	
	public ServerResponse getEstPrice(String oriLatLng, String destLatLng, String city) throws Exception {
		ori.set(oriLatLng);
		dest.set(destLatLng);
		String json = tkService.routematrix(oriLatLng, destLatLng, city);
		Double distance = JsonUtil.parseDistanceByJson(json);
		if (distance<0) {
			if (distance==Constants.INCORRECT_STATUS) {
				String message= "获取距离失败。";
				return ServerResponse.createByErrorCodeMessage(ResponseCode.CALL_BAIDU_FAILD.getCode(), message);
			}else {
				String message= "起始和结束时间必须在24小时之内";
				return ServerResponse.createByErrorCodeMessage(ResponseCode.CALL_BAIDU_FAILD.getCode(), message);
			}
		}
		if (distance==0) {
			String message= "起点和终点不能相同。";
			return ServerResponse.createByErrorCodeMessage(ResponseCode.CALL_BAIDU_FAILD.getCode(),message);
		}
		Map<String, Double> priceMap = new HashMap<>();
		priceMap.put("SELF", rtpCounter.calPrice("driverId", "SELF", distance, city));
		priceMap.put("MANY", rtpCounter.calPrice("driverId", "MANY", distance, city));

		return ServerResponse.createBySuccess("获取估算价格成功",priceMap);
	}


}
