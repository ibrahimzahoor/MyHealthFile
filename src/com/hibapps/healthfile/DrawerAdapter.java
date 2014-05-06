package com.hibapps.healthfile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hibapps.healthfile.R;


public class DrawerAdapter extends  ArrayAdapter<DrawerItem>
{
	Context context;
	int LayoutResourceID;
	DrawerItem data[]=null;
	
	public DrawerAdapter(Context context, int resource, DrawerItem[] objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.LayoutResourceID=resource;
		this.data=objects;
	}
	
	
	
	@Override
	public View getView(int position, View ConvertView, ViewGroup parent ){
		
		View row = null;
		
		if(ConvertView==null)
		{
			LayoutInflater inflater=((Activity)context).getLayoutInflater();
			row=inflater.inflate(LayoutResourceID, parent, false);
		}
		else
		{
			row=(View)ConvertView;
		}
		
		TextView title1 = (TextView) row.findViewById(R.id.textView1);
		title1.setText(data[position].title.toString());
		
		ImageView img = (ImageView) row.findViewById(R.id.imageView1);
		img.setImageResource(data[position].image);
		
		
		return row;
		
	}
}
