package com.laurenelder.aquapharm;

public class Weather {

	public String location;
	public String day;
	public String month;
	public String year;
	public String lowTemp;
	public String highTemp;
	
	public Weather(String thisLocation, String thisDay, String thisMonth, String thisYear, 
			String thisLowTemp, String thisHighTemp) {
		this.location = thisLocation;
		this.day = thisDay;
		this.month = thisMonth;
		this.year = thisYear;
		this.lowTemp = thisLowTemp;
		this.highTemp = thisHighTemp;
	}
	
	public String toString() {
		return location + ": " + month + "/" + day + "/" + year;
	}
	
}
