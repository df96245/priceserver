package com.ywcx.price.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ywcx.price.service.TrackService;
@Controller
@RequestMapping("/track")
public class TrackController {
	@Autowired
	private TrackService service;
	
	@RequestMapping(value="/addPoint",method = RequestMethod.POST)
	@ResponseBody
	public String addPoint(@RequestParam (value="entityName",required=true) String entityName,
			@RequestParam (value="lat",required=true) String lat,
			@RequestParam (value="lon",required=true) String lon) {
		String result=service.addPoint(entityName, lat, lon);
		return result;
	}
	
	@RequestMapping(value="/getDistance",method = RequestMethod.GET)
	@ResponseBody
	public String getDistance(@RequestParam (value="entityName",required=true) String entityName,
			@RequestParam (value="start_time",required=true) String start_time,
			@RequestParam (value="end_time",required=true) String end_time) {
		String result=service.getDistance(entityName, start_time, end_time);
		return result;
	}
	
	@RequestMapping(value="/getPrice",method = RequestMethod.GET)
	@ResponseBody
	public Double getPrice(
			@RequestParam (value="city",required=true) String city,
			@RequestParam (value="entityName",required=true) String entityName,
			@RequestParam (value="start_time",required=true) String start_time,
			@RequestParam (value="end_time",required=true) String end_time) {
		Double result=service.getPrice(city,entityName, start_time, end_time);
		return result;
	}
	
	
}
