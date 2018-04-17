package com.ywcx.price.pricecounter;

import com.ywcx.price.contants.Contants;

public class RealTimePriceCounter {

	public void calPrice(String driverId, String type) {
		if (type.trim().equals(Contants.SELF_CAR)) {
			System.out.println("It is self car...");
			callSelfPrice();
		} else if (type.trim().equals(Contants.MANY_CAR)) {
			System.out.println("It is Many car...");
			callManyPrice();
		}
	}

	private void callSelfPrice() {
		System.out.println("Begin calculate self car price ...");
	}

	private void callManyPrice() {
		System.out.println("Begin calculate many car price ...");
	}

	public static Double calMileageFee(Double startFee, Double startMile, Double distance) {
		if (distance<startMile) {
			return startFee;
		}
		Double mileDis=distance-startMile;
		Double pricePreMile=2.1;
		
		Double mileFee=pricePreMile*mileDis;
		
		return mileFee+startFee;
	}

}
