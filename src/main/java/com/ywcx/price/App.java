package com.ywcx.price;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ywcx.price.test.MapDistanceUtil;
import com.ywcx.price.test.Point;
import com.ywcx.price.test.RandomUtil;
import com.ywcx.price.util.HttpUtil;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String... strings) throws Exception {
		double lon = 110.2932342978026;
		double lat = 20.013426508350145;
		Point pointMid = new Point(lon, lat);
		HashMap<String, Point> map = MapDistanceUtil.findNeighPosition(pointMid);
		Point maxPoint = map.get("MAX");
		Point minPoint = map.get("MIN");
			Point oriPoint = RandomUtil.randomLonLat(maxPoint, minPoint);
			String oriLatLng=String.valueOf(oriPoint.getLatitude())+","+oriPoint.getLongitude();
			Point destPonit = RandomUtil.randomLonLat(maxPoint, minPoint);
			String destLatLng=String.valueOf(destPonit.getLatitude())+","+destPonit.getLongitude();
			List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
			pairList.add(new BasicNameValuePair("city", "HAIKOU"));
			pairList.add(new BasicNameValuePair("oriLatLng", oriLatLng));
			pairList.add(new BasicNameValuePair("destLatLng", destLatLng));
			String url = "http://localhost:8080/priceserver/price/getEstPrice";
			new T(url, pairList).start();
	}
}

class T extends Thread {
	private static Logger logger = LoggerFactory.getLogger(T.class);

	String url;
	List<BasicNameValuePair> pairList;

	public T(String url, List<BasicNameValuePair> pairList) {
		super();
		this.url = url;
		this.pairList = pairList;
	}

	@Override
	public void run() {
		try {
				String result = new HttpUtilTest().getConfCall(url, pairList, false, false, false);
				logger.info("ori:{} dest:{} result:{}",pairList.get(1).getValue(),pairList.get(2).getValue() ,result);
//			Thread.sleep(new Random().nextInt(5000));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
