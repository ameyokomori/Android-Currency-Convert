package com.vert.Calculate;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.vert.Datebase.DBAdapter;
import com.vert.Datebase.SaveRate;
import com.vert.Service.GetRate;


public class ParseRate {
	
	static String s1 = "";
	static String s2 = "";

	
	public static void parseXML(String xmlString) {
		// TODO Auto-generated method stub
		CalculateRate.rate1 = 0.0;
		try {
			XmlPullParserFactory factory;
			factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new ByteArrayInputStream(xmlString.getBytes()),
					"utf-8");
			int eventType = parser.getEventType();
			String tagName;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					tagName = parser.getName();
					if (tagName.equalsIgnoreCase("rate")) {
						String text = parser.nextText();
						CalculateRate.rate1 = Double.parseDouble(text);
					} else if (tagName.equalsIgnoreCase("update")) {
						String text = parser.nextText();
						CalculateRate.updateDate = text.toString();
						s1 = text.substring(0, 10);
						s2 = text.substring(11);
					}
					insertRate();					
					break;
				case XmlPullParser.END_TAG:
					break;
				default:
					break;
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void parseXML2(String xmlString) {
		// TODO Auto-generated method stub
		CalculateRate.rate2 = 0.0;
		try {
			XmlPullParserFactory factory;
			factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new ByteArrayInputStream(xmlString.getBytes()),
					"utf-8");
			int eventType = parser.getEventType();
			String tagName;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					tagName = parser.getName();
					if (tagName.equalsIgnoreCase("rate")) {
						String text = parser.nextText();
						CalculateRate.rate2 = Double.parseDouble(text);
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				default:
					break;
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void insertRate() {	
		
		SaveRate saveRate = new SaveRate();
		saveRate.Country = GetRate.Country[0] + GetRate.Country[1] + GetRate.Country[2];
		saveRate.Rate = CalculateRate.rate1;
		saveRate.Day = s1;
		saveRate.Time = s2;
		
		if(saveRate.Country.equals(null) || saveRate.Rate.equals(null) || saveRate.Day.equals(null) || saveRate.Time.equals(null))
			return;
		else
			DBAdapter.insert(saveRate);
		
		
	}

}
