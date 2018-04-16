package com.ywcx.price.pricecounter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ywcx.price.common.Constants;
import com.ywcx.price.entity.MileageBase;
import com.ywcx.price.entity.PriceBase;
import com.ywcx.price.factory.PriceCounter;
import com.ywcx.price.processor.PriceCalculator;
import com.ywcx.price.service.reporstory.PriceBaseService;
import com.ywcx.price.util.NumberUtil;

@Component
public class ManyPriceCounter extends PriceCounter{
	private Logger logger = LoggerFactory.getLogger(RealTimePriceCounter.class);
	
	public static Map<String, PriceBase> manyMap= new HashMap<String, PriceBase>();
	
	@Autowired
	private PriceBaseService pbService;
	
	@PostConstruct
	public void init(){
		List<PriceBase> manyList =pbService.getAllPriceBaseByType(Constants.MANY_CAR);
		for (PriceBase priceBase : manyList) {
			manyMap.put(priceBase.getCity(), priceBase);
		}
	}

	public Double callPrice(Double distance, String city) {
		logger.debug("Begin calculate many car price ...");
		//TODO
		MileageBase mileageBase=initMileage(NumberUtil.holdDecimalPlaces(distance/1000, 2));
		PriceBase priceBase=manyMap.get(city);
		if (null==priceBase) {
			//TODO
//			throw new Exception("目前该城市还未开通顺风车服务，敬请期待。");
		}
		return PriceCalculator.calAllPrice(priceBase,mileageBase);
	}

}
