package com.ywcx.price.pricecounter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ywcx.price.bean.MileageBase;
import com.ywcx.price.bean.PriceBase;
import com.ywcx.price.common.Constants;
import com.ywcx.price.factory.PriceCounter;
import com.ywcx.price.processor.PriceCalculator;
import com.ywcx.price.service.reporstory.PriceBaseService;
import com.ywcx.price.util.NumberUtil;

@Component
public class SelfPriceCounter extends PriceCounter {
	private Logger logger = LoggerFactory.getLogger(RealTimePriceCounter.class);
	
	public static Map<String, PriceBase> selfMap= new HashMap<String, PriceBase>();
	
	@Autowired
	private PriceBaseService pbService;
	
	
	@PostConstruct
	public void init(){
		List<PriceBase> selfList =pbService.getAllPriceBaseByType(Constants.SELF_CAR);
		for (PriceBase priceBase : selfList) {
			selfMap.put(priceBase.getCity(), priceBase);
		}
	}


	public Double callPrice(Double distance, String city) {
		logger.debug("Begin calculate self car price ...");
		// TODO
		MileageBase mileageBase = initMileage(NumberUtil.holdDecimalPlaces(distance / 1000, 2));

		PriceBase priceBase = selfMap.get(city);
		if (null == priceBase) {
			// TODO
			// throw new Exception("目前该城市还未开通专车服务，敬请期待。");
		}
		return PriceCalculator.calAllPrice(priceBase, mileageBase);
	}

}
