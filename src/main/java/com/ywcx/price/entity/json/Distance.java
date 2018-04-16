package com.ywcx.price.entity.json;
public class Distance{
	private String text;
	private Double value;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Distance [text=" + text + ", value=" + value + "]";
	}
	
	
}