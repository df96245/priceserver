package com.ywcx.price.util;

import java.text.DecimalFormat;

public class NumberUtil {
	public static Double holdDecimalPlaces(Double beHoldDecimal, int placeNum) {
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i < placeNum; i++) {
			sBuilder.append("0");
		}
		String pattern="#."+sBuilder.toString();
		DecimalFormat df = new DecimalFormat(pattern);  
		return Double.valueOf(df.format(beHoldDecimal));
	}
}
