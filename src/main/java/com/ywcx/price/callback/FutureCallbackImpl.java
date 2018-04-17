package com.ywcx.price.callback;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FutureCallbackImpl implements FutureCallback<HttpResponse> {
	private static Logger LOG = LoggerFactory.getLogger(FutureCallbackImpl.class);
	private StringBuilder builder = new StringBuilder();
	private CountDownLatch latch;

	/**
	 * 请求完成后调用该函数
	 */
	public void completed(HttpResponse response) {
		latch.countDown();
	}
	
	public FutureCallbackImpl(CountDownLatch latch) {
		this.latch=latch;
	}

	/**
	 * 请求取消后调用该函数
	 */
	public void cancelled() {

	}

	/**
	 * 请求失败后调用该函数
	 */
	public void failed(Exception e) {

	}

	public String getCallbackResult() {
		return builder.toString();
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	protected String getHttpContent(HttpResponse response) {

		HttpEntity entity = response.getEntity();
		String body = null;

		if (entity == null) {
			return null;
		}

		try {

			body = EntityUtils.toString(entity, "utf-8");

		} catch (ParseException e) {

			LOG.warn("the response's content inputstream is corrupt", e);
		} catch (IOException e) {

			LOG.warn("the response's content inputstream is corrupt", e);
		}
		return body;
	}

}