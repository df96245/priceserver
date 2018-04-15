package com.ywcx.price.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpConf {
	@Value("#{configProperties['socket_timeout']}")
	private Integer socketTimeout;
	@Value("#{configProperties['connect_timeout']}")
	private Integer connectTimeout;
	@Value("#{configProperties['retry_times']}")
	private Integer retryTimes;
	@Value("#{configProperties['retry_interval']}")
	private Integer retryInterval;

	public Integer getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(Integer socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(Integer retryTimes) {
		this.retryTimes = retryTimes;
	}

	public Integer getRetryInterval() {
		return retryInterval;
	}

	public void setRetryInterval(Integer retryInterval) {
		this.retryInterval = retryInterval;
	}

}
