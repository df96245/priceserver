package com.ywcx.price.factory;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ywcx.price.common.Constants;
import com.ywcx.price.pricecounter.ManyPriceCounter;
import com.ywcx.price.pricecounter.SelfPriceCounter;

public class PriceCounterFactory {
	
	private static Logger logger= LoggerFactory.getLogger(PriceCounterFactory.class);
	private static List<String> typeList= new ArrayList<>();
	
	static {
		typeList.add(Constants.MANY_CAR);
		typeList.add(Constants.SELF_CAR);
	}
	
	public static PriceCounter getPriceCounter(String type) {
		if (type.trim().equals(Constants.SELF_CAR)) {
			return new SelfPriceCounter();
		}
		if (type.trim().equals(Constants.MANY_CAR)) {
			return new ManyPriceCounter();
		}
		
		logger.error("目前仅支持{}和{}，请确认选择的车型正确。",typeList.toArray());
		return null;
		
	}
}
