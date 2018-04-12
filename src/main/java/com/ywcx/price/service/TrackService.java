package com.ywcx.price.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ywcx.price.bean.MileageBase;
import com.ywcx.price.bean.PriceBase;
import com.ywcx.price.config.BaiduApiInfo;
import com.ywcx.price.contants.Contants;
import com.ywcx.price.pricecounter.RealTimePriceCounter;
import com.ywcx.price.processor.PriceCalculator;
import com.ywcx.price.util.HttpUtil;
import com.ywcx.price.util.NumberUtil;

@Service
public class TrackService {
	private static final String HAIKOU="HAIKOU";
	private static final String SANYA="SANYA";

	@Autowired
	private HttpUtil instance;

	@Autowired
	private BaiduApiInfo bdConfig;

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
			result = instance.getConfCall(Contants.TRACK_ADD_POINT, HttpUtil.map2BasicNVPair(KVParams), true, true,
					false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

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
			result = instance.getConfCall(Contants.TRACK_GET_DISTANCE, HttpUtil.map2BasicNVPair(KVParams), false, false,
					false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Double getPrice(String city,String entityName, String start_time, String end_time) {
		JsonParser parser = new JsonParser();
		JsonObject object = (JsonObject) parser.parse(getDistance(entityName, start_time, end_time));
		String distance = object.get("distance").getAsString();
		Map<String, PriceBase> map= new HashMap<String, PriceBase>();
		initData(map);
		MileageBase mileageBase=initMileage(NumberUtil.holdDecimalPlaces(Double.valueOf(distance)/1000, 2));
		PriceBase priceBase=map.get(city);
		return PriceCalculator.calAllPrice(priceBase,mileageBase);
	}
	
	private static MileageBase initMileage(Double distance) {
		MileageBase mileageBase= new MileageBase();
		mileageBase.setEstimateMileage(3.01);
		mileageBase.setRealTimeMileage(distance);
		mileageBase.setLowSpeedMileage(2.200);
		return mileageBase;
	}

	private static void initData(Map<String, PriceBase> map) {
		PriceBase hkBase= new PriceBase();
		hkBase.setStartKm(3.0);
		hkBase.setFeePreKm(2.1);
		hkBase.setLowSpeedFeePreKm(0.6);
		hkBase.setMidnightFeePreKm(2.4);
		hkBase.setOutOfRangeFeePreKm(0.8);
		hkBase.setStartFee(10.0);
		
		PriceBase syBase= new PriceBase();
		syBase.setStartKm(3.0);
		syBase.setFeePreKm(2.2);
		syBase.setLowSpeedFeePreKm(0.7);
		syBase.setMidnightFeePreKm(2.5);
		syBase.setOutOfRangeFeePreKm(0.9);
		syBase.setStartFee(11.0);
		
		map.put(HAIKOU, hkBase);
		map.put(SANYA, syBase);
		
	}

}
