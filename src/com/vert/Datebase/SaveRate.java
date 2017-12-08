package com.vert.Datebase;

public class SaveRate {
	
	public int ID = -1;
	public String Country = null;
	public Double Rate = null;
	public String Day = null;
	public String Time = null;
	
	@Override
	public String toString(){
		String result = "";
		result += "币种：" + this.Country + "，";
		result += "汇率：" + this.Rate + "， ";
		result += "日期：" + this.Day + "，";
		result += "时间：" + this.Time;
		return result;
	}

}
