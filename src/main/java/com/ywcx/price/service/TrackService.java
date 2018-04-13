package com.ywcx.price.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ywcx.price.config.BaiduApiInfo;
import com.ywcx.price.constants.Constants;
import com.ywcx.price.pricecounter.RealTimePriceCounter;
import com.ywcx.price.util.HttpUtil;

@Service
public class TrackService extends BaiduAbstractService{
	
	public String getDistance(String entityName, String start_time, String end_time) {
		String result = "";
		try {
			Map<String, String> KVParams = new HashMap<>();
			KVParams.put("ak", bdConfig.getAk());
			KVParams.put("service_id", bdConfig.getServiceId());
			KVParams.put("entity_name", entityName);
			KVParams.put("process_option", "need_denoise=1,need_denoise =1,need_mapmatch=1,radius_threshold=100");
			KVParams.put("start_time", start_time);
			KVParams.put("end_time", end_time);
			KVParams.put("supplement_mode", "driving");
			result = instance.getConfCall(Constants.TRACK_GET_DISTANCE, HttpUtil.map2BasicNVPair(KVParams), false, false,
					false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//计算两个点的距离,用于估算价格.
	public String routematrix(String oriCoordinate,String destCoordinate,String city) {
		String result = "";
		try {
			Map<String, String> KVParams = new HashMap<>();
			KVParams.put("ak", bdConfig.getAk());
			KVParams.put("origins", oriCoordinate);
			KVParams.put("destinations", destCoordinate);
			KVParams.put("city", city);
			result = instance.getConfCall(Constants.TRACK_ROUTE_MATRIX, HttpUtil.map2BasicNVPair(KVParams), false, false,
					false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String addPoint(String entityName, String lat, String lon) {
		String result = "";
		try {
			Map<String, String> KVParams = new HashMap<>();
			KVParams.put("ak", bdConfig.getAk());
			KVParams.put("entity_name", entityName);
			KVParams.put("service_id", bdConfig.getServiceId());
			KVParams.put("latitude", lat);
			KVParams.put("longitude", lon);
			KVParams.put("coord_type_input", bdConfig.getCoordTypeInput());
			KVParams.put("loc_time", String.valueOf(System.currentTimeMillis() / 1000));
			result = instance.getConfCall(Constants.TRACK_ADD_POINT, HttpUtil.map2BasicNVPair(KVParams), true, true,
					false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


}
