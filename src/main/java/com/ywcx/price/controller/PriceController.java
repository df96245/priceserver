package com.ywcx.price.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ywcx.price.service.PriceService;
@Controller
@RequestMapping("/price")
public class PriceController {
	@Autowired
	private PriceService service;
	
	@RequestMapping(value="/getRTPrice",method = RequestMethod.GET)
	@ResponseBody
	public Double getPrice(
			@RequestParam (value="user_type",required=true) String type,
			@RequestParam (value="city",required=true) String city,
			@RequestParam (value="entityName",required=true) String entityName,
			@RequestParam (value="start_time",required=true) String start_time,
			@RequestParam (value="end_time",required=true) String end_time) throws Exception {
		Double result=service.getRealTimePrice(type,city,entityName, start_time, end_time);
		return result;
	}
	
	@RequestMapping(value="/getEstPriceByAddress",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Double> getEstPriceByAddress(
			@RequestParam (value="originAdr",required=true) String oriAdr,
			@RequestParam (value="destAdr",required=true) String destAdr,
			@RequestParam (value="city",required=true) String city
			) throws Exception {
		Map<String, Double> priceMap=service.getEstPrice(oriAdr, destAdr,city);
		return priceMap;
	}
	
	@RequestMapping(value="/getEstPrice",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Double> getEstPrice(
			@RequestParam (value="oriLatLng",required=true) String oriLatLng,
			@RequestParam (value="destLatLng",required=true) String destLatLng,
			@RequestParam (value="city",required=true) String city
			) throws Exception {
		Map<String, Double> priceMap=service.getEstPrice(oriLatLng, destLatLng,city);
		return priceMap;
	}
	
	
}
