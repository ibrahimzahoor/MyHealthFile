package com.webileapps.navdrawer;

public class Medicine 
{
	public int medicine_id;
 	public String name_of_medicine;
 	public String medicine_picture;
 	public String medicine_description;
 	public boolean medicine_is_monday;
 	public boolean medicine_is_tuesday;
 	public boolean medicine_is_wednesday;
 	public boolean medicine_is_thursday;
 	public boolean medicine_is_friday;
 	public boolean medicine_is_saturday;
 	public boolean medicine_is_sunday;
 	public String medicine_start_time;
 	public String medicine_delay;
 	public boolean medicine_is_custom;
 	public String medicine_alarm_time;
 	
 	public Medicine(int medicine_id,String name_of_medicine,String medicine_picture,String medicine_description,
 			boolean medicine_is_monday,boolean medicine_is_tuesday,boolean medicine_is_wednesday,boolean medicine_is_thursday,
 			boolean medicine_is_friday,boolean medicine_is_saturday,boolean medicine_is_sunday,String medicine_start_time,
 			String medicine_delay,boolean medicine_is_custom,String medicine_alarm_time)
 	{
 		this.medicine_id=medicine_id;
 		this.name_of_medicine=name_of_medicine;
 		this.medicine_picture=medicine_picture;
 		this.medicine_description=medicine_description;
 		this.medicine_is_friday=medicine_is_friday;
 		this.medicine_is_saturday=medicine_is_saturday;
 		this.medicine_is_sunday=medicine_is_sunday;
 		this.medicine_is_monday=medicine_is_monday;
 		this.medicine_is_tuesday=medicine_is_tuesday;
 		this.medicine_is_wednesday=medicine_is_wednesday;
 		this.medicine_is_thursday=medicine_is_thursday;
 		this.medicine_start_time=medicine_start_time;
 		this.medicine_delay=medicine_delay;
 		this.medicine_is_custom=medicine_is_custom;
 		this.medicine_alarm_time=medicine_alarm_time;
 		
 	}
 	
 	
 	
 	

}
