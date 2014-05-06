package com.hibapps.healthfile;

import java.util.ArrayList;

public class Item 
{
	public String category;
	public String name;
	public String description;
	public String time_due;
	public String image;
	public ArrayList<String> rep_images;
	public int id;
	public String phone_no;
	public String email;
	
	public Item(String category,String name,String desc,String time,String image)
	{
		this.category=category;
		this.name=name;
		this.description=desc;
		this.time_due=time;
		this.image=image;
	}
	
	public Item()
	{
		
	}

	public void setImages(ArrayList<String> rep_images)
	{
		this.rep_images = rep_images;
	}
	
	public ArrayList<String> getImages()
	{
		return this.rep_images;
	}
	
	public void setID(int id)
	{
		this.id = id;
	}
	
	public int getID()
	{
		return this.id;
	}
}
