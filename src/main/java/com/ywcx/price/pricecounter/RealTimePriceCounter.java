package com.ywcx.price.pricecounter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ywcx.price.bean.MileageBase;
import com.ywcx.price.bean.PriceBase;
import com.ywcx.price.constants.Constants;
import com.ywcx.price.processor.PriceCalculator;
import com.ywcx.price.service.TrackService;
import com.ywcx.price.service.reporstory.PriceBaseService;
import com.ywcx.price.util.NumberUtil;

@Component
public class RealTimePriceCounter {
	private Logger logger= Logger.getLogger(RealTimePriceCounter.class);
	
	private Map<String, PriceBase> selfMap= new HashMap<String, PriceBase>();
	private Map<String, PriceBase> manyMap= new HashMap<String, PriceBase>();
	
	@Autowired
	private PriceBaseService pbService;
	
	
	@PostConstruct
	public void initPriceBaseIntoCache(){
		List<PriceBase> selfList =pbService.getAllPriceBaseByType(Constants.SELF_CAR);
		for (PriceBase priceBase : selfList) {
			selfMap.put(priceBase.getCity(), priceBase);
		}
		
		List<PriceBase> manyList =pbService.getAllPriceBaseByType(Constants.MANY_CAR);
		for (PriceBase priceBase : manyList) {
			manyMap.put(priceBase.getCity(), priceBase);
		}
	}
	
	public Double calPrice(String driverId, String type, Double distance ,String city) {
		if (type.trim().equals(Constants.SELF_CAR)) {
			System.out.println("It is self car...");
			return callSelfPrice(distance,city);
		} else if (type.trim().equals(Constants.MANY_CAR)) {
			System.out.println("It is Many car...");
			return callManyPrice(distance,city);
		}else {
			logger.error("目前仅支持快车和顺风车，请确认选择的车型正确。");
		}
		return 0.0;
	}

	private Double callSelfPrice(Double distance ,String city) {
		System.out.println("Begin calculate self car price ...");
		//TODO
		MileageBase mileageBase=initMileage(NumberUtil.holdDecimalPlaces(distance/1000, 2));
		
		PriceBase priceBase=selfMap.get(city);
		if (null==priceBase) {
			//TODO
//			throw new Exception("目前该城市还未开通专车服务，敬请期待。");
		}
		return PriceCalculator.calAllPrice(priceBase,mileageBase);
	}

	private Double callManyPrice(Double distance ,String city) {
		System.out.println("Begin calculate many car price ...");
		//TODO
		MileageBase mileageBase=initMileage(NumberUtil.holdDecimalPlaces(distance/1000, 2));
		PriceBase priceBase=manyMap.get(city);
		if (null==priceBase) {
			//TODO
//			throw new Exception("目前该城市还未开通顺风车服务，敬请期待。");
		}
		return PriceCalculator.calAllPrice(priceBase,mileageBase);
	}
	
	private static MileageBase initMileage(Double distance) {
		MileageBase mileageBase= new MileageBase();
		mileageBase.setEstimateMileage(distance);
		mileageBase.setRealTimeMileage(distance);
		mileageBase.setLowSpeedMileage(0.0);
		return mileageBase;
	}
	

}
