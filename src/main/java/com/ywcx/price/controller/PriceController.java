package com.ywcx.price.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ywcx.price.common.ServerResponse;
import com.ywcx.price.service.PriceService;
@RestController
@RequestMapping("/price")
public class PriceController {
	@Autowired
	private PriceService service;
	
	@RequestMapping(value="/getRTPrice",method = RequestMethod.GET)
	public ServerResponse getRTPrice(
			@RequestParam (value="user_type",required=true) String type,
			@RequestParam (value="city",required=true) String city,
			@RequestParam (value="entityName",required=true) String entityName,
			@RequestParam (value="start_time",required=true) String start_time) throws Exception {
		return service.getRealTimePrice(type,city,entityName, start_time, null);
//		return result;
	}
	
	@RequestMapping(value="/getFinalPrice",method = RequestMethod.GET)
	public ServerResponse getFinalPrice(
			@RequestParam (value="user_type",required=true) String type,
			@RequestParam (value="city",required=true) String city,
			@RequestParam (value="entityName",required=true) String entityName,
			@RequestParam (value="start_time",required=true) String start_time,
			@RequestParam (value="end_time",required=true) String end_time) throws Exception {
		return service.getRealTimePrice(type,city,entityName, start_time, end_time);
	}
	
	
	@RequestMapping(value="/getEstPrice",method = RequestMethod.GET)
	public ServerResponse getEstPrice(
			@RequestParam (value="oriLatLng",required=true) String oriLatLng,
			@RequestParam (value="destLatLng",required=true) String destLatLng,
			@RequestParam (value="city",required=true) String city
			) throws Exception {
		return service.getEstPrice(oriLatLng, destLatLng,city);
	}
	
	
}
