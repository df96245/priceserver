package com.ywcx.price.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ywcx.price.bean.PriceBase;

public interface PriceBaseDao extends JpaRepository<PriceBase, Integer> {
	List<PriceBase> findByType(String type);
}
