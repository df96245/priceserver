package com.ywcx.price.entity.json;

import java.util.Comparator;

public class Result implements Comparator<Result>{
	private Distance distance;
	private Duration duration;
	public Distance getDistance() {
		return distance;
	}
	public void setDistance(Distance distance) {
		this.distance = distance;
	}
	public Duration getDuration() {
		return duration;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	@Override
	public String toString() {
		return "Result [duration=" + duration + ", distance=" + distance + "]";
	}
	public int compare(Result o1, Result o2) {
			int key1 = o1.getDuration().getValue();
			int key2 = o2.getDuration().getValue();
			return key1>=key2 ? 1:-1;
	}
	
}

