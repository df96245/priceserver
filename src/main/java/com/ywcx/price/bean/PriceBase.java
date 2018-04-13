package com.ywcx.price.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="price_base")
public class PriceBase {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String city;
	private String city_desc;
	private Double startKm;
	private Double startFee;
	private Double feePreKm;
	private Double lowSpeedFeePreKm;
	private Double outOfRangeFeePreKm;
	private Double midnightFeePreKm;
	private String type;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCity_desc() {
		return city_desc;
	}
	public void setCity_desc(String city_desc) {
		this.city_desc = city_desc;
	}
	public Double getStartFee() {
		return startFee;
	}
	public void setStartFee(Double startFee) {
		this.startFee = startFee;
	}
	public Double getFeePreKm() {
		return feePreKm;
	}
	public void setFeePreKm(Double feePreKm) {
		this.feePreKm = feePreKm;
	}
	public Double getLowSpeedFeePreKm() {
		return lowSpeedFeePreKm;
	}
	public void setLowSpeedFeePreKm(Double lowSpeedFeePreKm) {
		this.lowSpeedFeePreKm = lowSpeedFeePreKm;
	}
	public Double getOutOfRangeFeePreKm() {
		return outOfRangeFeePreKm;
	}
	public void setOutOfRangeFeePreKm(Double outOfRangeFeePreKm) {
		this.outOfRangeFeePreKm = outOfRangeFeePreKm;
	}
	public Double getMidnightFeePreKm() {
		return midnightFeePreKm;
	}
	public void setMidnightFeePreKm(Double midnightFeePreKm) {
		this.midnightFeePreKm = midnightFeePreKm;
	}
	public Double getStartKm() {
		return startKm;
	}
	public void setStartKm(Double startKm) {
		this.startKm = startKm;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "PriceBase [id=" + id + ", city=" + city + ", city_desc=" + city_desc + ", startKm=" + startKm
				+ ", startFee=" + startFee + ", feePreKm=" + feePreKm + ", lowSpeedFeePreKm=" + lowSpeedFeePreKm
				+ ", outOfRangeFeePreKm=" + outOfRangeFeePreKm + ", midnightFeePreKm=" + midnightFeePreKm + ", type="
				+ type + "]";
	}
	
}

