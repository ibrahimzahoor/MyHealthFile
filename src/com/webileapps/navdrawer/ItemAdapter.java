package com.webileapps.navdrawer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class ItemAdapter extends  ArrayAdapter<Item>
{
	Context context;
	int layoutResourceid;
	Item[] data=null;
	

	
	public ItemAdapter(Context context, int layoutResourceId, Item[] data) 
	{
		
        super(context, layoutResourceId, data);
        this.layoutResourceid = layoutResourceId;
        this.context = context;
        this.data = data;
        
    }
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) 
	{
		View row;
		if(convertView != null)
		{
			row = convertView;
			row.setBackgroundColor(getContext().getResources().getColor( android.R.color.transparent)); // Default color
			
			
		}
		else
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceid, parent, false);
			
		}
		
		//Log.i("mp","my Position is: "+ data[position].subjectName);
		
		
		
		TextView TV1 = (TextView) row.findViewById(R.id.name);
		TV1.setText( data[position].name );
		
		TextView TV2 = (TextView) row.findViewById(R.id.desc);
		TV2.setText( data[position].description );
		
		TextView TV3 = (TextView) row.findViewById(R.id.time);
		TV3.setText( data[position].time_due );
		
		return row;
	}
	
	

}
