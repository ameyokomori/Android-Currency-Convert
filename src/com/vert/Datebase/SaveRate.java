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
		result += "���֣�" + this.Country + "��";
		result += "���ʣ�" + this.Rate + "�� ";
		result += "���ڣ�" + this.Day + "��";
		result += "ʱ�䣺" + this.Time;
		return result;
	}

}
