package com.ywcx.price.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ywcx.price.config.BaiduApiInfo;
import com.ywcx.price.constants.Constants;
import com.ywcx.price.pricecounter.RealTimePriceCounter;
import com.ywcx.price.util.HttpUtil;
import com.ywcx.price.util.JsonUtil;

@Service
public class PriceService {
	
	@Autowired
	private TrackService tkService;
	
	@Autowired
	private GeoCoderService geoService;
	
	@Autowired
	private RealTimePriceCounter rtpCounter;
	
	/**
	 * 
	 * @param type SELF：快车  MANY：顺风车
	 * @param city HAIKOU、SANYA
	 * @param entityName DRIVER_ID
	 * @param start_time UNIX TIMESTAM
	 * @param end_time UNIX TIMESTAM
	 * @return 实际价钱
	 * @throws Exception
	 */
	public Double getRealTimePrice(String type,String city,String entityName, String start_time, String end_time) throws Exception {
		JsonObject object = (JsonObject) JsonUtil.getParser().parse(tkService.getDistance(entityName, start_time, end_time));
		Double distance = object.get("distance").getAsDouble();
		return rtpCounter.calPrice("driverId", type, distance, city);
	}
	
	/**
	 * 
	 * @param 出发地中文： 海口市第十四中学
	 * @param 目的地： 龙珠大厦
	 * @return 估算价格
	 * @throws Exception
	 */
	public Map<String, Double> getEstPrice(String oriAdr, String destAdr ,String city) throws Exception {
		//oriAdr destAdr 解析
		String oriLngLat=geoService.addressDecoder(oriAdr);
		String destLngLat=geoService.addressDecoder(destAdr);
		Double distance = parseDistanceByJson(oriLngLat, destLngLat, city);
		Map<String, Double> priceMap= new HashMap<>();
		priceMap.put("SELF", rtpCounter.calPrice("driverId", "SELF", distance, city));
		priceMap.put("MANY", rtpCounter.calPrice("driverId", "MANY", distance, city));
		
		return priceMap;
	}

	private Double parseDistanceByJson(String oriLngLat, String destLngLat, String city) {
		String json=tkService.routematrix(oriLngLat,destLngLat,city);
		JsonObject object = (JsonObject) JsonUtil.getParser().parse(json);
		JsonArray array =object.get("result").getAsJsonArray();
		JsonObject resJson=array.get(0).getAsJsonObject();
		Double distance=resJson.get("distance").getAsJsonObject().get("value").getAsDouble();
		return distance;
	}
	

}
