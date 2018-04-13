package com.ywcx.price.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.ywcx.price.bean.PriceBase;
import com.ywcx.price.bean.User;
import com.ywcx.price.service.UserService;
import com.ywcx.price.service.reporstory.PriceBaseService;

@ContextConfiguration(locations = { "classpath:spring/dispatcher-servlet.xml",
		"classpath:spring/applicationContext.xml" })
public class PriceBaseDaoTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	private PriceBaseService service;

	@Test
	public void getAllPriceBase() {
		List<PriceBase> list=service.getAllPriceBase();
		for (PriceBase priceBase : list) {
			System.out.println("==="+priceBase);
		}
	}
	
	@Test
	public void getAllSelfPriceBase() {
		List<PriceBase> list=service.getAllPriceBaseByType("SELF");
		for (PriceBase priceBase : list) {
			System.out.println("==="+priceBase);
		}
	}
	
	@Test
	public void getAllManyPriceBase() {
		List<PriceBase> list=service.getAllPriceBaseByType("MANY");
		for (PriceBase priceBase : list) {
			System.out.println("==="+priceBase);
		}
	}
	
	@Test
	public void addPriceBase() {
		PriceBase priceBase= new PriceBase();
		priceBase.setCity("SANYA");
		priceBase.setCity_desc("海南省三亚市");
		priceBase.setFeePreKm(3.5);
		priceBase.setLowSpeedFeePreKm(2.2);
		priceBase.setMidnightFeePreKm(2.5);
		priceBase.setOutOfRangeFeePreKm(3.2);
		priceBase.setStartFee(11.0);
		priceBase.setStartKm(3.0);
		if (service.addPriceBase(priceBase)) {
			System.out.println(" 添加成功");
		}else {
			System.out.println(" 添加失败");
		}
	}

}
