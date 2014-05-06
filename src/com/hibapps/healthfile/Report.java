package com.hibapps.healthfile;

import java.util.ArrayList;

public class Report 
{
	public int id;
	public String title;
	public String desciption;
	ArrayList<String> images;
	
	public Report(int id,String title,String desc,ArrayList<String> imgs)
	{
		this.id=id;
		this.desciption=desc;
		this.title=title;
		this.images=imgs;
	}
	
	public Report()
	{
		
	}

}
