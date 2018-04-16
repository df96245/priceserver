package com.ywcx.price.processor;

import com.ywcx.price.entity.Fee;
import com.ywcx.price.entity.MileageBase;
import com.ywcx.price.entity.PriceBase;
import com.ywcx.price.util.NumberUtil;

public class PriceCalculator {
	
	/*public static Double estimatePrice() {
		
	}*/
	
	public static Double calAllPrice(PriceBase priceBase, MileageBase mileageBase) {
		
		Double higthWayFee=0.0;
		Double lowSpeedFee = calLowSpeedMileageFee(priceBase.getLowSpeedFeePreKm(), mileageBase.getLowSpeedMileage());
		Double midnightFee=0.0;
		Double mileageFee=PriceCalculator.calMileageFee(priceBase.getStartFee(), priceBase.getStartKm(),priceBase.getFeePreKm(), mileageBase.getRealTimeMileage());
		Double outOfRangeFee=0.0;
		
		
		Fee fee = new Fee();
		fee.buildHigthWayFee(higthWayFee)
		.buildLowSpeedFee(lowSpeedFee)
		.buildMidNightFee(midnightFee)
		.buildMileageFee(mileageFee)
		.buildOutOfRangeFee(outOfRangeFee);
		return fee.getAllFee();
	}
	
	public static Double calEstimatePrice(PriceBase priceBase, MileageBase mileageBase) {
		//TODO
		return 0.0;
	}
	
	//if distance<startMile ? startFee : (distance-startMile)*feePreKm + startFee;
	private static Double calMileageFee(Double startFee, Double startMile,Double feePreKm, Double distance) {
		if (distance<=startMile) {
			return startFee;
		}
		Double mileDis=distance-startMile;
		Double mileFee=feePreKm*mileDis;
		Double totalFee=mileFee+startFee;
		return NumberUtil.holdDecimalPlaces(totalFee,2);
	}

	
	private static Double calLowSpeedMileageFee(Double lowSpPricePreKm ,Double lowSpMileage) {
		return NumberUtil.holdDecimalPlaces(lowSpPricePreKm*lowSpMileage,2);
		
	}
	
}
