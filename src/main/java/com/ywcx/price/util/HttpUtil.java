package com.ywcx.price.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ywcx.price.callback.FutureCallbackImpl;

@Component
public class HttpUtil {
	private static Logger logger = Logger.getLogger(HttpUtil.class);

	// TODO
	private String callUrlGetAsyn(HttpGet httpMethod, List<BasicNameValuePair> urlParams) {
		String resJson = null;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig).build();
		try {
			httpclient.start();
			final CountDownLatch latch = new CountDownLatch(1);
			buildUrlParams(httpMethod, urlParams);
			Future<HttpResponse> future = httpclient.execute(httpMethod, new FutureCallbackImpl(latch));
			latch.await();
			HttpResponse response = future.get();
			resJson = getHttpContent(response);
			System.out.println("Shutting down");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resJson;
	}

	private String callUrlGetSyn(HttpGet httpMethod, List<BasicNameValuePair> urlParams) {
		String resJson = null;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		CloseableHttpClient httpSynclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		try {
			buildUrlParams(httpMethod, urlParams);
			HttpResponse response = httpSynclient.execute(httpMethod);
			resJson = getHttpContent(response);
			System.out.println("Shutting down");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpSynclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resJson;
	}

	// TODO 提出参数
	private String callUrlPostAsyn(HttpPost httpMethod, List<BasicNameValuePair> postBodyParams,
			List<BasicNameValuePair> urlParams) {
		String resJson = null;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		CloseableHttpAsyncClient httpAsynclient = HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig)
				.build();
		try {
			if (null != postBodyParams) {
				logger.debug("exeAsyncReq post postBody={}" + postBodyParams);
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postBodyParams, "UTF-8");
				httpMethod.setEntity(entity);
			}

			buildUrlParams(httpMethod, urlParams);
			httpAsynclient.start();

			final CountDownLatch latch = new CountDownLatch(1);
			Future<HttpResponse> future = httpAsynclient.execute(httpMethod, new FutureCallbackImpl(latch));
			latch.await();
			HttpResponse response = future.get();
			resJson = getHttpContent(response);

			System.out.println("Shutting down");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpAsynclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resJson;
	}

	private String callUrlPostSyn(HttpPost httpMethod, List<BasicNameValuePair> postBodyParams,
			List<BasicNameValuePair> urlParams) {
		String resJson = null;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		CloseableHttpClient httpSynclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		try {
			if (null != postBodyParams) {
				logger.debug("exeAsyncReq post postBody={}" + postBodyParams);
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postBodyParams, "UTF-8");
				httpMethod.setEntity(entity);
			}

			buildUrlParams(httpMethod, urlParams);
			HttpResponse response = httpSynclient.execute(httpMethod);

			resJson = getHttpContent(response);

			System.out.println("Shutting down");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpSynclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resJson;
	}

	public static List<BasicNameValuePair> map2BasicNVPair(Map<String, String> KVParams) {
		List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
		for (String key : KVParams.keySet()) {
			pairList.add(new BasicNameValuePair(key, KVParams.get(key)));
		}
		return pairList;
	}

	private void buildUrlParams(HttpRequestBase httpMethod, List<BasicNameValuePair> urlParams) {
		if (null != urlParams) {
			try {
				String getUrl = EntityUtils.toString(new UrlEncodedFormEntity(urlParams));
				httpMethod.setURI(new URI(httpMethod.getURI().toString() + "?" + getUrl));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getConfCall(String url, List<BasicNameValuePair> urlParams, boolean isPost, boolean isPostBody,
			boolean isAsyn) throws Exception {
		String resultJson = null;
		if (null == url || url.trim().isEmpty()) {
			logger.warn("Url is empty or null , please check if correctly!");
			throw new Exception("Url is empty or null , please check if correctly!");
		}
		if (isPost) {
			List<BasicNameValuePair> postBodyParams = new ArrayList<BasicNameValuePair>();
			HttpPost httpMethod = new HttpPost(url);
			if (isPostBody) {
				postBodyParams = urlParams;
				urlParams = null;
			}
			if (isAsyn) {
				return callUrlPostAsyn(httpMethod, postBodyParams, urlParams);
			}
			return callUrlPostSyn(httpMethod, postBodyParams, urlParams);
		} else {
			HttpGet httpMethod = new HttpGet(url);
			if (isAsyn) {
				return callUrlGetAsyn(httpMethod, urlParams);
			}
			return callUrlGetSyn(httpMethod, urlParams);
		}
	}

	public String getHttpContent(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		String body = null;
		if (entity == null) {
			return null;
		}
		try {
			body = EntityUtils.toString(entity, "utf-8");
		} catch (ParseException e) {
			logger.warn("the response's content inputstream is corrupt", e);
		} catch (IOException e) {
			logger.warn("the response's content inputstream is corrupt", e);
		}
		return body;
	}

}
