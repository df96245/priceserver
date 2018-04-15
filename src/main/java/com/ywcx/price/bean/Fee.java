package com.ywcx.price.bean;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fee{
	private static Logger logger = LoggerFactory.getLogger(Fee.class);
	
	private Double mileageFee=0.0D;
	private Double lowSpeedFee=0.0D;
	private Double higthWayFee=0.0D;
	private Double midNightFee=0.0D;
	private Double outOfRangeFee=0.0D;
	
	public Fee buildMileageFee(Double mileageFee) {
		this.setMileageFee(mileageFee);
		return this;
	}
	
	public Fee buildLowSpeedFee(Double lowSpeedFee) {
		this.setLowSpeedFee(lowSpeedFee);
		return this;
	}
	
	public Fee buildHigthWayFee(Double higthWayFee) {
		this.setHigthWayFee(higthWayFee);
		return this;
	}
	public Fee buildMidNightFee(Double midNightFee) {
		this.setMidNightFee(midNightFee);
		return this;
	}
	public Fee buildOutOfRangeFee(Double outOfRangeFee) {
		this.setOutOfRangeFee(outOfRangeFee);
		return this;
	}
	public Double getMileageFee() {
		return mileageFee;
	}
	private void setMileageFee(Double mileageFee) {
		this.mileageFee = mileageFee;
	}
	public Double getLowSpeedFee() {
		return lowSpeedFee;
	}
	private void setLowSpeedFee(Double lowSpeedFee) {
		this.lowSpeedFee = lowSpeedFee;
	}
	public Double getHigthWayFee() {
		return higthWayFee;
	}
	private void setHigthWayFee(Double higthWayFee) {
		this.higthWayFee = higthWayFee;
	}
	public Double getMidNightFee() {
		return midNightFee;
	}
	private void setMidNightFee(Double midNightFee) {
		this.midNightFee = midNightFee;
	}
	public Double getOutOfRangeFee() {
		return outOfRangeFee;
	}
	private void setOutOfRangeFee(Double outOfRangeFee) {
		this.outOfRangeFee = outOfRangeFee;
	}
	
	@Override
	public String toString() {
		return "Fee [mileageFee=" + mileageFee + ", lowSpeedFee=" + lowSpeedFee
				+ ", higthWayFee=" + higthWayFee + ", midNightFee=" + midNightFee + ", outOfRangeFee=" + outOfRangeFee
				+ "]";
	}

	public Double getAllFee() {
		logger.info("Fee [mileageFee=" + mileageFee + ", lowSpeedFee=" + lowSpeedFee
				+ ", higthWayFee=" + higthWayFee + ", midNightFee=" + midNightFee + ", outOfRangeFee=" + outOfRangeFee
				+ "]");
		
		BigDecimal bdMileageFee=new BigDecimal(String.valueOf(mileageFee));
		BigDecimal  bdLowSpeedFee=new BigDecimal(String.valueOf(lowSpeedFee));
		BigDecimal  bdHigthWayFee=new BigDecimal(String.valueOf(higthWayFee));
		BigDecimal  bdMidNightFee=new BigDecimal(String.valueOf(midNightFee));
		BigDecimal  bdOutOfRangeFee=new BigDecimal(String.valueOf(outOfRangeFee));
		// no need startFee , because mileageFee has been included startFee
		return bdMileageFee.add(bdLowSpeedFee).add(bdHigthWayFee).add(bdMidNightFee).add(bdOutOfRangeFee).doubleValue();
	}
}