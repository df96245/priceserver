package com.ywcx.price.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ywcx.price.common.Constants;
import com.ywcx.price.entity.json.BaiduResponse;
import com.ywcx.price.entity.json.Result;
public class JsonUtil {
	private static Logger logger=LoggerFactory.getLogger(JsonUtil.class);

	//两点间距离
	public static Double parseDistanceByJson(String json) {
		logger.info("received json: {}",json);
		if (StringUtils.isBlank(json)) {
			return Constants.INCORRECT_STATUS;
		}
		if (!json.contains("\"status\":0")) {
			if (json.contains(Constants.OUT_TIME_RANGE)) {
				return Constants.ILLEGALE_AGUMENT;
			}
			return Constants.INCORRECT_STATUS;
		}
		BaiduResponse<Result> obj = (BaiduResponse<Result>) JSON.parseObject(json, new TypeReference<BaiduResponse<Result>>(){});
		Double distance = obj.getDistance();
		distance = null!=distance ? distance : obj.getResult().get(0).getDistance().getValue();
		return distance;
		
	}
	
	public static String buildSimpleJson(int code , String msg) {
		String str =    "{\"status\":"
				+ code+",\"message\":"
				+ msg +
				"} ";
		return  str;
	}

}
