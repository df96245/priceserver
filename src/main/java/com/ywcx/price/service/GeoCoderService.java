package com.ywcx.price.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.ywcx.price.constants.Constants;
import com.ywcx.price.util.HttpUtil;
import com.ywcx.price.util.JsonUtil;

@Service
public class GeoCoderService extends BaiduAbstractService{
	
	public String addressDecoder(String adrBeDecoder) {
		String result = "";
		try {
			Map<String, String> KVParams = new HashMap<>();
			KVParams.put("ak", bdConfig.getAk());
			KVParams.put("output", bdConfig.getOutput());
			KVParams.put("address", adrBeDecoder);
			result = instance.getConfCall(Constants.GEO_CODER, HttpUtil.map2BasicNVPair(KVParams), false, false,false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonObject object = (JsonObject) JsonUtil.getParser().parse(result);
		JsonObject location=object.get("result").getAsJsonObject().get("location").getAsJsonObject();
		String lng=location.get("lng").getAsString();
		String lat=location.get("lat").getAsString();
		result=lat+","+lng;
				
		return result;
	}

}
