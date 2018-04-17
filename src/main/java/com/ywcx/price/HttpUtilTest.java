package com.ywcx.price;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class HttpUtilTest {
	private static Logger logger = LoggerFactory.getLogger(HttpUtilTest.class);

	// TODO
	private String callUrlGetAsyn(HttpGet httpMethod, List<BasicNameValuePair> urlParams) {
		return "";
	}

	private String callUrlGetSyn(HttpGet httpMethod, List<BasicNameValuePair> urlParams, HttpContext context) {
		String resJson = null;
		URI uri = null;
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(10000)
				.setConnectTimeout(10000).build();
		CloseableHttpClient httpSynclient = HttpClients.custom()
				.setRetryHandler(new DefaultHttpRequestRetryHandler())
				.setDefaultRequestConfig(requestConfig).build();
		try {
			if (null != urlParams) {
				String getUrl = EntityUtils.toString(new UrlEncodedFormEntity(urlParams, "UTF-8"));
				uri = new URI(httpMethod.getURI().toString() + "?" + getUrl);
				httpMethod.setURI(uri);
			}
			HttpResponse response = httpSynclient.execute(httpMethod);
			resJson = getHttpContent(response);
			Integer retryTimes = 3;
			for (int i = 0; i < retryTimes; i++) {
				if (resJson.contains("\"status\":0") || !resJson.contains("status")) {
					break;
				}
				Thread.sleep(2000);
				logger.warn("Havn't received response correctly. retry {} times , response json as below {}",i,resJson );
			}
			logger.info("callUrlGetAsyn {} 请求结束. 结果：{}", uri, resJson);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
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
		return "";
	}

	private String callUrlPostSyn(HttpPost httpMethod, List<BasicNameValuePair> postBodyParams,
			List<BasicNameValuePair> urlParams, HttpContext context) {
		return "";
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
				String urlEncoder = URLEncoder.encode(getUrl, "UTF-8");
				httpMethod.setURI(new URI(httpMethod.getURI().toString() + "?" + urlEncoder));
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
		if (null == url || url.trim().isEmpty()) {
			logger.warn("Url is empty or null , please check if correctly!");
			throw new Exception("Url is empty or null , please check if correctly!");
		}
		CookieStore cookieStore = new BasicCookieStore();
		HttpClientContext localContext = HttpClientContext.create();
		localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

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
			return callUrlPostSyn(httpMethod, postBodyParams, urlParams, localContext);
		} else {
			HttpGet httpMethod = new HttpGet(url);
			if (isAsyn) {
				return callUrlGetAsyn(httpMethod, urlParams);
			}
			return callUrlGetSyn(httpMethod, urlParams, localContext);
		}
	}

	public String getHttpContent(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		String body = null;
		if (entity == null) {
			return null;
		}
		try {
			return body = EntityUtils.toString(entity, "utf-8");
		} catch (ParseException e) {
			logger.warn("the response's content inputstream is corrupt", e);
		} catch (IOException e) {
			logger.warn("the response's content inputstream is corrupt", e);
		}
		return body;
	}

}
