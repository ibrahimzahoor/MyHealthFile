package com.webileapps.navdrawer;



public class Appointment 
{
	public int id;
	public String drName;
	public String date;
	public String time;
	public String location;
	public String latitude;
	public String longitude;
	
	public Appointment(int id,String drName,String date,String time,String location,String latitude,String longitude)
	{
		this.id=id;
		this.drName=drName;
		this.date=date;
		this.time=time;
		this.location=location;
		this.latitude=latitude;
		this.longitude=longitude;
	}
	
	public Appointment()
	{
		
	}

}