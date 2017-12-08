package com.vert.Service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.vert.Activity.VertActivity;
import com.vert.Calculate.CalculateRate;
import com.vert.Calculate.ParseRate;

public class GetRate {
	
	public static String Country[] = {"CNY", "to", "USD"};
	
	public static void getRateOnline() {
		
		for(int i = 0; i < CalculateRate.codeList.length; i++) {
			for(int j = 0; j < CalculateRate.codeList.length; j++) {
				
				VertActivity.tv.setText("正在更新汇率" + CalculateRate.codeList[i] + "to" + CalculateRate.codeList[j]);
				String urlString = "http://api.k780.com:88/?app=finance.rate&scur="
						+ CalculateRate.codeList[i]
						+ "&tcur="
						+ CalculateRate.codeList[j]
						+ "&appkey=12678&sign=f2423eb012401557225c0ce0490f25a6&format=xml";
				
				String rateInfo = "";
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(urlString);

				try {
					HttpResponse response = httpclient.execute(httppost);
					rateInfo = EntityUtils.toString(response.getEntity());
					Country[0] = CalculateRate.codeList[i];
					Country[2] = CalculateRate.codeList[j];
				ParseRate.parseXML(rateInfo);
//				CalculateRate.getRateFromDB();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		}
	}
}
