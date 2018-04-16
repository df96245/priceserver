package com.ywcx.price.entity.json;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BaiduResponse<T> {
	private int status;
	private List<T> result;
	private String message;
	private Double distance;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	@Override
	public String toString() {
		return "BaiduResponse [status=" + status + ", result=" + result + ", message=" + message + ", distance="
				+ distance + "]";
	}
	
	
}
