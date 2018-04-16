package com.ywcx.price.service.reporstory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ywcx.price.dao.PriceBaseDao;
import com.ywcx.price.entity.PriceBase;

@Service
@Transactional
public class PriceBaseService {

	@Autowired
	private PriceBaseDao dao;
	
	public List<PriceBase> getAllPriceBase(){
		return dao.findAll();
	}
	
	public boolean addPriceBase(PriceBase priceBase) {
		PriceBase pricebase=dao.save(priceBase);
		return  null!=pricebase? true: false;
	}
	
	public List<PriceBase> getAllPriceBaseByType(String type){
		return dao.findByType(type.toUpperCase());
	}
}
