package com.ywcx.price.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ywcx.price.config.BaiduConf;
import com.ywcx.price.util.HttpUtil;

public abstract class BaiduAbstractService {
	@Autowired
	protected HttpUtil instance;

	@Autowired
	protected BaiduConf bdConfig;
}
