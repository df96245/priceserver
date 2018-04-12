package com.ywcx.price.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelComeController {
	@RequestMapping("/test")
	@ResponseBody
	public HashMap homePage() {
		HashMap s = new HashMap();
		s.put("aaa", "你好啊");
		return s;
	}
}
