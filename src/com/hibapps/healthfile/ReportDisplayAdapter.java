	package com.hibapps.healthfile;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

	
	public class ReportDisplayAdapter extends  ArrayAdapter<String>
	{
		Context context;
		int layoutResourceid;
		String[] data=null;

		public ReportDisplayAdapter(Context context, int layoutResourceId, String[] data) 
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
			}
			else
			{
				LayoutInflater inflater = ((Activity)context).getLayoutInflater();
				row = inflater.inflate(layoutResourceid, parent, false);
			}
			
			ImageView IV = (ImageView) row.findViewById(R.id.imageView1);
			IV.setImageBitmap(BitmapFactory.decodeFile(data[position]));
			
			return row;
		}
}