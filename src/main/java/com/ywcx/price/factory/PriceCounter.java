package com.ywcx.price.factory;

import java.util.List;

import javax.annotation.PostConstruct;

import com.ywcx.price.bean.MileageBase;
import com.ywcx.price.bean.PriceBase;
import com.ywcx.price.common.Constants;

public abstract class PriceCounter {
	
	public Double callPrice(Double distance,String city) {
		return 0.0;
	};
	
	protected MileageBase initMileage(Double distance) {
		MileageBase mileageBase= new MileageBase();
		mileageBase.setEstimateMileage(distance);
		mileageBase.setRealTimeMileage(distance);
		mileageBase.setLowSpeedMileage(0.0);
		return mileageBase;
	}
	
}