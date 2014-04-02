package com.webileapps.navdrawer;

public class Item 
{
	public String name;
	public String description;
	public String time_due;
	public String image;
	
	public Item(String name,String desc,String time,String image)
	{
		this.name=name;
		this.description=desc;
		this.time_due=time;
		this.image=image;
	}

}
