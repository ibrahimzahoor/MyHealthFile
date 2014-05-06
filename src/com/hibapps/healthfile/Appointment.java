package com.hibapps.healthfile;



public class Appointment 
{
	public int id;
	public String drName;
	public String date;
	public String time;
	public String location;
	public String phone_no;
	public String email;
	
	public Appointment(int id,String drName,String date,String time,String location,String phone_no,String email)
	{
		this.id=id;
		this.drName=drName;
		this.date=date;
		this.time=time;
		this.location=location;
		this.phone_no=phone_no;
		this.email=email;
	}
	
	public Appointment()
	{
		
	}

}