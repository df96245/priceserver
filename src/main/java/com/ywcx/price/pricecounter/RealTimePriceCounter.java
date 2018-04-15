package com.ywcx.price.pricecounter;

import org.springframework.stereotype.Component;

import com.ywcx.price.factory.PriceCounterFactory;

@Component
public class RealTimePriceCounter {
	
	public Double calPrice(String driverId, String type, Double distance ,String city) {
		return PriceCounterFactory.getPriceCounter(type).callPrice(distance, city);
	}

}
