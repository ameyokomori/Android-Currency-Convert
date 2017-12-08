package com.vert.Calculate;

import java.text.DecimalFormat;

import com.vert.Activity.VertActivity;
import com.vert.Datebase.DBAdapter;
import com.vert.Datebase.SaveRate;

public class CalculateRate {
	
	public static Double rate1 = 0.0;
	public static Double rate2 = 0.0;
	public static DecimalFormat df2 = new DecimalFormat("##0.00");
	public static String country1 = "";
	public static String country2 = "";
	public static String codeList[] = { "CNY", "USD", "JPY", "EUR", "HKD", "CAD", "AUD" };
	public static String codePresent[] = { "CNY", "USD", "JPY", "EUR", "HKD", "CAD", "AUD" };
	public static String code1 = "CNY";
	public static String code2 = "USD";
	public static String updateDate = "";
	public static String Day = "";
	public static String Time = "";
	
	public static void calculateMoney() {
		
		double output = 0;
		String input = VertActivity.money1.getText().toString();
		if (input != null && input.length() > 0) {
			output = Double.parseDouble(input) * rate1;
			VertActivity.money2.setText(df2.format(output));
			//return true;
		} else {
			VertActivity.money2.setText("0");
			//return false;
		}		
	}
	
	public static void calculateMoney2() {
		
		double output = 0;
		String input = VertActivity.money2.getText().toString();
		if (input != null && input.length() > 0) {
			output = Double.parseDouble(input) * rate2;
			VertActivity.money1.setText(df2.format(output));
			//return true;
		} else {
			VertActivity.money1.setText("0");
			//return false;
		}		
	}
	
	public static void getRateFromDB() {
		rate1 = 0.0;
		rate2 = 0.0;
		
		String country = code1 + "to" + code2;
		SaveRate[] getRate = DBAdapter.queryOneData(country);
		if(getRate == null) {
			VertActivity.tv.setText("无本地汇率");
			return;
		}
		rate1 = getRate[0].Rate;
		Day = getRate[0].Day;
		Time = getRate[0].Time;
		
		country = code2 + "to" + code1;
		SaveRate[] getRate2 = DBAdapter.queryOneData(country);
		if(getRate2 == null) {
			rate2 = 1 / rate1;
		}else {
			rate2 = getRate2[0].Rate;
		}
		
		VertActivity.tv.setText("1" + country1 + " = " + rate1.toString() + country2
				+ "，更新于" + Day + " " + Time);
		
	}

}
